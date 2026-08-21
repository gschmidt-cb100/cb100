package ar.uba.fi.cb100.librerias.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Lectura y escritura de planillas de Excel, con la misma forma de uso que
 * {@link java.nio.file.Files}: m&eacute;todos est&aacute;ticos que reciben un
 * {@link Path} y trabajan con datos planos. Apache POI queda adentro; quien
 * usa esta clase no necesita conocerlo.
 *
 * <p><b>Escribir:</b>
 * <pre>{@code
 * String[] encabezados = {"Padron", "Socio", "Multa"};
 * Object[][] filas = {
 *         {39876, "Bruno Ferrari", 3600},
 *         {42001, "Carla Nunez", 0},
 * };
 * ExcelUtils.escribir(Path.of("reporte.xlsx"), "Multas", encabezados, filas);
 * }</pre>
 *
 * <p><b>Leer:</b>
 * <pre>{@code
 * String[][] datos = ExcelUtils.leer(Path.of("reporte.xlsx"));
 * // datos[0] es la fila de encabezados; datos[1] en adelante, los datos.
 * }</pre>
 *
 * <h2>Por qu&eacute; se escribe con tipos y se lee como texto</h2>
 *
 * Al <b>escribir</b>, cada valor llega a Excel con su tipo real: un
 * {@code Integer} queda como n&uacute;mero (Excel lo suma y lo ordena) y un
 * {@link LocalDate} queda como fecha con formato {@code dd/mm/aaaa}. Si todo se
 * guardara como texto, la planilla no servir&iacute;a para calcular, que es la
 * &uacute;nica razon para generar un {@code .xlsx} en lugar de un {@code .csv}.
 *
 * <p>Al <b>leer</b>, en cambio, siempre se devuelve texto: es lo mismo que
 * entrega {@link java.nio.file.Files#readAllLines}, y se parsea con
 * {@code Integer.parseInt} o {@code LocalDate.parse} como cualquier archivo.
 *
 * <p>La conversi&oacute;n a texto evita dos trampas cl&aacute;sicas de POI, que
 * devuelve todos los n&uacute;meros como {@code double}:
 * <ul>
 *   <li>una celda con {@code 3600} se lee {@code "3600"}, y no {@code "3600.0"};</li>
 *   <li>un ISBN como {@code 9789871234567} se lee entero, y no en notaci&oacute;n
 *       cient&iacute;fica ({@code "9.789871234567E12"}).</li>
 * </ul>
 *
 * <p>Las fechas se devuelven en formato ISO ({@code "2026-05-04"}), que es el
 * que {@link LocalDate#parse} entiende sin configurar nada.
 */
public final class ExcelUtils {

    /**
     * Tope de columnas a las que se les ajusta el ancho autom&aacute;ticamente.
     * El autoajuste mide el texto de cada celda, as&iacute; que en planillas muy
     * anchas se vuelve costoso.
     */
    private static final int MAXIMO_DE_COLUMNAS_AUTOAJUSTADAS = 64;

    /** Formato con el que se muestran las fechas en la planilla generada. */
    private static final String FORMATO_DE_FECHA = "dd/mm/yyyy";

    /** Clase de utilidades: no se instancia. */
    private ExcelUtils() {
    }

    // ==================================================================
    //  Escritura
    // ==================================================================

    /**
     * Escribe una planilla de una sola hoja, con los encabezados en negrita y
     * las columnas autoajustadas. Crea los directorios que falten y pisa el
     * archivo si ya exist&iacute;a.
     *
     * @param destino     ruta del {@code .xlsx} a generar
     * @param nombreHoja  nombre de la solapa
     * @param encabezados t&iacute;tulos de las columnas
     * @param filas       una fila por elemento; cada una con tantos valores
     *                    como encabezados
     * @throws IOException              si falla la escritura del archivo
     * @throws IllegalArgumentException si los datos no son consistentes
     */
    public static void escribir(Path destino, String nombreHoja,
                                String[] encabezados, Object[][] filas) throws IOException {
        escribir(destino, new Hoja(nombreHoja, encabezados, filas));
    }

    /**
     * Escribe una planilla con varias hojas, en el orden en que se pasan.
     *
     * @param destino ruta del {@code .xlsx} a generar
     * @param hojas   las hojas a escribir; al menos una
     * @throws IOException              si falla la escritura del archivo
     * @throws IllegalArgumentException si no hay hojas o hay nombres repetidos
     */
    public static void escribir(Path destino, Hoja... hojas) throws IOException {
        Objects.requireNonNull(destino, "el destino no puede ser null");
        Objects.requireNonNull(hojas, "las hojas no pueden ser null");
        if (hojas.length == 0) {
            throw new IllegalArgumentException("hay que escribir al menos una hoja");
        }
        verificarQueNoHayaNombresRepetidos(hojas);

        try (Workbook libro = new XSSFWorkbook()) {
            CellStyle negrita = crearEstiloNegrita(libro);
            CellStyle fecha = crearEstiloFecha(libro);

            for (Hoja hoja : hojas) {
                escribirHoja(libro, hoja, negrita, fecha);
            }

            Path directorio = destino.getParent();
            if (directorio != null) {
                Files.createDirectories(directorio);
            }
            try (OutputStream salida = Files.newOutputStream(destino)) {
                libro.write(salida);
            }
        }
    }

    // ==================================================================
    //  Lectura
    // ==================================================================

    /**
     * Lee la primera hoja de la planilla y la devuelve como una grilla de
     * texto. La fila 0 es la de encabezados. Las celdas vac&iacute;as se
     * devuelven como cadena vac&iacute;a, nunca {@code null}, y todas las filas
     * tienen la misma cantidad de columnas.
     *
     * <p>Acepta tanto {@code .xlsx} como el formato viejo {@code .xls}.
     *
     * @param origen ruta de la planilla a leer
     * @return la grilla de la primera hoja; vac&iacute;a si la hoja no tiene datos
     * @throws IOException           si falla la lectura
     * @throws NoSuchFileException   si el archivo no existe
     */
    public static String[][] leer(Path origen) throws IOException {
        Objects.requireNonNull(origen, "el origen no puede ser null");
        verificarQueExista(origen);
        try (InputStream entrada = Files.newInputStream(origen);
             Workbook libro = WorkbookFactory.create(entrada)) {
            if (libro.getNumberOfSheets() == 0) {
                return new String[0][];
            }
            return leerHoja(libro.getSheetAt(0));
        }
    }

    /**
     * Lee una hoja puntual, buscada por nombre.
     *
     * @param origen     ruta de la planilla a leer
     * @param nombreHoja nombre exacto de la solapa
     * @return la grilla de esa hoja
     * @throws IOException              si falla la lectura
     * @throws IllegalArgumentException si el libro no tiene una hoja con ese nombre
     */
    public static String[][] leer(Path origen, String nombreHoja) throws IOException {
        Objects.requireNonNull(origen, "el origen no puede ser null");
        Objects.requireNonNull(nombreHoja, "el nombre de la hoja no puede ser null");
        verificarQueExista(origen);
        try (InputStream entrada = Files.newInputStream(origen);
             Workbook libro = WorkbookFactory.create(entrada)) {
            Sheet hoja = libro.getSheet(nombreHoja);
            if (hoja == null) {
                throw new IllegalArgumentException(
                        "el archivo " + origen.getFileName() + " no tiene una hoja llamada \""
                                + nombreHoja + "\". Las que tiene son: "
                                + String.join(", ", nombresDeLasHojas(libro)));
            }
            return leerHoja(hoja);
        }
    }

    /**
     * Devuelve los nombres de las hojas del libro, en orden.
     *
     * @param origen ruta de la planilla a inspeccionar
     * @return los nombres de las solapas
     * @throws IOException si falla la lectura
     */
    public static String[] nombresDeHojas(Path origen) throws IOException {
        Objects.requireNonNull(origen, "el origen no puede ser null");
        verificarQueExista(origen);
        try (InputStream entrada = Files.newInputStream(origen);
             Workbook libro = WorkbookFactory.create(entrada)) {
            return nombresDeLasHojas(libro);
        }
    }

    // ==================================================================
    //  Auxiliares privados
    // ==================================================================

    private static void escribirHoja(Workbook libro, Hoja datos,
                                     CellStyle negrita, CellStyle fecha) {
        Sheet hoja = libro.createSheet(datos.nombre());

        Row cabecera = hoja.createRow(0);
        for (int c = 0; c < datos.encabezados().length; c++) {
            Cell celda = cabecera.createCell(c);
            celda.setCellValue(datos.encabezados()[c]);
            celda.setCellStyle(negrita);
        }

        Object[][] filas = datos.filas();
        for (int f = 0; f < filas.length; f++) {
            Row fila = hoja.createRow(f + 1);   // +1 porque la 0 es el encabezado
            for (int c = 0; c < filas[f].length; c++) {
                escribirValor(fila.createCell(c), filas[f][c], fecha);
            }
        }

        hoja.createFreezePane(0, 1);   // el encabezado queda fijo al hacer scroll

        int aAjustar = Math.min(datos.columnas(), MAXIMO_DE_COLUMNAS_AUTOAJUSTADAS);
        for (int c = 0; c < aAjustar; c++) {
            hoja.autoSizeColumn(c);
        }
    }

    /**
     * Vuelca un valor en una celda respetando su tipo. Lo que no sea texto,
     * n&uacute;mero, booleano o fecha se guarda con su {@code toString()}.
     */
    private static void escribirValor(Cell celda, Object valor, CellStyle estiloFecha) {
        switch (valor) {
            case null -> celda.setBlank();
            case String texto -> celda.setCellValue(texto);
            case Boolean booleano -> celda.setCellValue(booleano);
            case LocalDate f -> {
                celda.setCellValue(f);
                celda.setCellStyle(estiloFecha);
            }
            case LocalDateTime f -> {
                celda.setCellValue(f);
                celda.setCellStyle(estiloFecha);
            }
            case Number numero -> celda.setCellValue(numero.doubleValue());
            default -> celda.setCellValue(String.valueOf(valor));
        }
    }

    private static String[][] leerHoja(Sheet hoja) {
        DataFormatter formateador = new DataFormatter(Locale.ROOT);

        int ultimaFila = hoja.getLastRowNum();
        if (ultimaFila < 0) {
            return new String[0][];
        }

        int columnas = 0;
        for (int f = 0; f <= ultimaFila; f++) {
            Row fila = hoja.getRow(f);
            if (fila != null) {
                columnas = Math.max(columnas, fila.getLastCellNum());
            }
        }
        if (columnas <= 0) {
            return new String[0][];
        }

        String[][] grilla = new String[ultimaFila + 1][columnas];
        for (int f = 0; f <= ultimaFila; f++) {
            Row fila = hoja.getRow(f);
            for (int c = 0; c < columnas; c++) {
                Cell celda = (fila == null) ? null : fila.getCell(c);
                grilla[f][c] = textoDe(celda, formateador);
            }
        }
        return grilla;
    }

    /** Convierte una celda a texto, sin dejar nunca {@code null}. */
    private static String textoDe(Cell celda, DataFormatter formateador) {
        if (celda == null) {
            return "";
        }
        CellType tipo = celda.getCellType();
        if (tipo == CellType.FORMULA) {
            tipo = celda.getCachedFormulaResultType();   // el valor que Excel dejó calculado
        }
        return switch (tipo) {
            case STRING -> celda.getStringCellValue();
            case BOOLEAN -> String.valueOf(celda.getBooleanCellValue());
            case NUMERIC -> textoDeNumero(celda);
            case BLANK, ERROR -> "";
            default -> formateador.formatCellValue(celda);
        };
    }

    /**
     * POI devuelve todo n&uacute;mero como {@code double}. Ac&aacute; se lo
     * convierte a texto sin el {@code .0} de m&aacute;s ni notaci&oacute;n
     * cient&iacute;fica, y las fechas se devuelven en ISO.
     */
    private static String textoDeNumero(Cell celda) {
        if (DateUtil.isCellDateFormatted(celda)) {
            LocalDateTime momento = celda.getLocalDateTimeCellValue();
            if (momento == null) {
                return "";
            }
            return momento.toLocalTime().equals(LocalTime.MIDNIGHT)
                    ? momento.toLocalDate().toString()   // 2026-05-04
                    : momento.toString();                // 2026-05-04T13:30
        }
        double valor = celda.getNumericCellValue();
        return BigDecimal.valueOf(valor).stripTrailingZeros().toPlainString();
    }

    private static CellStyle crearEstiloNegrita(Workbook libro) {
        CellStyle estilo = libro.createCellStyle();
        Font fuente = libro.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        return estilo;
    }

    private static CellStyle crearEstiloFecha(Workbook libro) {
        CreationHelper ayudante = libro.getCreationHelper();
        CellStyle estilo = libro.createCellStyle();
        estilo.setDataFormat(ayudante.createDataFormat().getFormat(FORMATO_DE_FECHA));
        return estilo;
    }

    private static String[] nombresDeLasHojas(Workbook libro) {
        String[] nombres = new String[libro.getNumberOfSheets()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = libro.getSheetName(i);
        }
        return nombres;
    }

    private static void verificarQueNoHayaNombresRepetidos(Hoja[] hojas) {
        for (int i = 0; i < hojas.length; i++) {
            Objects.requireNonNull(hojas[i], "la hoja " + i + " es null");
            for (int j = i + 1; j < hojas.length; j++) {
                if (hojas[i].nombre().equalsIgnoreCase(hojas[j].nombre())) {
                    throw new IllegalArgumentException(
                            "hay dos hojas llamadas \"" + hojas[i].nombre()
                                    + "\": Excel no admite nombres repetidos");
                }
            }
        }
    }

    private static void verificarQueExista(Path origen) throws IOException {
        if (!Files.exists(origen)) {
            throw new NoSuchFileException(origen.toString());
        }
    }
}

package ar.uba.fi.cb100.librerias.excel;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ExcelUtilsTest {

    @TempDir
    Path carpeta;

    private static final String[] ENCABEZADOS = {"Padron", "Socio", "Multa", "Estado"};

    @Test
    @DisplayName("lo que se escribe es lo que se lee (ida y vuelta)")
    void idaYVuelta() throws IOException {
        Object[][] filas = {
                {39876, "Bruno Ferrari", 3600, "CON_DEUDA"},
                {42001, "Carla Nunez", 0, "AL_DIA"},
        };
        Path archivo = carpeta.resolve("reporte.xlsx");

        ExcelUtils.escribir(archivo, "Multas", ENCABEZADOS, filas);
        String[][] leido = ExcelUtils.leer(archivo);

        assertEquals(3, leido.length, "encabezado + 2 filas de datos");
        assertArrayEquals(ENCABEZADOS, leido[0]);
        assertArrayEquals(new String[]{"39876", "Bruno Ferrari", "3600", "CON_DEUDA"}, leido[1]);
        assertArrayEquals(new String[]{"42001", "Carla Nunez", "0", "AL_DIA"}, leido[2]);
    }

    @Test
    @DisplayName("un entero no vuelve con el .0 pegado")
    void losEnterosNoTraenDecimalDeMas() throws IOException {
        Path archivo = carpeta.resolve("enteros.xlsx");
        ExcelUtils.escribir(archivo, "Datos", new String[]{"n"}, new Object[][]{{3600}});

        assertEquals("3600", ExcelUtils.leer(archivo)[1][0]);
    }

    @Test
    @DisplayName("un ISBN de 13 digitos no se convierte en notacion cientifica")
    void losNumerosGrandesNoSeNotanEnCientifica() throws IOException {
        Path archivo = carpeta.resolve("isbn.xlsx");
        ExcelUtils.escribir(archivo, "Libros", new String[]{"isbn"},
                new Object[][]{{9789871234567L}});

        assertEquals("9789871234567", ExcelUtils.leer(archivo)[1][0]);
    }

    @Test
    @DisplayName("los decimales conservan su parte decimal")
    void losDecimalesSeConservan() throws IOException {
        Path archivo = carpeta.resolve("decimales.xlsx");
        ExcelUtils.escribir(archivo, "Datos", new String[]{"promedio"},
                new Object[][]{{7.25}});

        assertEquals("7.25", ExcelUtils.leer(archivo)[1][0]);
    }

    @Test
    @DisplayName("una fecha se guarda como fecha y vuelve en formato ISO")
    void lasFechasVuelvenEnIso() throws IOException {
        Path archivo = carpeta.resolve("fechas.xlsx");
        ExcelUtils.escribir(archivo, "Prestamos", new String[]{"retiro"},
                new Object[][]{{LocalDate.of(2026, 5, 4)}});

        assertEquals("2026-05-04", ExcelUtils.leer(archivo)[1][0]);
    }

    @Test
    @DisplayName("un booleano vuelve como true o false")
    void losBooleanosVuelvenComoTexto() throws IOException {
        Path archivo = carpeta.resolve("booleanos.xlsx");
        ExcelUtils.escribir(archivo, "Datos", new String[]{"pendiente"},
                new Object[][]{{true}, {false}});

        String[][] leido = ExcelUtils.leer(archivo);
        assertEquals("true", leido[1][0]);
        assertEquals("false", leido[2][0]);
    }

    @Test
    @DisplayName("una celda null se lee como cadena vacia, nunca como null")
    void lasCeldasVaciasSeLeenComoCadenaVacia() throws IOException {
        Path archivo = carpeta.resolve("vacias.xlsx");
        ExcelUtils.escribir(archivo, "Datos", new String[]{"a", "b"},
                new Object[][]{{"hola", null}});

        assertEquals("", ExcelUtils.leer(archivo)[1][1]);
    }

    @Test
    @DisplayName("se pueden escribir y leer varias hojas en un mismo libro")
    void variasHojas() throws IOException {
        Path archivo = carpeta.resolve("libro.xlsx");
        Hoja multas = new Hoja("Multas", new String[]{"socio"},
                new Object[][]{{"Bruno Ferrari"}});
        Hoja ranking = new Hoja("Ranking", new String[]{"titulo", "pedidos"},
                new Object[][]{{"Estructuras de Datos", 6}});

        ExcelUtils.escribir(archivo, multas, ranking);

        assertArrayEquals(new String[]{"Multas", "Ranking"}, ExcelUtils.nombresDeHojas(archivo));
        assertEquals("Bruno Ferrari", ExcelUtils.leer(archivo, "Multas")[1][0]);
        assertEquals("6", ExcelUtils.leer(archivo, "Ranking")[1][1]);
    }

    @Test
    @DisplayName("pedir una hoja que no existe avisa cuales hay")
    void hojaInexistente() throws IOException {
        Path archivo = carpeta.resolve("libro.xlsx");
        ExcelUtils.escribir(archivo, "Multas", new String[]{"a"}, new Object[][]{{1}});

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> ExcelUtils.leer(archivo, "NoExiste"));
        assertTrue(error.getMessage().contains("Multas"),
                "el mensaje deberia listar las hojas disponibles: " + error.getMessage());
    }

    @Test
    @DisplayName("leer un archivo que no existe falla con NoSuchFileException")
    void archivoInexistente() {
        assertThrows(NoSuchFileException.class,
                () -> ExcelUtils.leer(carpeta.resolve("fantasma.xlsx")));
    }

    @Test
    @DisplayName("se crean los directorios que falten en la ruta de destino")
    void creaLosDirectoriosQueFaltan() throws IOException {
        Path archivo = carpeta.resolve("salidas").resolve("2026").resolve("reporte.xlsx");
        ExcelUtils.escribir(archivo, "Datos", new String[]{"a"}, new Object[][]{{1}});

        assertTrue(java.nio.file.Files.exists(archivo));
    }

    @Test
    @DisplayName("una fila con distinta cantidad de columnas que el encabezado se rechaza")
    void filaConColumnasDeMas() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> new Hoja("Datos", new String[]{"a", "b"},
                        new Object[][]{{1, 2}, {1, 2, 3}}));
        assertTrue(error.getMessage().contains("fila 1"), error.getMessage());
    }

    @Test
    @DisplayName("un nombre de hoja de mas de 31 caracteres se rechaza")
    void nombreDeHojaDemasiadoLargo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hoja("A".repeat(32), new String[]{"a"}, new Object[][]{}));
    }

    @Test
    @DisplayName("un nombre de hoja con caracteres que Excel prohibe se rechaza")
    void nombreDeHojaConCaracteresProhibidos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hoja("Multas/2026", new String[]{"a"}, new Object[][]{}));
    }

    @Test
    @DisplayName("dos hojas con el mismo nombre se rechazan")
    void hojasConNombreRepetido() {
        Hoja una = new Hoja("Datos", new String[]{"a"}, new Object[][]{});
        Hoja otra = new Hoja("datos", new String[]{"a"}, new Object[][]{});

        assertThrows(IllegalArgumentException.class,
                () -> ExcelUtils.escribir(carpeta.resolve("x.xlsx"), una, otra));
    }

    @Test
    @DisplayName("una hoja sin filas de datos deja solo el encabezado")
    void hojaSoloConEncabezado() throws IOException {
        Path archivo = carpeta.resolve("solo-encabezado.xlsx");
        ExcelUtils.escribir(archivo, "Datos", ENCABEZADOS, new Object[][]{});

        String[][] leido = ExcelUtils.leer(archivo);
        assertEquals(1, leido.length);
        assertArrayEquals(ENCABEZADOS, leido[0]);
    }
}

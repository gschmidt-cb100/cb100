package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Geocodificacion;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.librerias.excel.ExcelUtils;

/**
 * La "base de datos": lee y escribe personas en un Excel. Es el único lugar
 * del sistema que sabe en qué columna está cada dato.
 * <p>
 * Una fila mal formada no aborta la carga: se descarta, se anota el motivo
 * con su número de fila, y se sigue con las demás.
 */
public final class ArchivoDePersonas {

    public static final String NOMBRE_DE_LA_HOJA = "Personas";

    private static final String[] ENCABEZADOS = {
        "DNI", "Nombre", "Apellido", "Edad", "Ciudad", "Provincia", "País", "Teléfono", "Email",
        "Dirección", "Código postal", "Geocodificación (latitud,longitud)", "DNI del padre", "DNI de la madre"};

    /** El resultado de leer el archivo: lo que se pudo cargar y lo que no. */
    public record ResultadoDeCarga(List<Persona> personas, List<String> errores) {
        public ResultadoDeCarga {
            personas = List.copyOf(personas);
            errores = List.copyOf(errores);
        }
    }

    private ArchivoDePersonas() {
    }

    /** post: devuelve una copia de los títulos de las columnas, en orden. */
    public static String[] getEncabezados() {
        return ENCABEZADOS.clone();
    }

    /**
     * pre: {@code archivo} existe y es un Excel con la fila de encabezados.
     * post: devuelve las personas de las filas válidas y un mensaje por cada fila descartada.
     */
    public static ResultadoDeCarga leer(Path archivo) throws IOException {
        ValidacionesUtiles.validarDistintoDeNull(archivo, "archivo");
        String[][] grilla = ExcelUtils.leer(archivo);
        List<Persona> personas = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        for (int f = 1; f < grilla.length; f++) {                 // la fila 0 es la de encabezados
            try {
                personas.add(aPersona(grilla[f]));
            } catch (RuntimeException e) {
                errores.add("fila " + (f + 1) + ": " + e.getMessage());
            }
        }
        return new ResultadoDeCarga(personas, errores);
    }

    /**
     * pre: {@code archivo} y {@code personas} no son null.
     * post: escribe (o pisa) el Excel con una fila por persona.
     */
    public static void escribir(Path archivo, List<Persona> personas) throws IOException {
        ValidacionesUtiles.validarDistintoDeNull(archivo, "archivo");
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        Object[][] filas = personas.stream().map(ArchivoDePersonas::aFila).toArray(Object[][]::new);
        ExcelUtils.escribir(archivo, NOMBRE_DE_LA_HOJA, ENCABEZADOS, filas);
    }

    private static Persona aPersona(String[] fila) {
        ValidacionesUtiles.validarFalso(fila.length < ENCABEZADOS.length,
                "faltan columnas: hay " + fila.length + " y se esperan " + ENCABEZADOS.length);
        return new Persona(aNumero(fila[0], "DNI"), fila[1].trim(), fila[2].trim(), (int) aNumero(fila[3], "edad"),
                fila[4].trim(), fila[5].trim(), fila[6].trim(), fila[7].trim(), fila[8].trim(),
                fila[9].trim(), fila[10].trim(), Geocodificacion.desdeTexto(fila[11]),
                aNumeroOpcional(fila[12], "DNI del padre"), aNumeroOpcional(fila[13], "DNI de la madre"));
    }

    private static Object[] aFila(Persona persona) {
        return new Object[]{
            persona.dni(), persona.nombre(), persona.apellido(), persona.edad(), persona.ciudad(),
            persona.provincia(), persona.pais(), persona.telefono(), persona.email(), persona.direccion(),
            persona.codigoPostal(), persona.geocodificacion().aTexto(),
            persona.dniDelPadre(), persona.dniDeLaMadre()};       // null se escribe como celda vacía
    }

    private static long aNumero(String texto, String nombreDelCampo) {
        try {
            return Long.parseLong(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("el campo " + nombreDelCampo + " no es un número: \"" + texto + "\"");
        }
    }

    private static Long aNumeroOpcional(String texto, String nombreDelCampo) {
        return texto.isBlank() ? null : aNumero(texto, nombreDelCampo);
    }
}

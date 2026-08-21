package ar.uba.fi.cb100.librerias.excel;

import java.util.Objects;

/**
 * Una hoja de una planilla, lista para escribir: su nombre, la fila de
 * encabezados y las filas de datos.
 *
 * <p>Valida sus invariantes al construirse, as&iacute; un error de armado se
 * detecta ac&aacute; y no a mitad de la escritura del archivo, con el
 * <code>.xlsx</code> ya a medio hacer.
 *
 * @param nombre      nombre de la solapa. Excel no admite m&aacute;s de 31
 *                    caracteres ni los s&iacute;mbolos {@code [ ] : * ? / \}
 * @param encabezados t&iacute;tulos de las columnas. Define cu&aacute;ntas
 *                    columnas tiene la hoja
 * @param filas       una fila por elemento. Cada fila debe tener exactamente
 *                    tantos valores como encabezados
 */
public record Hoja(String nombre, String[] encabezados, Object[][] filas) {

    /** Cantidad m&aacute;xima de caracteres que Excel acepta en el nombre de una solapa. */
    public static final int LARGO_MAXIMO_DEL_NOMBRE = 31;

    private static final String CARACTERES_PROHIBIDOS = "[]:*?/\\";

    public Hoja {
        Objects.requireNonNull(nombre, "el nombre de la hoja no puede ser null");
        Objects.requireNonNull(encabezados, "los encabezados no pueden ser null");
        Objects.requireNonNull(filas, "las filas no pueden ser null");

        if (nombre.isBlank()) {
            throw new IllegalArgumentException("el nombre de la hoja no puede estar vacío");
        }
        if (nombre.length() > LARGO_MAXIMO_DEL_NOMBRE) {
            throw new IllegalArgumentException(
                    "el nombre de la hoja no puede superar los " + LARGO_MAXIMO_DEL_NOMBRE
                            + " caracteres, y \"" + nombre + "\" tiene " + nombre.length());
        }
        for (int i = 0; i < nombre.length(); i++) {
            if (CARACTERES_PROHIBIDOS.indexOf(nombre.charAt(i)) >= 0) {
                throw new IllegalArgumentException(
                        "el nombre de la hoja no puede contener '" + nombre.charAt(i)
                                + "'. Excel prohíbe estos caracteres: " + CARACTERES_PROHIBIDOS);
            }
        }
        if (encabezados.length == 0) {
            throw new IllegalArgumentException("la hoja \"" + nombre + "\" no tiene encabezados");
        }
        for (int f = 0; f < filas.length; f++) {
            if (filas[f] == null) {
                throw new IllegalArgumentException(
                        "la fila " + f + " de la hoja \"" + nombre + "\" es null");
            }
            if (filas[f].length != encabezados.length) {
                throw new IllegalArgumentException(
                        "la fila " + f + " de la hoja \"" + nombre + "\" tiene "
                                + filas[f].length + " valores, pero hay "
                                + encabezados.length + " encabezados");
            }
        }
    }

    /** Cantidad de columnas de la hoja. */
    public int columnas() {
        return encabezados.length;
    }

    /** Cantidad de filas de datos, sin contar el encabezado. */
    public int filasDeDatos() {
        return filas.length;
    }
}

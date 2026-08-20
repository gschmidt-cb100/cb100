package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e08;

import java.util.TreeMap;

/**
 * e08: frecuencia de palabras con salida alfabética. Mismo problema que
 * con {@code HashMap} en la unidad de hashing, pero al usar {@link TreeMap}
 * el {@code keySet} ya queda en orden alfabético sin ningún paso extra.
 */
public final class FrecuenciasOrdenadas {

    private FrecuenciasOrdenadas() {
    }

    /**
     * Cuenta cuántas veces aparece cada palabra del texto, ignorando
     * mayúsculas y signos de puntuación. Las claves quedan en orden alfabético.
     *
     * @param texto texto a analizar
     * @return mapa palabra → cantidad de apariciones, con claves ordenadas
     */
    public static TreeMap<String, Integer> frecuencias(String texto) {
        TreeMap<String, Integer> conteo = new TreeMap<>();
        // Pasamos a minúsculas y separamos por todo lo que no sea letra.
        for (String palabra : texto.toLowerCase().split("[^a-záéíóúüñ]+")) {
            if (!palabra.isEmpty()) {
                conteo.merge(palabra, 1, Integer::sum);
            }
        }
        return conteo;
    }

    public static void main(String[] args) {
        String texto = "El perro y el gato. ¡El perro ladra!";
        System.out.println("Texto: " + texto);
        System.out.println("Frecuencias (alfabéticas): " + frecuencias(texto));
    }
}

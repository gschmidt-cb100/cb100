package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e09;

/**
 * Ejercicio 09 - Histograma de letras.
 * Cuenta la frecuencia de cada letra a-z ignorando otros caracteres.
 */
public class Histograma {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private Histograma() {
    }

    /**
     * Construye un histograma de frecuencias de las letras a-z.
     * La posicion 0 corresponde a 'a' y la 25 a 'z'.
     *
     * @param s texto a analizar (no debe ser null)
     * @return arreglo de 26 posiciones con las frecuencias
     */
    public static int[] histograma(String s) {
        if (s == null) {
            throw new IllegalArgumentException("La cadena no puede ser null");
        }

        int[] frecuencias = new int[26];

        // Pasamos a minusculas y contamos solo las letras a-z
        String minusculas = s.toLowerCase();
        for (int i = 0; i < minusculas.length(); i++) {
            char c = minusculas.charAt(i);
            if (c >= 'a' && c <= 'z') {
                frecuencias[c - 'a']++;
            }
        }
        return frecuencias;
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        int[] h = histograma("Anita lava la tina");
        for (int i = 0; i < h.length; i++) {
            if (h[i] > 0) {
                System.out.println((char) ('a' + i) + ": " + h[i]);
            }
        }
    }
}

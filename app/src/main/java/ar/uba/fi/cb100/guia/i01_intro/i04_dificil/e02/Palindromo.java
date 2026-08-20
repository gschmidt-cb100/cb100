package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e02;

/**
 * Ejercicio 02 - Palindromo.
 * Determina si un texto es palindromo ignorando mayusculas y espacios.
 */
public class Palindromo {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private Palindromo() {
    }

    /**
     * Indica si la cadena es palindromo ignorando mayusculas/minusculas y espacios.
     *
     * @param s texto a evaluar (no debe ser null)
     * @return true si se lee igual de izquierda a derecha que al reves
     */
    public static boolean esPalindromo(String s) {
        if (s == null) {
            throw new IllegalArgumentException("La cadena no puede ser null");
        }

        // Normalizamos: pasamos a minusculas y sacamos los espacios
        String limpio = s.toLowerCase().replace(" ", "");

        int izquierda = 0;
        int derecha = limpio.length() - 1;

        // Comparamos desde ambos extremos hacia el centro
        while (izquierda < derecha) {
            if (limpio.charAt(izquierda) != limpio.charAt(derecha)) {
                return false;
            }
            izquierda++;
            derecha--;
        }
        return true;
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        System.out.println("Anita lava la tina -> " + esPalindromo("Anita lava la tina"));
        System.out.println("hola -> " + esPalindromo("hola"));
        System.out.println("Neuquen -> " + esPalindromo("Neuquen"));
    }
}

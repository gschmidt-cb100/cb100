package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e05;

/**
 * Ejercicio 05 - Contar palabras.
 * Cuenta las palabras de un texto cuidando espacios multiples y de los extremos.
 */
public class ContarPalabras {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private ContarPalabras() {
    }

    /**
     * Cuenta cuantas palabras hay en el texto.
     * Una palabra es una secuencia maxima de caracteres distintos de espacio.
     *
     * @param s texto a analizar (no debe ser null)
     * @return cantidad de palabras
     */
    public static int contarPalabras(String s) {
        if (s == null) {
            throw new IllegalArgumentException("La cadena no puede ser null");
        }

        int cantidad = 0;
        boolean dentroDePalabra = false;

        // Recorremos caracter por caracter; contamos cada transicion
        // de espacio a no-espacio como el comienzo de una palabra
        for (int i = 0; i < s.length(); i++) {
            char actual = s.charAt(i);
            if (actual != ' ') {
                if (!dentroDePalabra) {
                    cantidad++;
                    dentroDePalabra = true;
                }
            } else {
                dentroDePalabra = false;
            }
        }
        return cantidad;
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        System.out.println("'  hola   mundo  ' -> " + contarPalabras("  hola   mundo  "));
        System.out.println("'' -> " + contarPalabras(""));
    }
}

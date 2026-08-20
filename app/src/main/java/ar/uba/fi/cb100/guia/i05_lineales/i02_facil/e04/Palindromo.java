package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e04;

import java.util.List;

/**
 * e04: determinar si una lista de caracteres es un palindromo
 * (se lee igual de izquierda a derecha que de derecha a izquierda).
 */
public final class Palindromo {

    private Palindromo() {
    }

    /**
     * Indica si {@code l} es un palindromo.
     *
     * @param l lista de caracteres
     * @return true si se lee igual en ambos sentidos
     */
    public static boolean esPalindromo(List<Character> l) {
        // Dos indices que avanzan desde los extremos hacia el centro.
        int i = 0;
        int j = l.size() - 1;
        while (i < j) {
            if (!l.get(i).equals(l.get(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Character> palabra = List.of('n', 'e', 'u', 'q', 'u', 'e', 'n');
        System.out.println(palabra + " es palindromo? " + esPalindromo(palabra));
    }
}

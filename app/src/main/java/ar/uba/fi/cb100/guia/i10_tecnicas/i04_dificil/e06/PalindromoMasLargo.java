package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e06;

/**
 * TECNICA: PROGRAMACION DINAMICA con tabla booleana de subproblemas.
 *
 * Problema: encontrar la subcadena palindroma mas larga de un texto
 * (subcadena = caracteres contiguos, no subsecuencia).
 *
 * De las dos soluciones vistas en clase (tabla de PD y expansion desde
 * el centro) ACA USAMOS LA TABLA esPalindromo[i][j], porque hace visible
 * la estructura de subproblemas:
 *   esPalindromo[i][j] = true si s[i..j] es palindromo.
 *
 * Recurrencia: s[i..j] es palindromo si y solo si sus puntas coinciden
 * (s[i] == s[j]) y el interior s[i+1..j-1] tambien lo es. Los casos base
 * son los largos 1 (siempre palindromo) y 2 (palindromo si las dos letras
 * coinciden, el "interior" es vacio). Para que el interior este resuelto
 * antes de consultarlo, llenamos la tabla POR LARGO CRECIENTE: primero
 * todos los pares (i, j) con j - i = 1, despues j - i = 2, etc.
 *
 * Mientras llenamos la tabla recordamos el (inicio, largo) del mejor
 * palindromo visto; al final devolvemos esa subcadena.
 *
 * Complejidad: O(n^2) en tiempo y O(n^2) en espacio por la tabla.
 * (La expansion desde el centro logra el mismo tiempo con O(1) de
 * espacio, pero la tabla es el ejemplo didactico de PD en 2 dimensiones.)
 */
public class PalindromoMasLargo {

    /**
     * Subcadena palindroma mas larga de {@code s}. Si hay varias del
     * mismo largo maximo devuelve la que empieza mas a la izquierda.
     * Para la cadena vacia devuelve "".
     */
    public String masLarga(String s) {
        if (s == null) {
            throw new IllegalArgumentException("La cadena no puede ser null");
        }
        int n = s.length();
        if (n == 0) {
            return "";
        }
        boolean[][] esPalindromo = new boolean[n][n];
        int mejorInicio = 0;
        int mejorLargo = 1;

        // Caso base: todo caracter suelto es palindromo de largo 1.
        for (int i = 0; i < n; i++) {
            esPalindromo[i][i] = true;
        }
        // Llenado por largo creciente: el interior (largo - 2) ya esta resuelto.
        for (int largo = 2; largo <= n; largo++) {
            for (int i = 0; i + largo - 1 < n; i++) {
                int j = i + largo - 1;
                boolean puntasIguales = s.charAt(i) == s.charAt(j);
                boolean interiorPalindromo = largo == 2 || esPalindromo[i + 1][j - 1];
                if (puntasIguales && interiorPalindromo) {
                    esPalindromo[i][j] = true;
                    if (largo > mejorLargo) {
                        mejorLargo = largo;
                        mejorInicio = i;
                    }
                }
            }
        }
        return s.substring(mejorInicio, mejorInicio + mejorLargo);
    }

    /** Demostracion con los ejemplos clasicos. */
    public static void main(String[] args) {
        PalindromoMasLargo buscador = new PalindromoMasLargo();
        for (String texto : new String[] {"babad", "cbbd", "neuquen", "abc"}) {
            System.out.println("\"" + texto + "\" -> \"" + buscador.masLarga(texto) + "\"");
        }
    }
}

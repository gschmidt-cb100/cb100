package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e07;

/**
 * Ejercicio 07 - Matriz transpuesta.
 * Devuelve la transpuesta de una matriz de enteros.
 */
public class MatrizTranspuesta {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private MatrizTranspuesta() {
    }

    /**
     * Calcula la transpuesta de una matriz rectangular.
     * El elemento (i, j) de la entrada pasa a la posicion (j, i) de la salida.
     *
     * @param m matriz de entrada (rectangular, no null)
     * @return matriz transpuesta
     */
    public static int[][] transponer(int[][] m) {
        if (m == null) {
            throw new IllegalArgumentException("La matriz no puede ser null");
        }

        int filas = m.length;
        int columnas = (filas == 0) ? 0 : m[0].length;

        int[][] resultado = new int[columnas][filas];

        // Copiamos cada elemento en su posicion transpuesta
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[j][i] = m[i][j];
            }
        }
        return resultado;
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[][] t = transponer(m);
        for (int[] fila : t) {
            StringBuilder sb = new StringBuilder();
            for (int valor : fila) {
                sb.append(valor).append(' ');
            }
            System.out.println(sb.toString().trim());
        }
    }
}

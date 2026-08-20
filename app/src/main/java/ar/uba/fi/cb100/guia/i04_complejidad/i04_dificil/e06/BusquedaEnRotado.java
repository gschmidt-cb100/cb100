package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e06;

/**
 * Búsqueda binaria en un arreglo ordenado ascendentemente y luego rotado
 * (por ejemplo {4,5,6,7,0,1,2}). Se asume que no hay elementos repetidos.
 *
 * Idea: en cada paso, al menos una de las dos mitades respecto del medio está
 * ordenada. Se determina cuál y, según si x cae en su rango, se descarta la
 * otra mitad.
 *
 * Complejidad temporal: O(log n), se descarta la mitad en cada iteración.
 * Complejidad espacial: O(1).
 */
public final class BusquedaEnRotado {

    private BusquedaEnRotado() {
    }

    /** Devuelve el índice de x, o -1 si no está. */
    public static int buscar(int[] a, int x) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        int desde = 0;
        int hasta = a.length - 1;
        while (desde <= hasta) {
            int medio = desde + (hasta - desde) / 2;
            if (a[medio] == x) {
                return medio;
            }
            // ¿La mitad izquierda [desde..medio] está ordenada?
            if (a[desde] <= a[medio]) {
                if (a[desde] <= x && x < a[medio]) {
                    hasta = medio - 1; // x está en la izquierda ordenada
                } else {
                    desde = medio + 1;
                }
            } else {
                // Entonces la mitad derecha [medio..hasta] está ordenada.
                if (a[medio] < x && x <= a[hasta]) {
                    desde = medio + 1; // x está en la derecha ordenada
                } else {
                    hasta = medio - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] datos = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Índice de 0: " + buscar(datos, 0)); // 4
        System.out.println("Índice de 3: " + buscar(datos, 3)); // -1
    }
}

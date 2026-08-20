package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContarInversionesTest {

    /** Conteo ingenuo O(n^2) usado como referencia (oráculo) en los tests. */
    private static long contarIngenuo(int[] a) {
        long inv = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    inv++;
                }
            }
        }
        return inv;
    }

    @Test
    @DisplayName("Arreglo ordenado tiene 0 inversiones")
    void ordenado() {
        int[] a = {1, 2, 3, 4, 5};
        assertEquals(0, ContarInversiones.contar(a));
    }

    @Test
    @DisplayName("Arreglo inverso tiene n*(n-1)/2 inversiones")
    void inverso() {
        int[] a = {5, 4, 3, 2, 1};
        assertEquals(10, ContarInversiones.contar(a)); // 5*4/2
    }

    @Test
    @DisplayName("Coincide con el conteo ingenuo O(n^2)")
    void coincideConIngenuo() {
        int[][] casos = {
            {2, 4, 1, 3, 5},
            {3, 1, 2},
            {1, 1, 1, 1},
            {9, -1, 4, 4, 0, 7, -3},
            {}
        };
        for (int[] c : casos) {
            assertEquals(contarIngenuo(c), ContarInversiones.contar(c));
        }
    }

    @Test
    @DisplayName("No modifica el arreglo original")
    void noModifica() {
        int[] a = {3, 1, 2};
        ContarInversiones.contar(a);
        assertArrayEquals(new int[]{3, 1, 2}, a);
    }

    @Test
    @DisplayName("Arreglo null lanza excepción")
    void nullLanza() {
        assertThrows(IllegalArgumentException.class, () -> ContarInversiones.contar(null));
    }
}

package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ContarInversionesTest {

    /** Conteo directo O(n^2), usado como referencia para verificar. */
    private static long fuerzaBruta(int[] a) {
        long total = 0L;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    total++;
                }
            }
        }
        return total;
    }

    @Test
    @DisplayName("arreglo ordenado tiene 0 inversiones")
    void ordenadoCero() {
        assertEquals(0L, ContarInversiones.contar(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0L, ContarInversiones.contar(new int[]{}));
        assertEquals(0L, ContarInversiones.contar(new int[]{7}));
    }

    @Test
    @DisplayName("arreglo invertido tiene n(n-1)/2 inversiones")
    void invertidoMaximo() {
        int[] a = {5, 4, 3, 2, 1};
        int n = a.length;
        assertEquals((long) n * (n - 1) / 2, ContarInversiones.contar(a));
    }

    @Test
    @DisplayName("caso concreto conocido")
    void casoConcreto() {
        // {2,4,1,3,5}: pares (2,1),(4,1),(4,3) => 3 inversiones
        assertEquals(3L, ContarInversiones.contar(new int[]{2, 4, 1, 3, 5}));
    }

    @Test
    @DisplayName("no modifica el arreglo original")
    void noModificaOriginal() {
        int[] original = {3, 1, 2};
        ContarInversiones.contar(original);
        assertArrayEquals(new int[]{3, 1, 2}, original);
    }

    @Test
    @DisplayName("coincide con la fuerza bruta sobre arreglos aleatorios")
    void coincideConFuerzaBruta() {
        Random r = new Random(99);
        for (int caso = 0; caso < 200; caso++) {
            int[] a = new int[r.nextInt(50)];
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(30) - 15;
            }
            assertEquals(fuerzaBruta(a), ContarInversiones.contar(a));
        }
    }
}

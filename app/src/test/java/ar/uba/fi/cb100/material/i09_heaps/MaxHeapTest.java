package ar.uba.fi.cb100.material.i09_heaps;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaxHeapTest {

    /** El invariante de max-heap, verificado celda por celda. */
    private static void assertEsMaxHeap(Object[] a) {
        for (int i = 1; i < a.length; i++) {
            int padre = (i - 1) / 2;
            assertTrue((Integer) a[padre] >= (Integer) a[i],
                    "padre " + a[padre] + " < hijo " + a[i] + " en " + i);
        }
    }

    @Test
    @DisplayName("El máximo está siempre en la raíz")
    void maximoEnLaRaiz() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        for (int v : new int[]{7, 3, 9, 1, 5}) {
            heap.encolar(v);
            assertEsMaxHeap(heap.aArreglo());
        }
        assertEquals(9, heap.verMaximo());
        assertEquals(5, heap.tamanio());
    }

    @Test
    @DisplayName("Desencolar todo entrega los valores en orden descendente")
    void vaciarDaDescendente() {
        MaxHeap<Integer> heap = new MaxHeap<>(2);        // fuerza el redimensionado
        Random azar = new Random(7);
        List<Integer> esperado = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            int v = azar.nextInt(1000);
            heap.encolar(v);
            esperado.add(v);
        }
        esperado.sort((a, b) -> b - a);
        List<Integer> salida = new ArrayList<>();
        while (!heap.estaVacio()) {
            salida.add(heap.desencolarMaximo());
        }
        assertEquals(esperado, salida);
    }

    @Test
    @DisplayName("heapify deja un max-heap válido con el arreglo esperado")
    void heapify() {
        MaxHeap<Integer> heap = MaxHeap.heapify(new Integer[]{12, 5, 9, 3, 20, 7});
        assertArrayEquals(new Object[]{20, 12, 9, 3, 5, 7}, heap.aArreglo());
        assertEsMaxHeap(heap.aArreglo());
        assertEquals(20, heap.desencolarMaximo());
        assertEquals(12, heap.verMaximo());
    }

    @Test
    @DisplayName("Hundir elige el MAYOR de los dos hijos (el caso que rompe la versión con bug)")
    void hundirConElMayorDeLosHijos() {
        // raíz 1 con hijos 5 (izq) y 9 (der): la versión que compara sólo con el
        // izquierdo subiría el 5 y dejaría 5 > 9 roto.
        MaxHeap<Integer> heap = MaxHeap.heapify(new Integer[]{1, 5, 9});
        assertArrayEquals(new Object[]{9, 5, 1}, heap.aArreglo());
    }

    @Test
    @DisplayName("Vacío: verMaximo y desencolarMaximo lanzan excepción")
    void vacio() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        assertTrue(heap.estaVacio());
        assertThrows(IllegalStateException.class, heap::verMaximo);
        assertThrows(IllegalStateException.class, heap::desencolarMaximo);
    }

    @Test
    @DisplayName("Es el espejo del MinHeap: mismos valores, raíces opuestas")
    void espejoDelMinHeap() {
        Integer[] valores = {4, 8, 15, 16, 23, 42};
        MinHeap<Integer> min = MinHeap.heapify(valores.clone());
        MaxHeap<Integer> max = MaxHeap.heapify(valores.clone());
        assertEquals(4, min.verMinimo());
        assertEquals(42, max.verMaximo());
    }
}

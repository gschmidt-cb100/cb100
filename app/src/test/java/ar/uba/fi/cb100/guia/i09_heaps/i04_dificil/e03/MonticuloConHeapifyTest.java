package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class MonticuloConHeapifyTest {

    @Test
    @DisplayName("Heapify de {12,5,9,3,20,7} deja el arreglo interno [3,5,7,12,20,9]")
    void arregloInternoEsperado() {
        MonticuloConHeapify<Integer> monticulo =
                MonticuloConHeapify.desde(new Integer[] {12, 5, 9, 3, 20, 7});
        assertArrayEquals(new Object[] {3, 5, 7, 12, 20, 9}, monticulo.aArreglo());
        assertEquals(6, monticulo.tamanio());
        assertEquals(3, monticulo.verMinimo());
    }

    @Test
    @DisplayName("Desencolar todo despues del heapify entrega los valores ordenados")
    void desencolarTodoDaOrdenado() {
        MonticuloConHeapify<Integer> monticulo =
                MonticuloConHeapify.desde(new Integer[] {12, 5, 9, 3, 20, 7});
        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolarMinimo());
        }
        assertEquals(List.of(3, 5, 7, 9, 12, 20), salida);
    }

    @Test
    @DisplayName("Heapify de un arreglo ya ordenado no lo modifica: ya es un min-heap")
    void arregloOrdenadoQuedaIgual() {
        MonticuloConHeapify<Integer> monticulo =
                MonticuloConHeapify.desde(new Integer[] {1, 2, 3, 4, 5});
        assertArrayEquals(new Object[] {1, 2, 3, 4, 5}, monticulo.aArreglo());
    }

    @Test
    @DisplayName("Heapify de un arreglo vacio y de uno de un elemento no falla")
    void casosBorde() {
        MonticuloConHeapify<Integer> vacio = MonticuloConHeapify.desde(new Integer[] {});
        assertTrue(vacio.estaVacio());
        assertThrows(IllegalStateException.class, vacio::verMinimo);

        MonticuloConHeapify<Integer> unitario = MonticuloConHeapify.desde(new Integer[] {7});
        assertEquals(7, unitario.verMinimo());
        assertEquals(7, unitario.desencolarMinimo());
        assertTrue(unitario.estaVacio());
    }

    @Test
    @DisplayName("El heapify copia el arreglo: modificar el original no afecta al monticulo")
    void copiaDefensiva() {
        Integer[] original = {30, 10, 20};
        MonticuloConHeapify<Integer> monticulo = MonticuloConHeapify.desde(original);
        original[0] = -99; // Pisamos el arreglo del llamador.
        assertEquals(10, monticulo.verMinimo());
    }
}

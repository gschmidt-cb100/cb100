package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorDeHeapTest {

    @Test
    @DisplayName("Un min-heap valido devuelve true")
    void heapValido() {
        assertTrue(ValidadorDeHeap.esMinHeap(new int[] {3, 5, 7, 12, 20, 9}));
        assertTrue(ValidadorDeHeap.esMinHeap(new int[] {1, 2, 3, 4, 5, 6, 7}));
        assertTrue(ValidadorDeHeap.esMinHeap(new int[] {2, 2, 2, 2})); // Iguales: vale el <=.
    }

    @Test
    @DisplayName("El mismo heap con UNA posicion alterada devuelve false")
    void unaPosicionAlterada() {
        // Valido: {3, 5, 7, 12, 20, 9}. Cambiamos el 20 (indice 4) por 4:
        // ahora 5 (su padre, indice 1) es mayor que 4 y el invariante se rompe.
        assertFalse(ValidadorDeHeap.esMinHeap(new int[] {3, 5, 7, 12, 4, 9}));
    }

    @Test
    @DisplayName("Caza la violacion en el hijo DERECHO (no alcanza con mirar el izquierdo)")
    void violacionEnHijoDerecho() {
        // 1 <= 2 (izquierdo, ok) pero 0 (derecho) es menor que la raiz:
        // un validador que solo mira el hijo izquierdo diria true por error.
        assertFalse(ValidadorDeHeap.esMinHeap(new int[] {1, 2, 0}));
    }

    @Test
    @DisplayName("El arreglo vacio y el de un elemento son heaps trivialmente")
    void casosBorde() {
        assertTrue(ValidadorDeHeap.esMinHeap(new int[] {}));
        assertTrue(ValidadorDeHeap.esMinHeap(new int[] {42}));
    }

    @Test
    @DisplayName("Una violacion profunda (lejos de la raiz) tambien se detecta")
    void violacionProfunda() {
        // Los primeros niveles estan bien, pero el 6 (indice 3) tiene como
        // hijo izquierdo al 0 (indice 7).
        assertFalse(ValidadorDeHeap.esMinHeap(new int[] {1, 3, 2, 6, 5, 4, 8, 0}));
    }
}

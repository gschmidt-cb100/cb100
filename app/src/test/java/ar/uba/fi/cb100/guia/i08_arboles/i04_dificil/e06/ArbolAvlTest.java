package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ArbolAvlTest {

    @Test
    @DisplayName("Insertar 1..15 en orden deja un arbol perfecto: altura 3 y raiz 8")
    void insertarQuinceEnOrden() {
        ArbolAvl<Integer> avl = new ArbolAvl<>();
        for (int valor = 1; valor <= 15; valor++) {
            avl.insertar(valor);
        }
        assertEquals(15, avl.tamanio());
        assertEquals(3, avl.altura()); // Un ABB comun mediria 14.
        assertEquals(8, avl.raiz());   // Queda el arbol perfecto con el 8 arriba.
    }

    @Test
    @DisplayName("Insertar 30,10,20 dispara el caso LR y deja raiz 20")
    void casoLR() {
        ArbolAvl<Integer> avl = new ArbolAvl<>();
        avl.insertar(30);
        avl.insertar(10);
        avl.insertar(20); // El 30 queda FE 2 con el peso en izq.der: caso LR.
        assertEquals(20, avl.raiz());
        assertEquals(1, avl.altura());
        assertEquals(List.of(10, 20, 30), avl.enOrden());
    }

    @Test
    @DisplayName("Insertar 10,30,20 dispara el caso RL y deja raiz 20")
    void casoRL() {
        ArbolAvl<Integer> avl = new ArbolAvl<>();
        avl.insertar(10);
        avl.insertar(30);
        avl.insertar(20); // El 10 queda FE -2 con el peso en der.izq: caso RL.
        assertEquals(20, avl.raiz());
        assertEquals(1, avl.altura());
        assertEquals(List.of(10, 20, 30), avl.enOrden());
    }

    @Test
    @DisplayName("Los casos simples LL y RR tambien dejan raiz 20")
    void casosLLyRR() {
        ArbolAvl<Integer> ll = new ArbolAvl<>();
        ll.insertar(30);
        ll.insertar(20);
        ll.insertar(10); // Todo por la izquierda: caso LL.
        assertEquals(20, ll.raiz());
        assertEquals(1, ll.altura());

        ArbolAvl<Integer> rr = new ArbolAvl<>();
        rr.insertar(10);
        rr.insertar(20);
        rr.insertar(30); // Todo por la derecha: caso RR.
        assertEquals(20, rr.raiz());
        assertEquals(1, rr.altura());
    }

    @Test
    @DisplayName("enOrden siempre devuelve ordenado, roten lo que roten")
    void enOrdenSiempreOrdenado() {
        ArbolAvl<Integer> avl = new ArbolAvl<>();
        for (int valor : new int[] {7, 3, 9, 1, 5, 11, 2, 4, 6, 8, 10}) {
            avl.insertar(valor);
        }
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), avl.enOrden());
        assertTrue(avl.contiene(5));
        assertFalse(avl.contiene(12));
    }

    @Test
    @DisplayName("Los duplicados no se agregan ni desbalancean nada")
    void sinDuplicados() {
        ArbolAvl<Integer> avl = new ArbolAvl<>();
        avl.insertar(20);
        avl.insertar(10);
        avl.insertar(30);
        avl.insertar(20); // Duplicado.
        avl.insertar(10); // Duplicado.
        assertEquals(3, avl.tamanio());
        assertEquals(1, avl.altura());
        assertEquals(List.of(10, 20, 30), avl.enOrden());
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecorridosTest {

    private Nodo arbolDeEjemplo() {
        //        50
        //      /    \
        //    30      70
        //   /  \    /  \
        //  20  40  60  80
        return new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
    }

    @Test
    @DisplayName("En orden sobre un ABB devuelve los valores ordenados")
    void enOrden() {
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80),
                Recorridos.enOrden(arbolDeEjemplo()));
    }

    @Test
    @DisplayName("Preorden visita primero cada raíz y después sus subárboles")
    void preOrden() {
        assertEquals(List.of(50, 30, 20, 40, 70, 60, 80),
                Recorridos.preOrden(arbolDeEjemplo()));
    }

    @Test
    @DisplayName("Postorden visita cada raíz después de sus dos subárboles")
    void postOrden() {
        assertEquals(List.of(20, 40, 30, 60, 80, 70, 50),
                Recorridos.postOrden(arbolDeEjemplo()));
    }

    @Test
    @DisplayName("Los tres recorridos del árbol vacío son listas vacías")
    void arbolVacio() {
        assertTrue(Recorridos.enOrden(null).isEmpty());
        assertTrue(Recorridos.preOrden(null).isEmpty());
        assertTrue(Recorridos.postOrden(null).isEmpty());
    }
}

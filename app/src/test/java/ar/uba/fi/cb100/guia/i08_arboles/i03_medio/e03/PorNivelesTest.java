package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PorNivelesTest {

    @Test
    @DisplayName("El árbol de ejemplo se recorre nivel por nivel")
    void arbolDeEjemplo() {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
        assertEquals(List.of(50, 30, 70, 20, 40, 60, 80),
                PorNiveles.porNiveles(raiz));
    }

    @Test
    @DisplayName("El árbol vacío devuelve una lista vacía")
    void arbolVacio() {
        assertTrue(PorNiveles.porNiveles(null).isEmpty());
    }

    @Test
    @DisplayName("Una hoja sola devuelve una lista con su único valor")
    void unaHoja() {
        assertEquals(List.of(7), PorNiveles.porNiveles(new Nodo(7)));
    }

    @Test
    @DisplayName("En un árbol incompleto no se mezclan los niveles")
    void arbolIncompleto() {
        //      1
        //     / \
        //    2   3
        //     \    \
        //      4    5
        Nodo raiz = new Nodo(1,
                new Nodo(2, null, new Nodo(4)),
                new Nodo(3, null, new Nodo(5)));
        assertEquals(List.of(1, 2, 3, 4, 5), PorNiveles.porNiveles(raiz));
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HojasYNivelesTest {

    private Nodo arbolDeEjemplo() {
        return new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
    }

    @Test
    @DisplayName("El árbol vacío tiene 0 hojas y 0 nodos en cualquier nivel")
    void arbolVacio() {
        assertEquals(0, HojasYNiveles.hojas(null));
        assertEquals(0, HojasYNiveles.nodosEnNivel(null, 0));
    }

    @Test
    @DisplayName("Una hoja sola: 1 hoja, 1 nodo en el nivel 0")
    void unaHoja() {
        Nodo hoja = new Nodo(42);
        assertEquals(1, HojasYNiveles.hojas(hoja));
        assertEquals(1, HojasYNiveles.nodosEnNivel(hoja, 0));
        assertEquals(0, HojasYNiveles.nodosEnNivel(hoja, 1));
    }

    @Test
    @DisplayName("El árbol completo de 7 nodos tiene 4 hojas")
    void hojasDelArbolCompleto() {
        assertEquals(4, HojasYNiveles.hojas(arbolDeEjemplo()));
    }

    @Test
    @DisplayName("Los niveles del árbol completo tienen 1, 2 y 4 nodos")
    void nodosPorNivel() {
        Nodo raiz = arbolDeEjemplo();
        assertEquals(1, HojasYNiveles.nodosEnNivel(raiz, 0));
        assertEquals(2, HojasYNiveles.nodosEnNivel(raiz, 1));
        assertEquals(4, HojasYNiveles.nodosEnNivel(raiz, 2));
        assertEquals(0, HojasYNiveles.nodosEnNivel(raiz, 3));
    }

    @Test
    @DisplayName("En un árbol incompleto cuenta solo los nodos que existen")
    void arbolIncompleto() {
        //      1
        //     / \
        //    2   3
        //   /      \
        //  4        5
        Nodo raiz = new Nodo(1,
                new Nodo(2, new Nodo(4), null),
                new Nodo(3, null, new Nodo(5)));
        assertEquals(2, HojasYNiveles.hojas(raiz));
        assertEquals(2, HojasYNiveles.nodosEnNivel(raiz, 2));
    }
}

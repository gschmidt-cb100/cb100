package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlturaYTamanioTest {

    @Test
    @DisplayName("El árbol vacío tiene altura -1 y tamaño 0")
    void arbolVacio() {
        assertEquals(-1, AlturaYTamanio.altura(null));
        assertEquals(0, AlturaYTamanio.tamanio(null));
    }

    @Test
    @DisplayName("Una hoja sola tiene altura 0 y tamaño 1")
    void unaHoja() {
        Nodo hoja = new Nodo(42);
        assertEquals(0, AlturaYTamanio.altura(hoja));
        assertEquals(1, AlturaYTamanio.tamanio(hoja));
    }

    @Test
    @DisplayName("Árbol completo de 7 nodos: altura 2 y tamaño 7")
    void arbolCompleto() {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
        assertEquals(2, AlturaYTamanio.altura(raiz));
        assertEquals(7, AlturaYTamanio.tamanio(raiz));
    }

    @Test
    @DisplayName("Árbol degenerado en lista: la altura es tamaño - 1")
    void arbolDegenerado() {
        Nodo raiz = new Nodo(10, null,
                new Nodo(20, null,
                        new Nodo(30, null, new Nodo(40))));
        assertEquals(3, AlturaYTamanio.altura(raiz));
        assertEquals(4, AlturaYTamanio.tamanio(raiz));
    }
}

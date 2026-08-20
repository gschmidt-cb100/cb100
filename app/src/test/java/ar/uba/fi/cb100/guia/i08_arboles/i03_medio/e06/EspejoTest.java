package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class EspejoTest {

    @Test
    @DisplayName("El espejo del árbol vacío es el árbol vacío")
    void arbolVacio() {
        assertNull(Espejo.espejo(null));
    }

    @Test
    @DisplayName("El espejo intercambia los hijos en todos los niveles")
    void intercambiaHijos() {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        Nodo espejado = Espejo.espejo(raiz);

        assertEquals(70, espejado.izquierdo.valor);
        assertEquals(30, espejado.derecho.valor);
        assertEquals(80, espejado.izquierdo.izquierdo.valor);
        assertEquals(20, espejado.derecho.derecho.valor);
    }

    @Test
    @DisplayName("El en-orden del espejo es el inverso del en-orden original")
    void enOrdenInvertido() {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        List<Integer> original = Espejo.enOrden(raiz);
        List<Integer> invertido = new ArrayList<>(original);
        Collections.reverse(invertido);

        assertEquals(invertido, Espejo.enOrden(Espejo.espejo(raiz)));
    }

    @Test
    @DisplayName("El espejo es un árbol nuevo: el original queda intacto")
    void noModificaElOriginal() {
        Nodo raiz = new Nodo(10, new Nodo(5), new Nodo(15));

        Nodo espejado = Espejo.espejo(raiz);

        assertNotSame(raiz, espejado);
        assertEquals(5, raiz.izquierdo.valor);
        assertEquals(15, raiz.derecho.valor);
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class RotacionesTest {

    @Test
    @DisplayName("Rotar a la derecha el zig 30-20-10 deja raiz 20 con hijos 10 y 30")
    void rotarDerechaEnderezaElZigIzquierdo() {
        // 30 con 20 colgando a la izquierda y 10 debajo del 20.
        Rotaciones.Nodo raiz = new Rotaciones.Nodo(30);
        raiz.izq = new Rotaciones.Nodo(20);
        raiz.izq.izq = new Rotaciones.Nodo(10);
        raiz.izq.altura = 1;
        raiz.altura = 2;

        Rotaciones.Nodo nuevaRaiz = Rotaciones.rotarDerecha(raiz);

        assertEquals(20, nuevaRaiz.valor);
        assertEquals(10, nuevaRaiz.izq.valor);
        assertEquals(30, nuevaRaiz.der.valor);
        // Las alturas quedaron recalculadas: hojas en 0, raiz en 1.
        assertEquals(1, nuevaRaiz.altura);
        assertEquals(0, nuevaRaiz.izq.altura);
        assertEquals(0, nuevaRaiz.der.altura);
    }

    @Test
    @DisplayName("Rotar a la izquierda el zig 10-20-30 deja raiz 20 con hijos 10 y 30")
    void rotarIzquierdaEnderezaElZigDerecho() {
        // 10 con 20 colgando a la derecha y 30 debajo del 20.
        Rotaciones.Nodo raiz = new Rotaciones.Nodo(10);
        raiz.der = new Rotaciones.Nodo(20);
        raiz.der.der = new Rotaciones.Nodo(30);
        raiz.der.altura = 1;
        raiz.altura = 2;

        Rotaciones.Nodo nuevaRaiz = Rotaciones.rotarIzquierda(raiz);

        assertEquals(20, nuevaRaiz.valor);
        assertEquals(10, nuevaRaiz.izq.valor);
        assertEquals(30, nuevaRaiz.der.valor);
        assertEquals(1, nuevaRaiz.altura);
        assertEquals(0, nuevaRaiz.izq.altura);
        assertEquals(0, nuevaRaiz.der.altura);
    }

    @Test
    @DisplayName("La rotacion a la derecha recuelga el subarbol del medio en el otro padre")
    void rotarDerechaRecuelgaElSubarbolDelMedio() {
        //      50            30
        //     /  \          /  \
        //    30   60  ==>  20   50
        //   /  \               /  \
        //  20   40            40   60
        Rotaciones.Nodo raiz = new Rotaciones.Nodo(50);
        raiz.izq = new Rotaciones.Nodo(30);
        raiz.der = new Rotaciones.Nodo(60);
        raiz.izq.izq = new Rotaciones.Nodo(20);
        raiz.izq.der = new Rotaciones.Nodo(40); // El subarbol "del medio".
        raiz.izq.altura = 1;
        raiz.altura = 2;

        Rotaciones.Nodo nuevaRaiz = Rotaciones.rotarDerecha(raiz);

        assertEquals(30, nuevaRaiz.valor);
        assertEquals(50, nuevaRaiz.der.valor);
        // El 40 cambio de padre: era hijo derecho del 30, ahora es izquierdo del 50.
        assertEquals(40, nuevaRaiz.der.izq.valor);
        assertEquals(60, nuevaRaiz.der.der.valor);
        assertEquals(20, nuevaRaiz.izq.valor);
    }

    @Test
    @DisplayName("Rotar a un lado y despues al otro vuelve al arbol original")
    void lasRotacionesSonInversas() {
        Rotaciones.Nodo raiz = new Rotaciones.Nodo(30);
        raiz.izq = new Rotaciones.Nodo(20);
        raiz.izq.izq = new Rotaciones.Nodo(10);
        raiz.izq.altura = 1;
        raiz.altura = 2;

        Rotaciones.Nodo rotada = Rotaciones.rotarDerecha(raiz);
        Rotaciones.Nodo devuelta = Rotaciones.rotarIzquierda(rotada);

        assertEquals(30, devuelta.valor);
        assertEquals(20, devuelta.izq.valor);
        assertEquals(10, devuelta.izq.izq.valor);
        assertEquals(2, devuelta.altura);
        assertEquals(1, devuelta.izq.altura);
        assertEquals(0, devuelta.izq.izq.altura);
    }

    @Test
    @DisplayName("La altura de un subarbol vacio es -1 y la de una hoja es 0")
    void alturaConNull() {
        assertEquals(-1, Rotaciones.altura(null));
        assertEquals(0, Rotaciones.altura(new Rotaciones.Nodo(7)));
    }
}

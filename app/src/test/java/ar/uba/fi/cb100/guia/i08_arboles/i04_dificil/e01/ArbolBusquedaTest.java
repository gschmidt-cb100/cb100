package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ArbolBusquedaTest {

    @Test
    @DisplayName("Un arbol nuevo esta vacio y no contiene nada")
    void arbolVacio() {
        ArbolBusqueda<Integer> arbol = new ArbolBusqueda<>();
        assertEquals(0, arbol.tamanio());
        assertFalse(arbol.contiene(10));
        assertTrue(arbol.enOrden().isEmpty());
    }

    @Test
    @DisplayName("Insertar desordenado y recorrer enOrden devuelve ordenado")
    void enOrdenDevuelveOrdenado() {
        ArbolBusqueda<Integer> arbol = new ArbolBusqueda<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), arbol.enOrden());
        assertEquals(7, arbol.tamanio());
    }

    @Test
    @DisplayName("Contiene encuentra lo insertado y rechaza lo ausente")
    void contiene() {
        ArbolBusqueda<Integer> arbol = new ArbolBusqueda<>();
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        assertTrue(arbol.contiene(50));
        assertTrue(arbol.contiene(30));
        assertTrue(arbol.contiene(70));
        assertFalse(arbol.contiene(40));
    }

    @Test
    @DisplayName("Insertar un duplicado no cambia el tamanio ni el recorrido")
    void sinDuplicados() {
        ArbolBusqueda<Integer> arbol = new ArbolBusqueda<>();
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(50); // Duplicado: se ignora.
        arbol.insertar(30); // Duplicado: se ignora.
        assertEquals(2, arbol.tamanio());
        assertEquals(List.of(30, 50), arbol.enOrden());
    }

    @Test
    @DisplayName("Funciona con Strings porque son Comparable")
    void conStrings() {
        ArbolBusqueda<String> arbol = new ArbolBusqueda<>();
        arbol.insertar("pera");
        arbol.insertar("manzana");
        arbol.insertar("uva");
        assertEquals(List.of("manzana", "pera", "uva"), arbol.enOrden());
        assertTrue(arbol.contiene("pera"));
        assertFalse(arbol.contiene("kiwi"));
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ArbolBusquedaConQuitarTest {

    /** Arma el arbol de la consigna: 50,30,70,20,40,60,80. */
    private ArbolBusquedaConQuitar<Integer> armarArbol() {
        ArbolBusquedaConQuitar<Integer> arbol = new ArbolBusquedaConQuitar<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        return arbol;
    }

    @Test
    @DisplayName("Quitar una hoja (20) mantiene el resto ordenado")
    void quitarHoja() {
        ArbolBusquedaConQuitar<Integer> arbol = armarArbol();
        assertTrue(arbol.quitar(20));
        assertEquals(6, arbol.tamanio());
        assertEquals(List.of(30, 40, 50, 60, 70, 80), arbol.enOrden());
        assertFalse(arbol.contiene(20));
    }

    @Test
    @DisplayName("La secuencia hoja, un hijo y dos hijos deja el arbol consistente")
    void quitarLosTresCasosEnSecuencia() {
        ArbolBusquedaConQuitar<Integer> arbol = armarArbol();

        // Caso 1: el 20 es hoja.
        assertTrue(arbol.quitar(20));
        assertEquals(List.of(30, 40, 50, 60, 70, 80), arbol.enOrden());
        assertEquals(6, arbol.tamanio());

        // Caso 2: al 30 le quedo un solo hijo (el 40).
        assertTrue(arbol.quitar(30));
        assertEquals(List.of(40, 50, 60, 70, 80), arbol.enOrden());
        assertEquals(5, arbol.tamanio());

        // Caso 3: el 50 (la raiz) tiene dos hijos; lo reemplaza su sucesor.
        assertTrue(arbol.quitar(50));
        assertEquals(List.of(40, 60, 70, 80), arbol.enOrden());
        assertEquals(4, arbol.tamanio());
    }

    @Test
    @DisplayName("Quitar un valor ausente devuelve false y no cambia nada")
    void quitarAusente() {
        ArbolBusquedaConQuitar<Integer> arbol = armarArbol();
        assertFalse(arbol.quitar(99));
        assertEquals(7, arbol.tamanio());
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), arbol.enOrden());
    }

    @Test
    @DisplayName("Quitar la raiz con dos hijos usa el sucesor (minimo de la derecha)")
    void quitarRaizConDosHijos() {
        ArbolBusquedaConQuitar<Integer> arbol = armarArbol();
        assertTrue(arbol.quitar(50));
        // El sucesor del 50 es el 60: el arbol sigue conteniendo todo menos el 50.
        assertFalse(arbol.contiene(50));
        assertTrue(arbol.contiene(60));
        assertEquals(List.of(20, 30, 40, 60, 70, 80), arbol.enOrden());
    }

    @Test
    @DisplayName("Quitar todos los valores deja el arbol vacio")
    void quitarTodo() {
        ArbolBusquedaConQuitar<Integer> arbol = armarArbol();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            assertTrue(arbol.quitar(valor));
        }
        assertEquals(0, arbol.tamanio());
        assertTrue(arbol.enOrden().isEmpty());
    }
}

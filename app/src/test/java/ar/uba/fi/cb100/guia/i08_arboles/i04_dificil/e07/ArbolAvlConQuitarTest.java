package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ArbolAvlConQuitarTest {

    @Test
    @DisplayName("Borrar la hoja 10 en {20,10,30,40} dispara una rotacion: sube el 30")
    void borrarHojaDisparaRotacion() {
        ArbolAvlConQuitar<Integer> avl = new ArbolAvlConQuitar<>();
        for (int valor : new int[] {20, 10, 30, 40}) {
            avl.insertar(valor);
        }
        assertEquals(20, avl.raiz());
        assertEquals(2, avl.altura());

        // Al quitar el 10, el 20 queda con FE -2 (nada a la izquierda,
        // el 30-40 a la derecha): caso RR, rota a la izquierda.
        assertTrue(avl.quitar(10));
        assertEquals(30, avl.raiz());
        assertEquals(1, avl.altura());
        assertEquals(List.of(20, 30, 40), avl.enOrden());
        assertEquals(3, avl.tamanio());
    }

    @Test
    @DisplayName("Borrar un nodo con dos hijos usa el sucesor y mantiene el balance")
    void borrarNodoConDosHijos() {
        ArbolAvlConQuitar<Integer> avl = new ArbolAvlConQuitar<>();
        for (int valor : new int[] {40, 20, 60, 10, 30, 50, 70}) {
            avl.insertar(valor);
        }
        // El 40 (la raiz) tiene dos hijos: lo reemplaza su sucesor, el 50.
        assertTrue(avl.quitar(40));
        assertFalse(avl.contiene(40));
        assertEquals(50, avl.raiz());
        assertEquals(List.of(10, 20, 30, 50, 60, 70), avl.enOrden());
        assertEquals(2, avl.altura()); // Sigue balanceado.
    }

    @Test
    @DisplayName("Quitar un valor ausente devuelve false y no cambia nada")
    void quitarAusente() {
        ArbolAvlConQuitar<Integer> avl = new ArbolAvlConQuitar<>();
        for (int valor : new int[] {20, 10, 30}) {
            avl.insertar(valor);
        }
        assertFalse(avl.quitar(99));
        assertEquals(3, avl.tamanio());
        assertEquals(List.of(10, 20, 30), avl.enOrden());
    }

    @Test
    @DisplayName("Borrados sucesivos mantienen la altura logaritmica")
    void borradosSucesivosMantienenBalance() {
        ArbolAvlConQuitar<Integer> avl = new ArbolAvlConQuitar<>();
        for (int valor = 1; valor <= 15; valor++) {
            avl.insertar(valor);
        }
        assertEquals(3, avl.altura());
        // Borramos toda la mitad baja: quedan 8..15, ocho valores.
        for (int valor = 1; valor <= 7; valor++) {
            assertTrue(avl.quitar(valor));
        }
        assertEquals(8, avl.tamanio());
        assertEquals(List.of(8, 9, 10, 11, 12, 13, 14, 15), avl.enOrden());
        // Con 8 nodos un AVL mide a lo sumo 3 (y aca sin rebalancear mediria mas).
        assertTrue(avl.altura() <= 3, "altura " + avl.altura() + " fuera del limite AVL");
    }

    @Test
    @DisplayName("Quitar todo deja el arbol vacio con altura -1")
    void quitarTodo() {
        ArbolAvlConQuitar<Integer> avl = new ArbolAvlConQuitar<>();
        for (int valor : new int[] {20, 10, 30, 40}) {
            avl.insertar(valor);
        }
        for (int valor : new int[] {20, 10, 30, 40}) {
            assertTrue(avl.quitar(valor));
        }
        assertEquals(0, avl.tamanio());
        assertEquals(-1, avl.altura());
        assertNull(avl.raiz());
    }
}

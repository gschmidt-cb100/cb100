package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class IteradorEnOrdenTest {

    /** Arma el arbol clasico de las decenas. */
    private AbbIterable<Integer> armarArbol() {
        AbbIterable<Integer> arbol = new AbbIterable<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        return arbol;
    }

    @Test
    @DisplayName("El for-each recorre los valores de menor a mayor")
    void forEachEnOrden() {
        AbbIterable<Integer> arbol = armarArbol();
        List<Integer> recorrido = new ArrayList<>();
        for (Integer valor : arbol) {
            recorrido.add(valor);
        }
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), recorrido);
    }

    @Test
    @DisplayName("hasNext devuelve false despues del ultimo valor")
    void hasNextAlFinal() {
        AbbIterable<Integer> arbol = armarArbol();
        Iterator<Integer> iterador = arbol.iterator();
        for (int i = 0; i < arbol.tamanio(); i++) {
            assertTrue(iterador.hasNext());
            iterador.next();
        }
        assertFalse(iterador.hasNext());
    }

    @Test
    @DisplayName("Pedir next despues del final lanza NoSuchElementException")
    void nextDespuesDelFinal() {
        AbbIterable<Integer> arbol = new AbbIterable<>();
        arbol.insertar(10);
        Iterator<Integer> iterador = arbol.iterator();
        assertEquals(10, iterador.next());
        assertThrows(NoSuchElementException.class, iterador::next);
    }

    @Test
    @DisplayName("Sobre un arbol vacio el iterador no tiene nada para dar")
    void arbolVacio() {
        AbbIterable<Integer> vacio = new AbbIterable<>();
        Iterator<Integer> iterador = vacio.iterator();
        assertFalse(iterador.hasNext());
        assertThrows(NoSuchElementException.class, iterador::next);
    }

    @Test
    @DisplayName("Un arbol degenerado (insertado en orden) tambien se recorre bien")
    void arbolDegenerado() {
        AbbIterable<Integer> arbol = new AbbIterable<>();
        for (int valor = 1; valor <= 6; valor++) {
            arbol.insertar(valor); // Queda como una lista hacia la derecha.
        }
        List<Integer> recorrido = new ArrayList<>();
        for (Integer valor : arbol) {
            recorrido.add(valor);
        }
        assertEquals(List.of(1, 2, 3, 4, 5, 6), recorrido);
    }

    @Test
    @DisplayName("Cada llamada a iterator() arranca un recorrido nuevo desde el minimo")
    void iteradoresIndependientes() {
        AbbIterable<Integer> arbol = armarArbol();
        Iterator<Integer> primero = arbol.iterator();
        assertEquals(20, primero.next());
        assertEquals(30, primero.next());
        Iterator<Integer> segundo = arbol.iterator();
        assertEquals(20, segundo.next()); // El segundo no hereda el avance del primero.
    }
}

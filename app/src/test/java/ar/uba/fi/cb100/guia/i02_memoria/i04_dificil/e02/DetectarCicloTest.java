package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DetectarCicloTest {

    @Test
    @DisplayName("Lista sin ciclo devuelve false")
    void listaSinCiclo() {
        Nodo a = new Nodo(1);
        Nodo b = new Nodo(2);
        Nodo c = new Nodo(3);
        a.siguiente = b;
        b.siguiente = c;
        assertFalse(DetectarCiclo.tieneCiclo(a));
    }

    @Test
    @DisplayName("Lista con ciclo (ultimo al primero) devuelve true")
    void listaConCiclo() {
        Nodo a = new Nodo(1);
        Nodo b = new Nodo(2);
        Nodo c = new Nodo(3);
        a.siguiente = b;
        b.siguiente = c;
        c.siguiente = a; // el ultimo apunta al primero
        assertTrue(DetectarCiclo.tieneCiclo(a));
    }

    @Test
    @DisplayName("Caso borde: lista vacia (null) no tiene ciclo")
    void listaVacia() {
        assertFalse(DetectarCiclo.tieneCiclo(null));
    }

    @Test
    @DisplayName("Caso borde: un unico nodo apuntandose a si mismo tiene ciclo")
    void nodoAutoreferente() {
        Nodo a = new Nodo(1);
        a.siguiente = a;
        assertTrue(DetectarCiclo.tieneCiclo(a));
    }

    @Test
    @DisplayName("Caso borde: un unico nodo sin siguiente no tiene ciclo")
    void unSoloNodo() {
        Nodo a = new Nodo(1);
        assertFalse(DetectarCiclo.tieneCiclo(a));
    }
}

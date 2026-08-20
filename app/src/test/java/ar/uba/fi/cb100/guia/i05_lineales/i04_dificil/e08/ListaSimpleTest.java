package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ListaSimpleTest {

    @Test
    @DisplayName("Invertir una lista de largo impar")
    void invertirImpar() {
        ListaSimple<Integer> l = new ListaSimple<>();
        for (int i = 1; i <= 5; i++) {
            l.agregar(i);
        }
        l.invertir();
        assertEquals(5, l.tamanio());
        int[] esperado = {5, 4, 3, 2, 1};
        for (int i = 0; i < esperado.length; i++) {
            assertEquals(esperado[i], l.obtener(i));
        }
    }

    @Test
    @DisplayName("Invertir una lista de largo par")
    void invertirPar() {
        ListaSimple<String> l = new ListaSimple<>();
        l.agregar("a");
        l.agregar("b");
        l.agregar("c");
        l.agregar("d");
        l.invertir();
        assertEquals("d", l.obtener(0));
        assertEquals("c", l.obtener(1));
        assertEquals("b", l.obtener(2));
        assertEquals("a", l.obtener(3));
    }

    @Test
    @DisplayName("Invertir lista vacia y de un elemento")
    void invertirCasosBorde() {
        ListaSimple<Integer> vacia = new ListaSimple<>();
        vacia.invertir();
        assertEquals(0, vacia.tamanio());

        ListaSimple<Integer> uno = new ListaSimple<>();
        uno.agregar(42);
        uno.invertir();
        assertEquals(1, uno.tamanio());
        assertEquals(42, uno.obtener(0));
    }

    @Test
    @DisplayName("Invertir dos veces devuelve la lista original")
    void invertirDosVeces() {
        ListaSimple<Integer> l = new ListaSimple<>();
        for (int i = 1; i <= 4; i++) {
            l.agregar(i);
        }
        l.invertir();
        l.invertir();
        for (int i = 0; i < 4; i++) {
            assertEquals(i + 1, l.obtener(i));
        }
    }
}

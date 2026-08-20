package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class KEsimoTest {

    private TreeSet<Integer> conjunto(int... valores) {
        TreeSet<Integer> ts = new TreeSet<>();
        for (int v : valores) {
            ts.add(v);
        }
        return ts;
    }

    @Test
    @DisplayName("k=1 devuelve el minimo y k=n el maximo")
    void extremos() {
        TreeSet<Integer> valores = conjunto(52, 47, 61, 49);
        assertEquals(47, KEsimo.kEsimo(valores, 1));
        assertEquals(61, KEsimo.kEsimo(valores, 4));
    }

    @Test
    @DisplayName("k=2 sobre {47, 49, 52, 61} devuelve 49")
    void kIntermedio() {
        assertEquals(49, KEsimo.kEsimo(conjunto(52, 47, 61, 49), 2));
    }

    @Test
    @DisplayName("k=0 lanza IllegalArgumentException")
    void kMenorQueUno() {
        assertThrows(IllegalArgumentException.class, () -> KEsimo.kEsimo(conjunto(1, 2, 3), 0));
    }

    @Test
    @DisplayName("k mayor que el tamano lanza IllegalArgumentException")
    void kFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> KEsimo.kEsimo(conjunto(1, 2, 3), 4));
    }
}

package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VueltoGreedyTest {

    @Test
    @DisplayName("con el sistema {100,50,20,10,1}, 180 se paga con 4 monedas")
    void sistemaCanonico() {
        assertEquals(4, VueltoGreedy.monedasGreedy(new int[]{100, 50, 20, 10, 1}, 180));
    }

    @Test
    @DisplayName("con {1,3,4} y monto 6 el greedy usa 3 monedas (4+1+1) aunque el óptimo real es 2 (3+3): greedy no siempre es óptimo")
    void greedyNoSiempreEsOptimo() {
        assertEquals(3, VueltoGreedy.monedasGreedy(new int[]{1, 3, 4}, 6));
    }

    @Test
    @DisplayName("monto 0 se paga con 0 monedas")
    void montoCero() {
        assertEquals(0, VueltoGreedy.monedasGreedy(new int[]{5, 2}, 0));
    }

    @Test
    @DisplayName("si el greedy no llega exactamente a 0 devuelve -1")
    void sinSolucionDevuelveMenosUno() {
        // Con {5, 2} y monto 3, el greedy toma 2 y se queda clavado en 1.
        assertEquals(-1, VueltoGreedy.monedasGreedy(new int[]{5, 2}, 3));
    }

    @Test
    @DisplayName("no modifica el arreglo del sistema monetario")
    void noModificaElSistema() {
        int[] sistema = {1, 100, 10};
        VueltoGreedy.monedasGreedy(sistema, 123);
        assertArrayEquals(new int[]{1, 100, 10}, sistema);
    }
}

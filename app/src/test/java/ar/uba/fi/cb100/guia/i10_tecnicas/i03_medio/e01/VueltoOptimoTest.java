package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VueltoOptimoTest {

    @Test
    @DisplayName("Con {1,3,4} y monto 6 el óptimo es 2 monedas (donde el greedy falla)")
    void sistemaNoCanonico() {
        assertEquals(2, VueltoOptimo.minimo(new int[] {1, 3, 4}, 6));
    }

    @Test
    @DisplayName("La reconstrucción para {1,3,4}/6 devuelve [3, 3]")
    void reconstruccionOptima() {
        List<Integer> monedas = new ArrayList<>(
                VueltoOptimo.monedasOptimas(new int[] {1, 3, 4}, 6));
        Collections.sort(monedas);
        assertEquals(List.of(3, 3), monedas);
    }

    @Test
    @DisplayName("Monto imposible: mínimo -1 y lista vacía")
    void montoImposible() {
        assertEquals(-1, VueltoOptimo.minimo(new int[] {5}, 7));
        assertTrue(VueltoOptimo.monedasOptimas(new int[] {5}, 7).isEmpty());
    }

    @Test
    @DisplayName("Monto 0 se forma con 0 monedas")
    void montoCero() {
        assertEquals(0, VueltoOptimo.minimo(new int[] {1, 3, 4}, 0));
        assertTrue(VueltoOptimo.monedasOptimas(new int[] {1, 3, 4}, 0).isEmpty());
    }

    @Test
    @DisplayName("Las monedas reconstruidas suman el monto y son tantas como el mínimo")
    void consistenciaEntreMinimoYLista() {
        int[] sistema = {2, 5, 7};
        int monto = 27;
        List<Integer> monedas = VueltoOptimo.monedasOptimas(sistema, monto);
        int suma = 0;
        for (int moneda : monedas) {
            suma += moneda;
        }
        assertEquals(monto, suma);
        assertEquals(VueltoOptimo.minimo(sistema, monto), monedas.size());
    }
}

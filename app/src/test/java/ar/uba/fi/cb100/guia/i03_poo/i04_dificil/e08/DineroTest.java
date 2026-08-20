package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DineroTest {

    @Test
    @DisplayName("equals y hashCode coinciden para el mismo monto")
    void equalsYHashCode() {
        Dinero a = new Dinero(1599);
        Dinero b = new Dinero(1599);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @DisplayName("compareTo ordena por monto")
    void compareToOrdena() {
        Dinero menor = new Dinero(100);
        Dinero mayor = new Dinero(500);
        assertTrue(menor.compareTo(mayor) < 0);
        assertTrue(mayor.compareTo(menor) > 0);
    }

    @Test
    @DisplayName("equals y compareTo son consistentes: compareTo==0 sii equals")
    void consistenciaEqualsCompareTo() {
        Dinero[] valores = {
                new Dinero(-100), new Dinero(0), new Dinero(100),
                new Dinero(100), new Dinero(999), new Dinero(1000)
        };

        for (Dinero x : valores) {
            for (Dinero y : valores) {
                boolean iguales = x.equals(y);
                boolean compareCero = x.compareTo(y) == 0;
                assertEquals(iguales, compareCero,
                        "Inconsistencia entre equals y compareTo para " + x + " y " + y);
            }
        }
    }

    @Test
    @DisplayName("toString formatea pesos y centavos, incluso negativos")
    void toStringFormatea() {
        assertEquals("$15.99", new Dinero(1599).toString());
        assertEquals("$0.05", new Dinero(5).toString());
        assertEquals("-$1.50", new Dinero(-150).toString());
    }
}

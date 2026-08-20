package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class VecinosTest {

    private TreeSet<Integer> conjunto(int... valores) {
        TreeSet<Integer> ts = new TreeSet<>();
        for (int v : valores) {
            ts.add(v);
        }
        return ts;
    }

    @Test
    @DisplayName("un valor del medio tiene vecino anterior y siguiente")
    void valorDelMedio() {
        assertArrayEquals(new Integer[] {2, 8}, Vecinos.vecinos(conjunto(2, 5, 8), 5));
    }

    @Test
    @DisplayName("lower y higher son estrictos: no devuelven al propio x")
    void estrictos() {
        // Aunque 5 está en el conjunto, ninguno de los dos vecinos es 5.
        Integer[] v = Vecinos.vecinos(conjunto(2, 5, 8), 5);
        assertNotEquals(Integer.valueOf(5), v[0]);
        assertNotEquals(Integer.valueOf(5), v[1]);
    }

    @Test
    @DisplayName("en el minimo el vecino anterior es null")
    void bordeInferior() {
        assertArrayEquals(new Integer[] {null, 5}, Vecinos.vecinos(conjunto(2, 5, 8), 2));
    }

    @Test
    @DisplayName("en el maximo el vecino siguiente es null")
    void bordeSuperior() {
        assertArrayEquals(new Integer[] {5, null}, Vecinos.vecinos(conjunto(2, 5, 8), 8));
    }

    @Test
    @DisplayName("conjunto vacio devuelve {null, null}")
    void conjuntoVacio() {
        assertArrayEquals(new Integer[] {null, null}, Vecinos.vecinos(new TreeSet<>(), 10));
    }
}

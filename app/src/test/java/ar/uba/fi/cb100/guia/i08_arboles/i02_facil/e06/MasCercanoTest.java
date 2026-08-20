package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MasCercanoTest {

    private TreeSet<Integer> conjunto(int... valores) {
        TreeSet<Integer> ts = new TreeSet<>();
        for (int v : valores) {
            ts.add(v);
        }
        return ts;
    }

    @Test
    @DisplayName("elige el vecino mas proximo cuando no hay empate")
    void sinEmpate() {
        assertEquals(20, MasCercano.masCercano(conjunto(10, 20, 40), 23));
        assertEquals(40, MasCercano.masCercano(conjunto(10, 20, 40), 33));
    }

    @Test
    @DisplayName("en empate devuelve el menor: {10, 20} con x=15 da 10")
    void empateGanaElMenor() {
        assertEquals(10, MasCercano.masCercano(conjunto(10, 20), 15));
    }

    @Test
    @DisplayName("si x esta en el conjunto devuelve x")
    void valorExacto() {
        assertEquals(20, MasCercano.masCercano(conjunto(10, 20, 40), 20));
    }

    @Test
    @DisplayName("fuera de los bordes devuelve el extremo correspondiente")
    void fueraDeLosBordes() {
        assertEquals(10, MasCercano.masCercano(conjunto(10, 20, 40), -5));
        assertEquals(40, MasCercano.masCercano(conjunto(10, 20, 40), 1000));
    }

    @Test
    @DisplayName("conjunto vacio devuelve null")
    void conjuntoVacio() {
        assertNull(MasCercano.masCercano(new TreeSet<>(), 5));
    }
}

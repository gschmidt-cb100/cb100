package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TorresHanoiTest {

    @Test
    @DisplayName("cero discos: no hay movimientos")
    void ceroDiscos() {
        assertTrue(TorresHanoi.resolver(0).isEmpty());
    }

    @Test
    @DisplayName("un disco: un unico movimiento de A a C")
    void unDisco() {
        assertEquals(List.of("mover disco 1 de A a C"), TorresHanoi.resolver(1));
    }

    @Test
    @DisplayName("dos discos: secuencia esperada")
    void dosDiscos() {
        assertEquals(List.of(
                "mover disco 1 de A a B",
                "mover disco 2 de A a C",
                "mover disco 1 de B a C"
        ), TorresHanoi.resolver(2));
    }

    @Test
    @DisplayName("la cantidad de movimientos es 2^n - 1")
    void cantidadDeMovimientos() {
        for (int n = 0; n <= 15; n++) {
            long esperado = (1L << n) - 1; // 2^n - 1
            assertEquals(esperado, TorresHanoi.resolver(n).size(), "fallo para n=" + n);
        }
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> TorresHanoi.resolver(-1));
    }
}

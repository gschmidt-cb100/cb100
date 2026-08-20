package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class HanoiTest {

    @Test
    @DisplayName("n=3 da 7 movimientos")
    void tresDiscos() {
        assertEquals(7, Hanoi.movimientos(3));
    }

    @Test
    @DisplayName("Valores conocidos de 2^n - 1")
    void valoresConocidos() {
        assertEquals(0, Hanoi.movimientos(0));
        assertEquals(1, Hanoi.movimientos(1));
        assertEquals(3, Hanoi.movimientos(2));
        assertEquals(15, Hanoi.movimientos(4));
        assertEquals(1023, Hanoi.movimientos(10));
    }

    @Test
    @DisplayName("La fórmula cerrada coincide con la versión recursiva")
    void coincideCerradaYRecursiva() {
        for (int n = 0; n <= 20; n++) {
            assertEquals(Hanoi.movimientosRecursivo(n), Hanoi.movimientos(n));
        }
    }

    @Test
    @DisplayName("n negativo o demasiado grande lanzan excepción")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> Hanoi.movimientos(-1));
        assertThrows(IllegalArgumentException.class, () -> Hanoi.movimientos(63));
        assertThrows(IllegalArgumentException.class, () -> Hanoi.movimientosRecursivo(-1));
    }
}

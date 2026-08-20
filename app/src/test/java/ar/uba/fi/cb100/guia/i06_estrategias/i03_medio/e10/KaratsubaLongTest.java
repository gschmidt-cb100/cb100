package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class KaratsubaLongTest {

    @Test
    @DisplayName("casos base con factores de un digito")
    void casosBase() {
        assertEquals(0L, KaratsubaLong.multiplicar(0, 12345));
        assertEquals(56L, KaratsubaLong.multiplicar(7, 8));
        assertEquals(9L, KaratsubaLong.multiplicar(1, 9));
    }

    @Test
    @DisplayName("productos conocidos")
    void productosConocidos() {
        assertEquals(7006652L, KaratsubaLong.multiplicar(1234, 5678));
        assertEquals(9999800001L, KaratsubaLong.multiplicar(99999, 99999));
        assertEquals(121932631112635269L, KaratsubaLong.multiplicar(123456789, 987654321));
    }

    @Test
    @DisplayName("maneja factores negativos por reglas de signo")
    void factoresNegativos() {
        assertEquals(-408L, KaratsubaLong.multiplicar(-12, 34));
        assertEquals(-408L, KaratsubaLong.multiplicar(12, -34));
        assertEquals(408L, KaratsubaLong.multiplicar(-12, -34));
    }

    @Test
    @DisplayName("coincide con x*y sobre pares aleatorios")
    void coincideConProductoDirecto() {
        Random r = new Random(555);
        for (int caso = 0; caso < 5000; caso++) {
            long x = r.nextInt(2_000_000) - 1_000_000;
            long y = r.nextInt(2_000_000) - 1_000_000;
            assertEquals(x * y, KaratsubaLong.multiplicar(x, y),
                    "fallo para " + x + " * " + y);
        }
    }
}

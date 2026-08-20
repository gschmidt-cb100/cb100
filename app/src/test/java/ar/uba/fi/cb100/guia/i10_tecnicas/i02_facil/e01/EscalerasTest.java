package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EscalerasTest {

    @Test
    @DisplayName("una escalera de 5 escalones se puede subir de 8 formas")
    void cincoEscalones() {
        assertEquals(8, Escaleras.formas(5));
    }

    @Test
    @DisplayName("casos base: 0 y 1 escalón tienen una sola forma")
    void casosBase() {
        assertEquals(1, Escaleras.formas(0));
        assertEquals(1, Escaleras.formas(1));
    }

    @Test
    @DisplayName("memoización y tabulación coinciden para n de 0 a 20")
    void memoYTabulacionCoinciden() {
        for (int n = 0; n <= 20; n++) {
            assertEquals(Escaleras.formas(n), Escaleras.formasTabulada(n),
                    "difieren en n = " + n);
        }
    }

    @Test
    @DisplayName("con memoización, n grande se resuelve al instante")
    void nGrandeNoExplota() {
        // Sin memoización esto tardaría siglos (2^60 llamadas).
        assertEquals(Escaleras.formasTabulada(60), Escaleras.formas(60));
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Escaleras.formas(-1));
        assertThrows(IllegalArgumentException.class, () -> Escaleras.formasTabulada(-3));
    }
}

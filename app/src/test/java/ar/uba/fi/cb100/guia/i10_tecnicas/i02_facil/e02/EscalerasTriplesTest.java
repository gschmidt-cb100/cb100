package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EscalerasTriplesTest {

    @Test
    @DisplayName("una escalera de 4 escalones con pasos 1, 2 o 3 tiene 7 formas")
    void cuatroEscalones() {
        assertEquals(7, EscalerasTriples.formas3(4));
    }

    @Test
    @DisplayName("casos chicos: formas3(0)=1, formas3(1)=1, formas3(2)=2, formas3(3)=4")
    void casosChicos() {
        assertEquals(1, EscalerasTriples.formas3(0));
        assertEquals(1, EscalerasTriples.formas3(1));
        assertEquals(2, EscalerasTriples.formas3(2));
        assertEquals(4, EscalerasTriples.formas3(3));
    }

    @Test
    @DisplayName("cada valor es la suma de los tres anteriores")
    void cumpleLaRecurrencia() {
        for (int n = 3; n <= 15; n++) {
            assertEquals(EscalerasTriples.formas3(n - 1)
                            + EscalerasTriples.formas3(n - 2)
                            + EscalerasTriples.formas3(n - 3),
                    EscalerasTriples.formas3(n),
                    "falla la recurrencia en n = " + n);
        }
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> EscalerasTriples.formas3(-2));
    }
}

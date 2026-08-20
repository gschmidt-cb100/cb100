package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasosAUnoTest {

    @Test
    @DisplayName("10 llega a 1 en 3 pasos (10 -> 9 -> 3 -> 1), no en 4 como haría el greedy de dividir")
    void diezEnTresPasos() {
        assertEquals(3, PasosAUno.pasosMinimos(10));
    }

    @Test
    @DisplayName("1 ya está en destino: 0 pasos")
    void unoEsCero() {
        assertEquals(0, PasosAUno.pasosMinimos(1));
    }

    @Test
    @DisplayName("casos chicos: 2 y 3 se resuelven en 1 paso, 4 en 2 pasos")
    void casosChicos() {
        assertEquals(1, PasosAUno.pasosMinimos(2));
        assertEquals(1, PasosAUno.pasosMinimos(3));
        assertEquals(2, PasosAUno.pasosMinimos(4));
    }

    @Test
    @DisplayName("una potencia de 3 se resuelve dividiendo por 3: 27 en 3 pasos")
    void potenciaDeTres() {
        assertEquals(3, PasosAUno.pasosMinimos(27));
    }

    @Test
    @DisplayName("n menor que 1 lanza IllegalArgumentException")
    void invalidoLanza() {
        assertThrows(IllegalArgumentException.class, () -> PasosAUno.pasosMinimos(0));
    }
}

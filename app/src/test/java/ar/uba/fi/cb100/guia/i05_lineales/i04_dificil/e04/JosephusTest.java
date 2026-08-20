package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class JosephusTest {

    @Test
    @DisplayName("Caso clasico n=7 k=3 sobrevive la posicion 3 (0-based)")
    void casoClasico() {
        // Eliminaciones (0-based): 2, 5, 1, 6, 4, 0 -> sobrevive 3.
        assertEquals(3, Josephus.sobreviviente(7, 3));
    }

    @Test
    @DisplayName("Con una sola persona sobrevive la posicion 0")
    void unaPersona() {
        assertEquals(0, Josephus.sobreviviente(1, 5));
    }

    @Test
    @DisplayName("Con k=1 se elimina en orden y sobrevive el ultimo")
    void pasoUno() {
        // k=1 elimina 0,1,2,3 -> sobrevive 4.
        assertEquals(4, Josephus.sobreviviente(5, 1));
    }

    @Test
    @DisplayName("n=2 k=2 sobrevive la posicion 0")
    void dosPersonas() {
        // Elimina 1 -> sobrevive 0.
        assertEquals(0, Josephus.sobreviviente(2, 2));
    }

    @Test
    @DisplayName("Argumentos invalidos lanzan excepcion")
    void argumentosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> Josephus.sobreviviente(0, 3));
        assertThrows(IllegalArgumentException.class, () -> Josephus.sobreviviente(5, 0));
    }
}

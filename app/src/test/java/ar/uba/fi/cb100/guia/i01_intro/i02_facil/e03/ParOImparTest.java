package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ParOImparTest {

    @Test
    @DisplayName("Numeros pares")
    void pares() {
        assertTrue(ParOImpar.esPar(4));
        assertTrue(ParOImpar.esPar(0));
    }

    @Test
    @DisplayName("Numeros impares")
    void impares() {
        assertFalse(ParOImpar.esPar(7));
        assertFalse(ParOImpar.esPar(-3));
    }
}

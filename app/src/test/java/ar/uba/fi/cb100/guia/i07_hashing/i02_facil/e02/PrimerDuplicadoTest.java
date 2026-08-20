package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class PrimerDuplicadoTest {

    @Test
    @DisplayName("devuelve el primero que se repite, no el que se repite antes")
    void primeroQueSeRepite() {
        // El 1 aparece por segunda vez en la posicion 3, antes que el 3 (posicion 5).
        assertEquals(1, PrimerDuplicado.primerDuplicado(new int[]{3, 1, 4, 1, 5, 3}));
    }

    @Test
    @DisplayName("sin duplicados devuelve null")
    void sinDuplicados() {
        assertNull(PrimerDuplicado.primerDuplicado(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("arreglo vacio devuelve null")
    void arregloVacio() {
        assertNull(PrimerDuplicado.primerDuplicado(new int[]{}));
    }

    @Test
    @DisplayName("dos elementos iguales seguidos")
    void dosIgualesSeguidos() {
        assertEquals(7, PrimerDuplicado.primerDuplicado(new int[]{7, 7}));
    }

    @Test
    @DisplayName("funciona con valores negativos")
    void valoresNegativos() {
        assertEquals(-2, PrimerDuplicado.primerDuplicado(new int[]{-2, 5, -2, 5}));
    }
}

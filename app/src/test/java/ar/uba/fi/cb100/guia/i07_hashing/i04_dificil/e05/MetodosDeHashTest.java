package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e05.MetodosDeHash.*;

class MetodosDeHashTest {

    @Test
    @DisplayName("cuadradoMedio(123, 2): 123^2 = 15129 y el medio es 51")
    void cuadradoMedioDe123() {
        assertEquals(51, cuadradoMedio(123, 2));
    }

    @Test
    @DisplayName("extraccion(123, 0, 2) toma los dos primeros digitos: 12")
    void extraccionDe123() {
        assertEquals(12, extraccion(123, 0, 2));
    }

    @Test
    @DisplayName("Son metodos distintos: sobre 123 dan 51 y 12")
    void metodosDistintos() {
        assertNotEquals(cuadradoMedio(123, 2), extraccion(123, 0, 2));
        assertEquals(51, cuadradoMedio(123, 2));
        assertEquals(12, extraccion(123, 0, 2));
    }

    @Test
    @DisplayName("cuadradoMedio no desborda con claves grandes")
    void cuadradoMedioClaveGrande() {
        // 99999^2 = 9999800001 (no entra en int): con long funciona igual.
        // "9999800001" tiene 10 digitos, el medio de 4 es "9800".
        assertEquals(9800, cuadradoMedio(99999, 4));
    }

    @Test
    @DisplayName("extraccion en posiciones intermedias: los 4 del medio de un DNI")
    void extraccionIntermedia() {
        // 20261234: desde la posicion 4 tomo 4 digitos -> "1234".
        assertEquals(1234, extraccion(20261234, 4, 4));
        // Desde la 2 tomo 3 -> "261".
        assertEquals(261, extraccion(20261234, 2, 3));
    }

    @Test
    @DisplayName("Argumentos invalidos lanzan excepcion")
    void argumentosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> cuadradoMedio(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> cuadradoMedio(123, 0));
        assertThrows(IllegalArgumentException.class, () -> extraccion(123, 0, 4));
        assertThrows(IllegalArgumentException.class, () -> extraccion(123, -1, 2));
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class OperacionesTest {

    @Test
    @DisplayName("Suma de enteros")
    void suma() {
        assertEquals(5, Operaciones.suma(2, 3));
        assertEquals(0, Operaciones.suma(-4, 4));
    }

    @Test
    @DisplayName("Resta de enteros")
    void resta() {
        assertEquals(3, Operaciones.resta(5, 2));
        assertEquals(-7, Operaciones.resta(-3, 4));
    }

    @Test
    @DisplayName("Producto de enteros")
    void producto() {
        assertEquals(12, Operaciones.producto(4, 3));
        assertEquals(0, Operaciones.producto(0, 99));
    }

    @Test
    @DisplayName("Division real")
    void division() {
        assertEquals(3.5, Operaciones.division(7, 2), 1e-9);
        assertEquals(2.0, Operaciones.division(4, 2), 1e-9);
    }

    @Test
    @DisplayName("Division por cero lanza excepcion")
    void divisionPorCero() {
        assertThrows(ArithmeticException.class, () -> Operaciones.division(1, 0));
    }
}

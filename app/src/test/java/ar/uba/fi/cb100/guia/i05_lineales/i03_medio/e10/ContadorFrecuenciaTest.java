package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContadorFrecuenciaTest {

    @Test
    @DisplayName("Cuenta múltiples apariciones")
    void variasApariciones() {
        assertEquals(3, ContadorFrecuencia.veces(List.of(1, 2, 2, 3, 2), 2));
    }

    @Test
    @DisplayName("Devuelve 0 si el valor no está")
    void valorAusente() {
        assertEquals(0, ContadorFrecuencia.veces(List.of(1, 2, 3), 9));
    }

    @Test
    @DisplayName("Cuenta una única aparición")
    void unaAparicion() {
        assertEquals(1, ContadorFrecuencia.veces(List.of(4, 5, 6), 5));
    }

    @Test
    @DisplayName("Lista vacía devuelve 0")
    void listaVacia() {
        assertEquals(0, ContadorFrecuencia.veces(List.of(), 1));
    }

    @Test
    @DisplayName("Contempla valores negativos")
    void valoresNegativos() {
        assertEquals(2, ContadorFrecuencia.veces(List.of(-1, -1, 0, 1), -1));
    }
}

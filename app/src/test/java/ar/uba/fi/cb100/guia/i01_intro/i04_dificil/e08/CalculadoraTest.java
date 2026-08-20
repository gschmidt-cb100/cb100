package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 08 - Calculadora. */
class CalculadoraTest {

    @Test
    @DisplayName("Operaciones basicas correctas")
    void operacionesBasicas() {
        assertEquals(5.0, Calculadora.operar(2, 3, '+'), 0.0001);
        assertEquals(-1.0, Calculadora.operar(2, 3, '-'), 0.0001);
        assertEquals(6.0, Calculadora.operar(2, 3, '*'), 0.0001);
        assertEquals(2.5, Calculadora.operar(10, 4, '/'), 0.0001);
    }

    @Test
    @DisplayName("Division por cero lanza DivisionPorCeroException")
    void divisionPorCero() {
        assertThrows(DivisionPorCeroException.class,
                () -> Calculadora.operar(5, 0, '/'));
    }

    @Test
    @DisplayName("Operador invalido lanza OperadorInvalidoException")
    void operadorInvalido() {
        assertThrows(OperadorInvalidoException.class,
                () -> Calculadora.operar(5, 2, '%'));
    }
}

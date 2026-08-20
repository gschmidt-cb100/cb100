package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class GerenteTest {

    private static final double DELTA = 1e-9;

    @Test
    @DisplayName("El sueldo de un empleado común es su sueldo base")
    void sueldoEmpleadoBase() {
        Empleado e = new Empleado("Ana", 500000.0);
        assertEquals(500000.0, e.sueldo(), DELTA);
    }

    @Test
    @DisplayName("El gerente suma el bono al sueldo base usando super.sueldo()")
    void sueldoGerenteConBono() {
        Gerente g = new Gerente("Beto", 500000.0, 200000.0);
        assertEquals(700000.0, g.sueldo(), DELTA);
    }

    @Test
    @DisplayName("Un Gerente tratado como Empleado usa el método redefinido (polimorfismo)")
    void polimorfismoSueldo() {
        Empleado comoEmpleado = new Gerente("Beto", 100000.0, 50000.0);
        assertEquals(150000.0, comoEmpleado.sueldo(), DELTA);
    }

    @Test
    @DisplayName("Un bono negativo lanza excepción")
    void bonoNegativoLanza() {
        assertThrows(IllegalArgumentException.class,
                () -> new Gerente("X", 100000.0, -1.0));
    }
}

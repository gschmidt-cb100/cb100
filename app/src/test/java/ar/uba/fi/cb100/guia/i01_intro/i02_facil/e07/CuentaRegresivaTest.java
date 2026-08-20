package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CuentaRegresivaTest {

    @Test
    @DisplayName("Cuenta regresiva desde 5: largo, primer y ultimo elemento")
    void regresivaDesdeCinco() {
        var r = CuentaRegresiva.regresiva(5);
        assertEquals(5, r.length);
        assertEquals(5, r[0]);
        assertEquals(1, r[4]);
    }

    @Test
    @DisplayName("Cuenta regresiva con n <= 0 devuelve arreglo vacio")
    void regresivaVacia() {
        assertEquals(0, CuentaRegresiva.regresiva(0).length);
        assertEquals(0, CuentaRegresiva.regresiva(-3).length);
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaTest {

    @Test
    @DisplayName("Depositar y extraer actualizan el saldo")
    void depositarYExtraer() {
        var cuenta = new CuentaBancaria();
        assertEquals(0.0, cuenta.getSaldo(), 1e-9);
        cuenta.depositar(1000);
        assertEquals(1000.0, cuenta.getSaldo(), 1e-9);
        cuenta.extraer(400);
        assertEquals(600.0, cuenta.getSaldo(), 1e-9);
    }

    @Test
    @DisplayName("Extraer más que el saldo lanza SaldoInsuficienteException")
    void saldoInsuficiente() {
        var cuenta = new CuentaBancaria(100);
        assertThrows(SaldoInsuficienteException.class, () -> cuenta.extraer(500));
        assertEquals(100.0, cuenta.getSaldo(), 1e-9);
    }

    @Test
    @DisplayName("Montos no positivos lanzan IllegalArgumentException")
    void montosInvalidos() {
        var cuenta = new CuentaBancaria();
        assertThrows(IllegalArgumentException.class, () -> cuenta.depositar(0));
        assertThrows(IllegalArgumentException.class, () -> cuenta.extraer(-5));
    }
}

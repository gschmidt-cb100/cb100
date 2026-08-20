package ar.uba.fi.cb100.material.i01_intro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias con JUnit 5. Cada método {@code @Test} verifica un
 * comportamiento concreto con aserciones.
 */
class TemperaturaTest {

    @Test
    @DisplayName("Clasifica correctamente cada franja de temperatura")
    void clasificaSegunLaFranja() {
        assertEquals("helado",   new Temperatura(-5).clasificar());
        assertEquals("frío",     new Temperatura(8).clasificar());
        assertEquals("templado", new Temperatura(21.5).clasificar());
        assertEquals("cálido",   new Temperatura(26).clasificar());
        assertEquals("caluroso", new Temperatura(35).clasificar());
    }

    @Test
    @DisplayName("Rechaza temperaturas por debajo del cero absoluto")
    void rechazaCeroAbsoluto() {
        assertThrows(IllegalArgumentException.class,
                () -> new Temperatura(-300));
    }

    @Test
    @DisplayName("Dos temperaturas con el mismo valor son iguales (record)")
    void recordDefineIgualdadPorValor() {
        assertEquals(new Temperatura(20), new Temperatura(20));
    }
}

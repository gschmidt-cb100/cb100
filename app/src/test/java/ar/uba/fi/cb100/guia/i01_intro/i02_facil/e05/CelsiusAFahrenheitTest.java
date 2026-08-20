package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CelsiusAFahrenheitTest {

    @Test
    @DisplayName("0 Celsius son 32 Fahrenheit")
    void ceroGrados() {
        assertEquals(32.0, CelsiusAFahrenheit.aFahrenheit(0), 1e-9);
    }

    @Test
    @DisplayName("100 Celsius son 212 Fahrenheit")
    void cienGrados() {
        assertEquals(212.0, CelsiusAFahrenheit.aFahrenheit(100), 1e-9);
    }

    @Test
    @DisplayName("37 Celsius son 98.6 Fahrenheit")
    void temperaturaCorporal() {
        assertEquals(98.6, CelsiusAFahrenheit.aFahrenheit(37), 1e-9);
    }
}

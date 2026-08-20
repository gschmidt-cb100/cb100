package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class IndicarSalidaTest {

    @Test
    @DisplayName("El escenario de aliasing y paso de parametros da {99, 7, 3}")
    void escenarioEsperado() {
        assertArrayEquals(new int[]{99, 7, 3}, IndicarSalida.escenario());
    }

    @Test
    @DisplayName("El escenario es determinista: dos corridas dan lo mismo")
    void escenarioDeterminista() {
        assertArrayEquals(IndicarSalida.escenario(), IndicarSalida.escenario());
    }
}

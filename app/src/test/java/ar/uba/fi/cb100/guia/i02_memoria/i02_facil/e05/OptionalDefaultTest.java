package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e05;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class OptionalDefaultTest {

    @Test
    @DisplayName("Optional presente devuelve su valor")
    void presenteDevuelveValor() {
        assertEquals("dato", OptionalDefault.orDefault(Optional.of("dato")));
    }

    @Test
    @DisplayName("Optional vacio devuelve el valor por defecto N/A")
    void vacioDevuelveDefault() {
        assertEquals("N/A", OptionalDefault.orDefault(Optional.empty()));
    }
}

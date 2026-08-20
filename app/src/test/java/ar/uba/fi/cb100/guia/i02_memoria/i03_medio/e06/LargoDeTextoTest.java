package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class LargoDeTextoTest {

    @Test
    @DisplayName("con Optional presente devuelve el largo del texto")
    void presenteDevuelveLargo() {
        assertEquals(4, LargoDeTexto.largoOrCero(Optional.of("hola")));
    }

    @Test
    @DisplayName("con Optional vacio devuelve cero")
    void vacioDevuelveCero() {
        assertEquals(0, LargoDeTexto.largoOrCero(Optional.empty()));
    }

    @Test
    @DisplayName("con texto vacio presente devuelve cero")
    void textoVacioPresenteDevuelveCero() {
        assertEquals(0, LargoDeTexto.largoOrCero(Optional.of("")));
    }
}

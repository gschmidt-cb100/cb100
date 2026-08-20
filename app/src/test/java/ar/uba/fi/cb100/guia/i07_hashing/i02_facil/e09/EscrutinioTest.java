package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EscrutinioTest {

    @Test
    @DisplayName("cuenta bien los votos de cada candidato")
    void cuentaVotos() {
        Map<String, Integer> conteo = Escrutinio.escrutinio(
                List.of("Ana", "Bruno", "Ana", "Carla", "Ana", "Bruno"));
        assertEquals(3, conteo.get("Ana"));
        assertEquals(2, conteo.get("Bruno"));
        assertEquals(1, conteo.get("Carla"));
        assertEquals(3, conteo.size());
    }

    @Test
    @DisplayName("sin votos el conteo queda vacio")
    void sinVotos() {
        assertTrue(Escrutinio.escrutinio(List.of()).isEmpty());
    }

    @Test
    @DisplayName("el ganador es quien tiene mas votos")
    void ganadorConMasVotos() {
        assertEquals("Ana", Escrutinio.ganador(Map.of("Ana", 3, "Bruno", 2, "Carla", 1)));
    }

    @Test
    @DisplayName("con conteo vacio el ganador es null")
    void ganadorDeVacio() {
        assertNull(Escrutinio.ganador(Map.of()));
    }

    @Test
    @DisplayName("escrutinio y ganador combinados")
    void combinados() {
        List<String> votos = List.of("X", "Y", "Y");
        assertEquals("Y", Escrutinio.ganador(Escrutinio.escrutinio(votos)));
    }
}

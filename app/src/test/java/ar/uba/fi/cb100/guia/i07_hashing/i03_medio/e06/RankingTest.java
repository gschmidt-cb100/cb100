package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RankingTest {

    @Test
    @DisplayName("Ordena por puntaje descendente")
    void ordenaPorPuntaje() {
        List<String> resultado = Ranking.ranking(Map.of("ana", 80, "beto", 95, "carla", 60));
        assertEquals(List.of("beto", "ana", "carla"), resultado);
    }

    @Test
    @DisplayName("Ante empate de puntaje desempata alfabéticamente ascendente")
    void desempataAlfabetico() {
        List<String> resultado = Ranking.ranking(Map.of("carla", 90, "ana", 90, "beto", 95));
        assertEquals(List.of("beto", "ana", "carla"), resultado);
    }

    @Test
    @DisplayName("Con mapa vacío devuelve lista vacía")
    void mapaVacio() {
        assertTrue(Ranking.ranking(Map.of()).isEmpty());
    }

    @Test
    @DisplayName("Con un solo jugador devuelve ese único nombre")
    void unSoloJugador() {
        assertEquals(List.of("ana"), Ranking.ranking(Map.of("ana", 42)));
    }

    @Test
    @DisplayName("Todos empatados: queda todo en orden alfabético")
    void todosEmpatados() {
        List<String> resultado = Ranking.ranking(Map.of("zoe", 10, "ana", 10, "mia", 10));
        assertEquals(List.of("ana", "mia", "zoe"), resultado);
    }
}

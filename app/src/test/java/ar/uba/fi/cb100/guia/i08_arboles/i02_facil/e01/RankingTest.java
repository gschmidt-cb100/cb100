package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class RankingTest {

    @Test
    @DisplayName("con cuatro jugadores devuelve los tres de mayor puntaje en orden")
    void topTresDeCuatro() {
        TreeMap<Integer, String> torneo = new TreeMap<>();
        torneo.put(870, "Mora");
        torneo.put(1200, "Julián");
        torneo.put(990, "Sofía");
        torneo.put(450, "Pedro");
        assertEquals(List.of("Julián", "Sofía", "Mora"), Ranking.topTres(torneo));
    }

    @Test
    @DisplayName("con dos jugadores devuelve los dos, del mejor al peor")
    void menosDeTres() {
        TreeMap<Integer, String> torneo = new TreeMap<>();
        torneo.put(100, "Ana");
        torneo.put(300, "Bruno");
        assertEquals(List.of("Bruno", "Ana"), Ranking.topTres(torneo));
    }

    @Test
    @DisplayName("mapa vacio devuelve lista vacia")
    void mapaVacio() {
        assertTrue(Ranking.topTres(new TreeMap<>()).isEmpty());
    }

    @Test
    @DisplayName("con exactamente tres jugadores devuelve los tres")
    void exactamenteTres() {
        TreeMap<Integer, String> torneo = new TreeMap<>();
        torneo.put(10, "Caro");
        torneo.put(20, "Dante");
        torneo.put(30, "Emma");
        assertEquals(List.of("Emma", "Dante", "Caro"), Ranking.topTres(torneo));
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RankingDeNotasTest {

    @Test
    @DisplayName("el promedio del curso del apunte es 6.5")
    void promedioDelCurso() {
        assertEquals(6.5, RankingDeNotas.promedio(RankingDeNotas.CURSO), 1e-9);
    }

    @Test
    @DisplayName("promedio de un curso vacio es 0, no divide por cero")
    void promedioVacio() {
        assertEquals(0.0, RankingDeNotas.promedio(List.of()), 1e-9);
    }

    @Test
    @DisplayName("los mejores tres son Dana, Ana y Carla en ese orden")
    void mejoresTres() {
        assertEquals(List.of("Dana", "Ana", "Carla"),
                RankingDeNotas.mejoresTres(RankingDeNotas.CURSO));
    }

    @Test
    @DisplayName("con menos de tres alumnos devuelve los que haya")
    void mejoresConPocos() {
        List<RankingDeNotas.Alumno> dos = List.of(
                new RankingDeNotas.Alumno("Ana", 9),
                new RankingDeNotas.Alumno("Beto", 4));
        assertEquals(List.of("Ana", "Beto"), RankingDeNotas.mejoresTres(dos));
    }

    @Test
    @DisplayName("la distribucion cuenta cuantos alumnos hay por nota")
    void distribucion() {
        Map<Integer, Long> d = RankingDeNotas.distribucion(RankingDeNotas.CURSO);
        assertEquals(6, d.size(), "todas las notas del curso son distintas");
        assertEquals(1L, d.get(9));
        assertEquals(1L, d.get(10));
        assertNull(d.get(5), "nadie se saco 5");
    }

    @Test
    @DisplayName("la distribucion agrupa notas repetidas")
    void distribucionConRepetidas() {
        List<RankingDeNotas.Alumno> curso = List.of(
                new RankingDeNotas.Alumno("Ana", 7),
                new RankingDeNotas.Alumno("Beto", 7),
                new RankingDeNotas.Alumno("Carla", 4));
        Map<Integer, Long> d = RankingDeNotas.distribucion(curso);
        assertEquals(2L, d.get(7));
        assertEquals(1L, d.get(4));
    }
}

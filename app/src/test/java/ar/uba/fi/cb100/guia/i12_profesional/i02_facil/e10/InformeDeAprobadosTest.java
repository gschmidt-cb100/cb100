package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InformeDeAprobadosTest {

    @Test
    @DisplayName("formato exacto del enunciado con Ana, Beto, Carla, Dana y Eva")
    void formatoExacto() {
        List<InformeDeAprobados.Alumno> curso = List.of(
                new InformeDeAprobados.Alumno("Ana", 9),
                new InformeDeAprobados.Alumno("Beto", 4),
                new InformeDeAprobados.Alumno("Carla", 7),
                new InformeDeAprobados.Alumno("Dana", 10),
                new InformeDeAprobados.Alumno("Eva", 3));
        assertEquals("Aprobados: Ana (9), Carla (7), Dana (10)",
                InformeDeAprobados.textoDeAprobados(curso));
    }

    @Test
    @DisplayName("con el curso completo del apunte, Fede (6) tambien aprueba")
    void cursoCompleto() {
        List<InformeDeAprobados.Alumno> curso = List.of(
                new InformeDeAprobados.Alumno("Ana", 9),
                new InformeDeAprobados.Alumno("Beto", 4),
                new InformeDeAprobados.Alumno("Carla", 7),
                new InformeDeAprobados.Alumno("Dana", 10),
                new InformeDeAprobados.Alumno("Eva", 3),
                new InformeDeAprobados.Alumno("Fede", 6));
        assertEquals("Aprobados: Ana (9), Carla (7), Dana (10), Fede (6)",
                InformeDeAprobados.textoDeAprobados(curso));
    }

    @Test
    @DisplayName("un solo aprobado: sin comas de mas")
    void unSoloAprobado() {
        assertEquals("Aprobados: Ana (9)",
                InformeDeAprobados.textoDeAprobados(
                        List.of(new InformeDeAprobados.Alumno("Ana", 9),
                                new InformeDeAprobados.Alumno("Eva", 3))));
    }

    @Test
    @DisplayName("nadie aprobo: queda solo el prefijo")
    void sinAprobados() {
        assertEquals("Aprobados: ",
                InformeDeAprobados.textoDeAprobados(
                        List.of(new InformeDeAprobados.Alumno("Eva", 3))));
    }
}

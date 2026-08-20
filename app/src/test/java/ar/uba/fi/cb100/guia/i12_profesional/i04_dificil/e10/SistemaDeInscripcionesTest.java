package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e10;

import ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e10.SistemaDeInscripciones.Materia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SistemaDeInscripcionesTest {

    private SistemaDeInscripciones sistema;

    @BeforeEach
    public void armarSistema() {
        sistema = new SistemaDeInscripciones(List.of(
                new Materia("Algoritmos 1", 10, List.of()),
                new Materia("Algoritmos 2", 2, List.of("Algoritmos 1")),
                new Materia("Sistemas Operativos", 3, List.of("Algoritmos 2"))));
        sistema.registrarAlumno("Ana", 9.0);
        sistema.registrarAlumno("Beto", 7.5);
        sistema.registrarAlumno("Carla", 8.2);
        sistema.registrarAlumno("Dario", 6.0);
        for (String alumno : List.of("Ana", "Beto", "Carla", "Dario")) {
            sistema.aprobar(alumno, "Algoritmos 1");
        }
    }

    @Test
    @DisplayName("un plan con ciclo de correlativas se rechaza al construir")
    public void planConCicloLanza() {
        assertThrows(IllegalArgumentException.class, () -> new SistemaDeInscripciones(List.of(
                new Materia("A", 10, List.of("B")),
                new Materia("B", 10, List.of("A")))));
    }

    @Test
    @DisplayName("sin la correlativa aprobada no hay inscripcion")
    public void correlativaFaltante() {
        assertThrows(IllegalStateException.class,
                () -> sistema.inscribir("Ana", "Sistemas Operativos"));
        // Con la correlativa aprobada, si.
        sistema.aprobar("Ana", "Algoritmos 2");
        assertTrue(sistema.inscribir("Ana", "Sistemas Operativos"));
    }

    @Test
    @DisplayName("cupo lleno: el tercero va a la lista de espera en orden FIFO")
    public void cupoYListaDeEspera() {
        assertTrue(sistema.inscribir("Ana", "Algoritmos 2"));
        assertTrue(sistema.inscribir("Beto", "Algoritmos 2"));
        assertFalse(sistema.inscribir("Carla", "Algoritmos 2"));   // llena: a la espera
        assertFalse(sistema.inscribir("Dario", "Algoritmos 2"));

        assertEquals(List.of("Ana", "Beto"), sistema.inscriptosDe("Algoritmos 2"));
        assertEquals(List.of("Carla", "Dario"), sistema.esperaDe("Algoritmos 2"));
    }

    @Test
    @DisplayName("una baja promueve al PRIMERO de la espera (no al de mejor promedio)")
    public void bajaYPromocion() {
        sistema.inscribir("Ana", "Algoritmos 2");
        sistema.inscribir("Beto", "Algoritmos 2");
        sistema.inscribir("Carla", "Algoritmos 2");   // espera 1ra
        sistema.inscribir("Dario", "Algoritmos 2");   // espera 2do

        sistema.bajar("Ana", "Algoritmos 2");

        assertEquals(List.of("Beto", "Carla"), sistema.inscriptosDe("Algoritmos 2"));
        assertEquals(List.of("Dario"), sistema.esperaDe("Algoritmos 2"));
    }

    @Test
    @DisplayName("bajarse de la espera no promueve a nadie")
    public void bajaDesdeLaEspera() {
        sistema.inscribir("Ana", "Algoritmos 2");
        sistema.inscribir("Beto", "Algoritmos 2");
        sistema.inscribir("Carla", "Algoritmos 2");   // a la espera

        sistema.bajar("Carla", "Algoritmos 2");

        assertEquals(List.of("Ana", "Beto"), sistema.inscriptosDe("Algoritmos 2"));
        assertTrue(sistema.esperaDe("Algoritmos 2").isEmpty());
    }

    @Test
    @DisplayName("el reporte ordena por promedio descendente dentro de cada materia")
    public void reporteOrdenadoPorPromedio() {
        sistema.inscribir("Beto", "Algoritmos 1");    // 7.5
        sistema.inscribir("Ana", "Algoritmos 1");     // 9.0
        sistema.inscribir("Dario", "Algoritmos 1");   // 6.0
        sistema.inscribir("Carla", "Algoritmos 1");   // 8.2

        Map<String, List<String>> reporte = sistema.reporte();

        assertEquals(List.of("Ana", "Carla", "Beto", "Dario"),
                reporte.get("Algoritmos 1"));
        assertEquals(List.of("Algoritmos 1", "Algoritmos 2", "Sistemas Operativos"),
                List.copyOf(reporte.keySet()));       // TreeMap: orden alfabetico
        assertTrue(reporte.get("Algoritmos 2").isEmpty());
    }

    @Test
    @DisplayName("validaciones: desconocidos, doble anotacion y correlativa inexistente")
    public void validaciones() {
        assertThrows(IllegalArgumentException.class,
                () -> sistema.inscribir("Zoe", "Algoritmos 1"));
        assertThrows(IllegalArgumentException.class,
                () -> sistema.inscribir("Ana", "Quimica"));
        sistema.inscribir("Ana", "Algoritmos 1");
        assertThrows(IllegalStateException.class,
                () -> sistema.inscribir("Ana", "Algoritmos 1"));
        assertThrows(IllegalArgumentException.class, () -> new SistemaDeInscripciones(
                List.of(new Materia("A", 10, List.of("Inexistente")))));
    }
}

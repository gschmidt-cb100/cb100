package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BuscadorDeAlumnosTest {

    private BuscadorDeAlumnos buscador;

    @BeforeEach
    void preparar() {
        buscador = new BuscadorDeAlumnos(List.of(
                new BuscadorDeAlumnos.Alumno("Ana", 9),
                new BuscadorDeAlumnos.Alumno("Beto", 4),
                new BuscadorDeAlumnos.Alumno("Carla", 7)));
    }

    @Test
    @DisplayName("si el alumno existe, el Optional viene lleno")
    void alumnoExistente() {
        Optional<BuscadorDeAlumnos.Alumno> ana = buscador.buscarPorNombre("Ana");
        assertTrue(ana.isPresent());
        assertEquals(9, ana.get().nota());
    }

    @Test
    @DisplayName("si el alumno no existe, el Optional viene vacio (no null)")
    void alumnoInexistente() {
        Optional<BuscadorDeAlumnos.Alumno> zoe = buscador.buscarPorNombre("Zoe");
        assertNotNull(zoe, "buscar devuelve un Optional, jamas null");
        assertTrue(zoe.isEmpty());
    }

    @Test
    @DisplayName("camino orElse: ausente cae en el valor por defecto")
    void caminoOrElse() {
        BuscadorDeAlumnos.Alumno porDefecto = new BuscadorDeAlumnos.Alumno("(sin datos)", 0);
        assertEquals(porDefecto, buscador.buscarPorNombre("Zoe").orElse(porDefecto));
        // Y si esta presente, orElse NO usa el defecto.
        assertEquals("Beto",
                buscador.buscarPorNombre("Beto").orElse(porDefecto).nombre());
    }

    @Test
    @DisplayName("camino orElseThrow: ausente lanza la excepcion elegida")
    void caminoOrElseThrow() {
        assertThrows(NoSuchElementException.class,
                () -> buscador.buscarPorNombre("Zoe")
                        .orElseThrow(() -> new NoSuchElementException("no cursa")));
        // Presente: no lanza y devuelve el alumno.
        assertDoesNotThrow(() -> buscador.buscarPorNombre("Carla")
                .orElseThrow(() -> new NoSuchElementException("no cursa")));
    }
}

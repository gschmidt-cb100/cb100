package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e03;

import ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e03.ColoreoDeIntervalos.Intervalo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ColoreoDeIntervalosTest {

    private final ColoreoDeIntervalos coloreador = new ColoreoDeIntervalos();

    /** Dos intervalos semiabiertos [a,b) y [c,d) solapan si a < d y c < b. */
    private boolean solapan(Intervalo x, Intervalo y) {
        return x.inicio() < y.fin() && y.inicio() < x.fin();
    }

    private void verificarSinConflictos(List<Intervalo> intervalos, Map<String, Integer> asignacion) {
        for (int i = 0; i < intervalos.size(); i++) {
            for (int j = i + 1; j < intervalos.size(); j++) {
                Intervalo a = intervalos.get(i);
                Intervalo b = intervalos.get(j);
                if (solapan(a, b)) {
                    assertNotEquals(asignacion.get(a.nombre()), asignacion.get(b.nombre()),
                            a.nombre() + " y " + b.nombre() + " solapan y comparten aula");
                }
            }
        }
    }

    @Test
    @DisplayName("Tres intervalos que solapan de a dos entran en 2 aulas y sin conflictos")
    void tresSolapadosDeADos() {
        List<Intervalo> clases = List.of(
                new Intervalo("A", 0, 10),
                new Intervalo("B", 5, 15),
                new Intervalo("C", 10, 20));
        Map<String, Integer> asignacion = coloreador.asignarAulas(clases);
        assertEquals(3, asignacion.size());
        assertEquals(2, coloreador.cantidadDeAulas(asignacion));
        verificarSinConflictos(clases, asignacion);
    }

    @Test
    @DisplayName("Intervalos disjuntos comparten una sola aula")
    void disjuntosUnaAula() {
        List<Intervalo> clases = List.of(
                new Intervalo("A", 0, 5),
                new Intervalo("B", 5, 10),
                new Intervalo("C", 10, 15));
        Map<String, Integer> asignacion = coloreador.asignarAulas(clases);
        assertEquals(1, coloreador.cantidadDeAulas(asignacion));
        verificarSinConflictos(clases, asignacion);
    }

    @Test
    @DisplayName("Tres intervalos que solapan todos entre si necesitan 3 aulas")
    void todosSolapadosTresAulas() {
        List<Intervalo> clases = List.of(
                new Intervalo("A", 0, 10),
                new Intervalo("B", 1, 9),
                new Intervalo("C", 2, 8));
        Map<String, Integer> asignacion = coloreador.asignarAulas(clases);
        assertEquals(3, coloreador.cantidadDeAulas(asignacion));
        verificarSinConflictos(clases, asignacion);
    }

    @Test
    @DisplayName("El minimo iguala al maximo de solapes simultaneos en un caso mixto")
    void casoMixto() {
        List<Intervalo> clases = List.of(
                new Intervalo("A", 0, 30),
                new Intervalo("B", 5, 10),
                new Intervalo("C", 10, 15),
                new Intervalo("D", 12, 20),
                new Intervalo("E", 25, 40));
        // En t = 12 conviven A, C y D: hacen falta 3 aulas y con 3 alcanza.
        Map<String, Integer> asignacion = coloreador.asignarAulas(clases);
        assertEquals(3, coloreador.cantidadDeAulas(asignacion));
        verificarSinConflictos(clases, asignacion);
    }

    @Test
    @DisplayName("La lista vacia produce una asignacion vacia con 0 aulas")
    void listaVacia() {
        Map<String, Integer> asignacion = coloreador.asignarAulas(List.of());
        assertTrue(asignacion.isEmpty());
        assertEquals(0, coloreador.cantidadDeAulas(asignacion));
    }

    @Test
    @DisplayName("Un intervalo con fin <= inicio lanza IllegalArgumentException")
    void intervaloInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Intervalo("X", 5, 5));
    }
}

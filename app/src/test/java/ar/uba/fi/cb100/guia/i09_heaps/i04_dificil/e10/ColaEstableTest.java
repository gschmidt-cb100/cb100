package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ColaEstableTest {

    /** Tarea con prioridad: dos tareas comparan SOLO por prioridad, no por nombre. */
    private record Tarea(int prioridad, String nombre) implements Comparable<Tarea> {
        @Override
        public int compareTo(Tarea otra) {
            return Integer.compare(this.prioridad, otra.prioridad);
        }
    }

    @Test
    @DisplayName("Los elementos con igual prioridad salen en orden de llegada (FIFO)")
    void empatadosSalenFifo() {
        ColaEstable<Tarea> cola = new ColaEstable<>();
        cola.encolar(new Tarea(5, "primera"));
        cola.encolar(new Tarea(5, "segunda"));
        cola.encolar(new Tarea(5, "tercera"));
        cola.encolar(new Tarea(5, "cuarta"));

        assertEquals("primera", cola.desencolar().nombre());
        assertEquals("segunda", cola.desencolar().nombre());
        assertEquals("tercera", cola.desencolar().nombre());
        assertEquals("cuarta", cola.desencolar().nombre());
    }

    @Test
    @DisplayName("Con prioridades mezcladas: primero por prioridad y entre iguales por llegada")
    void prioridadesMezcladas() {
        ColaEstable<Tarea> cola = new ColaEstable<>();
        cola.encolar(new Tarea(2, "a"));
        cola.encolar(new Tarea(1, "b"));
        cola.encolar(new Tarea(2, "c"));
        cola.encolar(new Tarea(1, "d"));
        cola.encolar(new Tarea(2, "e"));

        List<String> salida = new ArrayList<>();
        while (!cola.estaVacio()) {
            Tarea tarea = cola.desencolar();
            salida.add(tarea.prioridad() + ":" + tarea.nombre());
        }
        assertEquals(List.of("1:b", "1:d", "2:a", "2:c", "2:e"), salida);
    }

    @Test
    @DisplayName("La estabilidad sobrevive a muchos empates que fuerzan hundir con saltos")
    void estabilidadConMuchosEmpates() {
        ColaEstable<Tarea> cola = new ColaEstable<>();
        // 30 tareas de la misma prioridad: el heap pelado casi seguro las mezcla,
        // porque cada desencolar mueve la ultima hoja a la raiz.
        for (int i = 0; i < 30; i++) {
            cola.encolar(new Tarea(7, "t" + i));
        }
        for (int i = 0; i < 30; i++) {
            assertEquals("t" + i, cola.desencolar().nombre());
        }
        assertTrue(cola.estaVacio());
    }

    @Test
    @DisplayName("verPrimero muestra al proximo sin sacarlo y respeta el desempate")
    void verPrimero() {
        ColaEstable<Tarea> cola = new ColaEstable<>();
        cola.encolar(new Tarea(3, "x"));
        cola.encolar(new Tarea(3, "y"));
        assertEquals("x", cola.verPrimero().nombre());
        assertEquals(2, cola.tamanio()); // No saco nada.
    }

    @Test
    @DisplayName("Desencolar o verPrimero sobre una cola vacia lanza IllegalStateException")
    void vaciaLanza() {
        ColaEstable<Integer> cola = new ColaEstable<>();
        assertThrows(IllegalStateException.class, cola::verPrimero);
        assertThrows(IllegalStateException.class, cola::desencolar);
    }
}

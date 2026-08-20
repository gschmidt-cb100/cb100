package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanificadorTest {

    @Test
    @DisplayName("Ejecuta primero los procesos de prioridad menor")
    void ordenPorPrioridad() {
        Planificador plan = new Planificador();
        plan.agregar(new Proceso(10, 5, 0));
        plan.agregar(new Proceso(20, 1, 1));
        plan.agregar(new Proceso(30, 3, 2));
        assertEquals(List.of(20, 30, 10), plan.ejecutarTodos());
    }

    @Test
    @DisplayName("A igual prioridad gana el que llegó antes")
    void empatePorLlegada() {
        Planificador plan = new Planificador();
        plan.agregar(new Proceso(1, 2, 50));
        plan.agregar(new Proceso(2, 2, 10));
        plan.agregar(new Proceso(3, 2, 30));
        assertEquals(List.of(2, 3, 1), plan.ejecutarTodos());
    }

    @Test
    @DisplayName("El orden de agregado no importa, solo prioridad y llegada")
    void ordenDeAgregadoIrrelevante() {
        Planificador plan = new Planificador();
        plan.agregar(new Proceso(100, 3, 0));
        plan.agregar(new Proceso(200, 1, 1));
        plan.agregar(new Proceso(300, 3, 2));
        plan.agregar(new Proceso(400, 2, 3));
        assertEquals(List.of(200, 400, 100, 300), plan.ejecutarTodos());
    }

    @Test
    @DisplayName("Después de ejecutarTodos la cola queda vacía")
    void quedaVacio() {
        Planificador plan = new Planificador();
        plan.agregar(new Proceso(7, 1, 0));
        assertEquals(1, plan.pendientes());
        plan.ejecutarTodos();
        assertEquals(0, plan.pendientes());
        assertEquals(List.of(), plan.ejecutarTodos());
    }
}

package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class SubirPrioridadTest {

    @Test
    @DisplayName("tras reprioritizar, el pedido sale primero")
    void reprioritizadoSalePrimero() {
        PriorityQueue<SubirPrioridad.Pedido> cola = SubirPrioridad.nuevaCola();
        cola.offer(new SubirPrioridad.Pedido("P-1", 5));
        cola.offer(new SubirPrioridad.Pedido("P-2", 3));
        cola.offer(new SubirPrioridad.Pedido("P-3", 8));

        assertTrue(SubirPrioridad.reprioritizar(cola, "P-3", 1));
        assertEquals("P-3", cola.poll().id());
    }

    @Test
    @DisplayName("reprioritizar no cambia la cantidad de pedidos")
    void mantieneElTamano() {
        PriorityQueue<SubirPrioridad.Pedido> cola = SubirPrioridad.nuevaCola();
        cola.offer(new SubirPrioridad.Pedido("A", 2));
        cola.offer(new SubirPrioridad.Pedido("B", 7));
        SubirPrioridad.reprioritizar(cola, "B", 1);
        assertEquals(2, cola.size());
    }

    @Test
    @DisplayName("reprioritizar un id inexistente devuelve false y no agrega nada")
    void idInexistenteDevuelveFalse() {
        PriorityQueue<SubirPrioridad.Pedido> cola = SubirPrioridad.nuevaCola();
        cola.offer(new SubirPrioridad.Pedido("A", 2));
        assertFalse(SubirPrioridad.reprioritizar(cola, "ZZZ", 1));
        assertEquals(1, cola.size());
    }

    @Test
    @DisplayName("bajar la prioridad tambien reubica: el pedido deja de ser cabeza")
    void bajarPrioridadReubica() {
        PriorityQueue<SubirPrioridad.Pedido> cola = SubirPrioridad.nuevaCola();
        cola.offer(new SubirPrioridad.Pedido("A", 1));
        cola.offer(new SubirPrioridad.Pedido("B", 4));
        SubirPrioridad.reprioritizar(cola, "A", 9);
        assertEquals("B", cola.poll().id());
        assertEquals("A", cola.poll().id());
    }
}

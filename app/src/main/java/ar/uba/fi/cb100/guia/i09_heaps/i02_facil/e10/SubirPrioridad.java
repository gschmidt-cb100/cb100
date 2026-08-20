package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e10;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * e10: la {@link PriorityQueue} de Java NO tiene una operación para cambiar
 * la prioridad de un elemento que ya está adentro (el "decrease-key" de los
 * heaps de la teoría). El truco práctico es: removerlo con
 * {@link PriorityQueue#removeIf} y volver a encolarlo con la prioridad nueva.
 *
 * <p>Ojo con el costo: {@code removeIf} recorre el heap completo, así que
 * la operación es O(n), no O(log n). Para un sistema con muchísimos cambios
 * de prioridad haría falta un heap indexado (tema del nivel difícil).</p>
 */
public final class SubirPrioridad {

    private SubirPrioridad() {
    }

    /** Un pedido identificado por id, con prioridad numérica (menor = más urgente). */
    public record Pedido(String id, int prioridad) {
    }

    /**
     * Crea una cola de pedidos donde sale primero el de MENOR número de prioridad.
     *
     * @return cola de prioridad vacía, ordenada por prioridad ascendente
     */
    public static PriorityQueue<Pedido> nuevaCola() {
        return new PriorityQueue<>(Comparator.comparingInt(Pedido::prioridad));
    }

    /**
     * Cambia la prioridad del pedido con id {@code id}: lo remueve de la cola
     * y lo vuelve a encolar con {@code nuevaPrioridad}, para que el heap lo
     * reubique donde corresponde.
     *
     * @param cola           cola de pedidos (se modifica)
     * @param id             id del pedido a reubicar
     * @param nuevaPrioridad prioridad nueva (menor número = más urgente)
     * @return {@code true} si el pedido estaba y fue reubicado, {@code false} si no estaba
     */
    public static boolean reprioritizar(PriorityQueue<Pedido> cola, String id, int nuevaPrioridad) {
        // removeIf recorre todo el heap y saca los que cumplen la condición.
        boolean estaba = cola.removeIf(p -> p.id().equals(id));
        if (estaba) {
            cola.offer(new Pedido(id, nuevaPrioridad));
        }
        return estaba;
    }

    public static void main(String[] args) {
        PriorityQueue<Pedido> cola = nuevaCola();
        cola.offer(new Pedido("P-1", 5));
        cola.offer(new Pedido("P-2", 3));
        cola.offer(new Pedido("P-3", 8));
        System.out.println("Cabeza antes: " + cola.peek());
        reprioritizar(cola, "P-3", 1); // P-3 pasa a ser el mas urgente
        System.out.println("Cabeza despues de subirle la prioridad a P-3: " + cola.peek());
    }
}

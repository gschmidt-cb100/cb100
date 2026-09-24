package ar.uba.fi.cb100.material.i05_lineales;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * Pila y cola con la API de Java. Para las dos se usa la misma clase,
 * {@code ArrayDeque}: una cola de dos puntas sobre un arreglo circular. Lo que
 * cambia es <b>qué métodos se usan</b>, y por eso conviene declararla con la
 * interfaz que corresponde, para que el compilador no deje mezclar.
 *
 * <p>No se usa {@code java.util.Stack}: es de Java 1.0, hereda de
 * {@code Vector}, sincroniza cada método sin necesidad, y su propio javadoc
 * recomienda usar {@code Deque}.
 */
public class DemoPilaYCola {

    public static void main(String[] args) {

        // ------------------------------------------------------------ PILA (LIFO)
        Deque<String> pila = new ArrayDeque<>();
        pila.push("a");                   // apilar
        pila.push("b");
        pila.push("c");                   // tope = c
        System.out.println("pila:      " + pila + "   (el primero impreso es el tope)");
        System.out.println("peek:      " + pila.peek());        // mira el tope sin sacar
        System.out.println("pop:       " + pila.pop());         // saca el tope: c
        System.out.println("pop:       " + pila.pop());         // b  (LIFO)
        System.out.println("quedan:    " + pila + "  vacía? " + pila.isEmpty());

        // ------------------------------------------------------------ COLA (FIFO)
        Queue<String> cola = new ArrayDeque<>();
        cola.offer("a");                  // encolar por el fondo
        cola.offer("b");
        cola.offer("c");                  // frente = a
        System.out.println("cola:      " + cola + "   (el primero impreso es el frente)");
        System.out.println("peek:      " + cola.peek());        // mira el frente sin sacar
        System.out.println("poll:      " + cola.poll());        // saca el frente: a
        System.out.println("poll:      " + cola.poll());        // b  (FIFO)
        System.out.println("quedan:    " + cola + "  vacía? " + cola.isEmpty());

        // poll() sobre cola vacía devuelve null; remove() lanza excepción.
        cola.poll();
        System.out.println("poll vacía: " + cola.poll());

        // ------------------------------------------------------------ las dos puntas
        // Deque ("double-ended queue") tiene los dos juegos de métodos con nombre
        // explícito. Es lo mismo que arriba, dicho sin ambigüedad.
        Deque<Integer> ambas = new ArrayDeque<>();
        ambas.addFirst(2);
        ambas.addFirst(1);                // por el frente
        ambas.addLast(3);                 // por el fondo
        System.out.println("deque:     " + ambas);
        System.out.println("pollFirst: " + ambas.pollFirst() + "   pollLast: " + ambas.pollLast());
        System.out.println("quedan:    " + ambas);
    }
}

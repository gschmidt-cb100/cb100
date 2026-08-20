package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e09: una cola de prioridad con capacidad fija que retiene los k mejores.
 *
 * <p>"Mejor" acá significa MENOR según el orden natural de T (pensá en
 * tiempos de carrera: menos es mejor). Para poder echar rápido al PEOR de
 * los retenidos usamos un <b>max</b>-heap interno: su raíz es el peor
 * elemento guardado. Cuando la cola está llena, un candidato entra solo si
 * es estrictamente mejor (menor) que esa raíz, a la que desplaza.</p>
 *
 * <p>Es el mismo patrón de "top-k de un stream" (e01), empaquetado como
 * TDA genérico y reutilizable: cada {@code ofrecer} cuesta O(log k) y la
 * memoria queda acotada en O(k) sin importar cuántos candidatos pasen.</p>
 *
 * @param <T> tipo de los elementos, comparable por orden natural
 */
public class ColaAcotada<T extends Comparable<T>> {

    private final int capacidad;

    /** Max-heap: la raíz es el PEOR (mayor) de los elementos retenidos. */
    private final PriorityQueue<T> peores;

    /**
     * Crea una cola que retiene a lo sumo {@code capacidad} elementos,
     * los menores entre todos los ofrecidos.
     *
     * @param capacidad cantidad máxima de elementos, debe ser &gt;= 1
     * @throws IllegalArgumentException si capacidad &lt; 1
     */
    public ColaAcotada(int capacidad) {
        if (capacidad < 1) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser >= 1, vino " + capacidad);
        }
        this.capacidad = capacidad;
        this.peores = new PriorityQueue<>(capacidad, Comparator.reverseOrder());
    }

    /**
     * Ofrece un elemento. Si hay lugar, entra siempre; si la cola está
     * llena, entra solo si es menor que el peor elemento retenido
     * (al que desplaza).
     *
     * @param elemento candidato a retener
     * @return true si el elemento quedó en la cola, false si fue rechazado
     */
    public boolean ofrecer(T elemento) {
        if (peores.size() < capacidad) {
            peores.offer(elemento);
            return true;
        }
        if (elemento.compareTo(peores.peek()) < 0) {
            // Mejor que el peor retenido: lo desplaza.
            peores.poll();
            peores.offer(elemento);
            return true;
        }
        return false;
    }

    /**
     * Devuelve los elementos retenidos, de mejor a peor (ascendente).
     * No modifica la cola.
     *
     * @return copia ordenada del contenido
     */
    public List<T> contenidoOrdenado() {
        List<T> contenido = new ArrayList<>(peores);
        Collections.sort(contenido);
        return contenido;
    }

    /** @return cuántos elementos hay retenidos */
    public int tamanio() {
        return peores.size();
    }

    public static void main(String[] args) {
        // Los 3 mejores tiempos (en segundos) de una serie de intentos.
        ColaAcotada<Integer> mejores = new ColaAcotada<>(3);
        for (int tiempo : new int[] {52, 47, 60, 45, 58, 41}) {
            boolean entro = mejores.ofrecer(tiempo);
            System.out.println("Ofrezco " + tiempo + " → "
                    + (entro ? "entra" : "rechazado")
                    + ", retenidos: " + mejores.contenidoOrdenado());
        }
    }
}

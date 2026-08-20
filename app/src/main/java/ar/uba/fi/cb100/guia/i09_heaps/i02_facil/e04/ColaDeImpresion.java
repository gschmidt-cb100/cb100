package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e04;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * e04: una cola de impresión que atiende primero los trabajos más cortos
 * (el clásico "shortest job first"). El heap queda encapsulado como estado
 * interno del TDA: quien usa la clase no sabe (ni le importa) que adentro
 * hay una {@link PriorityQueue}.
 */
public class ColaDeImpresion {

    /** Un trabajo de impresión: nombre del documento y cantidad de páginas. */
    public record Trabajo(String nombre, int paginas) {
    }

    /** Estado interno: min-heap por cantidad de páginas. */
    private final PriorityQueue<Trabajo> pendientes =
            new PriorityQueue<>(Comparator.comparingInt(Trabajo::paginas));

    /**
     * Encola un trabajo de impresión.
     *
     * @param nombre  nombre del documento
     * @param paginas cantidad de páginas (debe ser positiva)
     * @throws IllegalArgumentException si {@code paginas} no es positiva
     */
    public void agregar(String nombre, int paginas) {
        if (paginas <= 0) {
            throw new IllegalArgumentException("Las paginas deben ser positivas: " + paginas);
        }
        pendientes.offer(new Trabajo(nombre, paginas));
    }

    /**
     * Imprime (saca de la cola) el trabajo con MENOS páginas y devuelve su nombre.
     * Si hay empate en páginas, puede salir cualquiera de los empatados.
     *
     * @return nombre del trabajo impreso
     * @throws NoSuchElementException si no hay trabajos pendientes
     */
    public String imprimirSiguiente() {
        if (pendientes.isEmpty()) {
            throw new NoSuchElementException("No hay trabajos pendientes");
        }
        return pendientes.poll().nombre();
    }

    /**
     * @return cantidad de trabajos que quedan en la cola
     */
    public int pendientes() {
        return pendientes.size();
    }

    public static void main(String[] args) {
        ColaDeImpresion cola = new ColaDeImpresion();
        cola.agregar("tesis.pdf", 120);
        cola.agregar("recibo.pdf", 1);
        cola.agregar("apunte.pdf", 35);
        System.out.println("Primero sale: " + cola.imprimirSiguiente());
        System.out.println("Despues sale: " + cola.imprimirSiguiente());
        System.out.println("Quedan pendientes: " + cola.pendientes());
    }
}

package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e04;

/**
 * Problema de Josephus resuelto con una lista circular propia (nodos que se
 * enlazan formando un anillo).
 *
 * n personas numeradas de 0 a n-1 forman un circulo. Empezando a contar desde
 * la posicion 0, se elimina cada k-esima persona; el conteo continua desde la
 * siguiente a la eliminada. El proceso sigue hasta que queda una sola persona.
 *
 * CONVENCION: las posiciones son 0-based. sobreviviente(n, k) devuelve la
 * posicion (0-based) del unico sobreviviente.
 *
 * Complejidad: se realizan n-1 eliminaciones y para cada una se avanzan k-1
 * pasos, por lo tanto O(n*k).
 */
public class Josephus {

    /** Nodo de la lista circular. */
    private static final class Nodo {
        final int posicion;
        Nodo siguiente;

        Nodo(int posicion) {
            this.posicion = posicion;
        }
    }

    private Josephus() {
        // Clase utilitaria: no se instancia.
    }

    /**
     * Devuelve la posicion (0-based) del sobreviviente eliminando cada k-esimo.
     *
     * @param n cantidad de personas (n >= 1)
     * @param k paso de conteo (k >= 1)
     */
    public static int sobreviviente(int n, int k) {
        if (n < 1) {
            throw new IllegalArgumentException("n debe ser >= 1");
        }
        if (k < 1) {
            throw new IllegalArgumentException("k debe ser >= 1");
        }

        // Construyo el anillo 0 -> 1 -> ... -> n-1 -> 0.
        Nodo cabeza = new Nodo(0);
        Nodo ultimo = cabeza;
        for (int i = 1; i < n; i++) {
            ultimo.siguiente = new Nodo(i);
            ultimo = ultimo.siguiente;
        }
        ultimo.siguiente = cabeza; // cierro el circulo

        // 'previo' es el nodo anterior al actual, para poder desenlazar.
        Nodo previo = ultimo;      // anterior a cabeza
        Nodo actual = cabeza;
        int restantes = n;

        while (restantes > 1) {
            // Avanzo k-1 pasos para dejar 'actual' sobre el k-esimo.
            for (int paso = 0; paso < k - 1; paso++) {
                previo = actual;
                actual = actual.siguiente;
            }
            // Elimino 'actual' desenlazandolo.
            previo.siguiente = actual.siguiente;
            actual = actual.siguiente; // el conteo sigue desde el siguiente
            restantes--;
        }
        return actual.posicion;
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        // Caso clasico: n=7, k=3 -> sobreviviente en posicion 3 (0-based).
        System.out.println("sobreviviente(7, 3) = " + sobreviviente(7, 3));
        System.out.println("sobreviviente(1, 5) = " + sobreviviente(1, 5));
        System.out.println("sobreviviente(5, 1) = " + sobreviviente(5, 1));
    }
}

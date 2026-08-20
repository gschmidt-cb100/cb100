package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e09;

import java.util.ArrayList;
import java.util.List;

/**
 * Multi-mapa: un diccionario donde cada clave puede tener VARIOS valores.
 *
 * Implementado A MANO sobre una tabla de hash con encadenamiento propia:
 * cada nodo de la cadena guarda una clave y la LISTA de valores asociados.
 * Ejemplo tipico: autor -> lista de libros, materia -> lista de alumnos.
 *
 * tamanio() cuenta la cantidad de pares (clave, valor), no la cantidad de
 * claves distintas: si "borges" tiene 3 libros, aporta 3 al tamanio.
 *
 * Complejidad esperada: agregar y valoresDe en O(1) (mas el largo de la
 * lista para copiarla); quitar en O(1 + largo de la lista).
 */
public class MultiMapa<K, V> {

    private static final int CAPACIDAD_INICIAL = 8;
    private static final double ALFA_MAXIMO = 0.75;

    /** Nodo de la cadena: una clave, sus valores y el siguiente nodo. */
    private static class Nodo<K, V> {
        final K clave;
        final List<V> valores;
        Nodo<K, V> siguiente;

        Nodo(K clave, Nodo<K, V> siguiente) {
            this.clave = clave;
            this.valores = new ArrayList<>();
            this.siguiente = siguiente;
        }
    }

    private Nodo<K, V>[] cadenas;
    /** Cantidad de claves distintas (para el factor de carga). */
    private int claves;
    /** Cantidad total de pares (clave, valor). */
    private int pares;

    @SuppressWarnings("unchecked")
    public MultiMapa() {
        this.cadenas = (Nodo<K, V>[]) new Nodo[CAPACIDAD_INICIAL];
        this.claves = 0;
        this.pares = 0;
    }

    /** Cantidad total de pares (clave, valor). O(1). */
    public int tamanio() {
        return pares;
    }

    private int indiceDe(K clave, int capacidad) {
        return Math.floorMod(clave.hashCode(), capacidad);
    }

    private Nodo<K, V> buscarNodo(K clave) {
        int i = indiceDe(clave, cadenas.length);
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                return nodo;
            }
        }
        return null;
    }

    /** Agrega el par (clave, valor). Una clave admite valores repetidos. */
    public void agregar(K clave, V valor) {
        Nodo<K, V> nodo = buscarNodo(clave);
        if (nodo == null) {
            int i = indiceDe(clave, cadenas.length);
            nodo = new Nodo<>(clave, cadenas[i]);
            cadenas[i] = nodo;
            claves++;
            if ((double) claves / cadenas.length > ALFA_MAXIMO) {
                rehash(cadenas.length * 2);
            }
        }
        nodo.valores.add(valor);
        pares++;
    }

    /**
     * Devuelve una COPIA de la lista de valores de la clave (vacia si la
     * clave no esta). Se copia para que el que llama no pueda modificar la
     * lista interna del multi-mapa.
     */
    public List<V> valoresDe(K clave) {
        Nodo<K, V> nodo = buscarNodo(clave);
        if (nodo == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(nodo.valores);
    }

    /**
     * Quita UNA aparicion del par (clave, valor). Devuelve true si el par
     * estaba. Si la clave se queda sin valores, se saca de la tabla.
     */
    public boolean quitar(K clave, V valor) {
        Nodo<K, V> nodo = buscarNodo(clave);
        if (nodo == null || !nodo.valores.remove(valor)) {
            return false;
        }
        pares--;
        if (nodo.valores.isEmpty()) {
            quitarNodo(clave);
        }
        return true;
    }

    /** Desengancha de su cadena el nodo de la clave (que ya quedo vacio). */
    private void quitarNodo(K clave) {
        int i = indiceDe(clave, cadenas.length);
        Nodo<K, V> anterior = null;
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                if (anterior == null) {
                    cadenas[i] = nodo.siguiente;
                } else {
                    anterior.siguiente = nodo.siguiente;
                }
                claves--;
                return;
            }
            anterior = nodo;
        }
    }

    @SuppressWarnings("unchecked")
    private void rehash(int nuevaCapacidad) {
        Nodo<K, V>[] viejas = cadenas;
        cadenas = (Nodo<K, V>[]) new Nodo[nuevaCapacidad];
        for (Nodo<K, V> cabeza : viejas) {
            Nodo<K, V> nodo = cabeza;
            while (nodo != null) {
                Nodo<K, V> siguiente = nodo.siguiente;
                int i = indiceDe(nodo.clave, nuevaCapacidad);
                nodo.siguiente = cadenas[i]; // Reengancho el mismo nodo.
                cadenas[i] = nodo;
                nodo = siguiente;
            }
        }
    }

    /** Demostracion: autores con varios libros. */
    public static void main(String[] args) {
        MultiMapa<String, String> biblioteca = new MultiMapa<>();
        biblioteca.agregar("borges", "Ficciones");
        biblioteca.agregar("borges", "El Aleph");
        biblioteca.agregar("cortazar", "Rayuela");
        System.out.println("pares: " + biblioteca.tamanio());
        System.out.println("borges -> " + biblioteca.valoresDe("borges"));
        biblioteca.quitar("borges", "Ficciones");
        System.out.println("borges -> " + biblioteca.valoresDe("borges"));
        System.out.println("quiroga -> " + biblioteca.valoresDe("quiroga"));
    }
}

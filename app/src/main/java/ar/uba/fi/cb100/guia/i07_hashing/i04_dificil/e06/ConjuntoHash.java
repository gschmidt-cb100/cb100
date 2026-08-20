package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e06;

/**
 * Conjunto (set) implementado A MANO sobre una tabla de hash con
 * encadenamiento propia.
 *
 * Un conjunto no guarda pares clave-valor: solo le importa si un elemento
 * ESTA o NO ESTA. Por eso la tabla interna guarda unicamente los elementos
 * en sus cadenas, sin valores asociados.
 *
 * La tabla interna duplica su capacidad cuando el factor de carga supera
 * 0.75, igual que una tabla de hash comun.
 *
 * Complejidad esperada: agregar, contiene y quitar en O(1); peor caso O(n).
 */
public class ConjuntoHash<T> {

    private static final int CAPACIDAD_INICIAL = 8;
    private static final double ALFA_MAXIMO = 0.75;

    /** Nodo de la cadena: un elemento y el siguiente de la lista. */
    private static class Nodo<T> {
        final T elemento;
        Nodo<T> siguiente;

        Nodo(T elemento, Nodo<T> siguiente) {
            this.elemento = elemento;
            this.siguiente = siguiente;
        }
    }

    private Nodo<T>[] cadenas;
    private int tamanio;

    @SuppressWarnings("unchecked")
    public ConjuntoHash() {
        this.cadenas = (Nodo<T>[]) new Nodo[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    /** Cantidad de elementos del conjunto. O(1). */
    public int tamanio() {
        return tamanio;
    }

    private int indiceDe(T elemento, int capacidad) {
        return Math.floorMod(elemento.hashCode(), capacidad);
    }

    /**
     * Agrega el elemento al conjunto. Devuelve true si lo agrego y false si
     * ya estaba (los conjuntos no admiten repetidos).
     */
    public boolean agregar(T elemento) {
        int i = indiceDe(elemento, cadenas.length);
        for (Nodo<T> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.elemento.equals(elemento)) {
                return false; // Ya estaba: el conjunto no cambia.
            }
        }
        cadenas[i] = new Nodo<>(elemento, cadenas[i]);
        tamanio++;
        if ((double) tamanio / cadenas.length > ALFA_MAXIMO) {
            rehash(cadenas.length * 2);
        }
        return true;
    }

    /** Indica si el elemento pertenece al conjunto. */
    public boolean contiene(T elemento) {
        int i = indiceDe(elemento, cadenas.length);
        for (Nodo<T> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.elemento.equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Quita el elemento del conjunto. Devuelve true si estaba (y lo saco)
     * o false si no estaba.
     */
    public boolean quitar(T elemento) {
        int i = indiceDe(elemento, cadenas.length);
        Nodo<T> anterior = null;
        for (Nodo<T> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.elemento.equals(elemento)) {
                if (anterior == null) {
                    cadenas[i] = nodo.siguiente;
                } else {
                    anterior.siguiente = nodo.siguiente;
                }
                tamanio--;
                return true;
            }
            anterior = nodo;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void rehash(int nuevaCapacidad) {
        Nodo<T>[] viejas = cadenas;
        cadenas = (Nodo<T>[]) new Nodo[nuevaCapacidad];
        for (Nodo<T> cabeza : viejas) {
            for (Nodo<T> nodo = cabeza; nodo != null; nodo = nodo.siguiente) {
                int i = indiceDe(nodo.elemento, nuevaCapacidad);
                cadenas[i] = new Nodo<>(nodo.elemento, cadenas[i]);
            }
        }
    }

    /** Demostracion: los repetidos no entran dos veces. */
    public static void main(String[] args) {
        ConjuntoHash<String> visitados = new ConjuntoHash<>();
        String[] recorrido = { "obelisco", "caminito", "obelisco", "rosedal", "caminito" };
        for (String lugar : recorrido) {
            boolean nuevo = visitados.agregar(lugar);
            System.out.println(lugar + (nuevo ? " (primera vez)" : " (repetido)"));
        }
        System.out.println("lugares distintos: " + visitados.tamanio());
        visitados.quitar("rosedal");
        System.out.println("contiene rosedal? " + visitados.contiene("rosedal"));
    }
}

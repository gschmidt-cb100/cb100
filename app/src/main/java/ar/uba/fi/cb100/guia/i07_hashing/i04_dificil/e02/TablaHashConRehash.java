package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e02;

/**
 * Tabla de hash con encadenamiento y REHASH automatico.
 *
 * Igual que la tabla del ejercicio 1, pero cuando el factor de carga
 * alfa = tamanio / capacidad supera 0.75, se duplica la capacidad y se
 * redistribuyen TODOS los pares (cada clave puede caer en otro indice
 * porque cambia el modulo).
 *
 * Complejidad: poner sigue siendo O(1) amortizado. El rehash puntual es
 * O(n + m), pero como se duplica la capacidad, ese costo se reparte entre
 * las inserciones (mismo argumento que el vector dinamico).
 */
public class TablaHashConRehash<K, V> {

    /** Umbral de factor de carga que dispara el rehash. */
    private static final double ALFA_MAXIMO = 0.75;

    /** Nodo de la cadena: un par clave-valor y el siguiente de la lista. */
    private static class Nodo<K, V> {
        final K clave;
        V valor;
        Nodo<K, V> siguiente;

        Nodo(K clave, V valor, Nodo<K, V> siguiente) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = siguiente;
        }
    }

    private Nodo<K, V>[] cadenas;
    private int tamanio;

    /** Crea una tabla con la capacidad inicial indicada. */
    @SuppressWarnings("unchecked")
    public TablaHashConRehash(int capacidadInicial) {
        if (capacidadInicial <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser positiva: " + capacidadInicial);
        }
        this.cadenas = (Nodo<K, V>[]) new Nodo[capacidadInicial];
        this.tamanio = 0;
    }

    /** Cantidad de pares clave-valor almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Capacidad actual del arreglo interno. O(1). */
    public int capacidad() {
        return cadenas.length;
    }

    /** Factor de carga actual: alfa = tamanio / capacidad. O(1). */
    public double factorDeCarga() {
        return (double) tamanio / cadenas.length;
    }

    private int indiceDe(K clave, int capacidad) {
        return Math.floorMod(clave.hashCode(), capacidad);
    }

    /** Asocia el valor a la clave (reemplaza si ya estaba) y rehashea si hace falta. */
    public void poner(K clave, V valor) {
        int i = indiceDe(clave, cadenas.length);
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                nodo.valor = valor;
                return;
            }
        }
        cadenas[i] = new Nodo<>(clave, valor, cadenas[i]);
        tamanio++;
        if (factorDeCarga() > ALFA_MAXIMO) {
            rehash(cadenas.length * 2);
        }
    }

    /** Devuelve el valor asociado a la clave, o null si no esta. */
    public V obtener(K clave) {
        int i = indiceDe(clave, cadenas.length);
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                return nodo.valor;
            }
        }
        return null;
    }

    /** Quita la clave. Devuelve el valor que tenia, o null si no estaba. */
    public V quitar(K clave) {
        int i = indiceDe(clave, cadenas.length);
        Nodo<K, V> anterior = null;
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                if (anterior == null) {
                    cadenas[i] = nodo.siguiente;
                } else {
                    anterior.siguiente = nodo.siguiente;
                }
                tamanio--;
                return nodo.valor;
            }
            anterior = nodo;
        }
        return null;
    }

    /**
     * Redistribuye todos los pares en un arreglo nuevo de mayor capacidad.
     * Hay que recalcular el indice de cada clave con el modulo nuevo.
     */
    @SuppressWarnings("unchecked")
    private void rehash(int nuevaCapacidad) {
        Nodo<K, V>[] viejas = cadenas;
        cadenas = (Nodo<K, V>[]) new Nodo[nuevaCapacidad];
        for (Nodo<K, V> cabeza : viejas) {
            for (Nodo<K, V> nodo = cabeza; nodo != null; nodo = nodo.siguiente) {
                int i = indiceDe(nodo.clave, nuevaCapacidad);
                cadenas[i] = new Nodo<>(nodo.clave, nodo.valor, cadenas[i]);
            }
        }
    }

    /** Demostracion: la tabla crece sola al superar alfa = 0.75. */
    public static void main(String[] args) {
        TablaHashConRehash<String, Integer> tabla = new TablaHashConRehash<>(4);
        for (int i = 0; i < 20; i++) {
            tabla.poner("clave" + i, i);
            System.out.printf("n=%d capacidad=%d alfa=%.2f%n",
                    tabla.tamanio(), tabla.capacidad(), tabla.factorDeCarga());
        }
        System.out.println("clave13 -> " + tabla.obtener("clave13"));
    }
}

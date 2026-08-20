package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e07;

/**
 * Tabla de hash con encadenamiento (replica de la del ejercicio 1 para que
 * este paquete sea autocontenido). Capacidad fija, sin rehash.
 */
public class TablaHash<K, V> {

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

    private final Nodo<K, V>[] cadenas;
    private int tamanio;

    /** Crea una tabla con la capacidad indicada (fija, sin rehash). */
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva: " + capacidad);
        }
        this.cadenas = (Nodo<K, V>[]) new Nodo[capacidad];
        this.tamanio = 0;
    }

    /** Cantidad de pares clave-valor almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    private int indiceDe(K clave) {
        return Math.floorMod(clave.hashCode(), cadenas.length);
    }

    /** Asocia el valor a la clave (reemplaza si ya estaba). */
    public void poner(K clave, V valor) {
        int i = indiceDe(clave);
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                nodo.valor = valor;
                return;
            }
        }
        cadenas[i] = new Nodo<>(clave, valor, cadenas[i]);
        tamanio++;
    }

    /** Devuelve el valor asociado a la clave, o null si no esta. */
    public V obtener(K clave) {
        int i = indiceDe(clave);
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                return nodo.valor;
            }
        }
        return null;
    }

    /** Quita la clave. Devuelve el valor que tenia, o null si no estaba. */
    public V quitar(K clave) {
        int i = indiceDe(clave);
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
}

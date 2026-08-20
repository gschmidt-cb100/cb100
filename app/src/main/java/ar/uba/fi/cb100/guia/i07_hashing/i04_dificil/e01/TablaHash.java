package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e01;

/**
 * Tabla de hash con encadenamiento (chaining) implementada A MANO.
 *
 * Cada posicion del arreglo interno guarda el primer nodo de una lista
 * enlazada de pares clave-valor. Todas las claves cuyo hash cae en el mismo
 * indice conviven en la misma cadena.
 *
 * La capacidad es FIJA (la fija el constructor): esta version no hace rehash,
 * asi se puede estudiar el efecto de las colisiones con capacidad chica.
 *
 * Complejidad (con n elementos y m posiciones, alfa = n/m):
 *  - poner / obtener / quitar: O(1 + alfa) esperado, O(n) en el peor caso
 *    (todas las claves en la misma cadena).
 *  - tamanio: O(1)
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

    /** Arreglo de cadenas. Cada posicion es la cabeza de una lista enlazada. */
    private final Nodo<K, V>[] cadenas;

    /** Cantidad de pares clave-valor almacenados. */
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

    /** Indice de la cadena que le toca a la clave. floorMod evita negativos. */
    private int indiceDe(K clave) {
        return Math.floorMod(clave.hashCode(), cadenas.length);
    }

    /**
     * Asocia el valor a la clave. Si la clave ya estaba, REEMPLAZA el valor
     * (no agrega un par duplicado).
     */
    public void poner(K clave, V valor) {
        int i = indiceDe(clave);
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                nodo.valor = valor; // Clave existente: solo actualizo el valor.
                return;
            }
        }
        // Clave nueva: la agrego al principio de la cadena, es O(1).
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

    /**
     * Quita la clave de la tabla. Devuelve el valor que tenia asociado,
     * o null si la clave no estaba.
     */
    public V quitar(K clave) {
        int i = indiceDe(clave);
        Nodo<K, V> anterior = null;
        for (Nodo<K, V> nodo = cadenas[i]; nodo != null; nodo = nodo.siguiente) {
            if (nodo.clave.equals(clave)) {
                if (anterior == null) {
                    cadenas[i] = nodo.siguiente; // Era la cabeza de la cadena.
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

    /** Demostracion: tres claves que colisionan en la misma cadena. */
    public static void main(String[] args) {
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        // "juan", "eva" y "sol" caen todas en el indice 0 con capacidad 4.
        tabla.poner("juan", 25);
        tabla.poner("eva", 30);
        tabla.poner("sol", 28);
        System.out.println("tamanio = " + tabla.tamanio());
        System.out.println("eva -> " + tabla.obtener("eva"));
        System.out.println("quito eva: " + tabla.quitar("eva"));
        System.out.println("eva ahora -> " + tabla.obtener("eva"));
        System.out.println("sol sigue -> " + tabla.obtener("sol"));
    }
}

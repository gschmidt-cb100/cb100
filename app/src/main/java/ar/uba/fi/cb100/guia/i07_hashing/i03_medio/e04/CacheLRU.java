package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e04;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * e04: caché LRU (Least Recently Used) con capacidad acotada.
 * {@link LinkedHashMap} ya trae casi todo resuelto:
 * <ul>
 *   <li>con {@code accessOrder = true}, cada {@code get} o {@code put}
 *       mueve la entrada al final (la más recientemente usada);</li>
 *   <li>redefiniendo {@link #removeEldestEntry} le decimos que, al superar
 *       la capacidad, elimine la entrada más vieja (la menos usada).</li>
 * </ul>
 * Esta clase tiene estado, por eso es instanciable y no utilitaria.
 *
 * @param <K> tipo de las claves
 * @param <V> tipo de los valores
 */
public class CacheLRU<K, V> extends LinkedHashMap<K, V> {

    /** Cantidad máxima de entradas que la caché retiene. */
    private final int capacidad;

    /**
     * Crea una caché LRU con la capacidad indicada.
     *
     * @param capacidad máximo de entradas simultáneas (mayor a 0)
     */
    public CacheLRU(int capacidad) {
        // 16 y 0.75f son los valores por defecto; el true activa accessOrder.
        super(16, 0.75f, true);
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        this.capacidad = capacidad;
    }

    /**
     * LinkedHashMap llama a este método después de cada put:
     * si devuelve true, elimina la entrada más vieja (la menos usada).
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> masVieja) {
        return size() > capacidad;
    }

    /** Guarda un par clave/valor en la caché (lo marca como recién usado). */
    public void poner(K clave, V valor) {
        put(clave, valor);
    }

    /**
     * Devuelve el valor asociado a la clave (y lo marca como recién usado),
     * o {@code null} si no está en la caché.
     */
    public V obtener(K clave) {
        return get(clave);
    }

    public static void main(String[] args) {
        CacheLRU<String, Integer> cache = new CacheLRU<>(2);
        cache.poner("a", 1);
        cache.poner("b", 2);
        cache.obtener("a");      // "a" pasa a ser la más reciente.
        cache.poner("c", 3);     // se supera la capacidad: se va "b".
        System.out.println("Contenido: " + cache);
        System.out.println("¿Está b? " + cache.obtener("b"));
    }
}

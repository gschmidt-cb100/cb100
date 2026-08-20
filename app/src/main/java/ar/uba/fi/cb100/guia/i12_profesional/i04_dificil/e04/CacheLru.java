package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e04;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TECNICA: CACHE LRU CON {@code LinkedHashMap} EN MODO ACCESS-ORDER.
 *
 * Una cache LRU (Least Recently Used) guarda a lo sumo {@code capacidad}
 * pares y, cuando entra uno nuevo con la cache llena, expulsa el MENOS
 * RECIENTEMENTE USADO. {@code LinkedHashMap} lo trae casi resuelto:
 * <ul>
 *   <li>con {@code accessOrder=true}, cada {@code get}/{@code put} mueve la
 *       entrada al final de su lista interna, asi el mas viejo en uso queda
 *       siempre primero;</li>
 *   <li>{@code removeEldestEntry} es el gancho que el mapa consulta despues
 *       de cada insercion: devolver {@code true} expulsa esa entrada mas
 *       vieja automaticamente.</li>
 * </ul>
 *
 * Por que todo es O(1): {@code LinkedHashMap} combina la tabla de hash de
 * la U7 (buscar la entrada por clave cuesta O(1) esperado) con una lista
 * DOBLEMENTE enlazada de la U5 que atraviesa todas las entradas (mover un
 * nodo al final o sacar el primero cuesta O(1) porque cada nodo conoce a
 * su anterior y su siguiente). Ninguna operacion recorre la estructura:
 * get, put y la expulsion son O(1).
 *
 * @param <K> tipo de las claves (necesita equals/hashCode coherentes, U7)
 * @param <V> tipo de los valores
 */
public class CacheLru<K, V> extends LinkedHashMap<K, V> {

    private final int capacidad;

    public CacheLru(int capacidad) {
        // accessOrder=true: el orden interno pasa a ser "orden de uso".
        super(16, 0.75f, true);
        if (capacidad <= 0) {
            throw new IllegalArgumentException("la capacidad debe ser positiva: " + capacidad);
        }
        this.capacidad = capacidad;
    }

    /** Tras cada put el mapa pregunta: expulso al mas viejo? Si, si me pase. */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> masVieja) {
        return size() > capacidad;
    }

    public static void main(String[] args) {
        CacheLru<String, Integer> cache = new CacheLru<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        System.out.println("Llena:           " + cache);   // {a=1, b=2, c=3}
        cache.get("a");                                    // "a" vuelve a estar fresca
        cache.put("d", 4);                                 // expulsa a "b", la menos usada
        System.out.println("Tras get(a)+put: " + cache);   // {c=3, a=1, d=4}
        System.out.println("b sigue? " + cache.containsKey("b"));  // false
    }
}

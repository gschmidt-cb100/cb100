package ar.uba.fi.cb100.material.i07_hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Los diccionarios y conjuntos <b>de la API de Java</b> — lo que se usa en
 * parciales, TPs y finales:
 * <ul>
 *   <li>{@link HashMap}: tabla de hash — O(1) promedio, sin orden;</li>
 *   <li>{@link LinkedHashMap}: igual + recuerda el orden de inserción;</li>
 *   <li>{@link TreeMap}: árbol balanceado — O(log n), claves ORDENADAS;</li>
 *   <li>{@link HashSet}: el conjunto sobre tabla de hash (Unidad 5, ahora
 *       entendés por qué es O(1)).</li>
 * </ul>
 */
public class DemoApiNativa {

    public static void main(String[] args) {
        // --- HashMap: el diccionario por defecto -------------------------------
        Map<String, Integer> stock = new HashMap<>();
        stock.put("tornillos", 120);          // poner
        stock.put("tuercas", 80);
        stock.put("tornillos", 150);          // la clave ya estaba: REEMPLAZA
        System.out.println(stock.get("tornillos"));        // 150
        System.out.println(stock.get("clavos"));           // null (no está)
        System.out.println(stock.getOrDefault("clavos", 0)); // 0 (con valor por defecto)
        System.out.println(stock.containsKey("tuercas"));  // true
        stock.remove("tuercas");
        System.out.println(stock.size());                  // 1

        // recorrer un mapa: entrySet da los pares
        stock.put("arandelas", 500);
        for (Map.Entry<String, Integer> e : stock.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        // --- merge / computeIfAbsent: los idiomas del conteo y agrupado --------
        Map<String, Integer> votos = new HashMap<>();
        for (String v : new String[]{"ana", "bob", "ana", "ana"}) {
            votos.merge(v, 1, Integer::sum);   // crea con 1, o suma 1 si ya estaba
        }
        System.out.println(votos.get("ana"));              // 3

        // --- LinkedHashMap: conserva el orden de inserción ---------------------
        Map<String, Integer> ordenLlegada = new LinkedHashMap<>();
        ordenLlegada.put("primero", 1); ordenLlegada.put("segundo", 2);
        System.out.println(ordenLlegada.keySet());         // [primero, segundo]

        // --- TreeMap: claves siempre ordenadas, a costo O(log n) --------------
        Map<String, Integer> porNombre = new TreeMap<>(votos);
        System.out.println(porNombre.keySet());            // [ana, bob] (¡ordenado!)

        // --- HashSet: pertenencia O(1) (el "por qué" pendiente de la Unidad 5) -
        Set<Integer> dnis = new HashSet<>();
        dnis.add(111); dnis.add(222);
        System.out.println(dnis.add(111));                 // false: ya estaba, O(1)
        System.out.println(dnis.contains(222));            // true, O(1)
    }
}

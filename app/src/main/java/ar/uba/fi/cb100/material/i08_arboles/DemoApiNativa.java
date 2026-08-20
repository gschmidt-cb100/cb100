package ar.uba.fi.cb100.material.i08_arboles;

import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Los árboles balanceados <b>de la API de Java</b>: {@link TreeMap} y
 * {@link TreeSet} son <b>árboles rojo-negro</b> por dentro. Todas sus
 * operaciones son O(log n), y a cambio las claves están SIEMPRE ordenadas —
 * lo que habilita las consultas que el hashing no puede responder: rangos,
 * vecinos más cercanos, mínimo y máximo.
 */
public class DemoApiNativa {

    public static void main(String[] args) {
        // --- TreeMap: diccionario ORDENADO (rojo-negro) ------------------------
        NavigableMap<Integer, String> porLegajo = new TreeMap<>();
        porLegajo.put(1300, "ana");
        porLegajo.put(1100, "juan");
        porLegajo.put(1500, "mia");
        porLegajo.put(1200, "leo");

        System.out.println(porLegajo.keySet());          // [1100, 1200, 1300, 1500] ¡ordenado!
        System.out.println(porLegajo.firstKey());        // 1100 (mínimo, O(log n))
        System.out.println(porLegajo.lastKey());         // 1500 (máximo)

        // consultas por RANGO: imposibles en un HashMap
        System.out.println(porLegajo.subMap(1150, true, 1350, true));
        // {1200=leo, 1300=ana}

        // vecinos más cercanos
        System.out.println(porLegajo.floorKey(1250));    // 1200 (el mayor <= 1250)
        System.out.println(porLegajo.ceilingKey(1250));  // 1300 (el menor >= 1250)
        System.out.println(porLegajo.firstEntry());      // 1100=juan

        // --- TreeSet: conjunto ORDENADO ----------------------------------------
        NavigableSet<String> nombres = new TreeSet<>();
        nombres.add("mia"); nombres.add("ana"); nombres.add("leo"); nombres.add("juan");
        System.out.println(nombres);                     // [ana, juan, leo, mia]
        System.out.println(nombres.first());             // ana
        System.out.println(nombres.headSet("leo"));      // [ana, juan] (los menores)
        System.out.println(nombres.higher("juan"));      // leo (el siguiente)

        // recorrer un TreeMap va en orden de claves, siempre
        for (Map.Entry<Integer, String> e : porLegajo.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

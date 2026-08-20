package ar.uba.fi.cb100.material.i12_profesional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <b>El mapa de las colecciones de Java</b>: cada estructura del curso, su
 * clase de la biblioteca estándar y su "jugada característica".
 * <p>
 * Regla de oro profesional: declarar por la INTERFAZ ({@code List}, {@code Set},
 * {@code Map}, {@code Deque}) y elegir la implementación por sus COSTOS:
 * <ul>
 *   <li>{@code ArrayList} — arreglo redimensionable (U2/U5): get O(1), contains O(n)</li>
 *   <li>{@code ArrayDeque} — pila y cola (U5): ambas puntas O(1)</li>
 *   <li>{@code HashSet}/{@code HashMap} — hashing (U7): contains/get O(1) promedio, SIN orden</li>
 *   <li>{@code TreeSet}/{@code TreeMap} — árbol autobalanceado (U8): O(log n), SIEMPRE ordenado</li>
 *   <li>{@code PriorityQueue} — heap (U9): el mínimo en O(log n)</li>
 * </ul>
 */
public final class DemoColecciones {

    private DemoColecciones() {}

    public static void main(String[] args) {
        // List: hay ORDEN DE POSICIÓN y puede haber repetidos.
        List<String> compras = new ArrayList<>();
        compras.add("pan");
        compras.add("leche");
        compras.add("pan");                            // repetido: permitido
        System.out.println(compras.get(2));            // pan  (acceso por índice, O(1))

        // Set: SIN repetidos; HashSet no promete orden.
        Set<String> vistos = new HashSet<>(compras);
        System.out.println(vistos.size());             // 2  (el "pan" repetido se fundió)
        System.out.println(vistos.contains("leche"));  // true  (O(1) promedio, U7)

        // TreeSet: sin repetidos Y siempre ordenado (U8).
        Set<String> ordenados = new TreeSet<>(compras);
        System.out.println(ordenados);                 // [leche, pan]  (orden alfabético)

        // Map: pares clave -> valor.
        Map<String, Integer> stock = new HashMap<>();
        stock.put("pan", 12);
        stock.put("leche", 6);
        stock.merge("pan", 3, Integer::sum);           // el idiom del contador (U7)
        System.out.println(stock.get("pan"));          // 15

        // TreeMap: claves siempre ordenadas + consultas de rango (U8).
        TreeMap<String, Integer> stockOrdenado = new TreeMap<>(stock);
        System.out.println(stockOrdenado.firstKey());  // leche

        // Deque: pila (push/pop) y cola (addLast/removeFirst) en una clase (U5).
        Deque<String> pendientes = new ArrayDeque<>();
        pendientes.push("tarea B");
        pendientes.push("tarea A");
        System.out.println(pendientes.pop());          // tarea A  (LIFO)

        // PriorityQueue: siempre sale el de mayor prioridad (U9).
        PriorityQueue<Integer> urgencias = new PriorityQueue<>();
        urgencias.add(30);
        urgencias.add(10);
        urgencias.add(20);
        System.out.println(urgencias.poll());          // 10  (el mínimo primero)
    }
}

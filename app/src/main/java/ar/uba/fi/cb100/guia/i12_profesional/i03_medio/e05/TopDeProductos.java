package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * e05: top-K de productos por monto total, con un heap de tamaño K.
 *
 * <p><b>La versión obvia</b> ordena todos los productos y corta: O(p log p)
 * con p productos distintos. <b>La versión con heap (U9)</b> mantiene una
 * {@code PriorityQueue} min-heap de tamaño K: por cada producto lo ofrecemos
 * y, si el heap supera K, sacamos el mínimo — el que seguro no es top-K.
 * Costo: O(p log K). Con p enorme y K chico (el "top 10 de un millón"),
 * la diferencia es la que importa en producción.</p>
 *
 * <p>Desempates por nombre de producto, para que el resultado sea único
 * y comparable con cualquier otra implementación.</p>
 */
public final class TopDeProductos {

    public record Venta(String vendedor, String producto, int monto) {}

    /** El orden del ranking: más plata primero; a igual plata, alfabético. */
    private static final Comparator<Map.Entry<String, Integer>> RANKING =
            Map.Entry.<String, Integer>comparingByValue().reversed()
                    .thenComparing(Map.Entry.comparingByKey());

    private TopDeProductos() {
    }

    /**
     * Los K productos con mayor monto total, de mayor a menor,
     * SIN ordenar la tabla completa.
     */
    public static List<String> topProductos(List<Venta> ventas, int k) {
        // 1) Agrupar: producto -> total (HashMap vía groupingBy, U7).
        Map<String, Integer> totales = ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::producto,
                        Collectors.summingInt(Venta::monto)));

        // 2) Min-heap de tamaño K: en la cima queda el PEOR del top parcial
        //    (el comparador del ranking, invertido).
        PriorityQueue<Map.Entry<String, Integer>> heap =
                new PriorityQueue<>(RANKING.reversed());
        for (Map.Entry<String, Integer> entrada : totales.entrySet()) {
            heap.offer(entrada);                    // O(log K)
            if (heap.size() > k) {
                heap.poll();                        // afuera el mínimo (U9)
            }
        }

        // 3) Vaciar el heap: sale de peor a mejor, así que damos vuelta.
        List<String> top = new ArrayList<>();
        while (!heap.isEmpty()) {
            top.add(heap.poll().getKey());
        }
        java.util.Collections.reverse(top);
        return top;
    }

    /** El oráculo O(p log p): ordenar todo y cortar. Sólo para comparar. */
    public static List<String> topProductosOrdenandoTodo(List<Venta> ventas, int k) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::producto,
                        Collectors.summingInt(Venta::monto)))
                .entrySet().stream()
                .sorted(RANKING)
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static void main(String[] args) {
        List<Venta> ventas = List.of(
                new Venta("Ana", "cuaderno", 3500),
                new Venta("Ana", "mochila", 25000),
                new Venta("Carla", "calculadora", 30000),
                new Venta("Beto", "resma", 8000),
                new Venta("Carla", "resma", 8000),
                new Venta("Beto", "cuaderno", 3500));

        System.out.println(topProductos(ventas, 2));  // [calculadora, mochila]
        System.out.println(topProductos(ventas, 3));  // [calculadora, mochila, resma]
    }
}

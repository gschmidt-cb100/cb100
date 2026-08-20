package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e06;

import java.util.ArrayList;
import java.util.List;

/**
 * e06: generacion de todas las combinaciones de {@code k} elementos tomados de
 * una lista, mediante backtracking.
 *
 * <p>La cantidad de combinaciones es el numero combinatorio {@code C(n, k)}.
 * A diferencia de las permutaciones, el orden no importa: para evitar repetir
 * combinaciones, siempre avanzamos hacia adelante en la lista (nunca miramos
 * indices anteriores al ultimo elegido).</p>
 */
public final class Combinaciones {

    private Combinaciones() {
    }

    /**
     * Devuelve todas las combinaciones de {@code k} elementos de {@code l}.
     *
     * @param l lista de elementos
     * @param k cantidad de elementos por combinacion (0 &lt;= k &lt;= n)
     * @return lista con las {@code C(n, k)} combinaciones
     * @throws IllegalArgumentException si {@code k} esta fuera de {@code [0, n]}
     */
    public static List<List<Integer>> combinaciones(List<Integer> l, int k) {
        if (k < 0 || k > l.size()) {
            throw new IllegalArgumentException("k fuera de rango [0, " + l.size() + "]: " + k);
        }
        List<List<Integer>> resultado = new ArrayList<>();
        generar(l, k, 0, new ArrayList<>(), resultado);
        return resultado;
    }

    /**
     * Elige elementos desde {@code inicio} hacia adelante hasta completar
     * {@code k} en {@code parcial}.
     */
    private static void generar(List<Integer> l, int k, int inicio,
                                List<Integer> parcial, List<List<Integer>> resultado) {
        if (parcial.size() == k) {
            resultado.add(new ArrayList<>(parcial)); // caso base: combinacion completa
            return;
        }
        // Poda: si ni tomando todos los restantes llegamos a k, cortamos.
        for (int i = inicio; i <= l.size() - (k - parcial.size()); i++) {
            parcial.add(l.get(i));
            generar(l, k, i + 1, parcial, resultado); // i + 1: nunca miramos atras
            parcial.remove(parcial.size() - 1); // backtracking
        }
    }

    public static void main(String[] args) {
        List<Integer> datos = List.of(1, 2, 3, 4);
        List<List<Integer>> todas = combinaciones(datos, 2);
        System.out.println("cantidad = " + todas.size() + " (esperado C(4,2) = 6)");
        todas.forEach(System.out::println);
    }
}

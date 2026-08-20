package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e05;

import java.util.ArrayList;
import java.util.List;

/**
 * e05: generacion de todas las permutaciones de una lista de forma recursiva.
 *
 * <p>Una lista de {@code n} elementos tiene {@code n!} permutaciones. Estrategia
 * de backtracking: en cada paso se elige un elemento aun no usado para la
 * siguiente posicion, se recurre, y luego se deshace la eleccion.</p>
 */
public final class Permutaciones {

    private Permutaciones() {
    }

    /**
     * Devuelve todas las permutaciones de {@code l}.
     *
     * @param l lista de elementos (se asumen distintos)
     * @return lista con las {@code n!} permutaciones
     */
    public static List<List<Integer>> permutaciones(List<Integer> l) {
        List<List<Integer>> resultado = new ArrayList<>();
        boolean[] usado = new boolean[l.size()];
        generar(l, usado, new ArrayList<>(), resultado);
        return resultado;
    }

    /**
     * Construye permutaciones eligiendo, en cada posicion, un elemento libre.
     */
    private static void generar(List<Integer> l, boolean[] usado,
                                List<Integer> parcial, List<List<Integer>> resultado) {
        if (parcial.size() == l.size()) {
            resultado.add(new ArrayList<>(parcial)); // caso base: permutacion completa
            return;
        }
        for (int i = 0; i < l.size(); i++) {
            if (usado[i]) {
                continue; // ya lo pusimos en una posicion anterior
            }
            usado[i] = true;
            parcial.add(l.get(i));
            generar(l, usado, parcial, resultado);
            parcial.remove(parcial.size() - 1); // deshacemos (backtracking)
            usado[i] = false;
        }
    }

    public static void main(String[] args) {
        List<Integer> datos = List.of(1, 2, 3);
        List<List<Integer>> todas = permutaciones(datos);
        System.out.println("cantidad = " + todas.size() + " (esperado 3! = 6)");
        todas.forEach(System.out::println);
    }
}

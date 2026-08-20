package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e03: mezclar k listas ya ordenadas en una única lista ordenada.
 *
 * <p>Es el "merge" del merge sort, generalizado a k vías. En vez de comparar
 * dos frentes, metemos en un min-heap el primer elemento de cada lista.
 * En cada paso sacamos el mínimo global (la raíz), lo agregamos al resultado
 * y encolamos el siguiente elemento de la misma lista de donde salió.</p>
 *
 * <p>Cada entrada del heap recuerda de qué lista vino y en qué posición está,
 * para poder avanzar ese "frente". Con n elementos totales el costo es
 * O(n log k): el heap nunca tiene más de k entradas.</p>
 */
public final class MezclaKListas {

    /**
     * Un frente de avance: el valor actual, en qué lista está y en qué
     * posición dentro de ella.
     */
    private record Entrada(int valor, int indiceLista, int posicion) {
    }

    private MezclaKListas() {
    }

    /**
     * Mezcla las listas (cada una ordenada de menor a mayor) en una sola
     * lista ordenada. Las listas vacías se ignoran.
     *
     * @param listas listas de enteros, cada una ya ordenada ascendente
     * @return todos los elementos, en una lista ordenada ascendente
     */
    public static List<Integer> mezclar(List<List<Integer>> listas) {
        PriorityQueue<Entrada> heap =
                new PriorityQueue<>(Comparator.comparingInt(Entrada::valor));
        // Sembramos el heap con el primer elemento de cada lista no vacía.
        for (int i = 0; i < listas.size(); i++) {
            if (!listas.get(i).isEmpty()) {
                heap.offer(new Entrada(listas.get(i).get(0), i, 0));
            }
        }
        List<Integer> resultado = new ArrayList<>();
        while (!heap.isEmpty()) {
            Entrada menor = heap.poll();
            resultado.add(menor.valor());
            // Avanzamos el frente de la lista de donde salió el mínimo.
            List<Integer> lista = listas.get(menor.indiceLista());
            int siguiente = menor.posicion() + 1;
            if (siguiente < lista.size()) {
                heap.offer(new Entrada(lista.get(siguiente),
                        menor.indiceLista(), siguiente));
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<List<Integer>> listas = List.of(
                List.of(1, 4, 7),
                List.of(2, 5),
                List.of(3, 6, 8, 9));
        System.out.println("Listas:  " + listas);
        System.out.println("Mezcla:  " + mezclar(listas));
    }
}

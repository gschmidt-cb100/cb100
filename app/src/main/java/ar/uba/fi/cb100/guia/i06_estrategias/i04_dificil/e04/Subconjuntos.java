package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e04;

import java.util.ArrayList;
import java.util.List;

/**
 * e04: generacion del conjunto potencia (power set) de forma recursiva.
 *
 * <p>El conjunto potencia de una lista de {@code n} elementos tiene
 * {@code 2^n} subconjuntos. Estrategia de division y conquista: para el primer
 * elemento hay dos decisiones (incluirlo o no); el resto de los subconjuntos se
 * obtiene recursivamente sobre la cola de la lista.</p>
 */
public final class Subconjuntos {

    private Subconjuntos() {
    }

    /**
     * Devuelve todos los subconjuntos de {@code l} (incluye el vacio y el total).
     *
     * @param l lista de elementos
     * @return lista con los {@code 2^n} subconjuntos
     */
    public static List<List<Integer>> subconjuntos(List<Integer> l) {
        List<List<Integer>> resultado = new ArrayList<>();
        generar(l, 0, new ArrayList<>(), resultado);
        return resultado;
    }

    /**
     * Recorre las decisiones incluir/excluir para cada elemento a partir de
     * {@code indice}, acumulando en {@code parcial}.
     */
    private static void generar(List<Integer> l, int indice,
                                List<Integer> parcial, List<List<Integer>> resultado) {
        if (indice == l.size()) {
            resultado.add(new ArrayList<>(parcial)); // caso base: copia del subconjunto actual
            return;
        }
        // Decision 1: NO incluir el elemento en la posicion indice.
        generar(l, indice + 1, parcial, resultado);
        // Decision 2: SI incluirlo.
        parcial.add(l.get(indice));
        generar(l, indice + 1, parcial, resultado);
        parcial.remove(parcial.size() - 1); // deshacemos para backtracking
    }

    public static void main(String[] args) {
        List<Integer> datos = List.of(1, 2, 3);
        List<List<Integer>> todos = subconjuntos(datos);
        System.out.println("cantidad = " + todos.size() + " (esperado 2^3 = 8)");
        todos.forEach(System.out::println);
    }
}

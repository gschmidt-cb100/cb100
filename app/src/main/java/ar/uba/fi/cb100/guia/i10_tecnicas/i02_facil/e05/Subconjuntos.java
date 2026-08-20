package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e05;

import java.util.ArrayList;
import java.util.List;

/**
 * e05: generar todos los subconjuntos (el "conjunto de partes") de un
 * arreglo de enteros.
 *
 * <p><b>Técnica: backtracking.</b> Recorremos un árbol de decisiones donde
 * en cada nivel elegimos si el elemento actual entra o no en el
 * subconjunto. El esqueleto es siempre el mismo y acá está escrito de
 * forma explícita: <i>elegir</i> ({@code add}), <i>avanzar</i> (llamada
 * recursiva) y <i>deshacer</i> ({@code remove}) para restaurar el estado
 * antes de probar la otra rama. A diferencia de greedy y DP, backtracking
 * explora exhaustivamente: acá no hay poda porque queremos las 2^n hojas,
 * así que el costo es O(2^n) inevitable (ese es el tamaño de la salida).</p>
 */
public final class Subconjuntos {

    private Subconjuntos() {
    }

    /**
     * Devuelve todos los subconjuntos de {@code a}, incluidos el vacío y
     * el total. Si {@code a} tiene n elementos, la lista tiene 2^n
     * subconjuntos.
     *
     * @param a arreglo de entrada (no se modifica)
     * @return lista con los 2^n subconjuntos, cada uno como lista de enteros
     */
    public static List<List<Integer>> subconjuntos(int[] a) {
        List<List<Integer>> resultado = new ArrayList<>();
        generar(a, 0, new ArrayList<>(), resultado);
        return resultado;
    }

    /**
     * Backtracking: decide qué hacer con {@code a[desde]} y recursa.
     *
     * @param a        arreglo original
     * @param desde    índice del elemento sobre el que estamos decidiendo
     * @param parcial  subconjunto en construcción (estado compartido)
     * @param resultado acumulador de subconjuntos terminados
     */
    private static void generar(int[] a, int desde, List<Integer> parcial,
                                List<List<Integer>> resultado) {
        if (desde == a.length) {
            // No quedan decisiones: guardamos una COPIA del parcial,
            // porque el parcial se sigue modificando al deshacer.
            resultado.add(new ArrayList<>(parcial));
            return;
        }
        // Rama 1: el elemento a[desde] NO va al subconjunto.
        generar(a, desde + 1, parcial, resultado);

        // Rama 2: el elemento a[desde] SÍ va.
        parcial.add(a[desde]);              // elegir
        generar(a, desde + 1, parcial, resultado); // avanzar
        parcial.remove(parcial.size() - 1); // deshacer (backtrack)
    }

    public static void main(String[] args) {
        int[] datos = {1, 2, 3};
        System.out.println("Subconjuntos de [1, 2, 3]:");
        for (List<Integer> s : subconjuntos(datos)) {
            System.out.println("  " + s);
        }
    }
}

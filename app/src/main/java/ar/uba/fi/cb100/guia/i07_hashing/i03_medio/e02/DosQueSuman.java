package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * e02: el clásico "two sum". Buscar dos posiciones i &lt; j tales que
 * {@code a[i] + a[j] == objetivo}, en una sola pasada O(n).
 * La idea: mientras recorro, guardo en un HashMap cada valor visto y su
 * índice. Para el elemento actual pregunto si el complemento
 * (objetivo - actual) ya apareció antes; el lookup es O(1).
 */
public final class DosQueSuman {

    private DosQueSuman() {
    }

    /**
     * Busca dos índices i &lt; j con {@code a[i] + a[j] == objetivo}.
     * Si hay varias respuestas posibles, devuelve la primera que se
     * encuentra recorriendo de izquierda a derecha.
     *
     * <p>Decisión de diseño: si no existe ningún par, devolvemos
     * {@code {-1, -1}} en vez de {@code null}. Así el que llama puede
     * chequear con {@code resultado[0] == -1} sin riesgo de
     * {@code NullPointerException}.</p>
     *
     * @param a        arreglo de enteros
     * @param objetivo suma buscada
     * @return arreglo {i, j} con i &lt; j, o {-1, -1} si no hay par
     */
    public static int[] dosQueSuman(int[] a, int objetivo) {
        // valor → índice donde lo vimos por última vez.
        Map<Integer, Integer> vistos = new HashMap<>();
        for (int j = 0; j < a.length; j++) {
            int complemento = objetivo - a[j];
            Integer i = vistos.get(complemento);
            if (i != null) {
                // El complemento apareció antes: i < j garantizado.
                return new int[] {i, j};
            }
            vistos.put(a[j], j);
        }
        return new int[] {-1, -1};
    }

    public static void main(String[] args) {
        int[] a = {2, 7, 11, 15};
        System.out.println("Arreglo: " + Arrays.toString(a) + ", objetivo: 9");
        System.out.println("Índices: " + Arrays.toString(dosQueSuman(a, 9)));
        System.out.println("Objetivo 100 (no hay): " + Arrays.toString(dosQueSuman(a, 100)));
    }
}

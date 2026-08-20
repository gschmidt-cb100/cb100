package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e01;

/**
 * e01: busqueda binaria recursiva sobre un arreglo ordenado.
 *
 * <p>Estrategia de division y conquista: en cada paso se mira el elemento del
 * medio y se descarta la mitad donde el valor buscado no puede estar. El
 * subproblema se reduce a la mitad, por lo que la complejidad es O(log n).</p>
 */
public final class BusquedaBinaria {

    private BusquedaBinaria() {
    }

    /**
     * Busca {@code x} en el arreglo {@code ordenado} (ascendente).
     *
     * @param ordenado arreglo ordenado de forma ascendente
     * @param x        valor a buscar
     * @return el indice de {@code x} si esta presente, o {@code -1} si no aparece
     */
    public static int buscar(int[] ordenado, int x) {
        return buscar(ordenado, x, 0, ordenado.length - 1);
    }

    /**
     * Auxiliar recursivo que busca dentro del rango cerrado [lo, hi].
     *
     * @param a  arreglo ordenado
     * @param x  valor buscado
     * @param lo limite inferior del rango (inclusive)
     * @param hi limite superior del rango (inclusive)
     * @return indice de {@code x} o {@code -1}
     */
    private static int buscar(int[] a, int x, int lo, int hi) {
        if (lo > hi) {
            return -1; // caso base: rango vacio, no esta
        }
        int medio = lo + (hi - lo) / 2; // evita desborde de (lo + hi)
        if (a[medio] == x) {
            return medio; // caso base: lo encontramos
        }
        if (x < a[medio]) {
            return buscar(a, x, lo, medio - 1); // buscamos en la mitad izquierda
        }
        return buscar(a, x, medio + 1, hi); // buscamos en la mitad derecha
    }

    public static void main(String[] args) {
        int[] datos = {1, 3, 5, 7, 9, 11, 13};
        System.out.println("Buscar 7  -> indice " + buscar(datos, 7));
        System.out.println("Buscar 1  -> indice " + buscar(datos, 1));
        System.out.println("Buscar 13 -> indice " + buscar(datos, 13));
        System.out.println("Buscar 8  -> indice " + buscar(datos, 8));
    }
}

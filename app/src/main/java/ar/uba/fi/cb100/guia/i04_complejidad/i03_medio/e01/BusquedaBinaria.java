package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e01;

import java.util.Objects;

/**
 * Busqueda binaria sobre un arreglo ORDENADO en forma ascendente.
 *
 * Complejidad temporal: O(log n).
 * En cada paso se descarta la mitad del rango de busqueda, por lo que la
 * cantidad de iteraciones es a lo sumo log2(n).
 * Complejidad espacial: O(1) (version iterativa, sin recursion).
 */
public final class BusquedaBinaria {

    private BusquedaBinaria() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Busca el valor {@code x} en el arreglo ordenado {@code a}.
     *
     * @param a arreglo ordenado en forma ascendente (no nulo).
     * @param x valor a buscar.
     * @return un indice donde se encuentra {@code x}, o -1 si no esta.
     */
    public static int buscar(int[] a, int x) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        int inicio = 0;
        int fin = a.length - 1;
        while (inicio <= fin) {
            // Se calcula asi para evitar desbordamiento de entero.
            int medio = inicio + (fin - inicio) / 2;
            if (a[medio] == x) {
                return medio;
            } else if (a[medio] < x) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] datos = {1, 3, 5, 7, 9, 11, 13};
        System.out.println("Buscar 7  -> indice " + buscar(datos, 7));   // encontrado
        System.out.println("Buscar 1  -> indice " + buscar(datos, 1));   // primer elemento
        System.out.println("Buscar 13 -> indice " + buscar(datos, 13));  // ultimo elemento
        System.out.println("Buscar 8  -> indice " + buscar(datos, 8));   // no esta (-1)
    }
}

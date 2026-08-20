package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e07;

import java.util.Objects;

/**
 * e07 - Busqueda lineal de un valor en un arreglo.
 *
 * Complejidad: O(n) en el peor caso.
 * Justificacion: en el peor caso (elemento ausente o al final) se recorre
 * todo el arreglo, una comparacion O(1) por elemento -> O(n). El mejor caso
 * (elemento en la primera posicion) es O(1).
 */
public final class BusquedaLineal {

    private BusquedaLineal() {
    }

    /**
     * Devuelve el indice de la primera aparicion de x, o -1 si no esta.
     *
     * @param a arreglo de enteros (no nulo)
     * @param x valor a buscar
     * @return indice de la primera aparicion, o -1
     */
    public static int indiceDe(int[] a, int x) {
        Objects.requireNonNull(a, "el arreglo no puede ser nulo");
        for (int i = 0; i < a.length; i++) { // O(n)
            if (a[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] datos = {4, 8, 15, 16, 23, 42};
        System.out.println("indiceDe(15) = " + indiceDe(datos, 15)); // 2
        System.out.println("indiceDe(99) = " + indiceDe(datos, 99)); // -1
    }
}

package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e09;

import java.util.Objects;

/**
 * Cuenta cuantas veces aparece un valor {@code x} en un arreglo ORDENADO
 * en forma ascendente, usando dos busquedas binarias:
 *   - una para hallar la PRIMERA posicion de x,
 *   - otra para hallar la ULTIMA posicion de x.
 * La cantidad de apariciones es (ultima - primera + 1).
 *
 * Complejidad temporal: O(log n).
 * Se realizan dos busquedas binarias, cada una O(log n) => 2 O(log n) = O(log n).
 * Complejidad espacial: O(1).
 */
public final class ContarApariciones {

    private ContarApariciones() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Cuenta las apariciones de {@code x} en el arreglo ordenado {@code a}.
     *
     * @param a arreglo ordenado en forma ascendente (no nulo).
     * @param x valor a contar.
     * @return cantidad de apariciones de {@code x} (0 si no esta).
     */
    public static int contar(int[] a, int x) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        int primera = primeraPosicion(a, x);
        if (primera == -1) {
            return 0; // No esta en el arreglo.
        }
        int ultima = ultimaPosicion(a, x);
        return ultima - primera + 1;
    }

    // Busqueda binaria de la primera posicion (mas a la izquierda) de x.
    private static int primeraPosicion(int[] a, int x) {
        int inicio = 0;
        int fin = a.length - 1;
        int resultado = -1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            if (a[medio] == x) {
                resultado = medio;
                fin = medio - 1; // Se sigue buscando hacia la izquierda.
            } else if (a[medio] < x) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return resultado;
    }

    // Busqueda binaria de la ultima posicion (mas a la derecha) de x.
    private static int ultimaPosicion(int[] a, int x) {
        int inicio = 0;
        int fin = a.length - 1;
        int resultado = -1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            if (a[medio] == x) {
                resultado = medio;
                inicio = medio + 1; // Se sigue buscando hacia la derecha.
            } else if (a[medio] < x) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 3, 5, 5, 8};
        System.out.println("Apariciones de 2: " + contar(a, 2)); // 3
        System.out.println("Apariciones de 5: " + contar(a, 5)); // 2
        System.out.println("Apariciones de 8: " + contar(a, 8)); // 1
        System.out.println("Apariciones de 4: " + contar(a, 4)); // 0
    }
}

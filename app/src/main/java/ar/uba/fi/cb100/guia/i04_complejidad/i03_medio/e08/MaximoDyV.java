package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e08;

import java.util.Objects;

/**
 * Maximo de un arreglo usando Division y Conquista.
 *
 * Se divide el arreglo en dos mitades, se calcula el maximo de cada una
 * recursivamente y se devuelve el mayor de ambos.
 *
 * Recurrencia: T(n) = 2 T(n/2) + O(1)
 *   - 2 T(n/2): el maximo de cada mitad.
 *   - O(1): la comparacion final entre los dos maximos.
 * Por el Teorema Maestro (a=2, b=2, f(n)=O(1), n^(log_b a)=n) => caso 1:
 *   T(n) = O(n).
 * Complejidad espacial: O(log n) por la profundidad de la recursion.
 */
public final class MaximoDyV {

    private MaximoDyV() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Devuelve el elemento maximo del arreglo {@code a}.
     *
     * @param a arreglo no nulo y no vacio.
     * @return el valor maximo.
     * @throws IllegalArgumentException si el arreglo esta vacio.
     */
    public static int maximo(int[] a) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        if (a.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacio");
        }
        return maximoRango(a, 0, a.length - 1);
    }

    // Maximo del subrango [inicio, fin].
    private static int maximoRango(int[] a, int inicio, int fin) {
        if (inicio == fin) {
            return a[inicio]; // Caso base: un solo elemento.
        }
        int medio = inicio + (fin - inicio) / 2;
        int maxIzq = maximoRango(a, inicio, medio);   // T(n/2)
        int maxDer = maximoRango(a, medio + 1, fin);  // T(n/2)
        return Math.max(maxIzq, maxDer);              // O(1)
    }

    public static void main(String[] args) {
        System.out.println(maximo(new int[]{5, 2, 9, 1, 5, 6})); // 9
        System.out.println(maximo(new int[]{-3, -1, -7}));       // -1
        System.out.println(maximo(new int[]{42}));               // 42
    }
}

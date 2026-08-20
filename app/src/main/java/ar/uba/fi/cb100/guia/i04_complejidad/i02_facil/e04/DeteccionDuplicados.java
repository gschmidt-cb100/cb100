package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e04;

import java.util.Objects;

/**
 * e04 - Determina si un arreglo tiene al menos un valor repetido.
 *
 * Complejidad: O(n^2) en el peor caso.
 * Justificacion: enfoque "todos con todos" mediante dos bucles anidados.
 * En el peor caso (sin duplicados) se realizan n(n-1)/2 comparaciones,
 * crecimiento cuadratico -> O(n^2). En el mejor caso (duplicado al inicio)
 * corta antes, pero la cota superior sigue siendo O(n^2).
 */
public final class DeteccionDuplicados {

    private DeteccionDuplicados() {
    }

    /**
     * Indica si existe algun par de posiciones distintas con el mismo valor.
     *
     * @param a arreglo de enteros (no nulo)
     * @return true si hay al menos un duplicado
     */
    public static boolean hayDuplicado(int[] a) {
        Objects.requireNonNull(a, "el arreglo no puede ser nulo");
        for (int i = 0; i < a.length; i++) {          // O(n)
            for (int j = i + 1; j < a.length; j++) {  // O(n) -> O(n^2)
                if (a[i] == a[j]) {
                    return true; // corte temprano
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("hayDuplicado {1,2,3,2} = " + hayDuplicado(new int[]{1, 2, 3, 2})); // true
        System.out.println("hayDuplicado {1,2,3,4} = " + hayDuplicado(new int[]{1, 2, 3, 4})); // false
    }
}

package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e08;

import java.util.Arrays;
import java.util.Objects;

/**
 * e08 - Devuelve un nuevo arreglo con los elementos en orden inverso.
 *
 * Complejidad: O(n).
 * Justificacion: se reserva un arreglo de tamano n y se copia cada elemento
 * a su posicion espejo con un unico bucle de n iteraciones. Tanto el tiempo
 * como la memoria adicional son O(n).
 */
public final class InvertirArreglo {

    private InvertirArreglo() {
    }

    /**
     * Crea y devuelve un nuevo arreglo con el orden invertido.
     * No modifica el arreglo original.
     *
     * @param a arreglo de enteros (no nulo)
     * @return nuevo arreglo invertido
     */
    public static int[] invertir(int[] a) {
        Objects.requireNonNull(a, "el arreglo no puede ser nulo");
        int n = a.length;
        int[] invertido = new int[n];
        for (int i = 0; i < n; i++) { // O(n)
            invertido[i] = a[n - 1 - i];
        }
        return invertido;
    }

    public static void main(String[] args) {
        int[] datos = {1, 2, 3, 4};
        System.out.println("invertir(" + Arrays.toString(datos) + ") = "
                + Arrays.toString(invertir(datos))); // [4, 3, 2, 1]
    }
}

package ar.uba.fi.cb100.material.i01_intro;

import java.util.Objects;

/**
 * Validaciones: verificar precondiciones antes de operar y rechazar datos
 * inválidos con excepciones claras. Programar "a la defensiva".
 */
public class Validaciones {

    static double promedio(int[] notas) {
        // requireNonNull lanza NullPointerException con un mensaje claro si es null.
        Objects.requireNonNull(notas, "el arreglo de notas no puede ser null");

        if (notas.length == 0) {
            throw new IllegalArgumentException("no hay notas para promediar");
        }
        int suma = 0;
        for (int n : notas) {
            if (n < 0 || n > 10) {
                throw new IllegalArgumentException("nota fuera de rango [0..10]: " + n);
            }
            suma += n;
        }
        return (double) suma / notas.length;
    }

    public static void main(String[] args) {
        System.out.println("promedio = " + promedio(new int[]{7, 8, 9}));
        try {
            promedio(new int[]{7, 15});      // 15 es inválida
        } catch (IllegalArgumentException e) {
            System.out.println("rechazado: " + e.getMessage());
        }
    }
}

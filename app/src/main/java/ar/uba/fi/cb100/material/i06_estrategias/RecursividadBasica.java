package ar.uba.fi.cb100.material.i06_estrategias;

/**
 * Ejemplos de <b>recursividad</b>: un método que se llama a sí mismo sobre una
 * entrada más chica, hasta llegar a un <b>caso base</b> que no recursiona. Cada
 * uno tiene su <b>ecuación de recurrencia</b> (Unidad 4) en el comentario.
 */
public class RecursividadBasica {

    /** n! = n · (n−1)!  ·  T(n) = T(n−1) + O(1) → O(n). Caso base: 0! = 1. */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n negativo");
        }
        if (n <= 1) {
            return 1;   // caso base
        }
        return n * factorial(n - 1);     // paso recursivo
    }

    /** Suma de a[i..n−1]  ·  T(n) = T(n−1) + O(1) → O(n). */
    public static long sumar(int[] a, int desde) {
        if (desde == a.length) {
            return 0;   // caso base: nada que sumar
        }
        return a[desde] + sumar(a, desde + 1);         // paso recursivo
    }

    /** base^exp por recursión simple  ·  T(exp) = T(exp−1) + O(1) → O(exp). */
    public static long potencia(long base, int exp) {
        if (exp == 0) {
            return 1;   // caso base
        }
        return base * potencia(base, exp - 1);
    }

    public static void main(String[] args) {
        System.out.println("factorial(5) = " + factorial(5));      // 120
        System.out.println("sumar([1,2,3,4]) = " + sumar(new int[]{1, 2, 3, 4}, 0)); // 10
        System.out.println("potencia(2,10) = " + potencia(2, 10)); // 1024
    }
}

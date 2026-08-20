package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e01;

/**
 * Ejercicio 01 (Swap).
 *
 * Muestra la diferencia entre pasar primitivos por valor y pasar la
 * referencia de un arreglo. En Java los parametros primitivos son copias:
 * modificarlos dentro del metodo NO afecta a las variables de afuera.
 * En cambio, un arreglo se comparte por referencia, por lo que sus
 * elementos si pueden intercambiarse.
 */
public final class IntercambioEnArreglo {

    private IntercambioEnArreglo() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Recibe dos enteros por valor. Aunque los "intercambie" localmente,
     * las variables de quien llama NO cambian.
     */
    public static void swap(int a, int b) {
        int tmp = a;
        a = b;
        b = tmp;
        // Al terminar el metodo, a y b (copias locales) se descartan.
    }

    /**
     * Intercambia los elementos en las posiciones i y j del arreglo.
     * Como el arreglo se comparte por referencia, el cambio SI se ve afuera.
     */
    public static void swap(int[] v, int i, int j) {
        int tmp = v[i];
        v[i] = v[j];
        v[j] = tmp;
    }

    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        swap(x, y);
        System.out.println("Despues de swap(x, y): x=" + x + " y=" + y); // sigue 1 y 2

        int[] v = {1, 2, 3};
        swap(v, 0, 2);
        System.out.println("Despues de swap(v, 0, 2): v[0]=" + v[0] + " v[2]=" + v[2]); // 3 y 1
    }
}

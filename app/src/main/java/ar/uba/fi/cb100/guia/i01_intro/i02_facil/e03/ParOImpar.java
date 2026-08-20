package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e03;

/**
 * Ejercicio 3 (facil): Determinar si un numero es par.
 */
public class ParOImpar {

    private ParOImpar() {
    }

    /**
     * Indica si un entero es par.
     *
     * @param n numero a evaluar
     * @return true si es par, false si es impar
     */
    public static boolean esPar(int n) {
        // El resto de dividir por 2 es 0 para los pares.
        return n % 2 == 0;
    }

    public static void main(String[] args) {
        System.out.println("esPar(4) = " + esPar(4));
        System.out.println("esPar(7) = " + esPar(7));
    }
}

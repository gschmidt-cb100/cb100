package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e01;

/**
 * Ejercicio 01 - FizzBuzz.
 * Devuelve "Fizz" si el numero es multiplo de 3, "Buzz" si es multiplo de 5,
 * "FizzBuzz" si es multiplo de ambos, o el numero como String en otro caso.
 */
public class FizzBuzz {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private FizzBuzz() {
    }

    /**
     * Calcula la representacion FizzBuzz del numero n.
     *
     * @param n numero a evaluar
     * @return "Fizz", "Buzz", "FizzBuzz" o el numero como texto
     */
    public static String fizzbuzz(int n) {
        boolean multiploDe3 = (n % 3 == 0);
        boolean multiploDe5 = (n % 5 == 0);

        if (multiploDe3 && multiploDe5) {
            return "FizzBuzz";
        }
        if (multiploDe3) {
            return "Fizz";
        }
        if (multiploDe5) {
            return "Buzz";
        }
        return String.valueOf(n);
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        // Recorremos del 1 al 20 mostrando la salida FizzBuzz
        for (int i = 1; i <= 20; i++) {
            System.out.println(i + " -> " + fizzbuzz(i));
        }
    }
}

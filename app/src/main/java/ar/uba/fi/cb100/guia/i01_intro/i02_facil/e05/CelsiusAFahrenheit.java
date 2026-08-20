package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e05;

/**
 * Ejercicio 5 (facil): Conversion de grados Celsius a Fahrenheit.
 * Formula: F = C * 9 / 5 + 32
 */
public class CelsiusAFahrenheit {

    private CelsiusAFahrenheit() {
    }

    /**
     * Convierte una temperatura en Celsius a Fahrenheit.
     *
     * @param c temperatura en grados Celsius
     * @return temperatura en grados Fahrenheit
     */
    public static double aFahrenheit(double c) {
        // Se usa 9.0 / 5.0 para forzar la division real.
        return c * 9.0 / 5.0 + 32.0;
    }

    public static void main(String[] args) {
        System.out.println("aFahrenheit(0) = " + aFahrenheit(0));
        System.out.println("aFahrenheit(100) = " + aFahrenheit(100));
        System.out.println("aFahrenheit(37) = " + aFahrenheit(37));
    }
}

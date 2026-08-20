package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e05;

/**
 * e05 - Potencia calculada multiplicando exp veces (metodo iterativo simple).
 *
 * Complejidad: O(exp), es decir O(n) siendo n el exponente.
 * Justificacion: un unico bucle que ejecuta exactamente exp iteraciones,
 * cada una con una multiplicacion O(1). El costo crece linealmente con el
 * exponente. (El algoritmo por cuadrados repetidos seria O(log n), pero aca
 * se pide la version lenta para ilustrar la complejidad lineal.)
 */
public final class PotenciaLenta {

    private PotenciaLenta() {
    }

    /**
     * Calcula base^exp multiplicando exp veces.
     *
     * @param base base de la potencia
     * @param exp  exponente no negativo
     * @return base elevado a exp; 1 cuando exp es 0
     * @throws IllegalArgumentException si el exponente es negativo
     */
    public static long potenciaLenta(long base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("el exponente no puede ser negativo");
        }
        long resultado = 1; // base^0 = 1
        for (int i = 0; i < exp; i++) { // exp iteraciones -> O(exp)
            resultado *= base;
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("2^10 = " + potenciaLenta(2, 10)); // 1024
        System.out.println("5^0 = " + potenciaLenta(5, 0));   // 1
        System.out.println("3^4 = " + potenciaLenta(3, 4));   // 81
    }
}

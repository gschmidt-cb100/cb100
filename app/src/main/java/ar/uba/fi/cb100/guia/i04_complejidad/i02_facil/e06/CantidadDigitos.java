package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e06;

/**
 * e06 - Cantidad de digitos de un numero entero.
 *
 * Complejidad: O(log n) respecto del valor absoluto de n.
 * Justificacion: en cada iteracion se divide por 10, reduciendo el numero
 * de digitos en uno. La cantidad de iteraciones es la cantidad de digitos,
 * que es proporcional a log_10(|n|) -> O(log n).
 */
public final class CantidadDigitos {

    private CantidadDigitos() {
    }

    /**
     * Cuenta los digitos de n. El cero tiene 1 digito y el signo se ignora.
     *
     * @param n numero entero (puede ser negativo)
     * @return cantidad de digitos (al menos 1)
     */
    public static int cantidadDigitos(int n) {
        if (n == 0) {
            return 1; // caso borde: el cero tiene un digito
        }
        // Se usa long para poder tomar el valor absoluto de Integer.MIN_VALUE
        // sin desbordar (|MIN_VALUE| no entra en un int).
        long valor = Math.abs((long) n);
        int digitos = 0;
        while (valor > 0) {   // se ejecuta ~log_10(|n|) veces -> O(log n)
            valor /= 10;
            digitos++;
        }
        return digitos;
    }

    public static void main(String[] args) {
        System.out.println("digitos(0) = " + cantidadDigitos(0));         // 1
        System.out.println("digitos(12345) = " + cantidadDigitos(12345)); // 5
        System.out.println("digitos(-99) = " + cantidadDigitos(-99));      // 2
    }
}

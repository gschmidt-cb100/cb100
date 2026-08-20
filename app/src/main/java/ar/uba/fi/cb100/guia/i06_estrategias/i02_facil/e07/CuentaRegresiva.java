package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e07;

import java.util.ArrayList;
import java.util.List;

/**
 * e07: cuenta regresiva de n hasta 0, generada recursivamente.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: si n &lt; 0 no se agrega nada (lista vacia)</li>
 *   <li>Paso recursivo: agregar n y luego la cuenta regresiva de n-1</li>
 * </ul>
 */
public final class CuentaRegresiva {

    private CuentaRegresiva() {
    }

    /**
     * Devuelve la lista {@code [n, n-1, ..., 1, 0]} construida recursivamente.
     * Si {@code n} es negativo, devuelve una lista vacia.
     *
     * @param n numero desde el cual contar hacia atras
     * @return la cuenta regresiva de {@code n} a 0
     */
    public static List<Integer> cuentaRegresiva(int n) {
        List<Integer> resultado = new ArrayList<>();
        agregar(n, resultado);
        return resultado;
    }

    /**
     * Metodo auxiliar recursivo que agrega n, n-1, ..., 0 a {@code acumulador}.
     *
     * @param n           valor actual a agregar
     * @param acumulador  lista donde se van agregando los valores
     */
    private static void agregar(int n, List<Integer> acumulador) {
        if (n < 0) {
            return; // caso base: nada mas que agregar
        }
        acumulador.add(n);
        agregar(n - 1, acumulador); // paso recursivo
    }

    public static void main(String[] args) {
        System.out.println("cuentaRegresiva(5) = " + cuentaRegresiva(5));
        System.out.println("cuentaRegresiva(0) = " + cuentaRegresiva(0));
        System.out.println("cuentaRegresiva(-1) = " + cuentaRegresiva(-1));
    }
}

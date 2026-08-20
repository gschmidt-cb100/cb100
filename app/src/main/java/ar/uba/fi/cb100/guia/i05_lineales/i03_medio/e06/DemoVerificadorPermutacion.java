package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e06;

import java.util.List;

/** Ejemplo de uso de {@link VerificadorPermutacion}. */
public class DemoVerificadorPermutacion {

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 2, 3, 4);
        List<Integer> b = List.of(4, 3, 2, 1);
        List<Integer> c = List.of(1, 2, 3, 5);

        System.out.println("a vs b: " + VerificadorPermutacion.esPermutacion(a, b));
        System.out.println("a vs c: " + VerificadorPermutacion.esPermutacion(a, c));
    }
}

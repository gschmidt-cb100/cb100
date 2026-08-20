package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e08;

import java.util.Set;

/** Ejemplo de uso de {@link VerificadorSubconjunto}. */
public class DemoVerificadorSubconjunto {

    public static void main(String[] args) {
        Set<Integer> a = Set.of(1, 2);
        Set<Integer> b = Set.of(1, 2, 3, 4);
        Set<Integer> c = Set.of(1, 9);

        System.out.println("a ⊆ b: " + VerificadorSubconjunto.esSubconjunto(a, b));
        System.out.println("c ⊆ b: " + VerificadorSubconjunto.esSubconjunto(c, b));
    }
}

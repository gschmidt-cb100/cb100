package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e07;

import java.util.Set;

/** Ejemplo de uso de {@link DiferenciaConjuntos}. */
public class DemoDiferenciaConjuntos {

    public static void main(String[] args) {
        Set<Integer> a = Set.of(1, 2, 3, 4, 5);
        Set<Integer> b = Set.of(2, 4, 6);
        System.out.println("A = " + a);
        System.out.println("B = " + b);
        System.out.println("A - B = " + DiferenciaConjuntos.diferencia(a, b));
    }
}

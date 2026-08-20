package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e10;

import java.util.List;

/** Ejemplo de uso de {@link ContadorFrecuencia}. */
public class DemoContadorFrecuencia {

    public static void main(String[] args) {
        List<Integer> l = List.of(1, 2, 2, 3, 2, 4);
        System.out.println("lista = " + l);
        System.out.println("veces(2) = " + ContadorFrecuencia.veces(l, 2));
        System.out.println("veces(5) = " + ContadorFrecuencia.veces(l, 5));
    }
}

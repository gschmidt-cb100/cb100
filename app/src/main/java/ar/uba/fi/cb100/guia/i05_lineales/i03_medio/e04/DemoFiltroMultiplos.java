package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e04;

import java.util.List;

/** Ejemplo de uso de {@link FiltroMultiplos}. */
public class DemoFiltroMultiplos {

    public static void main(String[] args) {
        List<Integer> entrada = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("entrada = " + entrada);
        System.out.println("sin múltiplos de 3 = " + FiltroMultiplos.sinMultiplosDe3(entrada));
    }
}

package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e03;

import java.util.List;

/** Ejemplo de uso de {@link MezcladorOrdenado}. */
public class DemoMezclador {

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 4, 7, 9);
        List<Integer> b = List.of(2, 3, 8);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("mezcla = " + MezcladorOrdenado.mezclarOrdenadas(a, b));
    }
}

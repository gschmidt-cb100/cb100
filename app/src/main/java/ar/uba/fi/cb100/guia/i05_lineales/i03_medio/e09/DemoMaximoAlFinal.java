package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e09;

import java.util.List;

/** Ejemplo de uso de {@link MaximoAlFinal}. */
public class DemoMaximoAlFinal {

    public static void main(String[] args) {
        List<Integer> l = List.of(3, 9, 1, 4, 2);
        System.out.println("entrada = " + l);
        System.out.println("máximo al final = " + MaximoAlFinal.maximoAlFinal(l));
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s06;

import java.util.LinkedList;
import java.util.List;

public class Eg05fE02 {

    public static void main(String[] args) {
        List<Integer> original = List.of(3, 1, 3, 2, 1, 5);
        List<Integer> procesados = new LinkedList<>();

        for(Integer valor : original) {
            if (!procesados.contains(valor)) {
                procesados.add(valor);
            }
        }

        System.out.println( procesados );
    }
}

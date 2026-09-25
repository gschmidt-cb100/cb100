package ar.uba.fi.cb100.clases.a2026.c02.s06;

import ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e03.MezcladorOrdenado;

import java.util.LinkedList;
import java.util.List;

public class Eg05mE03 {

    public static List<Integer> mezclarOrdenadas(Integer[] enteros) {
        return null;
    }
    public static List<Integer> mezclarOrdenadas1(Integer ... enteros) {
        return null;
    }

    public static List<Integer> mezclarOrdenadas(List<Integer> ... listas) {
        List<Integer> resultado = new LinkedList<>();
        for(List<Integer> lista : listas) {
            resultado.addAll(lista);
        }
        resultado.sort(Integer::compareTo);
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 4, 7, 9);
        List<Integer> b = List.of(2, 3, 8);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        List<Integer> mezcla = mezclarOrdenadas(a, b);
        System.out.println("mezcla = " + mezcla);
    }
}

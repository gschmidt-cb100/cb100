package ar.uba.fi.cb100.clases.a2026.c02.s06;

import java.util.LinkedList;
import java.util.List;

public class Eg05d09 {
    public static void main(String[] args) {
        List<Integer> lista = new LinkedList<>(List.of(1, 2, 3, 5, 6));

        System.out.println("Lista original: " + lista);

        System.out.println("Valor medio: " + lista.get(lista.size() / 2));  //division entera, devuelve el valor medio de la lista
    }
}

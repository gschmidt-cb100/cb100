package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e05;

import java.util.List;

/** Ejemplo de uso de {@link InsercionOrdenada}. */
public class DemoInsercionOrdenada {

    public static void main(String[] args) {
        List<Integer> ordenada = List.of(1, 3, 5, 7);
        System.out.println("ordenada = " + ordenada);
        System.out.println("insertar 4 = " + InsercionOrdenada.insertarOrdenado(ordenada, 4));
        System.out.println("insertar 0 = " + InsercionOrdenada.insertarOrdenado(ordenada, 0));
        System.out.println("insertar 9 = " + InsercionOrdenada.insertarOrdenado(ordenada, 9));
    }
}

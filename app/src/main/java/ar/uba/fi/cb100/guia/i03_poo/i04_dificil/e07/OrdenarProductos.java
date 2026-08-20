package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e07;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Demuestra cómo ordenar un arreglo de productos por precio usando un
 * {@link Comparator} pasado como expresión lambda a {@link Arrays#sort}.
 */
public class OrdenarProductos {

    public static void main(String[] args) {
        Producto[] productos = {
                new Producto("Café", 1500.0),
                new Producto("Azúcar", 800.0),
                new Producto("Leche", 1200.0),
                new Producto("Té", 950.0)
        };

        // Comparator como lambda: ordena por precio ascendente.
        Comparator<Producto> porPrecio = (a, b) -> Double.compare(a.precio(), b.precio());
        Arrays.sort(productos, porPrecio);

        System.out.println("Ordenados por precio (ascendente):");
        for (Producto p : productos) {
            System.out.println("  " + p);
        }

        // También se puede ordenar descendente reutilizando/invirtiendo el comparador.
        Arrays.sort(productos, porPrecio.reversed());
        System.out.println("Ordenados por precio (descendente):");
        for (Producto p : productos) {
            System.out.println("  " + p);
        }
    }
}

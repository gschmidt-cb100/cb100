package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e04;

import java.util.Arrays;
import java.util.Objects;

/**
 * Producto con nombre y precio. Es Comparable por precio (orden natural ascendente),
 * lo que permite ordenarlo con Arrays.sort sin pasar un Comparator.
 */
public class Producto implements Comparable<Producto> {

    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    /** Orden natural: por precio ascendente. */
    @Override
    public int compareTo(Producto otro) {
        return Double.compare(this.precio, otro.precio);
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }

    public static void main(String[] args) {
        Producto[] productos = {
                new Producto("Teclado", 25000.0),
                new Producto("Mouse", 12000.0),
                new Producto("Monitor", 180000.0),
                new Producto("Cable", 3000.0)
        };

        // Al ser Comparable, Arrays.sort usa el orden natural (precio ascendente).
        Arrays.sort(productos);

        System.out.println("Productos ordenados por precio:");
        for (Producto p : productos) {
            System.out.println("  " + p);
        }
    }
}

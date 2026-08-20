package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e07;

/** Producto simple con nombre y precio. */
public class Producto {

    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String nombre() {
        return nombre;
    }

    public double precio() {
        return precio;
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f)", nombre, precio);
    }
}

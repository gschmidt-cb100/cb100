package ar.uba.fi.cb100.material.i03_poo;

import java.util.Objects;

/**
 * Clase <b>abstracta</b>: reúne lo común a todas las figuras (el nombre, el orden
 * por área, el {@code toString}), pero deja {@code area()} y {@code perimetro()}
 * como <b>métodos abstractos</b> para que cada figura concreta los defina.
 */
public abstract class FiguraBase implements Figura, Comparable<Figura> {

    private final String nombre;

    protected FiguraBase(String nombre) {
        this.nombre = Objects.requireNonNull(nombre);
    }

    public String nombre() {
        return nombre;
    }

    // area() y perimetro() se heredan ABSTRACTOS de Figura: acá no se definen.

    @Override
    public int compareTo(Figura otra) {          // orden natural: por área
        return Double.compare(this.area(), otra.area());
    }

    @Override
    public String toString() {
        return String.format("%s(área=%.2f)", nombre, area());
    }
}

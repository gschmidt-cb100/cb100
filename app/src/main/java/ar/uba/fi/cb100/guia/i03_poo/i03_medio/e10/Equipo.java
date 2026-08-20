package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e10;

import java.util.Arrays;
import java.util.Objects;

/**
 * Equipo con un arreglo de puntajes. El constructor copia realiza una copia
 * profunda del arreglo para que el estado interno quede encapsulado: cambios
 * posteriores en el arreglo original NO afectan al equipo, y viceversa.
 */
public class Equipo {

    private final String nombre;
    private final int[] puntajes;

    public Equipo(String nombre, int[] puntajes) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
        Objects.requireNonNull(puntajes, "Los puntajes no pueden ser null");
        // Copia defensiva: se guarda una copia propia, no el arreglo recibido.
        this.puntajes = Arrays.copyOf(puntajes, puntajes.length);
    }

    /** Constructor copia: crea un equipo nuevo duplicando el arreglo (copia profunda). */
    public Equipo(Equipo otro) {
        Objects.requireNonNull(otro, "El equipo a copiar no puede ser null");
        this.nombre = otro.nombre;
        this.puntajes = Arrays.copyOf(otro.puntajes, otro.puntajes.length);
    }

    public String getNombre() {
        return nombre;
    }

    /** Devuelve una copia de los puntajes para no exponer el arreglo interno. */
    public int[] getPuntajes() {
        return Arrays.copyOf(puntajes, puntajes.length);
    }

    public int cantidadPuntajes() {
        return puntajes.length;
    }

    public static void main(String[] args) {
        int[] original = {10, 20, 30};
        Equipo a = new Equipo("A", original);
        Equipo copia = new Equipo(a);

        // Mutar el arreglo original no afecta al equipo (copia defensiva en constructor).
        original[0] = 999;
        System.out.println("Puntaje[0] de A: " + a.getPuntajes()[0]); // 10

        System.out.println("Copia igual a original: "
                + Arrays.equals(a.getPuntajes(), copia.getPuntajes())); // true
    }
}

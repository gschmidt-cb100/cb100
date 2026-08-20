package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e09;

import java.util.Objects;

/**
 * Persona que implementa Saludador definiendo sólo nombre();
 * reutiliza el método default saludar() sin reescribirlo.
 */
public class Persona implements Saludador {

    private final String nombre;

    public Persona(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
    }

    @Override
    public String nombre() {
        return nombre;
    }

    public static void main(String[] args) {
        Saludador s = new Persona("Carla");
        // saludar() proviene del método default de la interfaz.
        System.out.println(s.saludar()); // "Hola, soy Carla"
    }
}

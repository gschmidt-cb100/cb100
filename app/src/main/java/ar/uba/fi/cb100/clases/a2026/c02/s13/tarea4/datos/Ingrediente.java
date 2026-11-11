package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4.datos;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Ingrediente (clase de datos).
 * Un ingrediente de una receta: se identifica por su nombre y tiene la cantidad que la receta necesita.
 *
 * Invariantes:
 *  - nombre no es null ni vacío y no cambia.
 *  - cantidad > 0 y no cambia.
 */
public class Ingrediente {

//ATRIBUTOS

    private final String nombre;
    private final double cantidad;

//CONSTRUCTORES

    /**
     * Crea el ingrediente con el nombre y la cantidad indicados.
     * @param nombre nombre que identifica al ingrediente, no null ni vacío
     * @param cantidad cantidad del ingrediente, mayor que cero
     * @throws IllegalArgumentException si el nombre es null o vacío, o la cantidad no es mayor que cero
     */
    public Ingrediente(String nombre, double cantidad) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre del ingrediente no puede estar vacío");
        ValidacionesUtiles.esMayorQueCero(cantidad, "cantidad");
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

//CONSULTAS

    /**
     * @param otro ingrediente a comparar, no null
     * @return true si este ingrediente tiene una cantidad estrictamente menor que la del otro
     * @throws IllegalArgumentException si otro es null
     */
    public boolean tieneMenosCantidadQue(Ingrediente otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "otro");
        return this.cantidad < otro.cantidad;
    }

//GETTERS

    public String getNombre() {
        return nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

//METODOS GENERALES

    /**
     * Dos ingredientes son iguales si tienen el mismo nombre (la cantidad no se compara).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente otro = (Ingrediente) o;
        return this.nombre.equals(otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre + " (" + cantidad + ")";
    }
}

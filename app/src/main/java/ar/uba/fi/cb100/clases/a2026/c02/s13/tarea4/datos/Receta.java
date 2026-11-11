package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4.datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Receta (clase de datos).
 * Una receta se identifica por su nombre y tiene los ingredientes necesarios para prepararla.
 *
 * Invariantes:
 *  - nombre no es null ni vacío y no cambia.
 *  - ingredientes no es null, no tiene null y no tiene dos ingredientes con el mismo nombre.
 */
public class Receta {

//ATRIBUTOS

    private final String nombre;
    private final List<Ingrediente> ingredientes;

//CONSTRUCTORES

    /**
     * Crea la receta sin ingredientes asignados.
     * @param nombre nombre que identifica a la receta, no null ni vacío
     * @throws IllegalArgumentException si el nombre es null o vacío
     */
    public Receta(String nombre) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre de la receta no puede estar vacío");
        this.nombre = nombre;
        this.ingredientes = new ArrayList<>();
    }

//METODOS DE COMPORTAMIENTO

    /**
     * Agrega un ingrediente a la receta.
     * @param ingrediente ingrediente a agregar, no null
     * @throws IllegalArgumentException si el ingrediente es null o la receta ya tiene uno con ese nombre
     * post: la receta tiene un ingrediente más
     */
    public void agregarIngrediente(Ingrediente ingrediente) {
        ValidacionesUtiles.validarDistintoDeNull(ingrediente, "ingrediente");
        ValidacionesUtiles.validarFalso(this.ingredientes.contains(ingrediente),
                "La receta " + this.nombre + " ya tiene el ingrediente " + ingrediente.getNombre());
        this.ingredientes.add(ingrediente);
    }

//CONSULTAS

    /**
     * Busca en la receta el ingrediente con el mismo nombre que el indicado.
     * @param ingrediente ingrediente a buscar (se compara por nombre), no null
     * @return el ingrediente de esta receta con ese nombre, o null si la receta no lo tiene
     * @throws IllegalArgumentException si el ingrediente es null
     */
    public Ingrediente buscarIngrediente(Ingrediente ingrediente) {
        ValidacionesUtiles.validarDistintoDeNull(ingrediente, "ingrediente");
        int posicion = this.ingredientes.indexOf(ingrediente);
        if (posicion == -1) {
            return null;
        }
        return this.ingredientes.get(posicion);
    }

    /**
     * @return true si la receta no tiene ingredientes
     */
    public boolean estaVacia() {
        return this.ingredientes.isEmpty();
    }

//GETTERS

    public String getNombre() {
        return nombre;
    }

    /**
     * @return una copia de la lista de ingredientes de la receta (modificarla no cambia la receta)
     */
    public List<Ingrediente> getIngredientes() {
        return new ArrayList<>(this.ingredientes);
    }

//METODOS GENERALES

    /**
     * Dos recetas son iguales si tienen el mismo nombre.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receta otra = (Receta) o;
        return this.nombre.equals(otra.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre + " " + ingredientes;
    }
}

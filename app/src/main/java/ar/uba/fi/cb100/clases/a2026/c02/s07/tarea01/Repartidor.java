package ar.uba.fi.cb100.clases.a2026.c02.s07.tarea01;

/**
 * Representa a un repartidor del sistema, identificado por su DNI.
 */
public class Repartidor {
//ATRIBUTOS
    private String nombre;
    private int dni;

    /**
     * Crea un repartidor con nombre y DNI.
     *
     * @param nombre nombre del repartidor
     * @param dni documento del repartidor
     */
    public Repartidor(String nombre, int dni){
        this.nombre = nombre;
        this.dni = dni;
    }

//GETTERS

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Dos repartidores son iguales si tienen el mismo DNI.
     *
     * @param o otro objeto a comparar
     * @return true si representan al mismo repartidor
     */
    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        Repartidor repartidor = (Repartidor) o;
        return this.dni == repartidor.dni;
    }

    /**
     * El hash se calcula a partir del DNI para mantener la consistencia con equals.
     *
     * @return hash del repartidor
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(dni);
    }

}

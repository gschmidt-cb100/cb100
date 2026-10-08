package ar.uba.fi.cb100.clases.a2026.c02.s08.tarea;

public class Compuesto {

    private final String nombre;
    private final Unidad unidadDeMedida;
    private double cantidad;

    public Compuesto(String nombre, Unidad unidadDeMedida) {
        if (nombre == null || unidadDeMedida == null) {
            throw new IllegalArgumentException("Nombre y unidad no pueden ser nulos");
        }
        this.nombre = nombre;
        this.unidadDeMedida = unidadDeMedida;
        this.cantidad = 0;
    }

    //GETTERS

    /**
     * 
     * @return devuelve el nombre del compuesto.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * 
     * @return devuelve la unidad del compuesto.
     */
    public Unidad getUnidad(){
        return this.unidadDeMedida;
    }

    /**
     * 
     * @return devuelve la cantidad del compuesto.
     */
    public double getCantidad(){
        return this.cantidad;
    }

    /**
     * 
     * @param cantidadACambiar la cantidad que se quiere agregar o quitar de compuesto
     */
    public void cambiarCantidad(double cantidadACambiar){
        if (cantidadACambiar < 0 && this.cantidad + cantidadACambiar < 0){
            throw new IllegalArgumentException("No se puede quitar esa cantidad, es mayor a la que se encuentra");
        }
        this.cantidad += cantidadACambiar;
    }

      /**
     * Dos compuestos son iguales si tienen el mismo nombre.
     *
     * @param o otro objeto a comparar
     * @return true si representan al mismo compuesto
     */
    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        Compuesto compuesto = (Compuesto) o;
        return this.nombre.equals(compuesto.nombre);
    }

    public int hashCode(){
        return this.nombre.hashCode();
    }


}

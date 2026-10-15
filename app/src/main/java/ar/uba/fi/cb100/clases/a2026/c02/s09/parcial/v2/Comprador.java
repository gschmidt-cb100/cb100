package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.v2;

public class Comprador {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String nombre;
    private final double presupuesto;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code nombre} no es nulo ni vacío; {@code presupuesto} no es
     *      negativo.
     * post: crea el comprador con esos datos. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException} y no crea nada.
     *
     * @param nombre nombre del comprador
     * @param presupuesto presupuesto [$] disponible para comprar
     */
    public Comprador(String nombre, double presupuesto) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el comprador necesita un nombre");
        }
        if (presupuesto < 0) {
            throw new IllegalArgumentException("el presupuesto no puede ser negativo: " + presupuesto);
        }
        this.nombre = nombre;
        this.presupuesto = presupuesto;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("%s ($%.2f)", nombre, presupuesto);
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve [$] el presupuesto. */
    public double getPresupuesto() {
        return presupuesto;
    }

    /** post: devuelve el nombre del comprador. */
    public String getNombre() {
        return nombre;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
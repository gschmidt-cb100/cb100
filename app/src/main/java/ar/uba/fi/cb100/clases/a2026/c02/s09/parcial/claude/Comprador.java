package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.claude;

/**
 * Una persona que viene a comprar con un presupuesto y un nombre.
 *
 * <p>El comprador es el dueño del presupuesto, así que es él quien responde
 * "¿puedo pagar esto?" y "¿puedo comprar estos dos atuendos?". El que lo usa
 * nunca hace la cuenta {@code precio1 + precio2 <= presupuesto} por afuera:
 * si mañana el presupuesto se representa distinto (centavos enteros, otra
 * moneda), sólo cambia esta clase.
 */
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

    /**
     * post: devuelve {@code true} si {@code monto} no supera el presupuesto.
     */
    public boolean puedePagar(double monto) {
        return monto <= presupuesto;
    }

    /**
     * pre: {@code atuendo1} y {@code atuendo2} no son nulos.
     * post: devuelve {@code true} si el comprador puede llevarse los dos
     *       atuendos juntos: combinan entre sí (mismo color, distinto tipo)
     *       y la suma de sus precios no supera el presupuesto
     *       (condiciones 1, 2 y 3 del enunciado).
     */
    public boolean puedeComprar(Atuendo atuendo1, Atuendo atuendo2) {
        if (atuendo1 == null || atuendo2 == null) {
            throw new IllegalArgumentException("los atuendos no pueden ser nulos");
        }
        return atuendo1.combinaCon(atuendo2)
                && puedePagar(atuendo1.getPrecio() + atuendo2.getPrecio());
    }

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

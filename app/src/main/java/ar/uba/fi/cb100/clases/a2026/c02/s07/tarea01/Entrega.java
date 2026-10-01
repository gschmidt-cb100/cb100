package ar.uba.fi.cb100.clases.a2026.c02.s07.tarea01;

/**
 * Representa una entrega registrada por el sistema, con su repartidor,
 * distancia recorrida y estado del envío.
 */
public class Entrega {
//ATRIBUTOS
    private final Repartidor repartidor;
    private final double km;
    private final EstadoEntrega estado;

//METODOS CONSTRUCTORES

    /**
     * Crea una entrega asociada a un repartidor y con un estado determinado.
     *
     * @param repartidor repartidor responsable de la entrega
     * @param km distancia recorrida en kilómetros
     * @param estado estado actual de la entrega
     */
    public Entrega(Repartidor repartidor, double km, EstadoEntrega estado){
        this.repartidor = repartidor;
        this.km = km;
        this.estado = estado;
    }

// GETTERS

    /**
     * @return estado actual de la entrega
     */
    public EstadoEntrega getEstado() {
        return estado;
    }

    /**
     * @return distancia total de la entrega en kilómetros
     */
    public double getKm() {
        return km;
    }

    /**
     * @return repartidor asignado a la entrega
     */
    public Repartidor getRepartidor() {
        return repartidor;
    }

}
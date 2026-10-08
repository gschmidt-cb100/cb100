package ar.uba.fi.cb100.clases.a2026.c02.s08.tda;

public abstract class EntradaDeTelevisor {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int cantidadDeUsos;
    private EstadoDeEntradaDeTelevisor estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    protected EntradaDeTelevisor() {
        this.cantidadDeUsos = 0;
        this.estado = EstadoDeEntradaDeTelevisor.INACTIVO;
    }
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Activa la entrada de televisor si no está activa y aumenta la cantidad de usos en 1.
     * Si la entrada ya está activa, no hace nada.
     */
    public void activar() {
        if (!estaActiva()) {
            this.estado = EstadoDeEntradaDeTelevisor.ACTIVO;
            this.cantidadDeUsos++;
        }
    }

    /**
     * Inactiva la entrada de televisor si está activa.
     * Si la entrada ya está inactiva, no hace nada.
     */
    public void desactivar() {
        if (estaActiva()) {
            this.estado = EstadoDeEntradaDeTelevisor.INACTIVO;
        }
    }

    /**
     * Desactiva la entrada de televisor si está activa.
     * @return true si la entrada estaba activa y se desactivó, false si ya estaba inactiva.
     */
    public boolean estaActiva() {
        return this.estado == EstadoDeEntradaDeTelevisor.ACTIVO;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve la cantidad de veces que se ha activado la entrada de televisor.
     * @return la cantidad de usos de la entrada de televisor.
     */
    public int getCantidadDeUsos() {
        return cantidadDeUsos;
    }

    /**
     * Devuelve el estado actual de la entrada de televisor (ACTIVO o INACTIVO).
     * @return  el estado de la entrada de televisor.
     */
    public EstadoDeEntradaDeTelevisor getEstado() {
        return estado;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

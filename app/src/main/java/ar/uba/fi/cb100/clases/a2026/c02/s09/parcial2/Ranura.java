package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial2;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

public class Ranura {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int cantidadDePanesTostados = 0;
    private EstadoRanura estado = EstadoRanura.LIBRE;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Constructor de la clase Ranura
     * Inicializa la cantidad de panes tostados en 0 y el estado de la ranura en LIBRE.
     */
    public Ranura() {}

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Indica si la ranura está libre.
     * @return true si la ranura está libre, false en caso contrario.
     */
    public boolean estaLibre() {
        return this.estado == EstadoRanura.LIBRE;
    }

    /**
     * Inicia el proceso de tostado en la ranura.
     * Cambia el estado de la ranura a OCUPADA.
     * @throws IllegalStateException si la ranura no está libre.
     */
    public void iniciarTostado() {
        ValidacionesUtiles.validarFalso( !this.estaLibre(), "La ranura no está libre.");
        this.estado = EstadoRanura.OCUPADA;
    }

    /**
     * Finaliza el proceso de tostado en la ranura.
     * Cambia el estado de la ranura a LIBRE y aumenta la cantidad de panes tostados.
     * @throws IllegalStateException si la ranura está libre.
     */
    public void finalizarTostado() {
        ValidacionesUtiles.validarFalso( !this.estaLibre(), "La ranura está libre.");
        this.estado = EstadoRanura.LIBRE;
        this.cantidadDePanesTostados++;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve la cantidad de panes tostados en la ranura.
     * @return la cantidad de panes tostados.
     */
    public int getCantidadDePanesTostados() {
        return this.cantidadDePanesTostados;
    }

    /**
     * Devuelve el estado de la ranura.
     * @return el estado de la ranura.
     */
    public EstadoRanura getEstado() {
        return estado;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

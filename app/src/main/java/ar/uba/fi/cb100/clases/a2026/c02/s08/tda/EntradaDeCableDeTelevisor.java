package ar.uba.fi.cb100.clases.a2026.c02.s08.tda;

public class EntradaDeCableDeTelevisor extends EntradaDeTelevisor{
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int canalActual = 1;
    private int cantidadDeCanalesDisponibles;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public EntradaDeCableDeTelevisor(int cantidadDeCanalesDisponibles) {
        super();
        this.cantidadDeCanalesDisponibles = cantidadDeCanalesDisponibles;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Cambia el canal actual a un nuevo canal especificado.
     * @param nuevoCanal: el número del nuevo canal al que se desea cambiar.
     */
    public void cambiarCanal(int nuevoCanal) {
        if (nuevoCanal < 1 || nuevoCanal > cantidadDeCanalesDisponibles) {
            throw new IllegalArgumentException("El canal debe estar entre 1 y " + cantidadDeCanalesDisponibles);
        }
        this.canalActual = nuevoCanal;
    }

    /**
     * Sube al siguiente canal disponible. Si se supera el máximo de canales, vuelve al primer canal.
     */
    public void subirCanal() {
        if (canalActual < cantidadDeCanalesDisponibles) {
            canalActual++;
        } else {
            canalActual = 1; // Vuelve al primer canal si se supera el máximo
        }
    }

    /**
     * Baja al canal anterior disponible. Si se baja del primer canal, vuelve al último canal.
     */
    public void bajarCanal() {
        if (canalActual > 1) {
            canalActual--;
        } else {
            canalActual = cantidadDeCanalesDisponibles; // Vuelve al último canal si se baja del primero
        }
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getCanalActual() {
        return canalActual;
    }

    public int getCantidadDeCanalesDisponibles() {
        return cantidadDeCanalesDisponibles;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

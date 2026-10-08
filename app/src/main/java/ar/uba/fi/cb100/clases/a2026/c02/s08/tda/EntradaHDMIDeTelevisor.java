package ar.uba.fi.cb100.clases.a2026.c02.s08.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

public class EntradaHDMIDeTelevisor extends EntradaDeTelevisor{
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int numero;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public EntradaHDMIDeTelevisor(int numero) {
        super();
        ValidacionesUtiles.esMayorQueCero(numero, "El número de la entrada HDMI debe ser mayor que cero.");
        this.numero = numero;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve el número de la entrada HDMI del televisor.
     * @return el número de la entrada HDMI del televisor.
     */
    public int getNumero() {
        return numero;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

package ar.uba.fi.cb100.clases.a2026.c02.s07.p01;

public class Proyecto {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private Programador programador;
    private double puntaje;
    private EstadoDeProyecto estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public Proyecto(Programador programador, double puntaje, EstadoDeProyecto estado) {
        this.programador = programador;
        this.puntaje = puntaje;
        this.estado = estado;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Programador getProgramador() {
        return programador;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public EstadoDeProyecto getEstado() {
        return estado;
    }
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

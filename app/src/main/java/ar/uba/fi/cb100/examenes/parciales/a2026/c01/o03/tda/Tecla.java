package ar.uba.fi.cb100.examenes.parciales.a2026.c01.o03.tda;

import java.util.Objects;

public class Tecla {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Nota nota;
    private final int posicion;
    private int cantidadDeUsos = 0;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public Tecla(Nota nota, int posicion) {
        this.nota = nota;
        this.posicion = posicion;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tecla tecla = (Tecla) o;
        return posicion == tecla.posicion && nota == tecla.nota;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nota, posicion);
    }


//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    public void presionar() {
        this.cantidadDeUsos++;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Nota getNota() {
        return nota;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getCantidadDeUsos() {
        return cantidadDeUsos;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

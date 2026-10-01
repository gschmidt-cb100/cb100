package ar.uba.fi.cb100.clases.a2026.c02.s07.p01;

import java.util.Objects;

public class Programador {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private String nombre;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public Programador(String nombre) {
        this.nombre = nombre;
    }
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Programador that = (Programador) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }


//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

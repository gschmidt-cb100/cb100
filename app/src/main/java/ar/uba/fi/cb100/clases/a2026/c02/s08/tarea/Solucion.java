package ar.uba.fi.cb100.clases.a2026.c02.s08.tarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solucion {
    private final String codigo;
    private List<Compuesto> compuestos;

    public Solucion(String codigo){
        if (codigo == null){
            throw new IllegalArgumentException("el codigo no puede ser nulo");
        }
        this.codigo = codigo;
        this.compuestos = new ArrayList<Compuesto>();
    }

    /**
     * Agrega compuestos requeridos para la solucion.
     * @param compuestos una lista con los compuestos a agregar, si es uno, una lista con ese solo.
     */
    public void agregarCompuestos(List<Compuesto> compuestos){
        for (Compuesto compuesto : compuestos){
            if (this.compuestos.contains(compuesto)){
                throw new IllegalArgumentException("La solucion ya contiene el compuesto " + compuesto);
            }
            else{
                this.compuestos.add(compuesto);
            }
        }
    }

//GETTERS
    public String getCodigo() {
        return codigo;
    }

    public List<Compuesto> getCompuestos() {
        return compuestos;
    }

    /**    
     * Dos soluciones son iguales si tienen igual codigo
     * @param o otro objeto a comparar
    * @return true si representan la misma solucion
     */
    @Override 
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        Solucion solucion = (Solucion) o;
        return Objects.equals(this.codigo, solucion.codigo);
    }

    public int hashCode(){
        return this.codigo.hashCode();
    }


}

    
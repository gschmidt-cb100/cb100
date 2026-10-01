package ar.uba.fi.cb100.clases.a2026.c02.s07.p01;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EstadisticaDeProyecto {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private Programador programador;
    private List<Proyecto> proyectos;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public EstadisticaDeProyecto(Programador programador) {
        this.programador = programador;
        this.proyectos = new ArrayList<>();
    }

    public EstadisticaDeProyecto(Proyecto proyecto) {
        this(proyecto.getProgramador());
        this.proyectos.add(proyecto);
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticaDeProyecto that = (EstadisticaDeProyecto) o;
        return Objects.equals(programador, that.programador);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(programador);
    }


//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------


//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Programador getProgramador() {
        return programador;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public double getPuntajePromedio() {
        if (proyectos.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (Proyecto proyecto : proyectos) {
            suma += proyecto.getPuntaje();
        }
        return suma / proyectos.size();
    }

    public double getPuntajeTotal() {
        if (proyectos.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (Proyecto proyecto : proyectos) {
            suma += proyecto.getPuntaje();
        }
        return suma;
    }

    public int getCantidadDeProyectosEnProceso() {
        int count = 0;
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getEstado() == EstadoDeProyecto.EN_PROCESO) {
                count++;
            }
        }
        return count;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

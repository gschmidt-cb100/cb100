package ar.uba.fi.cb100.clases.a2026.c02.s07.p01;

import java.util.ArrayList;
import java.util.List;

public class IngenieroDeSoftware {

    /** Buscar los 10 programadores más cualificados.
    * Un programador será considerado más cualificado si posee mayor puntaje acumulado considerando todos sus proyectos.
    * Solo deben considerarse programadores que tengan como máximo cantidadMaximaDeProyectos proyectos en estado EN_PROCESO.
    * Un mismo programador puede aparecer en varios proyectos, pero en el resultado debe aparecer una sola vez.
    * El vector devuelto debe contener los programadores ordenados de mayor a menor puntaje acumulado.
    * Si existen menos de 10 programadores que cumplan las condiciones, devolver únicamente los disponibles.
    */
    public List<Programador> buscarProgramadoresMasCualificados(List<Proyecto> proyectos, int cantidadMaximaDeProyectos) {
        List<EstadisticaDeProyecto> getEstadisticasDeProyectos = getEstadisticasDeProyectos(proyectos);
        List<EstadisticaDeProyecto> programadoresMasCualificados = new ArrayList<>();
        for (EstadisticaDeProyecto estadistica : getEstadisticasDeProyectos) {
            if (estadistica.getCantidadDeProyectosEnProceso() <= cantidadMaximaDeProyectos) {
                programadoresMasCualificados.add(estadistica);
            }
        }
        programadoresMasCualificados.sort((o1, o2) -> Double.compare(o2.getPuntajeTotal(), o1.getPuntajeTotal()));
        List<Programador> resultado = new ArrayList<>();
        for (int i = 0; i < Math.min(10, programadoresMasCualificados.size()); i++) {
            resultado.add(programadoresMasCualificados.get(i).getProgramador());
        }
        return resultado;
    }

    /**
     * Obtiene las estadísticas de los proyectos proporcionados.
     * @param proyectos: la lista de proyectos de los cuales se desean obtener las estadísticas.
     * @return lista de estadísticas de proyectos, donde cada estadística corresponde a un programador y contiene la lista de proyectos asociados a ese programador.
     */
    public List<EstadisticaDeProyecto> getEstadisticasDeProyectos(List<Proyecto> proyectos) {
        List<EstadisticaDeProyecto> estadisticas = new ArrayList<>();
        for(Proyecto proyecto: proyectos) {
            agregarEstadisticaDeProyecto(estadisticas, proyecto);
        }
        return estadisticas;
    }

    /**
     * Agrega un proyecto a la estadística correspondiente del programador.
     * @param estadisticas: la lista de estadísticas de proyectos donde se buscará la estadística del programador.
     * @param proyecto: el proyecto que se desea agregar a la estadística del programador.
     */
    private void agregarEstadisticaDeProyecto(List<EstadisticaDeProyecto> estadisticas, Proyecto proyecto) {
        EstadisticaDeProyecto estadisticaDeProyecto = new EstadisticaDeProyecto(proyecto);
        if (estadisticas.contains(estadisticaDeProyecto)) {
            estadisticas.get(estadisticas.indexOf(estadisticaDeProyecto)).getProyectos().add(proyecto);
        } else {
            estadisticas.add(estadisticaDeProyecto);
        }
    }
}

//        programadoresMasCualificados.sort(new Comparator<EstadisticaDeProyecto>() {
//    @Override
//    public int compare(EstadisticaDeProyecto o1, EstadisticaDeProyecto o2) {
//        return Double.compare(o2.getPuntajeTotal(), o1.getPuntajeTotal());
//    }
//});


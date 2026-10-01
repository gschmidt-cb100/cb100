package ar.uba.fi.cb100.clases.a2026.c02.s07.tarea01;

import java.util.ArrayList;
import java.util.List;

/**
 * Administra y consolida la información de entregas por repartidor.
 */
public class AdministradorDeEnvios {

    /**
     * Agrupa las entregas por repartidor y devuelve una estadística por cada uno.
     *
     * @param entregas listado de entregas a procesar
     * @return estadísticas consolidadas por repartidor
     */
    public List<EstadisticaDeRepartidor> getEstadisticasDeRepartidor(List<Entrega> entregas) {
        //Validaciones
        List<EstadisticaDeRepartidor> estadisticas = new ArrayList<>();
        for (Entrega entrega: entregas){
            agregarEstadisticaDeEntrega(estadisticas, entrega);
        }
        return estadisticas;
    }

    /**
     * Agrega una entrega a la estadística del repartidor correspondiente.
     * Si el repartidor ya existe, incorpora la entrega en su lista; si no,
     * crea una nueva estadística.
     *
     * @param estadisticas lista de estadísticas acumuladas
     * @param entrega entrega a registrar
     */
    public void agregarEstadisticaDeEntrega(List<EstadisticaDeRepartidor> estadisticas, Entrega entrega) {
        //Validaciones
        EstadisticaDeRepartidor comparador = new EstadisticaDeRepartidor(entrega);
        if (estadisticas.contains(comparador)){
            estadisticas.get(estadisticas.indexOf(comparador)).getEntregas().add(entrega);
        } else {
            estadisticas.add(comparador);
        }
    }

    /**
     * Devuelve los repartidores con cantidad de entregas en reparto menor al límite,
     * ordenados por kilómetros totales recorridos de mayor a menor.
     *
     * @param entregas entregas a analizar
     * @param cantidaMaximaDeEntregasEnReparto límite máximo permitido de entregas en reparto
     * @return lista de repartidores calificados en orden decreciente por km
     */
    public List<Repartidor> buscarMejoresRepartidores(List<Entrega> entregas, int cantidaMaximaDeEntregasEnReparto){
        List<EstadisticaDeRepartidor> estadisticasRepartidores = getEstadisticasDeRepartidor(entregas);
        List<EstadisticaDeRepartidor> repartidoresMasCalificados = new ArrayList<>();
        for (EstadisticaDeRepartidor estadistica : estadisticasRepartidores){
            if (estadistica.getCantidadDeEntregasEnReparto() <= cantidaMaximaDeEntregasEnReparto){
                repartidoresMasCalificados.add(estadistica);
            }
        }

        repartidoresMasCalificados.sort((o1, o2) -> Double.compare(o2.getKmTotales(), o1.getKmTotales()));
        List<Repartidor> resultado = new ArrayList<>();
        for (int i = 0; i < Math.min(10, repartidoresMasCalificados.size()); i++) {
            resultado.add(repartidoresMasCalificados.get(i).getRepartidor());
        }
        return resultado;
    }
}

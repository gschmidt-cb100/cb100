package ar.uba.fi.cb100.clases.a2026.c02.s07.tarea01;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Acumula todas las entregas asociadas a un repartidor y permite consultar
 * métricas como kilómetros totales y entregas en reparto.
 */
public class EstadisticaDeRepartidor {
//ATRIBUTOS
    private final Repartidor repartidor;
    private final List<Entrega> entregas;


//Metodos contructores
    /**
     * Crea la estadística para un repartidor específico.
     *
     * @param repartidor repartidor sobre el que se acumulan las entregas
     */
    public EstadisticaDeRepartidor(Repartidor repartidor){
        this.repartidor = repartidor;
        this.entregas = new ArrayList<>();
    }

    /**
     * Crea la estadística a partir de una entrega inicial.
     *
     * @param entrega entrega con la que se inicia la estadística
     */
    public EstadisticaDeRepartidor(Entrega entrega) {
        this(entrega.getRepartidor());
        this.getEntregas().add(entrega);
    }


//GETTERS
    public List<Entrega> getEntregas() {
        return entregas;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    /**
     * @return suma de los kilómetros recorridos por todas las entregas del repartidor
     */
    public double getKmTotales(){
        double kmTotales = 0;
        for (Entrega entrega: getEntregas()){
            double km = entrega.getKm();
            kmTotales += km;
        }
        return kmTotales;

    }

    /**
     * @return cantidad de entregas que aún están en reparto
     */
    public int getCantidadDeEntregasEnReparto(){
        int cont = 0;
        for (Entrega entrega: getEntregas()){
            if (entrega.getEstado().equals(EstadoEntrega.EN_REPARTO)){
                cont ++;
            }
        }
        return cont;
    }


//METODOS GENERALES
    /**
     * Dos estadísticas son equivalentes si corresponden al mismo repartidor.
     *
     * @param o otro objeto a comparar
     * @return true si pertenecen al mismo repartidor
     */
    @Override 
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticaDeRepartidor estadistica = (EstadisticaDeRepartidor) o;
        return Objects.equals(this.repartidor, estadistica.repartidor);
    }

    /**
     * El hash se basa en el repartidor para mantener la consistencia con equals.
     *
     * @return hash calculado a partir del repartidor
     */
    @Override
    public int hashCode() {
        return Objects.hash(repartidor);
    }

}

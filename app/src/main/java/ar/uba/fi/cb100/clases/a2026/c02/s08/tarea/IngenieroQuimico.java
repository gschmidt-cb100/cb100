package ar.uba.fi.cb100.clases.a2026.c02.s08.tarea;

import java.util.ArrayList;
import java.util.List;

public class IngenieroQuimico {


     /** 
     * busca en 'solucionesDisponibles' las Soluciones que tengan los mismos Compuestos que 'solucionRequerida',  
     * con cantidades iguales o superiores pero menores (o igual) al doble.
     *  
     * @param solucionRequerida la solucion que se quiere buscar
     * @param solucionesDisponibles la lista de soluciones disponibles para buscar equivalentes
     * @return una lista con las soluciones equivalentes a la requerida 
     */ 
    public List<Solucion> buscarSolucionesEquivalentes(Solucion solucionRequerida, List<Solucion> solucionesDisponibles){
        if (solucionRequerida == null){
            throw new IllegalArgumentException("La solucion requerida no puede ser nula");
        }
        if (solucionesDisponibles == null){
            throw new IllegalArgumentException("Las soluciones disponibles no pueden ser nulas");
        }

        List<Solucion> solucionesEquivalentes = new ArrayList<Solucion>();
        for (Solucion solucion : solucionesDisponibles){
            if(solucion.getCompuestos().size() == solucionRequerida.getCompuestos().size()){
                boolean esEquivalente = true;
                for (Compuesto compuestoRequerido : solucionRequerida.getCompuestos()){
                    boolean encontrado = false;
                    for (Compuesto compuestoDisponible : solucion.getCompuestos()){
                        if (compuestoDisponible.equals(compuestoRequerido)){
                            if (compuestoDisponible.getCantidad() < compuestoRequerido.getCantidad() || 
                                compuestoDisponible.getCantidad() > 2 * compuestoRequerido.getCantidad()){
                                esEquivalente = false;
                                break;
                            }
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado){
                        esEquivalente = false;
                        break;
                    }
                }
                if (esEquivalente){
                    solucionesEquivalentes.add(solucion);
                }
            }
        }
        return solucionesEquivalentes;
    }

    /**
     * Busca en 'solucionesDisponibles' las Soluciones que tengan los mismos Compuestos que 'solucionRequerida',
     * con cantidades iguales o superiores pero menores (o igual) al doble.
     * @param solucionRequerida: la solucion que se quiere buscar
     * @param solucionesDisponibles: la lista de soluciones disponibles para buscar equivalentes
     * @return una lista con las soluciones equivalentes a la requerida
     */
    public List<Solucion> buscarSolucionesEquivalentes2(Solucion solucionRequerida, List<Solucion> solucionesDisponibles) {
        if (solucionRequerida == null) {
            throw new IllegalArgumentException("La solucion requerida no puede ser nula");
        }
        if (solucionesDisponibles == null) {
            throw new IllegalArgumentException("Las soluciones disponibles no pueden ser nulas");
        }
        List<Solucion> solucionesEquivalentes = new ArrayList<Solucion>();
        for (Solucion solucion : solucionesDisponibles){
            if (esSolucionEquivalente(solucion, solucionRequerida, 1, 2)) {
                solucionesEquivalentes.add(solucion);
            }
        }
        return solucionesEquivalentes;
    }

    /**
     * Verifica si una solucion es equivalente a otra, es decir, si tienen los mismos compuestos y las cantidades de cada compuesto están dentro del rango especificado.
     * @param solucion: la solucion a verificar
     * @param solucionRequerida: la solucion con la que se quiere comparar
     * @param rangoInferior: el factor mínimo de cantidad que debe tener cada compuesto en la solucion para ser considerada equivalente
     * @param rangoSuperior: el factor máximo de cantidad que debe tener cada compuesto en la solucion para ser considerada equivalente
     * @return true si la solucion es equivalente a la solucion requerida, false en caso contrario
     */
    public boolean esSolucionEquivalente(Solucion solucion, Solucion solucionRequerida, int rangoInferior, int rangoSuperior) {
        if (solucion.getCompuestos().size() != solucionRequerida.getCompuestos().size()) {
            return false;
        }
        for (Compuesto compuestoRequerido : solucionRequerida.getCompuestos()){
            if (!contieneCompuesto(solucion, compuestoRequerido, rangoInferior, rangoSuperior)){
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si una solucion contiene un compuesto requerido con una cantidad dentro del rango especificado.
     * @param solucion: la solucion a verificar
     * @param compuestoRequerido: el compuesto que se quiere buscar en la solucion
     * @param rangoInferior: el factor mínimo de cantidad que debe tener el compuesto en la solucion para ser considerado equivalente
     * @param rangoSuperior: el factor máximo de cantidad que debe tener el compuesto en la solucion para ser considerado equivalente
     * @return true si la solucion contiene el compuesto requerido con una cantidad dentro del rango especificado, false en caso contrario
     */
    public boolean contieneCompuesto(Solucion solucion, Compuesto compuestoRequerido, int rangoInferior, int rangoSuperior) {
        if (!solucion.getCompuestos().contains(compuestoRequerido)) {
            return false;
        }
        Compuesto compuestoDisponible = solucion.getCompuestos().get(solucion.getCompuestos().indexOf(compuestoRequerido));
        if (compuestoDisponible.getCantidad() < rangoInferior * compuestoRequerido.getCantidad() ||
            compuestoDisponible.getCantidad() > rangoSuperior * compuestoRequerido.getCantidad()){
            return false;
        }
        return true;
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s12.tarea.t01;

import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Ciudad;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Medicion;

import java.util.ArrayList;
import java.util.List;

public class ServicioMeteorologico {
    public List<Ciudad> buscarCiudadesConMayorAmplitudTermica(List<Medicion> mediciones, List<String> provincias, int cantidadMinimaDeMediciones, double amplitudMinima) {
        mediciones.sort((a, b) -> a.getCiudad().getNombre().compareTo(b.getCiudad().getNombre()));
        Ciudad ciudadAnalizada = null;
        List<EstadisticaCiudad> estadisticasCiudades = new ArrayList<>();
        EstadisticaCiudad estadisticaCiudad = null;
        for(Medicion medicion : mediciones){
            if(ciudadAnalizada == null || !ciudadAnalizada.equals(medicion.getCiudad())){
                ciudadAnalizada = medicion.getCiudad();
                estadisticaCiudad = new EstadisticaCiudad(medicion.getCiudad());
                estadisticasCiudades.add(estadisticaCiudad);
                estadisticaCiudad.agregarMedicion(medicion);    
            } else {
                estadisticaCiudad.agregarMedicion(medicion); 
            }
        }
        estadisticasCiudades.removeIf(estadistica ->estadistica.getAmplitudTermica(provincias, cantidadMinimaDeMediciones, amplitudMinima) == null);

        estadisticasCiudades.sort((a,b) -> Double.compare(b.getAmplitudTermica(provincias, cantidadMinimaDeMediciones, amplitudMinima), a.getAmplitudTermica(provincias, cantidadMinimaDeMediciones, amplitudMinima)));
        return convertirEstadisticasACiudades(estadisticasCiudades);
    }
    

    private List<Ciudad> convertirEstadisticasACiudades(List<EstadisticaCiudad> estadisticasCiudades){
        List<Ciudad> ciudades = new ArrayList<>();
        for (EstadisticaCiudad estadisticaCiudad : estadisticasCiudades){
            ciudades.add(estadisticaCiudad.getCiudad());
            
        }
        return ciudades;
    }


}

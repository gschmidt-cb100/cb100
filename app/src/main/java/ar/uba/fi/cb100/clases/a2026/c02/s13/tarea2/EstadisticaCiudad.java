package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea2;

import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Ciudad;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Medicion;

import java.util.ArrayList;
import java.util.List;

public class EstadisticaCiudad {
    private Ciudad ciudad;
    private List<Medicion> mediciones;
    private Double amplitudTermica = null;

    public EstadisticaCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        this.mediciones = new ArrayList<>();
    }

    public void agregarMedicion(Medicion medicion){
        this.amplitudTermica = null;
        this.mediciones.add(medicion);
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public Double getAmplitudTermica(List<String> provincias, int cantidadMinimaDeMedicionesValidas,double amplitudMinima) {

        if (!provincias.contains(ciudad.getProvincia())|| mediciones.size() < cantidadMinimaDeMedicionesValidas) {
            return null;
        }
        Double max = null;
        Double min = null;
        int cantidadMedicionesValidas = 0;
        for (Medicion medicion : mediciones) {
            if (medicion.esValida()) {
                cantidadMedicionesValidas++;

                if (max == null || medicion.getTemperatura() > max) {
                    max = medicion.getTemperatura();
                }
                if (min == null || medicion.getTemperatura() < min) {
                    min = medicion.getTemperatura();
                }
            }
        }

        if (cantidadMedicionesValidas == 0|| cantidadMedicionesValidas < cantidadMinimaDeMedicionesValidas) {
            return null;
        }

        if (max - min < amplitudMinima) {
            return null;
        }

        return max - min;
    }  

    public double getAmplitudTermica() {
        if (amplitudTermica == null) {
            //calculamos
        }
        return amplitudTermica;
    }
}

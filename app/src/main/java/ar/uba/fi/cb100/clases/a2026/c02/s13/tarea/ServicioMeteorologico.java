package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea;

import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Ciudad;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.EstadoMedicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Medicion;
import java.util.ArrayList;
import java.util.List;

/*
 * Estrategia -
 * Auxiliar 1. sinRepetir(): buscamos mantener la aparición de cada ciudad, máximo una vez.
 * Auxiliar 2. amplitudTermica(): buscamos la amplitud térmica de cada ciudad.
 * Auxiliar 3. amplitudTermicaMayor(): devuelve true si la amplitud térmica de la ciudad es mayor a amplitudMinima.
 * Auxiliar 4. cantidadMediciones(): buscamos cuántas mediciones válidas tiene una ciudad.
*/

public class ServicioMeteorologico {

    /** Firma: private List<Ciudad> sinRepetir(List<Medicion> mediciones)
    Pre: mediciones no es null.
    Post: devuelve una lista de las ciudades medidas sin repetir.
    @Param mediciones las mediciones a buscar no null.
    @Throws IllegalArgumentException si alguno de los parametros es null.
    */
    private List<Ciudad> sinRepetir(List<Medicion> mediciones) {
        if (mediciones == null) {
            throw new IllegalArgumentException("Los parametros son invalidos");
        }
        List<Ciudad> sinRepetir = new ArrayList<>();
        for (Medicion medicion : mediciones) {
            Ciudad ciudad = medicion.getCiudad();
            if (!sinRepetir.contains(ciudad)) {
                sinRepetir.add(ciudad);
            }
        }
        return sinRepetir;
    }

    /** Firma: private double amplitudTermica(List<Medicion> mediciones, Ciudad ciudad)
    Pre: mediciones y ciudad no son null, y la ciudad tiene al menos una medicion VALIDA.
    Post: devuelve la diferencia entre la mayor y la menor temperatura VALIDA registrada de esa ciudad.
    @Param mediciones mediciones a procesar no null.
    @Param ciudad ciudad a procesar no null.
    @Throws IllegalArgumentException si mediciones o ciudad son null.
    */
    private double amplitudTermica(List<Medicion> mediciones, Ciudad ciudad) {
        if (mediciones == null || ciudad == null) {
            throw new IllegalArgumentException("Los parametros son invalidos");
        }
        Double mayor = null;
        Double menor = null;
        for (Medicion medicion : mediciones) {
            if (medicion.getCiudad().equals(ciudad) && medicion.getEstado() == EstadoMedicion.VALIDA) {
                if (mayor == null || medicion.getTemperatura() > mayor) {
                    mayor = medicion.getTemperatura();
                }
                if (menor == null || medicion.getTemperatura() < menor) {
                    menor = medicion.getTemperatura();
                }
            }
        }
        return mayor - menor;
    }

    /** Firma: private boolean amplitudTermicaMayor(List<Medicion> mediciones, Ciudad ciudad, double amplitudMinima)
    Pre: mediciones y ciudad no son null, y la ciudad tiene al menos una medicion VALIDA.
    Post: devuelve true si la amplitud termica de la ciudad es mayor a amplitudMinima.
    @Param mediciones mediciones a procesar no null.
    @Param ciudad ciudad a procesar no null.
    @Param amplitudMinima el rango a superar.
    @Throws IllegalArgumentException si mediciones o ciudad son null.
    */
    private boolean amplitudTermicaMayor(List<Medicion> mediciones, Ciudad ciudad, double amplitudMinima) {
        if (mediciones == null || ciudad == null) {
            throw new IllegalArgumentException("Los parametros son invalidos");
        }
        return amplitudTermica(mediciones, ciudad) > amplitudMinima;
    }

    /** Firma: private int cantidadMediciones(List<Medicion> mediciones, Ciudad ciudad)
    Pre: mediciones y ciudad no null.
    Post: devuelve la cantidad de mediciones validas de esa ciudad.
    @Param mediciones mediciones a procesar no null.
    @Param ciudad ciudad a procesar no null.
    @Throws IllegalArgumentException si mediciones o ciudad son null.
    */
    private int cantidadMediciones(List<Medicion> mediciones, Ciudad ciudad) {
        if (mediciones == null || ciudad == null) {
            throw new IllegalArgumentException("Los parametros son invalidos");
        }
        int total = 0;
        for (Medicion medicion : mediciones) {
            if (medicion.getCiudad().equals(ciudad) && medicion.getEstado() == EstadoMedicion.VALIDA) {
                total++;
            }
        }
        return total;
    }

    /** Firma: public List<Ciudad> buscarCiudadesConMayorAmplitudTermica(List<Medicion> mediciones, List<String> provincias,
                                                                       int cantidadMinimaDeMediciones, double amplitudMinima)
    Pre: mediciones y provincias no son null, y cantidadMinimaDeMediciones es mayor que 0.
    Post: devuelve una lista nueva con las ciudades de las provincias indicadas, sin repetir, con al menos
          cantidadMinimaDeMediciones mediciones VALIDA y amplitud mayor a amplitudMinima, de mayor a menor amplitud.
    @Param mediciones mediciones a procesar no null.
    @Param provincias provincias a considerar no null.
    @Param cantidadMinimaDeMediciones cantidad minima de mediciones validas, mayor que 0.
    @Param amplitudMinima la amplitud de la ciudad tiene que ser mayor a este valor.
    @Throws IllegalArgumentException si mediciones o provincias son null, o cantidadMinimaDeMediciones <= 0.
    */
    public List<Ciudad> buscarCiudadesConMayorAmplitudTermica(List<Medicion> mediciones, List<String> provincias,
                                                              int cantidadMinimaDeMediciones, double amplitudMinima) {
        if (mediciones == null || provincias == null || cantidadMinimaDeMediciones <= 0) {
            throw new IllegalArgumentException("Algunos parametros son invalidos");
        }
        List<Ciudad> candidata = new ArrayList<>();
        List<Ciudad> sinRepetir = sinRepetir(mediciones);
        for (Ciudad ciudad : sinRepetir) {
            if (cantidadMediciones(mediciones, ciudad) >= cantidadMinimaDeMediciones
                    && provincias.contains(ciudad.getProvincia())) {
                if (amplitudTermicaMayor(mediciones, ciudad, amplitudMinima)) {
                    candidata.add(ciudad);
                }
            }
        }
        candidata.sort((a, b) -> Double.compare(amplitudTermica(mediciones, b), amplitudTermica(mediciones, a)));
        return candidata;
    }
}


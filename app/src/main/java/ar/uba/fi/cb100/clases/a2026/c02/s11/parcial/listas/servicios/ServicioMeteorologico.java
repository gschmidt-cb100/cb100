package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.servicios;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Ciudad;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.EstadisticaDeCiudad;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Medicion;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase funcional: no guarda estado, solo procesa mediciones (con la API de List, sin streams).
 */
public class ServicioMeteorologico {

    /**
     * Busca las ciudades cuya amplitud térmica sea mayor a amplitudMinima.
     * La amplitud térmica de una ciudad es la diferencia entre la mayor y la menor
     * temperatura registradas en sus mediciones.
     * Solo se consideran:
     *  - las mediciones en estado VALIDA,
     *  - las ciudades de alguna de las provincias indicadas,
     *  - las ciudades con al menos cantidadMinimaDeMediciones mediciones válidas.
     * Cada ciudad aparece una única vez en el resultado.
     *
     * @param mediciones mediciones a procesar, no null
     * @param provincias provincias a considerar, no null
     * @param cantidadMinimaDeMediciones cantidad mínima de mediciones válidas por ciudad, mayor que cero
     * @param amplitudMinima la amplitud de la ciudad debe ser estrictamente mayor a este valor
     * @return las ciudades que cumplen, ordenadas de mayor a menor amplitud térmica
     * @throws IllegalArgumentException si mediciones o provincias son null, o cantidadMinimaDeMediciones <= 0
     */
    public List<Ciudad> buscarCiudadesConMayorAmplitudTermica(
                                                List<Medicion> mediciones,
                                                List<String> provincias,
                                                int cantidadMinimaDeMediciones,
                                                double amplitudMinima) {

        ValidacionesUtiles.validarDistintoDeNull(mediciones, "mediciones");
        ValidacionesUtiles.validarDistintoDeNull(provincias, "provincias");
        ValidacionesUtiles.esMayorQueCero(cantidadMinimaDeMediciones, "cantidadMinimaDeMediciones");

        List<EstadisticaDeCiudad> estadisticas = getEstadisticasDeCiudades(mediciones, provincias);

        List<EstadisticaDeCiudad> estadisticasQueCumplen = new ArrayList<>();
        for (EstadisticaDeCiudad estadistica : estadisticas) {
            if (estadistica.getCantidadDeMedicionesValidas() >= cantidadMinimaDeMediciones &&
                estadistica.getAmplitudTermica() > amplitudMinima) {
                estadisticasQueCumplen.add(estadistica);
            }
        }

        // De mayor a menor amplitud térmica
        estadisticasQueCumplen.sort((e1, e2) -> Double.compare(e2.getAmplitudTermica(), e1.getAmplitudTermica()));

        return getCiudades(estadisticasQueCumplen);
    }

    /**
     * Obtiene la lista de ciudades a partir de la lista de estadísticas.
     * @param estadisticas: estadísticas de ciudades, no null
     * @return lista de ciudades correspondientes a las estadísticas, sin repetidas
     */
    public List<Ciudad> getCiudades(List<EstadisticaDeCiudad> estadisticas) {
        List<Ciudad> resultado = new ArrayList<>();
        for (EstadisticaDeCiudad estadistica : estadisticas) {
            resultado.add(estadistica.getCiudad());
        }
        return resultado;
    }

    /**
     * Agrupa las mediciones válidas de las provincias indicadas en una estadística por ciudad.
     *
     * @param mediciones mediciones a procesar, no null
     * @param provincias provincias a considerar, no null
     * @return una estadística por cada ciudad (sin repetidas)
     */
    public List<EstadisticaDeCiudad> getEstadisticasDeCiudades(List<Medicion> mediciones, List<String> provincias) {
        ValidacionesUtiles.validarDistintoDeNull(mediciones, "mediciones");
        ValidacionesUtiles.validarDistintoDeNull(provincias, "provincias");

        List<EstadisticaDeCiudad> estadisticas = new ArrayList<>();
        for (Medicion medicion : mediciones) {
            if (medicion.esValida() &&
                medicion.getCiudad().perteneceAAlgunaDe(provincias)) {
                agregarMedicion(estadisticas, medicion);
            }
        }
        return estadisticas;
    }

    /**
     * Agrega la medición a la estadística de su ciudad; si la ciudad todavía no tiene
     * estadística, la crea.
     * pre: la medición es válida
     * @param estadisticas estadísticas acumuladas
     * @param medicion medición a registrar
     */
    private void agregarMedicion(List<EstadisticaDeCiudad> estadisticas, Medicion medicion) {
        EstadisticaDeCiudad nueva = new EstadisticaDeCiudad(medicion);
        int posicion = estadisticas.indexOf(nueva);
        if (posicion == -1) {
            estadisticas.add(nueva);
        } else {
            estadisticas.get(posicion).agregar(medicion);
        }
    }
}

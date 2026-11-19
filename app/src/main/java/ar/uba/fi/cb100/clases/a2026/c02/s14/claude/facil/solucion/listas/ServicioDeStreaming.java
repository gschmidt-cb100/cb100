package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.listas;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Punto 3 del parcial. Clase funcional: no guarda estado, sólo procesa
 * opiniones con la API de List (sin streams).
 *
 * Estrategia (antes de escribir código):
 *  1. Agrupar: recorrer las opiniones una vez y armar una estadística por
 *     serie (cantidad y suma de puntajes), descartando las no publicadas y
 *     las de géneros no pedidos.
 *  2. Filtrar: quedarse con las estadísticas que tienen suficientes
 *     opiniones y promedio mayor al mínimo.
 *  3. Ordenar de mayor a menor promedio.
 *  4. Proyectar: de cada estadística, la serie.
 * Cada paso es un método; el público queda como un guion que los coordina.
 *
 * Hipótesis:
 *  H1. "Mayor a promedioMinimo" es estricto: una serie con promedio igual
 *      al mínimo no entra.
 *  H2. Si dos series tienen el mismo promedio, quedan en el orden en que
 *      aparecieron por primera vez en las opiniones (el sort es estable).
 *  H3. El resultado es una lista nueva: no se modifican las listas recibidas.
 */
public class ServicioDeStreaming {
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Busca las series cuyo puntaje promedio es mayor a {@code promedioMinimo}.
     * Sólo cuentan las opiniones PUBLICADAS, las series de alguno de los
     * géneros recibidos y las series con al menos
     * {@code cantidadMinimaDeOpiniones} opiniones publicadas.
     *
     * pre: {@code opiniones} y {@code generos} no son null;
     *      {@code cantidadMinimaDeOpiniones} es mayor que cero.
     * post: devuelve las series que cumplen, sin repetir, ordenadas de mayor
     *       a menor puntaje promedio. Si ninguna cumple, la lista está vacía.
     *       Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public List<Serie> buscarSeriesMejorPuntuadas(
                                        List<Opinion> opiniones,
                                        List<String> generos,
                                        int cantidadMinimaDeOpiniones,
                                        double promedioMinimo) {

        ValidacionesUtiles.validarDistintoDeNull(opiniones, "opiniones");
        ValidacionesUtiles.validarDistintoDeNull(generos, "géneros");
        ValidacionesUtiles.esMayorQueCero(cantidadMinimaDeOpiniones, "cantidad mínima de opiniones");

        List<EstadisticaDeSerie> estadisticas = getEstadisticasDeSeries(opiniones, generos);
        List<EstadisticaDeSerie> estadisticasQueCumplen =
                filtrar(estadisticas, cantidadMinimaDeOpiniones, promedioMinimo);

        // De mayor a menor puntaje promedio
        estadisticasQueCumplen.sort((e1, e2) -> Double.compare(e2.getPuntajePromedio(), e1.getPuntajePromedio()));

        return getSeries(estadisticasQueCumplen);
    }

    /**
     * pre: {@code opiniones} y {@code generos} no son null.
     * post: devuelve una estadística por cada serie (sin repetir) que tenga
     *       al menos una opinión publicada y sea de alguno de los géneros.
     */
    public List<EstadisticaDeSerie> getEstadisticasDeSeries(List<Opinion> opiniones, List<String> generos) {
        ValidacionesUtiles.validarDistintoDeNull(opiniones, "opiniones");
        ValidacionesUtiles.validarDistintoDeNull(generos, "géneros");

        List<EstadisticaDeSerie> estadisticas = new ArrayList<>();
        for (Opinion opinion : opiniones) {
            if (opinion.estaPublicada() && opinion.getSerie().esDeAlgunoDe(generos)) {
                agregarOpinion(estadisticas, opinion);
            }
        }
        return estadisticas;
    }

    /**
     * Agrega la opinión a la estadística de su serie; si la serie todavía no
     * tiene estadística, la crea.
     * pre: la opinión está publicada.
     */
    private void agregarOpinion(List<EstadisticaDeSerie> estadisticas, Opinion opinion) {
        EstadisticaDeSerie nueva = new EstadisticaDeSerie(opinion);
        int posicion = estadisticas.indexOf(nueva);
        if (posicion == -1) {
            estadisticas.add(nueva);
        } else {
            estadisticas.get(posicion).agregar(opinion);
        }
    }

    /**
     * Devuelve una lista nueva con las estadísticas que tienen al menos
     * {@code cantidadMinimaDeOpiniones} opiniones y promedio mayor a
     * {@code promedioMinimo} (H1).
     */
    private List<EstadisticaDeSerie> filtrar(List<EstadisticaDeSerie> estadisticas,
                                             int cantidadMinimaDeOpiniones,
                                             double promedioMinimo) {
        List<EstadisticaDeSerie> queCumplen = new ArrayList<>();
        for (EstadisticaDeSerie estadistica : estadisticas) {
            if (estadistica.getCantidadDeOpiniones() >= cantidadMinimaDeOpiniones &&
                estadistica.getPuntajePromedio() > promedioMinimo) {
                queCumplen.add(estadistica);
            }
        }
        return queCumplen;
    }

    /** Devuelve las series de las estadísticas, en el mismo orden. */
    private List<Serie> getSeries(List<EstadisticaDeSerie> estadisticas) {
        List<Serie> series = new ArrayList<>();
        for (EstadisticaDeSerie estadistica : estadisticas) {
            series.add(estadistica.getSerie());
        }
        return series;
    }
}

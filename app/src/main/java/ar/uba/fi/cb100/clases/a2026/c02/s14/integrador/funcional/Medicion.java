package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.TipoDeIndice;

/**
 * Lo que costó un índice: memoria que ocupa, tiempo que llevó armarlo y
 * tiempo promedio de una búsqueda. Es el dato que alimenta la gráfica.
 *
 * @param bytesDeMemoria        memoria aproximada del índice (ver {@link CatalogoDeIndices})
 * @param nanosDeConstruccion   tiempo de armado
 * @param nanosPorBusqueda      tiempo promedio de una búsqueda de la muestra
 */
public record Medicion(TipoDeIndice tipo, CampoDeBusqueda campo, long bytesDeMemoria,
                       long nanosDeConstruccion, long nanosPorBusqueda) {

    public double getKilobytes() {
        return bytesDeMemoria / 1024.0;
    }

    public double getMilisegundosDeConstruccion() {
        return nanosDeConstruccion / 1_000_000.0;
    }

    public double getMicrosegundosPorBusqueda() {
        return nanosPorBusqueda / 1_000.0;
    }
}

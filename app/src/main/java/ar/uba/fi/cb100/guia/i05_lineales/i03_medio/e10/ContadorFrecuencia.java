package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e10;

import java.util.Collections;
import java.util.List;

/**
 * Cálculo de la frecuencia (cantidad de apariciones) de un valor en una lista.
 */
public final class ContadorFrecuencia {

    private ContadorFrecuencia() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Cuenta cuántas veces aparece {@code x} en la lista.
     *
     * @param l lista de enteros (no nula)
     * @param x valor a contar
     * @return cantidad de apariciones de {@code x}
     */
    public static int veces(List<Integer> l, int x) {
        // Collections.frequency compara con equals sobre cada elemento.
        return Collections.frequency(l, x);
    }
}

package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e08;

import java.util.HashSet;
import java.util.List;

/**
 * e08: contar cuántos padrones distintos hay en una lista.
 * El {@link HashSet} descarta duplicados solo: metemos todo
 * y el tamaño final es la respuesta.
 */
public final class PadronesUnicos {

    private PadronesUnicos() {
    }

    /**
     * Devuelve la cantidad de valores distintos en {@code padrones}.
     *
     * @param padrones lista de padrones (puede tener repetidos)
     * @return cantidad de padrones distintos
     */
    public static int cantidadDistintos(List<Integer> padrones) {
        // El constructor de HashSet ya elimina los repetidos.
        return new HashSet<>(padrones).size();
    }

    public static void main(String[] args) {
        List<Integer> padrones = List.of(110001, 110002, 110001, 110003, 110002);
        System.out.println("Padrones: " + padrones);
        System.out.println("Distintos: " + cantidadDistintos(padrones));
    }
}

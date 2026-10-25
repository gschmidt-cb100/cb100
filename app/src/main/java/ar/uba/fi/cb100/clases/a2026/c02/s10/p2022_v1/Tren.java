package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Tren, versión 1: el tren <b>no sabe qué tipos de carga existen</b>.
 * Para cargar algo recorre sus vagones, se queda con los que son
 * {@link VagonCargable} y le pide a cada uno {@link VagonCargable#aceptaCarga};
 * el primero que dice que sí, la recibe. Pasajeros, contenedores, granel o el
 * tipo de carga número 100 pasan por el mismo método.
 * <p>
 * Hipótesis:
 * <ul>
 *   <li>H1: los vagones se numeran de 1 a {@code cantidadDeVagones} en orden de
 *       enganche; al retirar uno, los que siguen se corren.</li>
 *   <li>H2: no se retira un vagón cargable con carga a bordo.</li>
 *   <li>H3: una carga sube entera al primer vagón (de adelante hacia atrás)
 *       que la acepte; si ninguno la acepta, es un error y no sube.</li>
 *   <li>H4: para descargar hay que decir qué carga: el tren busca el vagón
 *       que la tiene. Si no está en el tren, es un error.</li>
 *   <li>H5: un mismo vagón no se engancha dos veces.</li>
 * </ul>
 */
public class Tren {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Vagon[] vagones;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code cantidadMaximaDeVagones} es mayor que cero.
     * post: crea el tren sin vagones, con lugar para esa cantidad.
     */
    public Tren(int cantidadMaximaDeVagones) {
        ValidacionesUtiles.esMayorQueCero(cantidadMaximaDeVagones, "cantidad máxima de vagones");
        this.vagones = new Vagon[cantidadMaximaDeVagones];
    }

//METODOS DE COMPORTAMIENTO: VAGONES ----------------------------------------------------------------------

    /**
     * pre: {@code vagon} no es null, no está ya en el tren (H5) y hay lugar.
     * post: engancha el vagón al final. Null o repetido: {@link IllegalArgumentException};
     *       tren completo: {@link IllegalStateException}.
     */
    public void agregarVagon(Vagon vagon) {
        ValidacionesUtiles.validarDistintoDeNull(vagon, "vagón");
        ValidacionesUtiles.validarFalso(posicionDe(vagon) != 0, "Ese vagón ya está en el tren, en la posición " + posicionDe(vagon));
        ValidacionesUtiles.validarEstadoFalso(estaCompleto(), "El tren está completo: " + vagones.length + " vagones");
        vagones[getCantidadDeVagones()] = vagon;
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de vagones (H1) y, si el
     *      vagón es cargable, está vacío (H2).
     * post: desengancha y devuelve el vagón; los que seguían se corren un lugar.
     */
    public Vagon retirarVagon(int posicion) {
        Vagon vagon = getVagon(posicion);
        if (vagon instanceof VagonCargable cargable) {
            ValidacionesUtiles.validarEstadoFalso(!cargable.estaVacio(),
                    "El vagón " + posicion + " tiene carga a bordo: hay que descargarlo antes");
        }
        for (int i = posicion - 1; i < this.vagones.length - 1; i++) {
            vagones[i] = vagones[i + 1];
        }
        vagones[this.vagones.length - 1] = null;
        return vagon;
    }

//METODOS DE COMPORTAMIENTO: CARGA ------------------------------------------------------------------------

    /**
     * pre: {@code carga} no es null y algún vagón cargable la acepta (H3).
     * post: la carga sube al primer vagón que la acepta y devuelve la posición
     *       de ese vagón. Si ninguno la acepta, lanza {@link IllegalStateException}
     *       y no cambia nada.
     */
    public int cargar(Carga carga) {
        ValidacionesUtiles.validarDistintoDeNull(carga, "carga");
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            if (cargables[i].aceptaCarga(carga)) {
                cargables[i].agregarCarga(carga);
                return i + 1;
            }
        }
        throw new IllegalStateException("Ningún vagón acepta " + carga.getDescripcion());
    }

    /**
     * pre: {@code carga} no es null y está a bordo de algún vagón (H4).
     * post: la carga baja del vagón que la tenía y devuelve la posición de ese
     *       vagón. Si no estaba en el tren, lanza {@link IllegalArgumentException}.
     */
    public int descargar(Carga carga) {
        ValidacionesUtiles.validarDistintoDeNull(carga, "carga");
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            if (cargables[i].contiene(carga)) {
                cargables[i].quitarCarga(carga);
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Esa carga no viaja en el tren: " + carga.getDescripcion());
    }

    /** post: indica si alguna carga del tren es igual a {@code carga}. */
    public boolean contiene(Carga carga) {
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            if (cargables[i].contiene(carga)) {
                return true;
            }
        }
        return false;
    }

//METODOS DE CONSULTA -------------------------------------------------------------------------------------

    /** post: devuelve la longitud total [m]: la suma de las de todos los vagones. */
    public double getLongitudTotal() {
        double total = 0;
        for(Vagon vagon : vagones) {
            if (vagon != null) {
                total += vagon.getLongitud();
            }
        }
        return total;
    }

    /** post: devuelve cuántas cargas hay a bordo, de todos los tipos. */
    public int getCantidadDeCargas() {
        int total = 0;
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            total += cargables[i].getCantidadDeCargas();
        }
        return total;
    }

    /** post: devuelve el peso [t] de todo lo que va a bordo. */
    public double getPesoDeCarga() {
        double total = 0;
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            total += cargables[i].getPesoDeCarga();
        }
        return total;
    }

    /**
     * post: devuelve cuántas cargas de ese tipo entran todavía, sumando los
     *       lugares libres de los vagones que lo admiten. Para PASAJERO es la
     *       "capacidad restante de pasajeros" del enunciado.
     */
    public int getLugaresLibresPara(TipoDeCarga tipo) {
        ValidacionesUtiles.validarDistintoDeNull(tipo, "tipo de carga");
        int total = 0;
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            if (cargables[i].admiteTipo(tipo)) {
                total += cargables[i].getLugaresLibres();
            }
        }
        return total;
    }

    /**
     * post: devuelve cuántas toneladas de ese tipo entran todavía, sumando el
     *       peso disponible de los vagones que lo admiten. Para CONTENEDOR o
     *       GRANEL es la "capacidad restante de carga" del enunciado.
     */
    public double getPesoDisponiblePara(TipoDeCarga tipo) {
        ValidacionesUtiles.validarDistintoDeNull(tipo, "tipo de carga");
        double total = 0;
        VagonCargable[] cargables = getVagonesCargables();
        for (int i = 0; i < cargables.length; i++) {
            if (cargables[i].admiteTipo(tipo)) {
                total += cargables[i].getPesoDisponible();
            }
        }
        return total;
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de vagones (H1).
     * post: devuelve el vagón de esa posición.
     */
    public Vagon getVagon(int posicion) {
        ValidacionesUtiles.validarRango(posicion, 1, getCantidadDeVagones(), "posición del vagón");
        return vagones[posicion - 1];
    }

    /** post: indica si no entran más vagones. */
    public boolean estaCompleto() {
        return getCantidadDeVagones() == vagones.length;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /** post: devuelve la posición (1..n) en la que está el vagón, o 0 si no está. */
    private int posicionDe(Vagon vagon) {
        for (int i = 0; i < getCantidadDeVagones(); i++) {
            if (vagones[i].equals(vagon)) {
                return i + 1;
            }
        }
        return 0;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "tren de " + getCantidadDeVagones() + "/" + vagones.length + " vagones, " + getLongitudTotal() + " m, "
                + getCantidadDeCargas() + " cargas, " + getPesoDeCarga() + " t";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public VagonCargable[] getVagonesCargables() {
        VagonCargable[] cargables = new VagonCargable[getCantidadDeVagonesDeCarga()];
        int j = 0;
        for (int i = 0; i < getCantidadDeVagones(); i++) {
            if (vagones[i] instanceof VagonCargable cargable) {
                cargables[j++] = cargable;
            }
        }
        return java.util.Arrays.copyOf(cargables, j);
    }

    /** post: devuelve cuántos vagones tiene enganchados. */
    public int getCantidadDeVagones() {
        for(int i = 0; i < vagones.length; i++) {
            if (vagones[i] == null) {
                return i + 1;
            }
        }
        return 0;
    }

    public int getCantidadDeVagonesDeCarga() {
        int cantidad = 0;
        for(int i = 0; i < vagones.length; i++) {
            if (vagones[i] instanceof VagonCargable) {
                cantidad++;
            }
            if (vagones[i] == null) {
                return cantidad;
            }
        }
        return 0;
    }

    /** post: devuelve cuántos vagones puede tener como máximo. */
    public int getCantidadMaximaDeVagones() {
        return vagones.length;
    }
}

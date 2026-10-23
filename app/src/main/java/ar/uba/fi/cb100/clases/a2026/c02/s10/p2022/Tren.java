package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Tren (parcial 2022, ejercicio 3). Enunciado: "El Tren se compone de una
 * serie de Vagones. Cada Vagón posee una capacidad de carga (en toneladas),
 * una capacidad de pasajeros y una longitud (en metros). Se deben poder
 * agregar y retirar Vagones del Tren. Agregar o retirar carga o pasajeros.
 * Proveer métodos para conocer la longitud total, capacidad total y restante
 * de pasajeros y capacidad total y restante de carga del Tren."
 * <p>
 * Diseño: el tren guarda sus vagones en un <b>arreglo</b> cuya capacidad
 * (cantidad máxima de vagones) entra por el constructor (checklist 9). Un
 * vagón es de pasajeros o de carga ({@link VagonDePasajeros},
 * {@link VagonDeCarga} heredan de {@link Vagon}); el tren les habla a todos
 * por igual a través de {@link Vagon} (polimorfismo) y no implementa lo que
 * es del vagón (checklist 6).
 * <p>
 * Hipótesis (lo que el enunciado no dice):
 * <ul>
 *   <li>H1: los vagones se numeran por posición de 1 a {@codegetCantidadDeVagones()},
 *       en el orden en que se engancharon; al retirar uno, los que siguen se
 *       corren un lugar.</li>
 *   <li>H2: no se puede retirar un vagón que lleva pasajeros o carga (se
 *       perderían): primero hay que bajarlos.</li>
 *   <li>H3: subir pasajeros o cargar es "todo o nada": si no entra la cantidad
 *       completa en el tren, no sube nadie y es un error. Los pasajeros se
 *       reparten llenando los vagones de adelante hacia atrás; la carga, igual.</li>
 *   <li>H4: bajar pasajeros o descargar también es "todo o nada": no se puede
 *       bajar más de lo que hay. Se vacían los vagones de atrás hacia adelante.</li>
 *   <li>H5: un mismo vagón no puede engancharse dos veces (ni en este tren ni
 *       en dos posiciones).</li>
 * </ul>
 */
public class Tren {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Vagon[] vagones;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code cantidadMaximaDeVagones} es mayor que cero.
     * post: crea el tren sin vagones, con lugar para esa cantidad. Si no se
     *       cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Tren(int cantidadMaximaDeVagones) {
        ValidacionesUtiles.esMayorQueCero(cantidadMaximaDeVagones, "cantidad máxima de vagones");
        this.vagones = new Vagon[cantidadMaximaDeVagones];
        for(int i = 0; i < vagones.length; i++) {
            vagones[i] = null;
        }
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code vagon} no es null, no está ya en el tren (H5), y hay lugar.
     * post: engancha el vagón al final del tren. Si es null o repetido, lanza
     *       {@link IllegalArgumentException}; si el tren está completo,
     *       {@link IllegalStateException}.
     */
    public void agregarVagon(Vagon vagon) {
        ValidacionesUtiles.validarDistintoDeNull(vagon, "vagón");
        ValidacionesUtiles.validarFalso(posicionDe(vagon) != 0, "Ese vagón ya está en el tren, en la posición " + posicionDe(vagon));
        ValidacionesUtiles.validarEstadoFalso(estaLleno(), "El tren está completo: " + vagones.length + " vagones");
        vagones[getCantidadDeVagones()] = vagon;
    }

    public boolean estaLleno() {
        return this.getCantidadDeVagones() == vagones.length;
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de vagones (H1), y ese
     *      vagón está vacío (H2).
     * post: desengancha y devuelve el vagón; los que seguían se corren un lugar.
     *       Posición inválida: {@link IllegalArgumentException}; vagón con
     *       pasajeros o carga: {@link IllegalStateException}.
     */
    public Vagon retirarVagonDePasajeros() {
        for(int i = this.vagones.length; i <= 0; i--) {
            if (vagones[i] instanceof VagonDePasajeros) {
                VagonDePasajeros f = (VagonDePasajeros) vagones[i];
                ValidacionesUtiles.validarEstadoFalso(!f.estaVacio(), "El vagón " + (i + 1) + " no está vacío: hay que vaciarlo antes");
                vagones[i] = null;
                return f;
            }
        }
        throw new IllegalStateException("No hay vagones de pasajeros en el tren");
    }

    /**
     * pre: {@code cantidad} es mayor que cero y no supera la capacidad
     *      restante de pasajeros del tren (H3).
     * post: sube los pasajeros repartidos de adelante hacia atrás. Si no se
     *       cumple la pre, lanza {@link IllegalArgumentException} y no sube nadie.
     */
    public void subirPasajeros(int cantidad) {
        ValidacionesUtiles.esMayorQueCero(cantidad, "cantidad de pasajeros a subir");
        ValidacionesUtiles.validarFalso(cantidad > getCapacidadRestanteDePasajeros(),
                "No hay lugar para " + cantidad + " pasajeros: quedan " + getCapacidadRestanteDePasajeros());
        int faltan = cantidad;
        for (int i = 0; i <getCantidadDeVagones() && faltan > 0; i++) {
            if (vagones[i] instanceof VagonDePasajeros vagonDePasajeros) {
                faltan -= vagonDePasajeros.subirPasajeros(faltan);
            }
        }
    }

    /**
     * pre: {@code cantidad} es mayor que cero y no supera los pasajeros a bordo (H4).
     * post: baja los pasajeros vaciando de atrás hacia adelante. Si no se
     *       cumple la pre, lanza {@link IllegalArgumentException} y no baja nadie.
     */
    public void bajarPasajeros(int cantidad) {
        ValidacionesUtiles.esMayorQueCero(cantidad, "cantidad de pasajeros a bajar");
        ValidacionesUtiles.validarFalso(cantidad > getPasajeros(),
                "No hay " + cantidad + " pasajeros a bordo: hay " + getPasajeros());
        int faltan = cantidad;
        for (int i =getCantidadDeVagones() - 1; i >= 0 && faltan > 0; i--) {
            if (vagones[i] instanceof VagonDePasajeros vagonDePasajeros) {
                faltan -= vagonDePasajeros.bajarPasajeros(faltan);
            }
        }
    }

    /**
     * pre: {@code toneladas} es mayor que cero y no supera la capacidad
     *      restante de carga del tren (H3).
     * post: carga repartiendo de adelante hacia atrás. Si no se cumple la pre,
     *       lanza {@link IllegalArgumentException} y no carga nada.
     */
    public void cargar(double toneladas) {
        ValidacionesUtiles.esMayorQueCero(toneladas, "carga");
        ValidacionesUtiles.validarFalso(toneladas > getCapacidadRestanteDeCarga(),
                "No hay lugar para " + toneladas + " t: quedan " + getCapacidadRestanteDeCarga() + " t");
        double faltan = toneladas;
        for (int i = 0; i <getCantidadDeVagones() && faltan > 0; i++) {
            if (vagones[i] instanceof VagonDeCarga vagonDeCarga) {
                faltan -= vagonDeCarga.cargar(faltan);
            }
        }
    }

    /**
     * pre: {@code toneladas} es mayor que cero y no supera la carga a bordo (H4).
     * post: descarga vaciando de atrás hacia adelante. Si no se cumple la pre,
     *       lanza {@link IllegalArgumentException} y no descarga nada.
     */
    public void descargar(double toneladas) {
        ValidacionesUtiles.esMayorQueCero(toneladas, "descarga");
        ValidacionesUtiles.validarFalso(toneladas > getCarga(),
                "No hay " + toneladas + " t a bordo: hay " + getCarga() + " t");
        double faltan = toneladas;
        for (int i =getCantidadDeVagones() - 1; i >= 0 && faltan > 0; i--) {
            if (vagones[i] instanceof VagonDeCarga vagonDeCarga) {
                faltan += vagonDeCarga.descargar(faltan);
            }
        }
    }

//METODOS DE CONSULTA -------------------------------------------------------------------------------------

    /** post: devuelve la longitud total [m]: la suma de las de sus vagones. */
    public double getLongitudTotal() {
        double total = 0;
        for (int i = 0; i <getCantidadDeVagones(); i++) {
            total += vagones[i].getLongitud();
        }
        return total;
    }

    /** post: devuelve la capacidad total de pasajeros del tren. */
    public int getCapacidadDePasajeros() {
        int total = 0;
        for (int i = 0; i <getCantidadDeVagones(); i++) {
            if (vagones[i] instanceof VagonDePasajeros vagonDePasajeros) {
                total += vagonDePasajeros.getCapacidadDePasajeros();
            }
        }
        return total;
    }

    /** post: devuelve los pasajeros a bordo del tren. */
    public int getPasajeros() {
        int total = 0;
        for (int i = 0; i <getCantidadDeVagones(); i++) {
            if (vagones[i] instanceof VagonDePasajeros vagonDePasajeros) {
                total += vagonDePasajeros.getPasajeros();
            }
        }
        return total;
    }

    /** post: devuelve cuántos pasajeros más entran en el tren. */
    public int getCapacidadRestanteDePasajeros() {
        return getCapacidadDePasajeros() - getPasajeros();
    }

    /** post: devuelve la capacidad total de carga [t] del tren. */
    public double getCapacidadDeCarga() {
        double total = 0;
        for (int i = 0; i < getCantidadDeVagones(); i++) {
            if (vagones[i] instanceof VagonDeCarga vagonDeCarga) {
                total += vagonDeCarga.getCapacidadDeCarga();
            }
        }
        return total;
    }

    /** post: devuelve la carga [t] a bordo del tren. */
    public double getCarga() {
        double total = 0;
        for (int i = 0; i < getCantidadDeVagones(); i++) {
            if (vagones[i] instanceof VagonDeCarga vagonDeCarga) {
                total += vagonDeCarga.getCarga();
            }
        }
        return total;
    }

    /** post: devuelve cuántas toneladas más entran en el tren. */
    public double getCapacidadRestanteDeCarga() {
        return getCapacidadDeCarga() - getCarga();
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de vagones (H1).
     * post: devuelve el vagón de esa posición; si no existe, lanza
     *       {@link IllegalArgumentException}.
     */
    public Vagon getVagon(int posicion) {
        ValidacionesUtiles.validarRango(posicion, 1, getCantidadDeVagones(), "posición del vagón");
        return vagones[posicion - 1];
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /** post: devuelve la posición (1..n) en la que está el vagón, o 0 si no está. */
    private int posicionDe(Vagon vagon) {
        for (int i = 0; i < getCantidadDeVagones(); i++) {
            if (vagones[i] == vagon) {
                return i + 1;
            }
        }
        return 0;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "tren de " + getCantidadDeVagones() + "/" + vagones.length + " vagones, " + getLongitudTotal() + " m, "
                + getPasajeros() + "/" + getCapacidadDePasajeros() + " pasajeros, "
                + getCarga() + "/" + getCapacidadDeCarga() + " t";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getCantidadDeVagones() {
        for(int i = 0; i < vagones.length; i++) {
            if (vagones[i] == null) {
                return i;
            }
        }
        return vagones.length;
    }

    /** post: devuelve cuántos vagones puede tener como máximo. */
    public int getCantidadMaximaDeVagones() {
        return vagones.length;
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.tda.datos.Bicicleta;

/**
 * TDA EstacionDeBicicletas (clase funcional).
 *
 * Una estación tiene una cantidad fija de anclajes, numerados de 1 a cantidadDeAnclajes.
 * Los anclajes se modelan con un vector de Bicicleta: la posición i-1 es el anclaje i,
 * y un null indica que el anclaje está libre (no hay TDA Anclaje).
 * La estación solo conoce las bicicletas que tiene ancladas; los usos los sabe cada bicicleta.
 *
 * Invariantes:
 *  - anclajes.length > 0 y no cambia.
 *  - Una misma bicicleta no puede estar en dos anclajes a la vez.
 */
public class EstacionDeBicicletas {

//ATRIBUTOS

    private final Bicicleta[] anclajes;

//CONSTRUCTORES

    /**
     * Crea una estación vacía (todos los anclajes libres) con la cantidad de anclajes indicada.
     * @param cantidadDeAnclajes cantidad fija de anclajes, mayor que cero
     * @throws IllegalArgumentException si cantidadDeAnclajes no es mayor que cero
     */
    public EstacionDeBicicletas(int cantidadDeAnclajes) {
        ValidacionesUtiles.esMayorQueCero(cantidadDeAnclajes, "cantidadDeAnclajes");
        this.anclajes = new Bicicleta[cantidadDeAnclajes];
    }

//METODOS DE COMPORTAMIENTO

    /**
     * Devuelve (ancla) una bicicleta en el primer anclaje libre de la estación.
     * @param bicicleta bicicleta a devolver, no null
     * @return el número de anclaje (1..cantidadDeAnclajes) donde quedó anclada
     * @throws IllegalArgumentException si la bicicleta es null o ya está anclada en esta estación
     * @throws IllegalStateException si la estación está llena
     * post: la bicicleta ocupa el primer anclaje que estaba libre
     */
    public int devolver(Bicicleta bicicleta) {
        ValidacionesUtiles.validarDistintoDeNull(bicicleta, "bicicleta");
        ValidacionesUtiles.validarFalso(estaAnclada(bicicleta), "La bicicleta " + bicicleta.getNumeroDeSerie() + " ya está anclada en la estación");
        ValidacionesUtiles.validarEstadoFalso(estaLlena(), "La estación está llena, no se puede devolver la bicicleta");

        int posicion = buscarPrimerAnclajeLibre();
        this.anclajes[posicion] = bicicleta;
        return posicion + 1;
    }

    /**
     * Retira la bicicleta del anclaje indicado.
     * @param numeroDeAnclaje número de anclaje, entre 1 y cantidadDeAnclajes
     * @return la bicicleta retirada
     * @throws IllegalArgumentException si el número de anclaje está fuera de rango
     * @throws IllegalStateException si el anclaje está libre o la bicicleta está en reparación
     * post: el anclaje queda libre y la bicicleta suma un uso
     */
    public Bicicleta retirar(int numeroDeAnclaje) {
        validarNumeroDeAnclaje(numeroDeAnclaje);
        Bicicleta bicicleta = this.anclajes[numeroDeAnclaje - 1];
        ValidacionesUtiles.validarEstadoFalso(bicicleta == null, "El anclaje " + numeroDeAnclaje + " está libre");
        ValidacionesUtiles.validarEstadoFalso(bicicleta.estaEnReparacion(), "La bicicleta " + bicicleta.getNumeroDeSerie() + " está en reparación, no se puede retirar");

        bicicleta.registrarUso();
        this.anclajes[numeroDeAnclaje - 1] = null;
        return bicicleta;
    }

//CONSULTAS

    /**
     * @return la cantidad de anclajes ocupados (con una bicicleta)
     */
    public int getCantidadDeAnclajesOcupados() {
        int ocupados = 0;
        for (Bicicleta bicicleta : this.anclajes) {
            if (bicicleta != null) {
                ocupados++;
            }
        }
        return ocupados;
    }

    /**
     * @return la cantidad de anclajes libres
     */
    public int getCantidadDeAnclajesLibres() {
        return this.anclajes.length - getCantidadDeAnclajesOcupados();
    }

    /**
     * @return el porcentaje de ocupación de la estación (entre 0 y 100)
     */
    public double getPorcentajeDeOcupacion() {
        return getCantidadDeAnclajesOcupados() * 100.0 / this.anclajes.length;
    }

    /**
     * Busca, entre las bicicletas ancladas, la que más veces se usó (retiró).
     * Si hay empate, devuelve la del anclaje de menor número.
     * @return la bicicleta anclada con más usos, o null si la estación está vacía
     */
    public Bicicleta getBicicletaMasUsada() {
        Bicicleta maxima = null;
        for (Bicicleta bicicleta : this.anclajes) {
            if (bicicleta != null &&
                (maxima == null || bicicleta.getCantidadDeUsos() > maxima.getCantidadDeUsos())) {
                maxima = bicicleta;
            }
        }
        return maxima;
    }

    /**
     * Busca, entre las bicicletas ancladas, la que tiene más kilómetros.
     * Si hay empate, devuelve la del anclaje de menor número.
     * @return la bicicleta anclada con más kilómetros, o null si la estación está vacía
     */
    public Bicicleta getBicicletaConMasKilometros() {
        Bicicleta maxima = null;
        for (Bicicleta bicicleta : this.anclajes) {
            if (bicicleta != null &&
                (maxima == null || bicicleta.getKilometros() > maxima.getKilometros())) {
                maxima = bicicleta;
            }
        }
        return maxima;
    }

    /**
     * @param numeroDeAnclaje número de anclaje, entre 1 y cantidadDeAnclajes
     * @return la bicicleta anclada, o null si el anclaje está libre
     * @throws IllegalArgumentException si el número de anclaje está fuera de rango
     */
    public Bicicleta getBicicleta(int numeroDeAnclaje) {
        validarNumeroDeAnclaje(numeroDeAnclaje);
        return this.anclajes[numeroDeAnclaje - 1];
    }

    /**
     * @return la cantidad total de anclajes de la estación
     */
    public int getCantidadDeAnclajes() {
        return this.anclajes.length;
    }

    /**
     * @return true si no quedan anclajes libres
     */
    public boolean estaLlena() {
        return getCantidadDeAnclajesLibres() == 0;
    }

    /**
     * @return true si no hay bicicletas ancladas
     */
    public boolean estaVacia() {
        return getCantidadDeAnclajesOcupados() == 0;
    }

    /**
     * @param bicicleta bicicleta a buscar
     * @return true si la bicicleta está anclada en esta estación
     */
    public boolean estaAnclada(Bicicleta bicicleta) {
        for (Bicicleta anclada : this.anclajes) {
            if (anclada != null && anclada.equals(bicicleta)) {
                return true;
            }
        }
        return false;
    }

//METODOS PRIVADOS

    private void validarNumeroDeAnclaje(int numeroDeAnclaje) {
        ValidacionesUtiles.validarRango(numeroDeAnclaje, 1, this.anclajes.length, "numeroDeAnclaje");
    }

    /**
     * pre: la estación no está llena
     * @return la posición (0..n-1) del primer anclaje libre
     */
    private int buscarPrimerAnclajeLibre() {
        int i = 0;
        while (this.anclajes[i] != null) {
            i++;
        }
        return i;
    }

//METODOS GENERALES

    @Override
    public String toString() {
        String resultado = "Estación (" + getCantidadDeAnclajesOcupados() + "/" + this.anclajes.length + ")";
        for (int i = 0; i < this.anclajes.length; i++) {
            resultado += "\n  Anclaje " + (i + 1) + ": " + (this.anclajes[i] == null ? "libre" : this.anclajes[i]);
        }
        return resultado;
    }
}

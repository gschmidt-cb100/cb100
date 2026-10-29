package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Mano (dato): las cartas que tiene un jugador para tirar.
 *
 * Invariante: tiene como mucho {@code capacidad} cartas, sin repetidas.
 */
public class Mano {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int capacidad;
    private final List<CartaEspanola> cartas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una mano vacía.
     * @param capacidad cuántas cartas entran, mayor que cero
     */
    public Mano(int capacidad) {
        ValidacionesUtiles.esMayorQueCero(capacidad, "capacidad");
        this.capacidad = capacidad;
        this.cartas = new ArrayList<>();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @param carta carta que se recibe, no null ni repetida
     * @throws IllegalStateException si la mano está llena
     */
    public void recibir(CartaEspanola carta) {
        ValidacionesUtiles.validarDistintoDeNull(carta, "carta");
        ValidacionesUtiles.validarEstadoFalso(estaLlena(), "La mano ya tiene " + this.capacidad + " cartas.");
        ValidacionesUtiles.validarFalso(contiene(carta), "La carta " + carta + " ya está en la mano.");
        this.cartas.add(carta);
    }

    /**
     * @param carta carta a tirar, que tiene que estar en la mano
     * @throws IllegalArgumentException si no está en la mano
     */
    public void quitar(CartaEspanola carta) {
        ValidacionesUtiles.validarFalso(!contiene(carta), "La carta " + carta + " no está en la mano.");
        this.cartas.remove(carta);
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    public boolean contiene(CartaEspanola carta) {
        return this.cartas.contains(carta);
    }

    /**
     * @return una copia de las cartas, en el orden en que se recibieron
     */
    public List<CartaEspanola> getCartas() {
        return new ArrayList<>(this.cartas);
    }

    public boolean estaVacia() {
        return this.cartas.isEmpty();
    }

    public boolean estaLlena() {
        return this.cartas.size() == this.capacidad;
    }

    public int getCantidad() {
        return this.cartas.size();
    }

    public int getCapacidad() {
        return capacidad;
    }
}

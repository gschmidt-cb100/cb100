package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Pila;

/**
 * TDA Casita (dato): la pila de cartas que ganó un jugador, boca arriba.
 * Es una {@link Pila}: sólo se ve la carta de arriba, y es la que decide si
 * otro jugador te la puede robar.
 *
 * <p>Robar una casita es llevarse la pila <b>entera</b>.
 */
public class Casita {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Pila<CartaEspanola> cartas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una casita vacía.
     */
    public Casita() {
        this.cartas = new Pila<>();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @param carta carta que se pone arriba, no null
     */
    public void agregar(CartaEspanola carta) {
        this.cartas.apilar(carta);
    }

    /**
     * @param cartas cartas que se ponen arriba, en orden (la última queda arriba), no null
     */
    public void agregarTodas(List<CartaEspanola> cartas) {
        ValidacionesUtiles.validarDistintoDeNull(cartas, "cartas");
        for (CartaEspanola carta : cartas) {
            agregar(carta);
        }
    }

    /**
     * Se lleva todas las cartas: la casita queda vacía.
     * @return las cartas en el orden en que estaban (la de arriba, última)
     */
    public List<CartaEspanola> robarTodas() {
        List<CartaEspanola> robadas = new ArrayList<>();
        while (!this.cartas.estaVacia()) {
            robadas.add(0, this.cartas.desapilar());     // desapilar da la de arriba primero: se agrega adelante
        }
        return robadas;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return la carta de arriba, o null si la casita está vacía
     */
    public CartaEspanola getCartaDeArriba() {
        if (estaVacia()) {
            return null;
        }
        return this.cartas.tope();
    }

    /**
     * @param carta carta a comparar, no null
     * @return true si la casita no está vacía y la de arriba tiene el mismo número
     */
    public boolean sePuedeRobarCon(CartaEspanola carta) {
        ValidacionesUtiles.validarDistintoDeNull(carta, "carta");
        return !estaVacia() && getCartaDeArriba().tieneElMismoNumeroQue(carta);
    }

    public boolean estaVacia() {
        return this.cartas.estaVacia();
    }

    public int getCantidad() {
        return this.cartas.tamanio();
    }
}

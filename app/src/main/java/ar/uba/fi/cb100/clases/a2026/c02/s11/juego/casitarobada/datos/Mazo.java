package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Pila;

/**
 * TDA Mazo (dato): las cartas boca abajo de las que se reparte. Es una
 * {@link Pila}: sólo se puede sacar la de arriba.
 */
public class Mazo {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Pila<CartaEspanola> cartas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea un mazo con las cartas indicadas; la última de la lista queda arriba.
     * @param cartas cartas del mazo, no null
     */
    public Mazo(List<CartaEspanola> cartas) {
        ValidacionesUtiles.validarDistintoDeNull(cartas, "cartas");
        this.cartas = new Pila<>();
        for (CartaEspanola carta : cartas) {
            this.cartas.apilar(carta);
        }
    }

    /**
     * @param azar generador para mezclar, no null
     * @return un mazo con las 40 cartas mezcladas
     */
    public static Mazo crearMezclado(Random azar) {
        ValidacionesUtiles.validarDistintoDeNull(azar, "azar");
        List<CartaEspanola> baraja = CartaEspanola.crearBaraja();
        Collections.shuffle(baraja, azar);
        return new Mazo(baraja);
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @return la carta de arriba, que sale del mazo
     * @throws IllegalStateException si el mazo está vacío
     */
    public CartaEspanola sacar() {
        ValidacionesUtiles.validarEstadoFalso(estaVacio(), "El mazo está vacío.");
        return this.cartas.desapilar();
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    public boolean estaVacio() {
        return this.cartas.estaVacia();
    }

    public int getCantidad() {
        return this.cartas.tamanio();
    }
}

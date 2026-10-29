package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA CartaEspanola (dato inmutable): una carta de la baraja española de 40
 * cartas (sin 8 ni 9): números del 1 al 7 y del 10 al 12, en cuatro palos.
 *
 * <p>En la casita robada sólo importa el <b>número</b>: dos cartas "coinciden"
 * si tienen el mismo número, sin importar el palo.
 *
 * Invariantes: el número es 1..7 o 10..12; el palo no es null.
 */
public class CartaEspanola {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final int[] NUMEROS_VALIDOS = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int numero;
    private final Palo palo;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param numero número de la carta: 1 a 7, o 10 a 12
     * @param palo palo, no null
     * @throws IllegalArgumentException si el número no existe en la baraja o el palo es null
     */
    public CartaEspanola(int numero, Palo palo) {
        ValidacionesUtiles.validarFalso(!esNumeroValido(numero), "No hay cartas con el número " + numero + " en la baraja española de 40.");
        ValidacionesUtiles.validarDistintoDeNull(palo, "palo");
        this.numero = numero;
        this.palo = palo;
    }

//METODOS DE CLASE ----------------------------------------------------------------------------------------

    /**
     * @return las 40 cartas de la baraja, ordenadas por palo y número
     */
    public static List<CartaEspanola> crearBaraja() {
        List<CartaEspanola> baraja = new ArrayList<>();
        for (Palo palo : Palo.values()) {
            for (int numero : NUMEROS_VALIDOS) {
                baraja.add(new CartaEspanola(numero, palo));
            }
        }
        return baraja;
    }

    private static boolean esNumeroValido(int numero) {
        for (int valido : NUMEROS_VALIDOS) {
            if (valido == numero) {
                return true;
            }
        }
        return false;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @param otra otra carta, no null
     * @return true si tiene el mismo número (el palo no importa)
     */
    public boolean tieneElMismoNumeroQue(CartaEspanola otra) {
        ValidacionesUtiles.validarDistintoDeNull(otra, "otra");
        return this.numero == otra.numero;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getNumero() {
        return numero;
    }

    public Palo getPalo() {
        return palo;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /**
     * Dos cartas son iguales si tienen el mismo número y el mismo palo (en la baraja hay una sola de cada una).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartaEspanola otra = (CartaEspanola) o;
        return this.numero == otra.numero && this.palo == otra.palo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, palo);
    }

    @Override
    public String toString() {
        return numero + " de " + palo.getNombre();
    }
}

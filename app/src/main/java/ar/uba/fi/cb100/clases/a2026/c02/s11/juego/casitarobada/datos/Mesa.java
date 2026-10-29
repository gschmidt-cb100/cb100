package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Mesa (dato): las cartas boca arriba en el centro, que cualquiera puede levantar.
 *
 * <p>Una observación para pensar: como al tirar una carta que coincide se
 * levantan <b>todas</b> las de ese número, una carta sólo queda en la mesa si
 * no había otra igual. Salvo las 4 del reparto inicial, nunca hay dos cartas
 * del mismo número en la mesa, así que la mesa nunca tiene muchas cartas.
 */
public class Mesa {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final List<CartaEspanola> cartas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una mesa vacía.
     */
    public Mesa() {
        this.cartas = new ArrayList<>();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @param carta carta que se deja en la mesa, no null
     */
    public void poner(CartaEspanola carta) {
        ValidacionesUtiles.validarDistintoDeNull(carta, "carta");
        this.cartas.add(carta);
    }

    /**
     * Levanta de la mesa todas las cartas con el mismo número que la indicada.
     * @param carta carta con la que se levanta, no null
     * @return las cartas levantadas (vacía si no había ninguna)
     */
    public List<CartaEspanola> levantarLasQueCoincidenCon(CartaEspanola carta) {
        List<CartaEspanola> levantadas = getLasQueCoincidenCon(carta);
        for (CartaEspanola levantada : levantadas) {
            this.cartas.remove(levantada);
        }
        return levantadas;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @param carta carta a comparar, no null
     * @return las cartas de la mesa con el mismo número (sin sacarlas)
     */
    public List<CartaEspanola> getLasQueCoincidenCon(CartaEspanola carta) {
        ValidacionesUtiles.validarDistintoDeNull(carta, "carta");
        List<CartaEspanola> coinciden = new ArrayList<>();
        for (CartaEspanola enLaMesa : this.cartas) {
            if (enLaMesa.tieneElMismoNumeroQue(carta)) {
                coinciden.add(enLaMesa);
            }
        }
        return coinciden;
    }

    /**
     * @return una copia de las cartas, en el orden en que se pusieron
     */
    public List<CartaEspanola> getCartas() {
        return new ArrayList<>(this.cartas);
    }

    public int getCantidad() {
        return this.cartas.size();
    }
}

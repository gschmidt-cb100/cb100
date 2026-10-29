package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Partida;

/**
 * Partida de casita robada (dato): una {@link Partida} <b>sin tablero</b>.
 * Hereda los turnos, el historial y el ganador; agrega lo propio del juego:
 * el mazo, la mesa, y una mano y una casita por jugador.
 *
 * <p>Como toda Partida, no conoce las reglas: no sabe cuándo se reparte ni qué
 * pasa al tirar una carta. Eso lo hace el AdministradorDePartidaDeCasitaRobada.
 *
 * Invariantes:
 *  - hay una mano y una casita por jugador, en el orden de turno.
 *  - entre el mazo, la mesa, las manos y las casitas están las 40 cartas, sin repetir.
 */
public class PartidaDeCasitaRobada extends Partida<Jugador> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Mazo mazo;
    private final Mesa mesa;
    private final List<Mano> manos;
    private final List<Casita> casitas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una partida con la mesa, las manos y las casitas vacías (el administrador reparte).
     * @param jugadores al menos 2 jugadores
     * @param mazo mazo con las 40 cartas, no null
     * @param cartasPorMano cuántas cartas entran en cada mano, mayor que cero
     */
    public PartidaDeCasitaRobada(List<Jugador> jugadores, Mazo mazo, int cartasPorMano) {
        super(jugadores);
        ValidacionesUtiles.validarDistintoDeNull(mazo, "mazo");
        this.mazo = mazo;
        this.mesa = new Mesa();
        this.manos = new ArrayList<>();
        this.casitas = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            this.manos.add(new Mano(cartasPorMano));
            this.casitas.add(new Casita());
        }
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @param jugador jugador de esta partida
     * @return su mano
     */
    public Mano getManoDe(Jugador jugador) {
        return this.manos.get(getOrdenDe(jugador));
    }

    /**
     * @param jugador jugador de esta partida
     * @return su casita
     */
    public Casita getCasitaDe(Jugador jugador) {
        return this.casitas.get(getOrdenDe(jugador));
    }

    /**
     * @return true si ningún jugador tiene cartas en la mano
     */
    public boolean todasLasManosEstanVacias() {
        for (Mano mano : this.manos) {
            if (!mano.estaVacia()) {
                return false;
            }
        }
        return true;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Mazo getMazo() {
        return mazo;
    }

    public Mesa getMesa() {
        return mesa;
    }
}

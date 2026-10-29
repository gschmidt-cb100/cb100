package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion;

import java.awt.Color;
import java.nio.file.Path;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.CartaEspanola;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Casita;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Palo;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.PartidaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.InterfazGrafica;

/**
 * {@link InterfazGrafica} de la casita robada. Hereda de InterfazGrafica
 * (ventana, teclado, apariencias), pero no de InterfazGraficaDeTablero: acá
 * cada celda no es un casillero de un tablero, sino un lugar de la
 * {@link DisposicionDeCasitaRobada} (una casita, el mazo, la mesa, la mano).
 *
 * <p>Apariencias: una por carta ("7-oros"), el dorso, el paño, el lugar vacío
 * y la casita vacía.
 */
public class InterfazGraficaDeCasitaRobada extends InterfazGrafica implements InterfazDeCasitaRobada {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final String PANO = "pano";
    private static final String HUECO = "hueco";
    private static final String DORSO = "dorso";
    private static final String CASITA_VACIA = "casita-vacia";

    private static final Color COLOR_DEL_PANO = new Color(39, 94, 56);
    private static final Color COLOR_DEL_DORSO = new Color(183, 28, 28);
    private static final Color COLOR_DE_CARTA = new Color(250, 245, 230);

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final DisposicionDeCasitaRobada disposicion;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea la ventana, todavía oculta, con todas las apariencias ya definidas.
     * @param disposicion dónde va cada cosa, no null
     * @param tamanioDeCelda lado de cada celda en píxeles
     * @param carpetaDeImagenes carpeta con las imágenes, o null para usar sólo colores
     */
    public InterfazGraficaDeCasitaRobada(DisposicionDeCasitaRobada disposicion, int tamanioDeCelda, Path carpetaDeImagenes) {
        super("Casita robada", DisposicionDeCasitaRobada.FILAS, DisposicionDeCasitaRobada.COLUMNAS, tamanioDeCelda, carpetaDeImagenes);
        ValidacionesUtiles.validarDistintoDeNull(disposicion, "disposicion");
        this.disposicion = disposicion;
        definirApariencia(PANO, PANO + ".png", COLOR_DEL_PANO);
        definirApariencia(HUECO, HUECO + ".png", COLOR_DEL_PANO.brighter());
        definirApariencia(DORSO, DORSO + ".png", COLOR_DEL_DORSO);
        definirApariencia(CASITA_VACIA, CASITA_VACIA + ".png", COLOR_DEL_PANO.darker());
        for (CartaEspanola carta : CartaEspanola.crearBaraja()) {
            definirApariencia(aparienciaDe(carta), aparienciaDe(carta) + ".png", colorDe(carta.getPalo()));
        }
    }

//METODOS DE InterfazDeCasitaRobada -----------------------------------------------------------------------

    @Override
    public void mostrar(PartidaDeCasitaRobada partida, Posicion cursor, CartaEspanola cartaElegida) {
        ValidacionesUtiles.validarDistintoDeNull(partida, "partida");
        ValidacionesUtiles.validarDistintoDeNull(cursor, "cursor");
        for (int fila = 1; fila <= DisposicionDeCasitaRobada.FILAS; fila++) {
            for (int columna = 1; columna <= DisposicionDeCasitaRobada.COLUMNAS; columna++) {
                Posicion celda = new Posicion(fila, columna);
                dibujar(celda, aparienciaDeLaCelda(partida, celda, cartaElegida), celda.equals(cursor));
            }
        }
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * Qué se ve en cada celda, según su zona.
     */
    private String aparienciaDeLaCelda(PartidaDeCasitaRobada partida, Posicion celda, CartaEspanola cartaElegida) {
        switch (this.disposicion.getZona(celda)) {
            case CASITA: {
                Jugador duenio = partida.getJugadores().get(this.disposicion.getIndice(celda));
                Casita casita = partida.getCasitaDe(duenio);
                return casita.estaVacia() ? CASITA_VACIA : aparienciaDe(casita.getCartaDeArriba());
            }
            case MAZO:
                return partida.getMazo().estaVacio() ? HUECO : DORSO;
            case MESA: {
                List<CartaEspanola> mesa = partida.getMesa().getCartas();
                int indice = this.disposicion.getIndice(celda);
                return indice < mesa.size() ? aparienciaDe(mesa.get(indice)) : PANO;
            }
            case MANO: {
                List<CartaEspanola> mano = partida.getManoDe(partida.getJugadorDeTurno()).getCartas();
                int indice = this.disposicion.getIndice(celda);
                if (indice >= mano.size() || mano.get(indice).equals(cartaElegida)) {
                    return HUECO;                       // la elegida se muestra aparte
                }
                return aparienciaDe(mano.get(indice));
            }
            case ELEGIDA:
                return cartaElegida == null ? PANO : aparienciaDe(cartaElegida);
            default:
                return PANO;
        }
    }

    /**
     * 7 de oros a "7-oros"
     */
    private static String aparienciaDe(CartaEspanola carta) {
        return carta.getNumero() + "-" + carta.getPalo().getNombre();
    }

    private static Color colorDe(Palo palo) {
        switch (palo) {
            case ORO:
                return new Color(255, 236, 179);
            case COPA:
                return new Color(255, 205, 210);
            case ESPADA:
                return new Color(187, 222, 251);
            default:
                return COLOR_DE_CARTA;
        }
    }
}

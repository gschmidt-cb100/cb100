package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi;

import java.awt.Color;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadorConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.ControladorDeJuegoDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.InterfazGraficaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Recursos;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Sonido;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.SonidoDeLaCatedra;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.datos.FichaDeReversi;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.funcional.AdministradorDePartidaDeReversi;

/**
 * Arma el reversi y lo juega. Es casi igual a PrincipalDeTateti: cambian el
 * administrador, las fichas y las imágenes. Todo lo demás es commons.
 *
 * Teclas: flechas (o WASD) mueven, Enter/Espacio juega, Z nueva partida, Esc sale.
 * Las celdas con un punto son las jugadas válidas del jugador de turno.
 */
public class PrincipalDeReversi {

    private static final String CARPETA_DE_IMAGENES = "ar/uba/fi/cb100/clases/a2026/c02/s11/juego/reversi/imagenes";
    private static final int TAMANIO_DE_CELDA = 72;

    public static void main(String[] args) {
        List<JugadorConFicha<FichaDeReversi>> jugadores = new ArrayList<>();
        jugadores.add(new JugadorConFicha<>("Ana", FichaDeReversi.NEGRA));     // por regla, empiezan las negras
        jugadores.add(new JugadorConFicha<>("Beto", FichaDeReversi.BLANCA));

        AdministradorDePartidaDeReversi administrador = new AdministradorDePartidaDeReversi(jugadores);
        Path imagenes = Recursos.buscarCarpeta(CARPETA_DE_IMAGENES, "vacio.png");

        try (InterfazGraficaConFicha<FichaDeReversi> interfaz = new InterfazGraficaConFicha<>("Reversi",
                    administrador.getPartida().getTablero().getCantidadDeFilas(),
                    administrador.getPartida().getTablero().getCantidadDeColumnas(),
                    TAMANIO_DE_CELDA, imagenes);
             Sonido sonido = SonidoDeLaCatedra.crear()) {

            interfaz.definirVacio("vacio.png", new Color(46, 125, 50));
            interfaz.definirMarcada("marcada.png", new Color(102, 187, 106));
            interfaz.definirFicha(FichaDeReversi.NEGRA, "negra.png", new Color(33, 33, 33));
            interfaz.definirFicha(FichaDeReversi.BLANCA, "blanca.png", new Color(250, 250, 250));

            new ControladorDeJuegoDeTablero<>(administrador, interfaz, sonido).jugar();
        }
    }
}

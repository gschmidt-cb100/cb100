package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.ControladorDeJuegoDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Recursos;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Sonido;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.SonidoDeLaCatedra;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos.Carta;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.funcional.AdministradorDePartidaDeMemotest;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.presentacion.InterfazGraficaDeMemotest;

/**
 * Arma el memotest y lo juega. Comparalo con PrincipalDeTateti: los jugadores
 * son Jugador (sin ficha), y la interfaz es la del memotest. El controlador,
 * el sonido y el ciclo del juego son exactamente los mismos.
 *
 * Teclas: flechas (o WASD) mueven, Enter/Espacio da vuelta una carta, Z nueva partida, Esc sale.
 */
public class PrincipalDeMemotest {

    private static final String CARPETA_DE_IMAGENES = "ar/uba/fi/cb100/clases/a2026/c02/s11/juego/memotest/imagenes";
    private static final int TAMANIO_DE_CELDA = 130;

    public static void main(String[] args) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Ana"));
        jugadores.add(new Jugador("Beto"));

        AdministradorDePartidaDeMemotest administrador = new AdministradorDePartidaDeMemotest(jugadores);
        Path imagenes = Recursos.buscarCarpeta(CARPETA_DE_IMAGENES, "dorso.png");

        try (InterfazGraficaDeMemotest interfaz = new InterfazGraficaDeMemotest(
                    administrador.getPartida().getTablero().getCantidadDeFilas(),
                    administrador.getPartida().getTablero().getCantidadDeColumnas(),
                    TAMANIO_DE_CELDA, imagenes);
             Sonido sonido = SonidoDeLaCatedra.crear()) {

            new ControladorDeJuegoDeTablero<Carta>(administrador, interfaz, sonido).jugar();
        }
    }
}

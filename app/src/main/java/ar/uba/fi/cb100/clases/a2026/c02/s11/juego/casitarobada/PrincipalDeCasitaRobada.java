package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.funcional.AdministradorDePartidaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion.ControladorDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion.DisposicionDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion.InterfazGraficaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Recursos;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Sonido;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.SonidoDeLaCatedra;

/**
 * Arma la casita robada y la juega. Se juega en la misma computadora, por
 * turnos: se ve la mano del jugador al que le toca.
 *
 * Teclas: flechas (o WASD) mueven, Enter elige una carta de la mano y después
 * la mesa o la casita de otro jugador, Z nueva partida, Esc sale.
 */
public class PrincipalDeCasitaRobada {

    private static final String CARPETA_DE_IMAGENES = "ar/uba/fi/cb100/clases/a2026/c02/s11/juego/casitarobada/imagenes";
    private static final int TAMANIO_DE_CELDA = 104;

    public static void main(String[] args) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Ana"));
        jugadores.add(new Jugador("Beto"));

        AdministradorDePartidaDeCasitaRobada administrador = new AdministradorDePartidaDeCasitaRobada(jugadores);
        DisposicionDeCasitaRobada disposicion = new DisposicionDeCasitaRobada(jugadores.size());
        Path imagenes = Recursos.buscarCarpeta(CARPETA_DE_IMAGENES, "dorso.png");

        try (InterfazGraficaDeCasitaRobada interfaz = new InterfazGraficaDeCasitaRobada(disposicion, TAMANIO_DE_CELDA, imagenes);
             Sonido sonido = SonidoDeLaCatedra.crear()) {

            new ControladorDeCasitaRobada(administrador, interfaz, disposicion, sonido).jugar();
        }
    }
}

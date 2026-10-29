package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti;

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
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.datos.FichaDeTateti;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.funcional.AdministradorDePartidaDeTateti;

/**
 * Arma el tateti y lo juega. Fijate lo poco que hay acá: crear los jugadores,
 * el administrador (las reglas), la interfaz con las imágenes de cada ficha y
 * el sonido. El ciclo del juego es el de commons.
 *
 * Teclas: flechas (o WASD) mueven, Enter/Espacio juega, Z nueva partida, Esc sale.
 */
public class PrincipalDeTateti {

    private static final String CARPETA_DE_IMAGENES = "ar/uba/fi/cb100/clases/a2026/c02/s11/juego/tateti/imagenes";
    private static final int TAMANIO_DE_CELDA = 140;

    public static void main(String[] args) {
        List<JugadorConFicha<FichaDeTateti>> jugadores = new ArrayList<>();
        jugadores.add(new JugadorConFicha<>("Ana", FichaDeTateti.X));
        jugadores.add(new JugadorConFicha<>("Beto", FichaDeTateti.O));

        AdministradorDePartidaDeTateti administrador = new AdministradorDePartidaDeTateti(jugadores);
        Path imagenes = Recursos.buscarCarpeta(CARPETA_DE_IMAGENES, "vacio.png");

        try (InterfazGraficaConFicha<FichaDeTateti> interfaz = new InterfazGraficaConFicha<>("Tateti",
                    administrador.getPartida().getTablero().getCantidadDeFilas(),
                    administrador.getPartida().getTablero().getCantidadDeColumnas(),
                    TAMANIO_DE_CELDA, imagenes);
             Sonido sonido = SonidoDeLaCatedra.crear()) {

            interfaz.definirVacio("vacio.png", new Color(245, 240, 225));
            interfaz.definirFicha(FichaDeTateti.X, "x.png", new Color(211, 47, 47));
            interfaz.definirFicha(FichaDeTateti.O, "o.png", new Color(25, 118, 210));

            new ControladorDeJuegoDeTablero<>(administrador, interfaz, sonido).jugar();
        }
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.awt.Color;
import java.nio.file.Path;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Ficha;

/**
 * {@link InterfazGraficaDeTablero} para juegos con fichas: cada ficha tiene una sola
 * apariencia, que se identifica con su nombre. El juego sólo dice qué imagen
 * corresponde a cada ficha con {@link #definirFicha}.
 *
 * <p>La usan, sin cambios, el tateti y el reversi.
 *
 * @param <F> tipo de ficha del juego
 */
public class InterfazGraficaConFicha<F extends Ficha> extends InterfazGraficaDeTablero<F> {

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea la ventana, todavía oculta.
     * @param titulo título de la ventana
     * @param cantidadDeFilas filas del tablero, mayor que cero
     * @param cantidadDeColumnas columnas del tablero, mayor que cero
     * @param tamanioDeCelda lado de cada celda en píxeles
     * @param carpetaDeImagenes carpeta con las imágenes, o null para usar sólo colores
     */
    public InterfazGraficaConFicha(String titulo, int cantidadDeFilas, int cantidadDeColumnas, int tamanioDeCelda,
                                   Path carpetaDeImagenes) {
        super(titulo, cantidadDeFilas, cantidadDeColumnas, tamanioDeCelda, carpetaDeImagenes);
    }

//METODOS DE CONFIGURACION --------------------------------------------------------------------------------

    /**
     * @param ficha ficha del juego, no null
     * @param imagen nombre de la imagen de la ficha (por ejemplo "x.png")
     * @param colorDeRespaldo color si no está la imagen
     * @throws IllegalArgumentException si la ficha es null o ya estaba definida
     */
    public void definirFicha(F ficha, String imagen, Color colorDeRespaldo) {
        ValidacionesUtiles.validarDistintoDeNull(ficha, "ficha");
        definirApariencia(ficha.getNombre(), imagen, colorDeRespaldo);
    }

//METODOS DE InterfazGraficaDeTablero ------------------------------------------------------------------------------

    @Override
    protected String getAparienciaDe(F ficha) {
        return ficha.getNombre();
    }
}

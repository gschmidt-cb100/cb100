package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.awt.Color;
import java.nio.file.Path;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Tablero;

/**
 * {@link InterfazGrafica} de un juego de tablero: cada celda de la ventana es
 * una celda del tablero.
 *
 * <p>Una celda vacía se dibuja con la apariencia "vacía" (o "marcada", si es
 * una jugada válida). Qué apariencia le toca a una celda con contenido lo
 * decide cada subclase con {@link #getAparienciaDe} (método abstracto):
 * <ul>
 *   <li>{@link InterfazGraficaConFicha}: la apariencia es la ficha.</li>
 *   <li>InterfazGraficaDeMemotest: depende de la carta y de si está boca arriba.</li>
 * </ul>
 *
 * @param <T> lo que hay en cada celda del tablero
 */
public abstract class InterfazGraficaDeTablero<T> extends InterfazGrafica implements InterfazDeTablero<T> {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final String VACIO = "_vacio";
    private static final String MARCADA = "_marcada";

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea la ventana, todavía oculta, del tamaño del tablero.
     * @param titulo título de la ventana
     * @param cantidadDeFilas filas del tablero
     * @param cantidadDeColumnas columnas del tablero
     * @param tamanioDeCelda lado de cada celda en píxeles
     * @param carpetaDeImagenes carpeta con las imágenes, o null para usar sólo colores
     */
    protected InterfazGraficaDeTablero(String titulo, int cantidadDeFilas, int cantidadDeColumnas, int tamanioDeCelda,
                                       Path carpetaDeImagenes) {
        super(titulo, cantidadDeFilas, cantidadDeColumnas, tamanioDeCelda, carpetaDeImagenes);
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------

    /**
     * @param contenido lo que hay en una celda, no null
     * @return el nombre de la apariencia con que se dibuja (tiene que estar definida)
     */
    protected abstract String getAparienciaDe(T contenido);

//METODOS DE CONFIGURACION --------------------------------------------------------------------------------

    /**
     * @param imagen nombre de la imagen de la celda vacía (por ejemplo "vacio.png")
     * @param colorDeRespaldo color si no está la imagen
     */
    public void definirVacio(String imagen, Color colorDeRespaldo) {
        definirApariencia(VACIO, imagen, colorDeRespaldo);
    }

    /**
     * @param imagen nombre de la imagen de una celda vacía marcada (jugada válida)
     * @param colorDeRespaldo color si no está la imagen
     */
    public void definirMarcada(String imagen, Color colorDeRespaldo) {
        definirApariencia(MARCADA, imagen, colorDeRespaldo);
    }

//METODOS DE InterfazDeTablero ----------------------------------------------------------------------------

    @Override
    public void mostrar(PartidaDeTablero<T, ?> partida, Posicion cursor, List<Posicion> posicionesMarcadas) {
        ValidacionesUtiles.validarDistintoDeNull(partida, "partida");
        ValidacionesUtiles.validarDistintoDeNull(cursor, "cursor");
        ValidacionesUtiles.validarDistintoDeNull(posicionesMarcadas, "posicionesMarcadas");
        Tablero<T> tablero = partida.getTablero();
        for (Posicion posicion : tablero.getPosiciones()) {
            dibujar(posicion, aparienciaDeLaCelda(tablero.obtener(posicion), posicionesMarcadas.contains(posicion)),
                    posicion.equals(cursor));
        }
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private String aparienciaDeLaCelda(T contenido, boolean estaMarcada) {
        if (contenido != null) {
            return getAparienciaDe(contenido);
        }
        return estaMarcada ? MARCADA : VACIO;
    }
}

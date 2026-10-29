package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.presentacion;

import java.awt.Color;
import java.nio.file.Path;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.InterfazGraficaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos.Carta;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos.FiguraDeCarta;

/**
 * {@link InterfazGraficaDeTablero} del memotest. Lo único que agrega es <b>cómo se ve
 * una carta</b>, que depende de su estado:
 * <ul>
 *   <li>boca abajo: el dorso ("dorso.png"),</li>
 *   <li>boca arriba: su figura ("sol.png"),</li>
 *   <li>encontrada: su figura atenuada ("sol-encontrada.png").</li>
 * </ul>
 * Todo lo demás (ventana, cursor, teclado) viene de commons.
 */
public class InterfazGraficaDeMemotest extends InterfazGraficaDeTablero<Carta> {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final String DORSO = "dorso";
    private static final String SUFIJO_ENCONTRADA = "-encontrada";
    private static final Color COLOR_DEL_DORSO = new Color(92, 107, 192);
    private static final Color COLOR_ENCONTRADA = new Color(200, 230, 201);
    /** Colores de respaldo, uno por figura en el orden del enum, por si faltan las imágenes. */
    private static final Color[] COLORES_DE_FIGURAS = {
            new Color(249, 168, 37), new Color(144, 164, 174), new Color(255, 213, 79), new Color(229, 57, 53),
            new Color(30, 136, 229), new Color(67, 160, 71), new Color(142, 36, 170), new Color(244, 81, 30),
    };

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea la ventana, todavía oculta, con todas las apariencias ya definidas.
     * @param cantidadDeFilas filas del tablero
     * @param cantidadDeColumnas columnas del tablero
     * @param tamanioDeCelda lado de cada celda en píxeles
     * @param carpetaDeImagenes carpeta con las imágenes, o null para usar sólo colores
     */
    public InterfazGraficaDeMemotest(int cantidadDeFilas, int cantidadDeColumnas, int tamanioDeCelda, Path carpetaDeImagenes) {
        super("Memotest", cantidadDeFilas, cantidadDeColumnas, tamanioDeCelda, carpetaDeImagenes);
        definirApariencia(DORSO, DORSO + ".png", COLOR_DEL_DORSO);
        for (FiguraDeCarta figura : FiguraDeCarta.values()) {
            String nombre = nombreDe(figura);
            Color color = COLORES_DE_FIGURAS[figura.ordinal() % COLORES_DE_FIGURAS.length];
            definirApariencia(nombre, nombre + ".png", color);
            definirApariencia(nombre + SUFIJO_ENCONTRADA, nombre + SUFIJO_ENCONTRADA + ".png", COLOR_ENCONTRADA);
        }
    }

//METODOS DE InterfazGraficaDeTablero ------------------------------------------------------------------------------

    @Override
    protected String getAparienciaDe(Carta carta) {
        if (carta.estaBocaAbajo()) {
            return DORSO;
        }
        if (carta.estaEncontrada()) {
            return nombreDe(carta.getFigura()) + SUFIJO_ENCONTRADA;
        }
        return nombreDe(carta.getFigura());
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * SOL a "sol", CORAZON a "corazon"
     */
    private static String nombreDe(FiguraDeCarta figura) {
        return figura.name().toLowerCase();
    }
}

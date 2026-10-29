package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;

/**
 * TDA DisposicionDeCasitaRobada: <b>dónde va cada cosa</b> en la pantalla.
 * La casita robada no tiene tablero, pero la librería dibuja una grilla: esta
 * clase es la traducción entre las dos, y es la única que la conoce.
 * La usan la interfaz (para dibujar) y el controlador (para saber qué eligió el usuario).
 *
 * <pre>
 *        1    2    3    4    5    6    7    8
 *   1  [C1] [C2] [C3] [C4]                [Mazo]     casitas de cada jugador, y el mazo
 *   2   ....................................         (paño)
 *   3  [M1] [M2] [M3] [M4] [M5] [M6] [M7] [M8]       la mesa
 *   4  [M9] ...                          [M16]
 *   5   ....................................         (paño)
 *   6  [m1] [m2] [m3]      [Elegida]                 la mano del jugador de turno
 * </pre>
 * Todas las posiciones van de 1 a n; los índices dentro de cada zona, de 0 a n-1.
 */
public class DisposicionDeCasitaRobada {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    public static final int FILAS = 6;
    public static final int COLUMNAS = 8;
    public static final int MAXIMO_DE_JUGADORES = 4;
    public static final int LUGARES_EN_LA_MESA = 16;
    public static final int LUGARES_EN_LA_MANO = 3;

    private static final int FILA_DE_CASITAS = 1;
    private static final int PRIMERA_FILA_DE_MESA = 3;
    private static final int FILA_DE_MANO = 6;
    private static final Posicion MAZO = new Posicion(FILA_DE_CASITAS, COLUMNAS);
    private static final Posicion ELEGIDA = new Posicion(FILA_DE_MANO, 5);

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int cantidadDeJugadores;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param cantidadDeJugadores entre 2 y {@link #MAXIMO_DE_JUGADORES}
     */
    public DisposicionDeCasitaRobada(int cantidadDeJugadores) {
        ValidacionesUtiles.validarRango(cantidadDeJugadores, 2, MAXIMO_DE_JUGADORES, "cantidadDeJugadores");
        this.cantidadDeJugadores = cantidadDeJugadores;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @param celda posición de la grilla
     * @return qué zona es
     */
    public Zona getZona(Posicion celda) {
        int fila = celda.getFila();
        int columna = celda.getColumna();
        if (celda.equals(MAZO)) {
            return Zona.MAZO;
        }
        if (celda.equals(ELEGIDA)) {
            return Zona.ELEGIDA;
        }
        if (fila == FILA_DE_CASITAS && columna <= this.cantidadDeJugadores) {
            return Zona.CASITA;
        }
        if (fila >= PRIMERA_FILA_DE_MESA && fila < PRIMERA_FILA_DE_MESA + LUGARES_EN_LA_MESA / COLUMNAS) {
            return Zona.MESA;
        }
        if (fila == FILA_DE_MANO && columna <= LUGARES_EN_LA_MANO) {
            return Zona.MANO;
        }
        return Zona.NINGUNA;
    }

    /**
     * @param celda posición de una casita, de la mesa o de la mano
     * @return el índice dentro de su zona (número de jugador, lugar en la mesa o en la mano), desde 0
     */
    public int getIndice(Posicion celda) {
        Zona zona = getZona(celda);
        if (zona == Zona.MESA) {
            return (celda.getFila() - PRIMERA_FILA_DE_MESA) * COLUMNAS + (celda.getColumna() - 1);
        }
        ValidacionesUtiles.validarFalso(zona != Zona.CASITA && zona != Zona.MANO, celda + " no tiene índice: es " + zona + ".");
        return celda.getColumna() - 1;
    }

    /**
     * @param indice número de jugador, desde 0
     * @return la celda de su casita
     */
    public Posicion getPosicionDeCasita(int indice) {
        ValidacionesUtiles.validarRango(indice, 0, this.cantidadDeJugadores - 1, "indice");
        return new Posicion(FILA_DE_CASITAS, indice + 1);
    }

    /**
     * @param indice lugar en la mesa, desde 0
     * @return su celda
     */
    public Posicion getPosicionDeMesa(int indice) {
        ValidacionesUtiles.validarRango(indice, 0, LUGARES_EN_LA_MESA - 1, "indice");
        return new Posicion(PRIMERA_FILA_DE_MESA + indice / COLUMNAS, indice % COLUMNAS + 1);
    }

    /**
     * @param indice lugar en la mano, desde 0
     * @return su celda
     */
    public Posicion getPosicionDeMano(int indice) {
        ValidacionesUtiles.validarRango(indice, 0, LUGARES_EN_LA_MANO - 1, "indice");
        return new Posicion(FILA_DE_MANO, indice + 1);
    }

    public Posicion getPosicionDelMazo() {
        return MAZO;
    }

    public Posicion getPosicionDeLaElegida() {
        return ELEGIDA;
    }

    public int getCantidadDeJugadores() {
        return cantidadDeJugadores;
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * El juego del tateti sobre un tablero de {@code filas × columnas}.
 *
 * <p>Reglas:
 * <ul>
 *   <li>Empieza siempre {@link Ficha#X} y las fichas se alternan.</li>
 *   <li>Gana quien completa <b>una línea entera</b>: una fila, una columna,
 *       o una de las dos diagonales. En un tablero no cuadrado, las
 *       diagonales tienen el largo del lado menor.</li>
 *   <li>Si el tablero se llena sin ganador, es empate.</li>
 *   <li>Terminado el juego, no se puede seguir colocando.</li>
 * </ul>
 *
 * <p>Filas y columnas van <b>de 1 a n</b>. Toda situación inválida (posición
 * fuera del tablero u ocupada, ficha fuera de turno, juego terminado) lanza
 * {@link IllegalArgumentException} con un mensaje que explica el motivo.
 */
public class Tateti {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final TableroMatriz<Ficha> tablero;
    private Ficha ultimaFicha = null;                       // null hasta la primera jugada
    private EstadoDeTateti estado = EstadoDeTateti.JUGANDO;
    private int cantidadDeJugadas = 0;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Constructor de la clase Tateti
     * @param filas: cantidad de filas del tablero. Debe ser mayor a 0.
     * @param columnas: cantidad de columnas del tablero. Debe ser mayor a 0.
     */
    public Tateti(int filas, int columnas) {
        ValidacionesUtiles.esMayorQueCero(filas, "filas");
        ValidacionesUtiles.esMayorQueCero(columnas, "columnas");
        this.tablero = new TableroMatriz<>(filas, columnas);
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    /**
     * Dibuja el tablero con sus índices, y debajo el estado del juego.
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("   ");
        for (int c = 1; c <= getCantidadDeColumnas(); c++) {
            texto.append(c).append(' ');
        }
        texto.append('\n');
        for (int f = 1; f <= getCantidadDeFilas(); f++) {
            texto.append(String.format("%2d ", f));
            for (int c = 1; c <= getCantidadDeColumnas(); c++) {
                Ficha ficha = tablero.obtener(f, c);
                texto.append(ficha == null ? '.' : ficha.name().charAt(0)).append(' ');
            }
            texto.append('\n');
        }
        texto.append("Estado: ").append(estado);
        if (!estaFinalizado()) {
            texto.append(" | le toca a ").append(getProximaFicha());
        }
        return texto.toString();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Coloca la ficha del jugador al que le toca en la posición indicada.
     * @param fila: fila en la que se colocará la ficha. De 1 a n.
     * @param columna: columna en la que se colocará la ficha. De 1 a n.
     * @return el estado del juego después de la jugada
     * @throws IllegalArgumentException si el juego terminó, o si la posición
     *         está fuera del tablero u ocupada
     */
    public EstadoDeTateti colocar(int fila, int columna) {
        ValidacionesUtiles.validarFalso(estaFinalizado(), "El tateti ya terminó: " + estado + ".");
        return colocar(fila, columna, getProximaFicha());
    }

    /**
     * Coloca una ficha en la posición indicada, verificando que sea la que
     * corresponde por turno.
     * @param fila: fila en la que se colocará la ficha. De 1 a n.
     * @param columna: columna en la que se colocará la ficha. De 1 a n.
     * @param ficha: la ficha a colocar. Tiene que ser la del turno actual.
     * @return el estado del juego después de la jugada
     * @throws IllegalArgumentException si el juego terminó, si la ficha es nula
     *         o no es la del turno, o si la posición está fuera del tablero u ocupada
     */
    public EstadoDeTateti colocar(int fila, int columna, Ficha ficha) {
        ValidacionesUtiles.validarFalso(estaFinalizado(), "El tateti ya terminó: " + estado + ".");
        ValidacionesUtiles.validarFalso(ficha == null, "La ficha no puede ser nula.");
        ValidacionesUtiles.validarFalso(ficha != getProximaFicha(),
                "Le toca a " + getProximaFicha() + ", no a " + ficha + ".");

        tablero.colocar(fila, columna, ficha);   // valida rango y que esté libre

        ultimaFicha = ficha;
        cantidadDeJugadas++;
        actualizarEstado();
        return estado;
    }

    /**
     * Busca una línea completa en el tablero.
     * @return la ficha que ganó, o null si todavía no hay ganador.
     */
    public Ficha obtenerGanador() {
        int filas = getCantidadDeFilas();
        int columnas = getCantidadDeColumnas();
        int largoDeLaDiagonal = Math.min(filas, columnas);

        for (int f = 1; f <= filas; f++) {                         // cada fila, de izquierda a derecha
            Ficha duenio = duenioDeLaLinea(f, 1, 0, 1, columnas);
            if (duenio != null) {
                return duenio;
            }
        }
        for (int c = 1; c <= columnas; c++) {                      // cada columna, de arriba a abajo
            Ficha duenio = duenioDeLaLinea(1, c, 1, 0, filas);
            if (duenio != null) {
                return duenio;
            }
        }
        Ficha duenio = duenioDeLaLinea(1, 1, 1, 1, largoDeLaDiagonal);         // diagonal principal
        if (duenio != null) {
            return duenio;
        }
        return duenioDeLaLinea(1, columnas, 1, -1, largoDeLaDiagonal);        // diagonal secundaria
    }

    /**
     * Recorre una línea desde (fila, columna) avanzando de a (pasoFila,
     * pasoColumna), y devuelve la ficha que la ocupa entera, o null si la
     * línea está incompleta o mezclada.
     *
     * <p>Las cuatro verificaciones (filas, columnas y las dos diagonales)
     * son el mismo recorrido con distinto punto de partida y distinto paso.
     * Escribirlo una vez acá evita cuatro copias del mismo ciclo.
     */
    private Ficha duenioDeLaLinea(int fila, int columna, int pasoFila, int pasoColumna, int largo) {
        Ficha primera = tablero.obtener(fila, columna);
        if (primera == null) {
            return null;
        }
        for (int k = 1; k < largo; k++) {
            Ficha siguiente = tablero.obtener(fila + k * pasoFila, columna + k * pasoColumna);
            if (siguiente != primera) {     // las fichas son un enum: == compara identidad, y alcanza
                return null;
            }
        }
        return primera;
    }

    /** Se llama después de cada jugada: decide si alguien ganó o si se empató. */
    private void actualizarEstado() {
        Ficha ganador = obtenerGanador();
        if (ganador == Ficha.X) {
            estado = EstadoDeTateti.GANO_X;
        } else if (ganador == Ficha.O) {
            estado = EstadoDeTateti.GANO_O;
        } else if (tablero.estaLleno()) {
            estado = EstadoDeTateti.EMPATE;
        }
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * La ficha en la posición indicada.
     * @return la ficha, o null si la posición está vacía
     * @throws IllegalArgumentException si la posición está fuera del tablero
     */
    public Ficha obtener(int fila, int columna) {
        return tablero.obtener(fila, columna);
    }

    /** @return true si la posición está vacía. */
    public boolean estaVacio(int fila, int columna) {
        return tablero.estaVacio(fila, columna);
    }

    /** @return true si alguien ganó. */
    public boolean hayGanador() {
        return estado.esGanadorX() || estado.esGanadorO();
    }

    /** @return true si se llenó el tablero sin ganador. */
    public boolean hayEmpate() {
        return estado == EstadoDeTateti.EMPATE;
    }

    /** @return true si el juego terminó, por ganador o por empate. */
    public boolean estaFinalizado() {
        return estado.esFinalizado();
    }

    /**
     * La ficha a la que le toca jugar: X al principio, y después la contraria
     * a la última que jugó.
     * @return la ficha del turno, o null si el juego ya terminó
     */
    public Ficha getProximaFicha() {
        if (estaFinalizado()) {
            return null;
        }
        return (ultimaFicha == null) ? Ficha.X : ultimaFicha.alternar();
    }

    /** @return la última ficha colocada, o null si todavía nadie jugó. */
    public Ficha getUltimaFicha() {
        return ultimaFicha;
    }

    public EstadoDeTateti getEstado() {
        return estado;
    }

    public int getCantidadDeFilas() {
        return tablero.getCantidadDeFilas();
    }

    public int getCantidadDeColumnas() {
        return tablero.getCantidadDeColumnas();
    }

    public int getCantidadDeJugadas() {
        return cantidadDeJugadas;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

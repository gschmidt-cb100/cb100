package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e02;

/**
 * TDA Tablero de ta-te-ti (3x3).
 *
 * <p>Decisión de representación: elegimos una MATRIZ {@code Ficha[3][3]}
 * porque el dominio (fila, columna) mapea directo a los dos índices y las
 * verificaciones de filas, columnas y diagonales se leen tal cual se piensan.
 * La alternativa (arreglo plano de 9 con {@code fila * 3 + columna}) ahorra
 * un nivel de indirección pero obliga a hacer la aritmética de índices a mano;
 * para este TDA priorizamos claridad.</p>
 *
 * <p>Reglas que el tablero garantiza: arranca X, los turnos se alternan,
 * no se puede poner en una casilla ocupada ni jugar terminada la partida.</p>
 */
public class Tablero {

    private static final int DIMENSION = 3;

    private final Ficha[][] casillas;
    private Ficha proximoTurno;

    /**
     * pre: -.<br>
     * post: crea un tablero de 3x3 vacío donde el próximo turno es de X.
     */
    public Tablero() {
        this.casillas = new Ficha[DIMENSION][DIMENSION];
        this.proximoTurno = Ficha.X;
    }

    /**
     * Pone una ficha en la casilla indicada.
     *
     * <p>pre: {@code ficha} no es null y es la del turno actual (arranca X y
     * se alterna); {@code fila} y {@code columna} están en [0, 3); la casilla
     * está libre; la partida no terminó.<br>
     * post: la casilla queda ocupada por {@code ficha} y el turno pasa a la
     * otra ficha.</p>
     *
     * @param ficha la ficha a poner.
     * @param fila fila de la casilla (base 0).
     * @param columna columna de la casilla (base 0).
     * @throws IllegalArgumentException si la ficha es null o la casilla está fuera de rango.
     * @throws IllegalStateException si no es el turno de esa ficha, la casilla
     *         está ocupada o la partida ya terminó.
     */
    public void poner(Ficha ficha, int fila, int columna) {
        if (ficha == null) {
            throw new IllegalArgumentException("La ficha no puede ser null");
        }
        validarCasilla(fila, columna);
        if (hayGanador() || hayEmpate()) {
            throw new IllegalStateException("La partida ya terminó: no se pueden poner más fichas");
        }
        if (ficha != proximoTurno) {
            throw new IllegalStateException(
                    "No es el turno de " + ficha + ": le toca a " + proximoTurno);
        }
        if (casillas[fila][columna] != null) {
            throw new IllegalStateException(
                    "La casilla (" + fila + ", " + columna + ") ya está ocupada por "
                            + casillas[fila][columna]);
        }
        casillas[fila][columna] = ficha;
        proximoTurno = (proximoTurno == Ficha.X) ? Ficha.O : Ficha.X;
    }

    /**
     * pre: {@code fila} y {@code columna} están en [0, 3).<br>
     * post: devuelve la ficha de esa casilla, o null si está vacía
     * ("sin valor" se representa con null, nunca con un valor centinela).
     *
     * @param fila fila de la casilla (base 0).
     * @param columna columna de la casilla (base 0).
     * @return la ficha en esa casilla, o null si está libre.
     * @throws IllegalArgumentException si la casilla está fuera de rango.
     */
    public Ficha fichaEn(int fila, int columna) {
        validarCasilla(fila, columna);
        return casillas[fila][columna];
    }

    /**
     * pre: -.<br>
     * post: devuelve true si alguna ficha completó una fila, una columna o
     * una diagonal; el tablero no se modifica (es calculable: MÉTODO, no atributo).
     *
     * @return true si hay tres en línea.
     */
    public boolean hayGanador() {
        for (int i = 0; i < DIMENSION; i++) {
            if (lineaCompleta(casillas[i][0], casillas[i][1], casillas[i][2])) {
                return true;
            }
            if (lineaCompleta(casillas[0][i], casillas[1][i], casillas[2][i])) {
                return true;
            }
        }
        return lineaCompleta(casillas[0][0], casillas[1][1], casillas[2][2])
                || lineaCompleta(casillas[0][2], casillas[1][1], casillas[2][0]);
    }

    /**
     * pre: -.<br>
     * post: devuelve true si el tablero está completo y nadie ganó;
     * el tablero no se modifica.
     *
     * @return true si la partida terminó empatada.
     */
    public boolean hayEmpate() {
        if (hayGanador()) {
            return false;
        }
        for (int fila = 0; fila < DIMENSION; fila++) {
            for (int columna = 0; columna < DIMENSION; columna++) {
                if (casillas[fila][columna] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * pre: -.<br>
     * post: devuelve el contenido completo del tablero, casilla por casilla,
     * con '.' para las libres.
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        for (int fila = 0; fila < DIMENSION; fila++) {
            for (int columna = 0; columna < DIMENSION; columna++) {
                Ficha ficha = casillas[fila][columna];
                texto.append(ficha == null ? "." : ficha.toString());
                if (columna < DIMENSION - 1) {
                    texto.append(' ');
                }
            }
            texto.append('\n');
        }
        return texto.toString();
    }

    private void validarCasilla(int fila, int columna) {
        if (fila < 0 || fila >= DIMENSION || columna < 0 || columna >= DIMENSION) {
            throw new IllegalArgumentException(
                    "Casilla fuera de rango: (" + fila + ", " + columna + "); debe estar en [0, 3)");
        }
    }

    private boolean lineaCompleta(Ficha primera, Ficha segunda, Ficha tercera) {
        return primera != null && primera == segunda && segunda == tercera;
    }

    /**
     * Demostración breve: una partida donde gana X por la primera columna.
     */
    public static void main(String[] args) {
        Tablero tablero = new Tablero();
        tablero.poner(Ficha.X, 0, 0);
        tablero.poner(Ficha.O, 0, 1);
        tablero.poner(Ficha.X, 1, 0);
        tablero.poner(Ficha.O, 1, 1);
        tablero.poner(Ficha.X, 2, 0);
        System.out.println(tablero);
        System.out.println("hayGanador(): " + tablero.hayGanador());
        System.out.println("hayEmpate(): " + tablero.hayEmpate());
        System.out.println("fichaEn(2, 2): " + tablero.fichaEn(2, 2) + " (null = vacía)");
    }
}

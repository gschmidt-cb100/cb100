package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

/**
 * Implementación de {@link Tablero} sobre un ARREGLO PLANO {@code Ficha[9]}:
 * la casilla (fila, columna) vive en el índice {@code fila * 3 + columna}.
 * Misma interfaz, otra representación: el cliente no nota la diferencia.
 */
public class TableroPlano implements Tablero {

    private static final int DIMENSION = 3;

    private final Ficha[] casillas;
    private Ficha proximoTurno;

    /**
     * pre: -.<br>
     * post: crea un tablero de 3x3 vacío (9 casillas planas) donde el próximo
     * turno es de X.
     */
    public TableroPlano() {
        this.casillas = new Ficha[DIMENSION * DIMENSION];
        this.proximoTurno = Ficha.X;
    }

    @Override
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
        int indice = indiceDe(fila, columna);
        if (casillas[indice] != null) {
            throw new IllegalStateException(
                    "La casilla (" + fila + ", " + columna + ") ya está ocupada");
        }
        casillas[indice] = ficha;
        proximoTurno = (proximoTurno == Ficha.X) ? Ficha.O : Ficha.X;
    }

    @Override
    public Ficha fichaEn(int fila, int columna) {
        validarCasilla(fila, columna);
        return casillas[indiceDe(fila, columna)];
    }

    @Override
    public boolean hayGanador() {
        for (int i = 0; i < DIMENSION; i++) {
            if (lineaCompleta(indiceDe(i, 0), indiceDe(i, 1), indiceDe(i, 2))) {
                return true;
            }
            if (lineaCompleta(indiceDe(0, i), indiceDe(1, i), indiceDe(2, i))) {
                return true;
            }
        }
        return lineaCompleta(indiceDe(0, 0), indiceDe(1, 1), indiceDe(2, 2))
                || lineaCompleta(indiceDe(0, 2), indiceDe(1, 1), indiceDe(2, 0));
    }

    @Override
    public boolean hayEmpate() {
        if (hayGanador()) {
            return false;
        }
        for (Ficha casilla : casillas) {
            if (casilla == null) {
                return false;
            }
        }
        return true;
    }

    private int indiceDe(int fila, int columna) {
        return fila * DIMENSION + columna;
    }

    private void validarCasilla(int fila, int columna) {
        if (fila < 0 || fila >= DIMENSION || columna < 0 || columna >= DIMENSION) {
            throw new IllegalArgumentException(
                    "Casilla fuera de rango: (" + fila + ", " + columna + "); debe estar en [0, 3)");
        }
    }

    private boolean lineaCompleta(int primera, int segunda, int tercera) {
        return casillas[primera] != null
                && casillas[primera] == casillas[segunda]
                && casillas[segunda] == casillas[tercera];
    }
}

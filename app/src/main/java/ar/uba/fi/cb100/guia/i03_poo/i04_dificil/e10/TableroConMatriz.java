package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

/**
 * Implementación de {@link Tablero} sobre una MATRIZ {@code Ficha[3][3]}:
 * (fila, columna) mapea directo a los dos índices y las líneas se leen
 * tal cual se piensan.
 */
public class TableroConMatriz implements Tablero {

    private static final int DIMENSION = 3;

    private final Ficha[][] casillas;
    private Ficha proximoTurno;

    /**
     * pre: -.<br>
     * post: crea un tablero de 3x3 vacío donde el próximo turno es de X.
     */
    public TableroConMatriz() {
        this.casillas = new Ficha[DIMENSION][DIMENSION];
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
        if (casillas[fila][columna] != null) {
            throw new IllegalStateException(
                    "La casilla (" + fila + ", " + columna + ") ya está ocupada");
        }
        casillas[fila][columna] = ficha;
        proximoTurno = (proximoTurno == Ficha.X) ? Ficha.O : Ficha.X;
    }

    @Override
    public Ficha fichaEn(int fila, int columna) {
        validarCasilla(fila, columna);
        return casillas[fila][columna];
    }

    @Override
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

    @Override
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
     * Demostración breve: el MISMO código cliente juega contra las dos
     * implementaciones a través del contrato {@link Tablero}.
     */
    public static void main(String[] args) {
        Tablero[] tableros = {new TableroConMatriz(), new TableroPlano()};
        for (Tablero tablero : tableros) {
            tablero.poner(Ficha.X, 0, 0);
            tablero.poner(Ficha.O, 1, 1);
            tablero.poner(Ficha.X, 0, 1);
            tablero.poner(Ficha.O, 2, 2);
            tablero.poner(Ficha.X, 0, 2);
            System.out.println(tablero.getClass().getSimpleName()
                    + " -> hayGanador(): " + tablero.hayGanador()
                    + ", fichaEn(1,1): " + tablero.fichaEn(1, 1));
        }
    }
}

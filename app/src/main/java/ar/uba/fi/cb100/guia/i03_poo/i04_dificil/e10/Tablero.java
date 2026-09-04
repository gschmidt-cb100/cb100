package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

/**
 * CONTRATO del TDA Tablero de ta-te-ti (3x3): qué se puede hacer, sin decir
 * cómo se guarda. El punto del ejercicio: un mismo TDA admite varias
 * implementaciones ({@link TableroConMatriz}, {@link TableroPlano}) y el
 * código cliente —incluidos los tests— funciona igual con cualquiera.
 *
 * <p>Reglas del contrato: arranca X, los turnos se alternan, no se puede
 * poner en casilla ocupada ni seguir jugando terminada la partida.</p>
 */
public interface Tablero {

    /**
     * Pone una ficha en la casilla indicada.
     *
     * <p>pre: {@code ficha} no es null y es la del turno actual (arranca X);
     * {@code fila} y {@code columna} están en [0, 3); la casilla está libre;
     * la partida no terminó.<br>
     * post: la casilla queda ocupada por {@code ficha} y el turno pasa a la otra.</p>
     *
     * @param ficha la ficha a poner.
     * @param fila fila de la casilla (base 0).
     * @param columna columna de la casilla (base 0).
     * @throws IllegalArgumentException si la ficha es null o la casilla está fuera de rango.
     * @throws IllegalStateException si no es el turno de esa ficha, la casilla
     *         está ocupada o la partida ya terminó.
     */
    void poner(Ficha ficha, int fila, int columna);

    /**
     * pre: {@code fila} y {@code columna} están en [0, 3).<br>
     * post: devuelve la ficha de esa casilla, o null si está vacía.
     *
     * @param fila fila de la casilla (base 0).
     * @param columna columna de la casilla (base 0).
     * @return la ficha en esa casilla, o null si está libre.
     * @throws IllegalArgumentException si la casilla está fuera de rango.
     */
    Ficha fichaEn(int fila, int columna);

    /**
     * pre: -.<br>
     * post: devuelve true si alguna ficha completó fila, columna o diagonal.
     *
     * @return true si hay tres en línea.
     */
    boolean hayGanador();

    /**
     * pre: -.<br>
     * post: devuelve true si el tablero está completo y nadie ganó.
     *
     * @return true si la partida terminó empatada.
     */
    boolean hayEmpate();
}

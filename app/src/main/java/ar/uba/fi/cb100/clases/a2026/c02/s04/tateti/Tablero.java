package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

public interface Tablero<T> {

    /**
     * Coloca una valor en la posición indicada.
     * @param fila: fila donde se quiere poner el valor. De 1 a n
     * @param columna: columna donde se quiere poner el valor. De 1 a n
     * @param valor: el valor a poner
     */
    void colocar(int fila, int columna, T valor);

    /**
     * Obtiene el valor en la posición indicada.
     * @param fila: fila de la posición a obtener. De 1 a n
     * @param columna: columna de la posición a obtener. De 1 a n
     * @return el valor en la posición indicada
     */
    T obtener(int fila, int columna);

    /*
     * Devuelve true si el tablero está lleno, false en caso contrario.
     */
    boolean estaLleno();

    /**
     * Devuelve true si la posición indicada está vacía, false si está ocupada.
     * @param fila: fila de la posición a consultar. De 1 a n
     * @param columna: columna de la posición a consultar. De 1 a n
     * @return true si la posición está vacía, false en caso contrario
     */
    boolean estaVacio(int fila, int columna);

    /**
     * Devuelve la cantidad de columnas del tablero.
     * @return la cantidad de columnas del tablero
     */
    int getCantidadDeColumnas();

    /**
     * Devuelve la cantidad de filas del tablero.
     * @return la cantidad de filas del tablero
     */
    int getCantidadDeFilas();
}

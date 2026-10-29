package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.List;

/**
 * Interfaz de un tablero rectangular genérico de celdas que contienen valores de tipo T.
 * Una celda vacía contiene null.
 *
 * <p>Es la misma idea que el Tablero de la semana 4, extendida con lo que
 * pide el reversi (reemplazar una ficha, contar fichas) y usando
 * {@link Posicion} en vez de dos enteros sueltos.
 *
 * <p>Que sea una <b>interfaz</b> permite cambiar la implementación
 * (matriz, vector, ...) sin tocar ni una línea de los juegos.
 *
 * @param <T> tipo de lo que se guarda en cada celda
 */
public interface Tablero<T> {

    /**
     * Coloca un valor en una celda vacía.
     * @param posicion posición dentro del tablero
     * @param valor valor a colocar, no null
     * @throws IllegalArgumentException si la posición está fuera, ocupada, o el valor es null
     */
    void colocar(Posicion posicion, T valor);

    /**
     * Reemplaza el valor de una celda ocupada (por ejemplo, dar vuelta una ficha).
     * @param posicion posición dentro del tablero
     * @param valor nuevo valor, no null
     * @throws IllegalArgumentException si la posición está fuera, vacía, o el valor es null
     */
    void reemplazar(Posicion posicion, T valor);

    /**
     * Vacía una celda.
     * @param posicion posición dentro del tablero
     * @return el valor que había (null si ya estaba vacía)
     * @throws IllegalArgumentException si la posición está fuera
     */
    T quitar(Posicion posicion);

    /**
     * @param posicion posición dentro del tablero
     * @return el valor de la celda, o null si está vacía
     * @throws IllegalArgumentException si la posición está fuera
     */
    T obtener(Posicion posicion);

    /**
     * @param posicion posición dentro del tablero
     * @return true si la celda está vacía
     * @throws IllegalArgumentException si la posición está fuera
     */
    boolean estaVacio(Posicion posicion);

    /**
     * @param posicion cualquier posición (puede ser null)
     * @return true si la posición existe en este tablero
     */
    boolean estaDentro(Posicion posicion);

    /**
     * @return true si no queda ninguna celda vacía
     */
    boolean estaLleno();

    /**
     * @param valor valor a contar, no null
     * @return cuántas celdas contienen ese valor
     */
    int contar(T valor);

    /**
     * @return todas las posiciones del tablero, recorridas por filas (de arriba a abajo, de izquierda a derecha)
     */
    List<Posicion> getPosiciones();

    /**
     * @return la cantidad de filas
     */
    int getCantidadDeFilas();

    /**
     * @return la cantidad de columnas
     */
    int getCantidadDeColumnas();
}

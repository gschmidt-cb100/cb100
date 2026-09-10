package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;


import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Implementación de {@link Tablero} sobre una matriz.
 *
 * <p><b>Convención de índices:</b> hacia afuera, filas y columnas van
 * <b>de 1 a n</b>, como en el tablero de verdad. Hacia adentro, la matriz de
 * Java va de 0 a n-1. La conversión se hace en un solo lugar, en
 * {@link #celda(int, int)}, y nadie más resta 1. Si la resta estuviera
 * repetida en cada método, tarde o temprano alguien la olvida.
 */
public class TableroMatriz<T> implements Tablero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final T[][] matriz;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Constructor de la clase TableroMatriz.
     * @param filas: cantidad de filas del tablero.
     * @param columnas: cantidad de columnas del tablero.
     */
    public TableroMatriz(int filas, int columnas) {
        ValidacionesUtiles.esMayorQueCero(filas, "filas");
        ValidacionesUtiles.esMayorQueCero(columnas, "columnas");
        // Java no permite `new T[filas][columnas]`: los genéricos se borran al
        // compilar y en ejecución no sabría qué arreglo crear. El idioma
        // estándar es crear un Object[][] y castearlo. El cast es seguro
        // porque la matriz nunca sale de esta clase, y por eso se silencia
        // el warning en esta línea y no en toda la clase.
        @SuppressWarnings("unchecked")
        T[][] nueva = (T[][]) new Object[filas][columnas];
        this.matriz = nueva;   // un arreglo recién creado ya viene lleno de null
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Coloca una ficha en la posición indicada.
     * @param fila: fila donde se quiere poner la ficha. De 1 a n
     * @param columna: columna donde se quiere poner la ficha. De 1 a n
     * @param ficha: la ficha a poner
     * @throws IllegalArgumentException si la posición está fuera del tablero,
     *         si ya está ocupada, o si la ficha es nula
     */
    @Override
    public void colocar(int fila, int columna, T ficha) {
        validarRango(fila, columna);
        ValidacionesUtiles.validarFalso(ficha == null, "La ficha no puede ser nula.");
        ValidacionesUtiles.validarFalso(!estaVacio(fila, columna),
                "La posición (" + fila + ", " + columna + ") ya está ocupada.");
        matriz[fila - 1][columna - 1] = ficha;
    }

    /**
     * Obtiene la ficha en la posición indicada.
     * @param fila: fila de la posición a obtener. De 1 a n
     * @param columna: columna de la posición a obtener. De 1 a n
     * @return la ficha en la posición indicada, o null si está vacía.
     */
    @Override
    public T obtener(int fila, int columna) {
        validarRango(fila, columna);
        return matriz[fila - 1][columna - 1];
    }

    /**
     * Valida si la posición indicada está vacía.
     * @param fila: fila de la posición a consultar. De 1 a n
     * @param columna: columna de la posición a consultar. De 1 a n
     * @return true si la posición está vacía, false si no lo está.
     */
    @Override
    public boolean estaVacio(int fila, int columna) {
        return obtener(fila, columna) == null;
    }

    /**
     * Valida si el tablero está lleno.
     * @return true si el tablero está lleno, false si no lo está.
     */
    @Override
    public boolean estaLleno() {
        for (T[] filaDeLaMatriz : matriz) {
            for (T celda : filaDeLaMatriz) {
                if (celda == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Valida que la fila y columna estén dentro del rango del tablero.
     * @param fila: fila a validar. De 1 a n
     * @param columna: columna a validar. De 1 a n
     */
    private void validarRango(int fila, int columna) {
        ValidacionesUtiles.validarRango(fila, 1, getCantidadDeFilas(), "filas");
        ValidacionesUtiles.validarRango(columna, 1, getCantidadDeColumnas(), "columnas");
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve la cantidad de columnas del tablero.
     * @return la cantidad de columnas del tablero
     */
    @Override
    public int getCantidadDeColumnas() {
        return matriz[0].length;
    }

    /**
     * Devuelve la cantidad de filas del tablero.
     * @return la cantidad de filas del tablero
     */
    @Override
    public int getCantidadDeFilas() {
        return matriz.length;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

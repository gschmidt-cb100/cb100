package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;


import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * implementación de Tablero sobre un vector (arreglo unidimensional)
 * convención de índices: hacia afuera, filas y columnas van
 * de 1 a n, como en el tablero de verdad
 * Hacia adentro, el vector de Java va de 0 a (filas*columnas - 1)
 * Una celda (fila, columna) se guarda enla posición (fila - 1) * columnas + (columna - 1):
 * cada fila ocupa lugares consecutivos del vector, una atrás de la otra
 * La conversión se hace en un solo lugar y nadie más hace la cuenta
 * Si la cuenta estuviera repetida en cada método, tarde o temprano alguien se equivoca
 */
public class TableroVector<T> implements Tablero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final T[] vector;
    private final int cantidadDeFilas;
    private final int cantidadDeColumnas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * constructor de la clase TableroVector
     * @param filas: cantidad de filas del tablero
     * @param columnas: cantidad de columnas del tablero
     */
    public TableroVector(int filas, int columnas) {
        ValidacionesUtiles.esMayorQueCero(filas, "filas");
        ValidacionesUtiles.esMayorQueCero(columnas, "columnas");
        this.cantidadDeFilas = filas;
        this.cantidadDeColumnas = columnas;
        // igual que en TableroMatriz: los genéricos se borran al compilar, así
        // que se crea un Object[] y se castea, el cast es seguro porque el
        // vector nunca sale de esta clase, y por eso se silencia el warning
        // en esta línea y no en toda la clase
        @SuppressWarnings("unchecked")
        T[] nuevo = (T[]) new Object[filas * columnas];
        this.vector = nuevo;   // un arreglo recién creado ya viene lleno de null
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * coloca una ficha en la posición indicada
     * @param fila: fila donde se quiere poner la ficha, de 1 a n
     * @param columna: columna donde se quiere poner la ficha, de 1 a n
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
        vector[indice(fila, columna)] = ficha;
    }

    /**
     * obtiene la ficha en la posición indicada
     * @param fila: fila de la posición a obtener, de 1 a n
     * @param columna: columna de la posición a obtener, de 1 a n
     * @return la ficha en la posición indicada, o null si está vacía
     */
    @Override
    public T obtener(int fila, int columna) {
        validarRango(fila, columna);
        return vector[indice(fila, columna)];
    }

    /**
     * valida si la posición indicada está vacía
     * @param fila: fila de la posición a consultar, de 1 a n
     * @param columna: columna de la posición a consultar, de 1 a n
     * @return true si la posición está vacía, false si no lo está
     */
    @Override
    public boolean estaVacio(int fila, int columna) {
        return obtener(fila, columna) == null;
    }

    /**
     * valida si el tablero está lleno
     * @return true si el tablero está lleno, false si no lo está
     */
    @Override
    public boolean estaLleno() {
        for (T celda : vector) {
            if (celda == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * convierte una posición (fila, columna), de 1 a n, en el índice
     * correspondiente del vector, de 0 a (filas*columnas - 1)
     * @param fila: fila de la posición a convertir, de 1 a n
     * @param columna: columna de la posición a convertir, de 1 a n
     * @return el índice del vector que corresponde a esa posición
     */
    private int indice(int fila, int columna) {
        return (fila - 1) * cantidadDeColumnas + (columna - 1);
    }

    /**
     * valida que la fila y columna estén dentro del rango del tablero
     * @param fila: fila a validar, de 1 a n
     * @param columna: columna a validar. de 1 a n
     */
    private void validarRango(int fila, int columna) {
        ValidacionesUtiles.validarRango(fila, 1, getCantidadDeFilas(), "filas");
        ValidacionesUtiles.validarRango(columna, 1, getCantidadDeColumnas(), "columnas");
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * devuelve la cantidad de columnas del tablero
     * @return la cantidad de columnas del tablero
     */
    @Override
    public int getCantidadDeColumnas() {
        return cantidadDeColumnas;
    }

    /**
     * devuelve la cantidad de filas del tablero
     * @return la cantidad de filas del tablero
     */
    @Override
    public int getCantidadDeFilas() {
        return cantidadDeFilas;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

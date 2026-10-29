package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Implementación de {@link Tablero} sobre una matriz.
 *
 * <p><b>Convención de índices:</b> hacia afuera, filas y columnas van de 1 a n.
 * Hacia adentro, la matriz de Java va de 0 a n-1. La resta se hace en un solo
 * lugar ({@link #getCelda} / {@link #setCelda}) y nadie más resta 1.
 *
 * @param <T> tipo de lo que se guarda en cada celda
 */
public class TableroMatriz<T> implements Tablero<T> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final T[][] matriz;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea un tablero vacío.
     * @param filas cantidad de filas, mayor que cero
     * @param columnas cantidad de columnas, mayor que cero
     * @throws IllegalArgumentException si filas o columnas no son mayores que cero
     */
    public TableroMatriz(int filas, int columnas) {
        ValidacionesUtiles.esMayorQueCero(filas, "filas");
        ValidacionesUtiles.esMayorQueCero(columnas, "columnas");
        // Java no permite new T[][]: se crea un Object[][] y se castea.
        // Es seguro porque la matriz nunca sale de esta clase.
        @SuppressWarnings("unchecked")
        T[][] nueva = (T[][]) new Object[filas][columnas];
        this.matriz = nueva;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    @Override
    public void colocar(Posicion posicion, T valor) {
        validarDentro(posicion);
        ValidacionesUtiles.validarDistintoDeNull(valor, "valor");
        ValidacionesUtiles.validarFalso(!estaVacio(posicion), "La posición " + posicion + " ya está ocupada.");
        setCelda(posicion, valor);
    }

    @Override
    public void reemplazar(Posicion posicion, T valor) {
        validarDentro(posicion);
        ValidacionesUtiles.validarDistintoDeNull(valor, "valor");
        ValidacionesUtiles.validarFalso(estaVacio(posicion), "La posición " + posicion + " está vacía: no hay nada que reemplazar.");
        setCelda(posicion, valor);
    }

    @Override
    public T quitar(Posicion posicion) {
        validarDentro(posicion);
        T anterior = getCelda(posicion);
        setCelda(posicion, null);
        return anterior;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    @Override
    public T obtener(Posicion posicion) {
        validarDentro(posicion);
        return getCelda(posicion);
    }

    @Override
    public boolean estaVacio(Posicion posicion) {
        return obtener(posicion) == null;
    }

    @Override
    public boolean estaDentro(Posicion posicion) {
        return posicion != null &&
               posicion.getFila() >= 1 && posicion.getFila() <= getCantidadDeFilas() &&
               posicion.getColumna() >= 1 && posicion.getColumna() <= getCantidadDeColumnas();
    }

    @Override
    public boolean estaLleno() {
        for (T[] fila : matriz) {
            for (T celda : fila) {
                if (celda == null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int contar(T valor) {
        ValidacionesUtiles.validarDistintoDeNull(valor, "valor");
        int cantidad = 0;
        for (T[] fila : matriz) {
            for (T celda : fila) {
                if (valor.equals(celda)) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    @Override
    public List<Posicion> getPosiciones() {
        List<Posicion> posiciones = new ArrayList<>();
        for (int fila = 1; fila <= getCantidadDeFilas(); fila++) {
            for (int columna = 1; columna <= getCantidadDeColumnas(); columna++) {
                posiciones.add(new Posicion(fila, columna));
            }
        }
        return posiciones;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    @Override
    public int getCantidadDeFilas() {
        return matriz.length;
    }

    @Override
    public int getCantidadDeColumnas() {
        return matriz[0].length;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private void validarDentro(Posicion posicion) {
        ValidacionesUtiles.validarDistintoDeNull(posicion, "posicion");
        ValidacionesUtiles.validarFalso(!estaDentro(posicion), "La posición " + posicion + " está fuera del tablero de "
                + getCantidadDeFilas() + "x" + getCantidadDeColumnas() + ".");
    }

    /** El único lugar que pasa de "1 a n" a "0 a n-1" para leer. */
    private T getCelda(Posicion posicion) {
        return matriz[posicion.getFila() - 1][posicion.getColumna() - 1];
    }

    /** El único lugar que pasa de "1 a n" a "0 a n-1" para escribir. */
    private void setCelda(Posicion posicion, T valor) {
        matriz[posicion.getFila() - 1][posicion.getColumna() - 1] = valor;
    }
}

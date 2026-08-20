package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e10;

import java.util.Arrays;

/**
 * Implementacion del TAD {@link Lista} sobre un arreglo con redimension por
 * duplicacion.
 *
 * Complejidad (n = tamanio):
 *  - obtener / tamanio: O(1)
 *  - agregar: O(1) amortizado
 *  - insertar(i, x) / eliminar(i): O(n) por el corrimiento
 */
public class VectorDinamico<T> implements Lista<T> {

    private static final int CAPACIDAD_INICIAL = 4;
    private Object[] datos;
    private int tamanio;

    public VectorDinamico() {
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @Override
    public void agregar(T x) {
        if (tamanio == datos.length) {
            datos = Arrays.copyOf(datos, datos.length * 2);
        }
        datos[tamanio++] = x;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("Indice invalido: " + i);
        }
        if (tamanio == datos.length) {
            datos = Arrays.copyOf(datos, datos.length * 2);
        }
        for (int j = tamanio; j > i; j--) {
            datos[j] = datos[j - 1];
        }
        datos[i] = x;
        tamanio++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T eliminar(int i) {
        validarIndice(i);
        T eliminado = (T) datos[i];
        for (int j = i; j < tamanio - 1; j++) {
            datos[j] = datos[j + 1];
        }
        datos[--tamanio] = null;
        return eliminado;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T obtener(int i) {
        validarIndice(i);
        return (T) datos[i];
    }

    private void validarIndice(int i) {
        if (i < 0 || i >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de rango: " + i + " (tamanio " + tamanio + ")");
        }
    }
}

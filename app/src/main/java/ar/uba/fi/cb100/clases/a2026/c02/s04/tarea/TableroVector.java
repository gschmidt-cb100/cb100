package ar.uba.fi.cb100.clases.a2026.c02.s04.tarea;

import ar.uba.fi.cb100.clases.a2026.c02.s04.tateti.Tablero;

import java.util.Vector;

public class TableroVector<T> implements Tablero<T> {

    private final int filas;
    private final int columnas;
    private final Vector<T> celdas;

    // constructor
    public TableroVector(int filas, int columnas){
        if(filas<=0){
            throw new IllegalArgumentException("Debe ser un numero positivo");
        }

        if (columnas<= 0){
            throw new IllegalArgumentException("Debe ser un número positivo");
        }

        this.columnas=columnas;
        this.filas=filas;
        this.celdas = new Vector<>(filas * columnas);

        for (int i = 0; i < filas * columnas; i++){
            celdas.add(null); //tablero vacio
        }
    }
    @Override
    public void colocar(int fila, int columna, T valor) {
        validarRango(fila, columna);
        if (valor == null) {
            throw new IllegalArgumentException("el valor a poner no puede ser nulo");
        }
        if (!estaVacio(fila, columna)) {
            throw new IllegalArgumentException(
                    "la posición (" + fila + ", " + columna + ") ya está ocupada");
        }
        celdas.set(indice(fila,columna),valor); //set --> remplaza en una posicion existente, en voctor y arraylist
    }

    @Override
    public T obtener(int fila, int columna) {
        validarRango(fila, columna);
        return celdas.get(indice(fila, columna));
    }

    //revisa si esta vacio. Gets las que son null
    @Override
    public boolean estaVacio(int fila, int columna) {
        validarRango(fila, columna);
        return celdas.get(indice(fila, columna)) == null;
    }

    //revisa si esta lleno. Si no, retorna false
    @Override
    public boolean estaLleno() {
        for (T celda : celdas) {
            if (celda == null) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int getCantidadDeColumnas() {
        return columnas;
    }

    @Override
    public int getCantidadDeFilas() {
        return filas;
    }

    private int indice(int fila, int columna) {
        return (fila - 1) * columnas + (columna - 1);
    }

    //Valida que la fila y columna estén dentro del rango del tablero.
    private void validarRango(int fila, int columna) {
        if(fila < 1 || fila >filas) {
            throw new IllegalArgumentException("Fila fuera de rango" + fila + ". El rango es de 1 a " + filas + ".");
        }
        if (columna<1 || columna > columnas){
            throw new IllegalArgumentException("Columna fuera de rango" + columna + ". El rango es de 1 a " + columnas + ".");
        }

    }
}




package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e02;

import java.util.Arrays;
import java.util.Objects;

/**
 * Conjunto (colección sin elementos repetidos) implementado sobre un arreglo.
 * La igualdad entre elementos se determina con {@code equals}.
 * No usa colecciones del JDK.
 *
 * @param <T> tipo de los elementos
 */
public class ConjuntoArreglo<T> {

    private Object[] datos;
    private int cantidad;

    public ConjuntoArreglo() {
        this.datos = new Object[4];
        this.cantidad = 0;
    }

    /**
     * Agrega el elemento sólo si aún no estaba presente.
     *
     * @return true si el elemento fue efectivamente agregado; false si ya existía
     */
    public boolean agregar(T elemento) {
        if (contiene(elemento)) {
            return false; // se ignoran los repetidos
        }
        if (cantidad == datos.length) {
            datos = Arrays.copyOf(datos, datos.length * 2);
        }
        datos[cantidad++] = elemento;
        return true;
    }

    public boolean contiene(T elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (Objects.equals(datos[i], elemento)) {
                return true;
            }
        }
        return false;
    }

    public int tamanio() {
        return cantidad;
    }

    public static void main(String[] args) {
        ConjuntoArreglo<String> colores = new ConjuntoArreglo<>();
        colores.agregar("rojo");
        colores.agregar("verde");
        colores.agregar("rojo"); // repetido: se ignora
        System.out.println("Tamaño: " + colores.tamanio());
        System.out.println("¿Contiene verde? " + colores.contiene("verde"));
        System.out.println("¿Contiene azul?  " + colores.contiene("azul"));
    }
}

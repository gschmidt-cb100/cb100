package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e07;

/**
 * Utilidad con un método genérico para hallar el máximo de un arreglo
 * cuyos elementos son comparables entre sí.
 */
public final class Maximos {

    private Maximos() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Devuelve el elemento máximo del arreglo según su orden natural.
     *
     * @param a   arreglo no vacío de elementos comparables
     * @param <T> tipo comparable consigo mismo
     * @return el mayor elemento
     * @throws IllegalArgumentException si el arreglo es null o vacío
     */
    public static <T extends Comparable<T>> T maximo(T[] a) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser null ni vacío");
        }
        T maximo = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(maximo) > 0) {
                maximo = a[i];
            }
        }
        return maximo;
    }

    public static void main(String[] args) {
        Integer[] enteros = {3, 9, 1, 7, 4};
        System.out.println("Máximo entero: " + maximo(enteros)); // 9

        String[] palabras = {"banana", "manzana", "kiwi", "durazno"};
        // Orden natural de String es lexicográfico: "manzana" es el mayor.
        System.out.println("Máximo alfabético: " + maximo(palabras));
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e08;

/**
 * Ejercicio 8 (facil): Promedio de un arreglo de numeros reales.
 */
public class PromedioArreglo {

    private PromedioArreglo() {
    }

    /**
     * Calcula el promedio de los elementos de un arreglo.
     *
     * @param v arreglo de valores
     * @return promedio de los elementos
     * @throws IllegalArgumentException si el arreglo es null o esta vacio
     */
    public static double promedio(double[] v) {
        if (v == null || v.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacio");
        }
        var suma = 0.0;
        for (var valor : v) {
            suma += valor;
        }
        return suma / v.length;
    }

    public static void main(String[] args) {
        var datos = new double[]{2.0, 4.0, 6.0};
        System.out.println("promedio = " + promedio(datos));
    }
}

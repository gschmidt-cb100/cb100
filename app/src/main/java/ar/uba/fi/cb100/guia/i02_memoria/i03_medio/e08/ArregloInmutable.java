package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e08;

import java.util.Arrays;

/**
 * Ejercicio 08 (ArregloInmutable).
 *
 * Envuelve un int[] y solo expone lectura (get, tamanio). Copia el arreglo
 * al construirse y devuelve copias, de modo que su contenido no puede
 * modificarse desde afuera.
 */
public final class ArregloInmutable {

    private final int[] datos;

    public ArregloInmutable(int[] datos) {
        // Copia defensiva: el llamador no puede mutar nuestro estado.
        this.datos = Arrays.copyOf(datos, datos.length);
    }

    public int get(int i) {
        return datos[i];
    }

    public int tamanio() {
        return datos.length;
    }

    /**
     * Devuelve una COPIA de los datos internos.
     */
    public int[] aArreglo() {
        return Arrays.copyOf(datos, datos.length);
    }

    public static void main(String[] args) {
        int[] entrada = {10, 20, 30};
        ArregloInmutable inm = new ArregloInmutable(entrada);

        entrada[0] = 99;          // mutar la entrada no afecta
        int[] salida = inm.aArreglo();
        salida[1] = 99;           // mutar la salida tampoco

        System.out.println("tamanio: " + inm.tamanio());     // 3
        System.out.println("get(0):  " + inm.get(0));         // 10
        System.out.println("contenido: " + Arrays.toString(inm.aArreglo())); // [10, 20, 30]
    }
}

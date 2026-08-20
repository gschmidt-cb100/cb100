package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e08;

import java.util.Arrays;

/**
 * Reproduce en un solo escenario dos ideas clave de memoria en Java:
 *
 *  - Aliasing: dos variables (datos y alias) apuntan al MISMO arreglo,
 *    asi que un cambio hecho por una lo ve la otra.
 *  - Paso de parametros por valor de la referencia:
 *      * mutar el arreglo recibido SI se ve afuera (mismo objeto).
 *      * reasignar el parametro a otro arreglo NO se ve afuera
 *        (solo cambia la copia local de la referencia).
 */
public class IndicarSalida {

    /** Muta el arreglo recibido: el cambio es visible para quien lo llamo. */
    private static void mutar(int[] a) {
        a[0] = 99;
    }

    /** Reasigna el parametro local: no afecta al arreglo del llamador. */
    private static void reasignar(int[] a) {
        a = new int[]{-1, -1, -1};
        a[0] = 100; // toca la copia local, se pierde al salir del metodo
    }

    /**
     * Devuelve el estado final observable del arreglo tras el escenario.
     * Esperado: {99, 7, 3}.
     */
    public static int[] escenario() {
        int[] datos = {1, 2, 3};
        int[] alias = datos;   // aliasing: misma referencia

        mutar(datos);          // datos y alias pasan a {99, 2, 3}
        reasignar(datos);      // no cambia nada afuera

        alias[1] = 7;          // via alias, visible en datos -> {99, 7, 3}
        return datos;
    }

    public static void main(String[] args) {
        System.out.println("Estado final: " + Arrays.toString(escenario()));
    }
}

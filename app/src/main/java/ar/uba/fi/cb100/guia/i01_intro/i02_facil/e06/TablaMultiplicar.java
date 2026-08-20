package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e06;

/**
 * Ejercicio 6 (facil): Tabla de multiplicar.
 * Devuelve los 10 primeros multiplos de un numero (n*1 .. n*10).
 */
public class TablaMultiplicar {

    private TablaMultiplicar() {
    }

    /**
     * Calcula los 10 primeros multiplos de n.
     *
     * @param n numero base
     * @return arreglo con {n*1, n*2, ..., n*10}
     */
    public static int[] tabla(int n) {
        var resultado = new int[10];
        for (var i = 0; i < 10; i++) {
            resultado[i] = n * (i + 1);
        }
        return resultado;
    }

    public static void main(String[] args) {
        var t = tabla(5);
        for (var i = 0; i < t.length; i++) {
            System.out.println("5 x " + (i + 1) + " = " + t[i]);
        }
    }
}

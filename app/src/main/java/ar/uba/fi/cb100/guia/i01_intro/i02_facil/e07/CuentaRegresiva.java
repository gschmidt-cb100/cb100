package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e07;

/**
 * Ejercicio 7 (facil): Cuenta regresiva.
 * Devuelve un arreglo {n, n-1, ..., 1}.
 */
public class CuentaRegresiva {

    private CuentaRegresiva() {
    }

    /**
     * Genera una cuenta regresiva desde n hasta 1.
     *
     * @param n valor inicial (si es menor o igual a 0 devuelve arreglo vacio)
     * @return arreglo {n, n-1, ..., 1}
     */
    public static int[] regresiva(int n) {
        if (n <= 0) {
            return new int[0];
        }
        var resultado = new int[n];
        for (var i = 0; i < n; i++) {
            resultado[i] = n - i;
        }
        return resultado;
    }

    public static void main(String[] args) {
        var r = regresiva(5);
        for (var valor : r) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e04;

/**
 * Ejercicio 04 - Ordenar tres numeros.
 * Devuelve los tres enteros ordenados de forma ascendente sin usar librerias.
 */
public class OrdenarTres {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private OrdenarTres() {
    }

    /**
     * Ordena tres enteros de menor a mayor.
     *
     * @param a primer valor
     * @param b segundo valor
     * @param c tercer valor
     * @return arreglo de longitud 3 ordenado ascendentemente
     */
    public static int[] ordenar(int a, int b, int c) {
        // Comparaciones e intercambios manuales (sin Arrays.sort)
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (b > c) {
            int tmp = b;
            b = c;
            c = tmp;
        }
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        return new int[] {a, b, c};
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        int[] r = ordenar(3, 1, 2);
        System.out.println(r[0] + ", " + r[1] + ", " + r[2]);
    }
}

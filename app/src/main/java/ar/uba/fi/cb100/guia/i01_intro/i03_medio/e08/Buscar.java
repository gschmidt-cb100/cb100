package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e08;

/**
 * Ejercicio 08: Buscar en un arreglo.
 * Búsqueda lineal que devuelve el índice del primer elemento igual a x.
 */
public class Buscar {

    /**
     * Devuelve el índice de la primera aparición de x, o -1 si no está.
     *
     * @param v arreglo donde buscar (si es nulo devuelve -1)
     * @param x valor buscado
     * @return índice o -1
     */
    public static int indiceDe(int[] v, int x) {
        if (v == null) {
            return -1;
        }
        for (int i = 0; i < v.length; i++) {
            if (v[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] datos = {10, 20, 30, 40};
        System.out.println("Índice de 30: " + indiceDe(datos, 30));
        System.out.println("Índice de 99: " + indiceDe(datos, 99));
    }
}

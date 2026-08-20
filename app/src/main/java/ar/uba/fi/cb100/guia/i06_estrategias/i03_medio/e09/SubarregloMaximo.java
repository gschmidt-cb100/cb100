package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e09;

/**
 * e09: maxima suma de un subarreglo contiguo con division y conquista.
 *
 * <p>Dado un arreglo (que puede tener numeros negativos), se busca la mayor suma
 * posible de una porcion contigua no vacia.</p>
 *
 * <p>Estrategia D&amp;C: se parte el arreglo por la mitad. El mejor subarreglo
 * esta en uno de tres lugares:</p>
 * <ul>
 *   <li>enteramente en la mitad izquierda,</li>
 *   <li>enteramente en la mitad derecha,</li>
 *   <li>cruzando el punto medio (calculado por {@code maxCruzando}).</li>
 * </ul>
 *
 * <p>Complejidad: O(n log n).</p>
 */
public final class SubarregloMaximo {

    private SubarregloMaximo() {
    }

    /**
     * Devuelve la maxima suma de un subarreglo contiguo no vacio de {@code a}.
     *
     * @param a arreglo no vacio (puede contener negativos)
     * @return la mayor suma contigua
     * @throws IllegalArgumentException si {@code a} es vacio
     */
    public static int sumaMaxima(int[] a) {
        if (a.length == 0) {
            throw new IllegalArgumentException("el arreglo no puede ser vacio");
        }
        return sumaMaxima(a, 0, a.length - 1);
    }

    /** Maxima suma contigua dentro del rango cerrado [lo, hi]. */
    private static int sumaMaxima(int[] a, int lo, int hi) {
        if (lo == hi) {
            return a[lo]; // caso base: un solo elemento
        }
        int medio = lo + (hi - lo) / 2;
        int maxIzq = sumaMaxima(a, lo, medio);
        int maxDer = sumaMaxima(a, medio + 1, hi);
        int maxCruce = maxCruzando(a, lo, medio, hi);
        return Math.max(maxCruce, Math.max(maxIzq, maxDer));
    }

    /**
     * Maxima suma de un subarreglo que cruza el medio, es decir que usa {@code a[medio]}
     * y {@code a[medio+1]}. Se extiende hacia la izquierda y hacia la derecha
     * tomando el mejor prefijo/sufijo desde el centro.
     */
    private static int maxCruzando(int[] a, int lo, int medio, int hi) {
        int sumaIzq = Integer.MIN_VALUE;
        int acum = 0;
        for (int i = medio; i >= lo; i--) {
            acum += a[i];
            sumaIzq = Math.max(sumaIzq, acum);
        }
        int sumaDer = Integer.MIN_VALUE;
        acum = 0;
        for (int j = medio + 1; j <= hi; j++) {
            acum += a[j];
            sumaDer = Math.max(sumaDer, acum);
        }
        return sumaIzq + sumaDer;
    }

    public static void main(String[] args) {
        int[] datos = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Suma maxima: " + sumaMaxima(datos)); // 6 -> {4,-1,2,1}
    }
}

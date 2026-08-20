package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e10;

import java.util.Arrays;

/**
 * e10: comparativa de algoritmos de ordenamiento.
 *
 * <p>Aplica cuatro algoritmos distintos (Mergesort y Quicksort comparativos;
 * Counting y Radix no comparativos) al mismo arreglo y verifica que los cuatro
 * produzcan exactamente el mismo resultado que {@link java.util.Arrays#sort}.
 * Es una forma de testeo cruzado: si todos coinciden, es muy probable que las
 * cuatro implementaciones sean correctas.</p>
 *
 * <p>Todos los algoritmos estan reimplementados dentro de este paquete para que
 * el ejercicio sea autocontenido. Como Counting y Radix trabajan sobre enteros
 * no negativos, la comparativa exige entradas no negativas.</p>
 */
public final class ComparativaOrdenamientos {

    private ComparativaOrdenamientos() {
    }

    /**
     * Ordena {@code a} con los cuatro algoritmos y con {@code Arrays.sort} y
     * verifica que todos den el mismo resultado.
     *
     * @param a arreglo de enteros no negativos (no se modifica)
     * @return {@code true} si los cuatro algoritmos coinciden con {@code Arrays.sort}
     * @throws IllegalArgumentException si algun valor es negativo
     */
    public static boolean todosCoinciden(int[] a) {
        for (int valor : a) {
            if (valor < 0) {
                throw new IllegalArgumentException(
                        "la comparativa exige valores no negativos: " + valor);
            }
        }
        int[] referencia = a.clone();
        Arrays.sort(referencia);

        return Arrays.equals(referencia, mergesort(a))
                && Arrays.equals(referencia, quicksort(a))
                && Arrays.equals(referencia, counting(a))
                && Arrays.equals(referencia, radix(a));
    }

    // ---------------------------------------------------------------------
    // Mergesort
    // ---------------------------------------------------------------------

    private static int[] mergesort(int[] a) {
        int[] copia = a.clone();
        if (copia.length > 1) {
            mergesortRec(copia, 0, copia.length - 1, new int[copia.length]);
        }
        return copia;
    }

    private static void mergesortRec(int[] a, int izq, int der, int[] aux) {
        if (izq >= der) {
            return;
        }
        int medio = (izq + der) / 2;
        mergesortRec(a, izq, medio, aux);
        mergesortRec(a, medio + 1, der, aux);
        mezclar(a, izq, medio, der, aux);
    }

    private static void mezclar(int[] a, int izq, int medio, int der, int[] aux) {
        for (int i = izq; i <= der; i++) {
            aux[i] = a[i];
        }
        int i = izq;
        int j = medio + 1;
        for (int k = izq; k <= der; k++) {
            if (i > medio) {
                a[k] = aux[j++];
            } else if (j > der) {
                a[k] = aux[i++];
            } else if (aux[i] <= aux[j]) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
            }
        }
    }

    // ---------------------------------------------------------------------
    // Quicksort
    // ---------------------------------------------------------------------

    private static int[] quicksort(int[] a) {
        int[] copia = a.clone();
        quicksortRec(copia, 0, copia.length - 1);
        return copia;
    }

    private static void quicksortRec(int[] a, int izq, int der) {
        if (izq >= der) {
            return;
        }
        int posPivote = particionar(a, izq, der);
        quicksortRec(a, izq, posPivote - 1);
        quicksortRec(a, posPivote + 1, der);
    }

    private static int particionar(int[] a, int izq, int der) {
        int pivote = a[der];
        int i = izq;
        for (int j = izq; j < der; j++) {
            if (a[j] <= pivote) {
                intercambiar(a, i, j);
                i++;
            }
        }
        intercambiar(a, i, der);
        return i;
    }

    private static void intercambiar(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    // ---------------------------------------------------------------------
    // Counting sort
    // ---------------------------------------------------------------------

    private static int[] counting(int[] a) {
        if (a.length == 0) {
            return new int[0];
        }
        int maximo = Arrays.stream(a).max().getAsInt();
        int[] conteos = new int[maximo + 1];
        for (int valor : a) {
            conteos[valor]++;
        }
        int[] resultado = new int[a.length];
        int indice = 0;
        for (int valor = 0; valor <= maximo; valor++) {
            for (int i = 0; i < conteos[valor]; i++) {
                resultado[indice++] = valor;
            }
        }
        return resultado;
    }

    // ---------------------------------------------------------------------
    // Radix sort (LSD, base 10)
    // ---------------------------------------------------------------------

    private static int[] radix(int[] a) {
        if (a.length == 0) {
            return new int[0];
        }
        int maximo = Arrays.stream(a).max().getAsInt();
        int[] actual = a.clone();
        for (int exp = 1; maximo / exp > 0; exp *= 10) {
            actual = radixPorDigito(actual, exp);
        }
        return actual;
    }

    private static int[] radixPorDigito(int[] a, int exp) {
        int[] conteos = new int[10];
        for (int valor : a) {
            conteos[(valor / exp) % 10]++;
        }
        for (int d = 1; d < 10; d++) {
            conteos[d] += conteos[d - 1];
        }
        int[] resultado = new int[a.length];
        for (int i = a.length - 1; i >= 0; i--) {
            int digito = (a[i] / exp) % 10;
            resultado[--conteos[digito]] = a[i];
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[][] casos = {
                {5, 2, 9, 1, 5, 6, 3},
                {170, 45, 75, 90, 802, 24, 2, 66},
                {},
                {7}
        };
        for (int[] caso : casos) {
            System.out.println(Arrays.toString(caso) + " -> coinciden: " + todosCoinciden(caso));
        }
    }
}

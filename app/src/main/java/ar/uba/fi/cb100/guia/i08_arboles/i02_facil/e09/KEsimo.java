package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e09;

import java.util.TreeSet;

/**
 * e09: k-ésimo menor elemento. El iterador de un {@link TreeSet} recorre
 * los elementos en orden ascendente (recorrido in-order del árbol), así
 * que el k-ésimo menor es simplemente el k-ésimo del recorrido.
 */
public final class KEsimo {

    private KEsimo() {
    }

    /**
     * Devuelve el k-ésimo menor elemento del conjunto (k arranca en 1:
     * k=1 es el mínimo).
     *
     * @param valores conjunto ordenado de enteros
     * @param k       posición buscada, entre 1 y {@code valores.size()}
     * @return el k-ésimo menor elemento
     * @throws IllegalArgumentException si k está fuera de rango
     */
    public static int kEsimo(TreeSet<Integer> valores, int k) {
        if (k < 1 || k > valores.size()) {
            throw new IllegalArgumentException(
                    "k debe estar entre 1 y " + valores.size() + ", pero vale " + k);
        }
        int posicion = 0;
        for (int valor : valores) { // el iterador va de menor a mayor
            posicion++;
            if (posicion == k) {
                return valor;
            }
        }
        // Nunca llegamos acá: k ya fue validado contra el tamaño.
        throw new IllegalStateException("inalcanzable");
    }

    public static void main(String[] args) {
        TreeSet<Integer> tiempos = new TreeSet<>();
        tiempos.add(52);
        tiempos.add(47);
        tiempos.add(61);
        tiempos.add(49);
        System.out.println("Tiempos de carrera: " + tiempos);
        System.out.println("Segundo mejor tiempo: " + kEsimo(tiempos, 2));
    }
}

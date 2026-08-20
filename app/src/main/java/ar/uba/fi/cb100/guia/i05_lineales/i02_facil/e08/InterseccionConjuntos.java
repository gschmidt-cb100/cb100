package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e08;

import java.util.HashSet;
import java.util.Set;

/**
 * e08: interseccion de dos conjuntos, devolviendo un nuevo conjunto.
 * Se usa una copia y {@link Set#retainAll}.
 */
public final class InterseccionConjuntos {

    private InterseccionConjuntos() {
    }

    /**
     * Devuelve un nuevo conjunto con los elementos comunes a {@code a} y {@code b}.
     *
     * @param a primer conjunto (no se modifica)
     * @param b segundo conjunto (no se modifica)
     * @return nuevo conjunto interseccion
     */
    public static Set<Integer> interseccion(Set<Integer> a, Set<Integer> b) {
        // Copiamos a para no modificar el conjunto original.
        Set<Integer> resultado = new HashSet<>(a);
        // retainAll deja solo los elementos que tambien estan en b.
        resultado.retainAll(b);
        return resultado;
    }

    public static void main(String[] args) {
        Set<Integer> a = Set.of(1, 2, 3, 4);
        Set<Integer> b = Set.of(3, 4, 5, 6);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("interseccion: " + interseccion(a, b));
    }
}

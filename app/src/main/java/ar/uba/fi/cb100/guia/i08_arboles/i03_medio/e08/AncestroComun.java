package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e08;

/**
 * Ejercicio: mínimo ancestro común (LCA) de dos valores en un ABB.
 * El invariante del ABB permite resolverlo bajando por una sola rama:
 * mientras los dos valores queden del mismo lado del nodo actual,
 * el ancestro común está en ese subárbol. En cuanto se separan
 * (o uno coincide con el nodo), lo encontramos.
 */
public final class AncestroComun {

    private AncestroComun() {
    }

    /**
     * Mínimo ancestro común de {@code a} y {@code b} en el ABB.
     * Precondición: ambos valores están en el árbol.
     *
     * @param raiz raíz de un ABB que contiene a ambos valores
     * @param a    un valor presente en el árbol
     * @param b    otro valor presente en el árbol
     * @return el valor del ancestro común más profundo de ambos
     */
    public static int ancestroComun(Nodo raiz, int a, int b) {
        if (a < raiz.valor && b < raiz.valor) {
            // Los dos están a la izquierda: el ancestro también.
            return ancestroComun(raiz.izquierdo, a, b);
        }
        if (a > raiz.valor && b > raiz.valor) {
            // Los dos están a la derecha: el ancestro también.
            return ancestroComun(raiz.derecho, a, b);
        }
        // Se separaron (o uno es el nodo actual): éste es el ancestro.
        return raiz.valor;
    }

    /** Demostración con el ABB de ejemplo de la clase. */
    public static void main(String[] args) {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("Ancestro común de 20 y 40: " + ancestroComun(raiz, 20, 40));
        System.out.println("Ancestro común de 20 y 60: " + ancestroComun(raiz, 20, 60));
        System.out.println("Ancestro común de 60 y 80: " + ancestroComun(raiz, 60, 80));
    }
}

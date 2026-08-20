package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e04;

/**
 * Ejercicio: validar si un árbol binario cumple el invariante de ABB.
 *
 * <p>Ojo con el error clásico: comparar cada nodo solamente con sus hijos
 * directos no alcanza. El invariante dice que TODO el subárbol izquierdo
 * tiene valores menores que la raíz, no solo el hijo. Por eso bajamos
 * pasando un rango (min, max) que cada nodo debe respetar.</p>
 */
public final class ValidadorABB {

    private ValidadorABB() {
    }

    /**
     * Indica si el árbol es un árbol binario de búsqueda válido
     * (sin valores repetidos).
     *
     * @param raiz raíz del árbol (puede ser {@code null})
     * @return {@code true} si cumple el invariante de ABB en todos los nodos
     */
    public static boolean esABB(Nodo raiz) {
        // Usamos long para que ningún int quede afuera del rango inicial.
        return esABB(raiz, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Cada nodo debe cumplir min &lt; valor &lt; max. Al bajar a la
     * izquierda el valor actual pasa a ser la cota superior; al bajar
     * a la derecha, la cota inferior.
     */
    private static boolean esABB(Nodo nodo, long min, long max) {
        if (nodo == null) {
            return true;
        }
        if (nodo.valor <= min || nodo.valor >= max) {
            return false;
        }
        return esABB(nodo.izquierdo, min, nodo.valor)
                && esABB(nodo.derecho, nodo.valor, max);
    }

    /** Demostración: un ABB válido y el árbol tramposo. */
    public static void main(String[] args) {
        Nodo valido = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
        // El 55 es mayor que su padre (30) pero está en el subárbol
        // izquierdo de 50: un chequeo ingenuo padre-hijo no lo detecta.
        Nodo tramposo = new Nodo(50,
                new Nodo(30, null, new Nodo(55)),
                new Nodo(70));

        System.out.println("¿El árbol válido es ABB? " + esABB(valido));
        System.out.println("¿El árbol tramposo es ABB? " + esABB(tramposo));
    }
}

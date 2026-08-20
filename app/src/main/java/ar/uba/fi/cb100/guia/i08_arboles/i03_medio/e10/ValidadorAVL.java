package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e10;

/**
 * Ejercicio: validar si un árbol es AVL. Un AVL es un ABB en el que
 * TODOS los nodos tienen factor de equilibrio (altura del subárbol
 * izquierdo menos la del derecho) en {-1, 0, 1}.
 *
 * <p>Esta versión recalcula alturas en cada nodo (O(n·log n) si el árbol
 * está balanceado): es la más simple de entender. En clase se puede
 * discutir cómo bajarla a O(n) devolviendo altura y validez juntas.</p>
 */
public final class ValidadorAVL {

    private ValidadorAVL() {
    }

    /**
     * Indica si el árbol es un AVL válido: cumple el invariante de ABB
     * y está balanceado en altura en todos sus nodos.
     *
     * @param raiz raíz del árbol (puede ser {@code null})
     * @return {@code true} si es un AVL
     */
    public static boolean esAVL(Nodo raiz) {
        return esABB(raiz, Long.MIN_VALUE, Long.MAX_VALUE) && estaEquilibrado(raiz);
    }

    /** Invariante de ABB con cotas, igual que en el ejercicio 4. */
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

    /**
     * Verifica que el factor de equilibrio de cada nodo esté en {-1, 0, 1}.
     */
    private static boolean estaEquilibrado(Nodo nodo) {
        if (nodo == null) {
            return true;
        }
        int factor = altura(nodo.izquierdo) - altura(nodo.derecho);
        if (factor < -1 || factor > 1) {
            return false;
        }
        return estaEquilibrado(nodo.izquierdo) && estaEquilibrado(nodo.derecho);
    }

    /** Altura recursiva: árbol vacío = -1. */
    private static int altura(Nodo nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    /** Demostración: un AVL y una "lista" degenerada que no lo es. */
    public static void main(String[] args) {
        Nodo equilibrado = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
        // ABB válido pero degenerado en lista: 10 → 20 → 30 por derecha.
        Nodo lista = new Nodo(10, null, new Nodo(20, null, new Nodo(30)));

        System.out.println("¿El árbol completo es AVL? " + esAVL(equilibrado));
        System.out.println("¿La lista 10→20→30 es AVL? " + esAVL(lista));
    }
}

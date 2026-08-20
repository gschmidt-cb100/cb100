package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e05;

/**
 * Ejercicio: contar hojas y contar los nodos de un nivel dado.
 * Dos variantes de la misma idea: recorrer el árbol recursivamente
 * y decidir en cada nodo si suma o no al resultado.
 */
public final class HojasYNiveles {

    private HojasYNiveles() {
    }

    /**
     * Cantidad de hojas del árbol (nodos sin hijos).
     *
     * @param raiz raíz del árbol (puede ser {@code null})
     * @return cuántas hojas tiene (0 si el árbol está vacío)
     */
    public static int hojas(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        if (raiz.izquierdo == null && raiz.derecho == null) {
            return 1;
        }
        return hojas(raiz.izquierdo) + hojas(raiz.derecho);
    }

    /**
     * Cantidad de nodos que hay en el nivel pedido, contando la raíz
     * como nivel 0. La idea: pedir el nivel n desde la raíz es lo mismo
     * que pedir el nivel n-1 desde cada uno de sus hijos.
     *
     * @param raiz  raíz del árbol (puede ser {@code null})
     * @param nivel nivel a contar (0 = raíz)
     * @return cuántos nodos hay en ese nivel (0 si el nivel no existe)
     */
    public static int nodosEnNivel(Nodo raiz, int nivel) {
        if (raiz == null || nivel < 0) {
            return 0;
        }
        if (nivel == 0) {
            return 1;
        }
        return nodosEnNivel(raiz.izquierdo, nivel - 1)
                + nodosEnNivel(raiz.derecho, nivel - 1);
    }

    /** Demostración con el árbol de ejemplo de la clase. */
    public static void main(String[] args) {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("Hojas: " + hojas(raiz));
        for (int nivel = 0; nivel <= 3; nivel++) {
            System.out.println("Nodos en nivel " + nivel + ": " + nodosEnNivel(raiz, nivel));
        }
    }
}

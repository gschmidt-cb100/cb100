package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e01;

/**
 * Ejercicio: altura y tamaño de un árbol binario, resueltos con la
 * receta recursiva clásica: caso base el árbol vacío, y para el caso
 * general combinamos los resultados de los dos subárboles.
 */
public final class AlturaYTamanio {

    private AlturaYTamanio() {
    }

    /**
     * Altura del árbol: cantidad de aristas del camino más largo desde
     * la raíz hasta una hoja. Por convención, el árbol vacío tiene
     * altura -1 y una hoja sola tiene altura 0.
     *
     * @param raiz raíz del árbol (puede ser {@code null})
     * @return la altura, o -1 si el árbol está vacío
     */
    public static int altura(Nodo raiz) {
        if (raiz == null) {
            return -1;
        }
        return 1 + Math.max(altura(raiz.izquierdo), altura(raiz.derecho));
    }

    /**
     * Cantidad total de nodos del árbol.
     *
     * @param raiz raíz del árbol (puede ser {@code null})
     * @return cuántos nodos tiene el árbol (0 si está vacío)
     */
    public static int tamanio(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        return 1 + tamanio(raiz.izquierdo) + tamanio(raiz.derecho);
    }

    /** Demostración con el árbol de ejemplo de la clase. */
    public static void main(String[] args) {
        //        50
        //      /    \
        //    30      70
        //   /  \    /  \
        //  20  40  60  80
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("Altura del árbol vacío: " + altura(null));
        System.out.println("Altura del árbol de ejemplo: " + altura(raiz));
        System.out.println("Tamaño del árbol de ejemplo: " + tamanio(raiz));
    }
}

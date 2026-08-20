package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e05;

/**
 * Las rotaciones: la pieza fundamental de los arboles autobalanceados.
 *
 * Una rotacion reordena tres punteros para bajar un lado del arbol y
 * subir el otro, SIN romper el orden del ABB (el recorrido en orden
 * queda igual). Es O(1): no se recorre nada, solo se recuelgan nodos.
 *
 * Rotacion a la DERECHA sobre p (sube el hijo izquierdo q):
 *
 *        p                q
 *       / \              / \
 *      q   C    ==>     A   p
 *     / \                  / \
 *    A   B                B   C
 *
 * El subarbol B (los valores entre q y p) cambia de padre: era el hijo
 * derecho de q y pasa a ser el hijo izquierdo de p. Ese es el detalle
 * que hay que cuidar. La rotacion a la izquierda es el espejo exacto.
 *
 * Despues de recolgar hay que recalcular las alturas, y en ese orden:
 * primero la del nodo que bajo (p), despues la del que subio (q),
 * porque la altura de q depende de la nueva altura de p.
 */
public class Rotaciones {

    /** Nodo con altura cacheada, como lo va a necesitar el AVL. */
    public static class Nodo {
        int valor;
        Nodo izq;
        Nodo der;
        int altura; // Una hoja tiene altura 0.

        public Nodo(int valor) {
            this.valor = valor;
            this.altura = 0;
        }
    }

    /** Altura de un subarbol, tolerando null: un arbol vacio mide -1. */
    public static int altura(Nodo nodo) {
        return (nodo == null) ? -1 : nodo.altura;
    }

    /** Recalcula la altura del nodo a partir de las de sus hijos. */
    private static void actualizarAltura(Nodo nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
    }

    /**
     * Rota a la derecha sobre p: sube su hijo izquierdo y devuelve la
     * nueva raiz del subarbol, para que el llamador la recuelgue.
     */
    public static Nodo rotarDerecha(Nodo p) {
        Nodo q = p.izq;      // q va a ser la nueva raiz.
        p.izq = q.der;       // El subarbol B cambia de padre: de q a p.
        q.der = p;           // p pasa a ser el hijo derecho de q.
        actualizarAltura(p); // Primero p, que ahora esta abajo...
        actualizarAltura(q); // ...y despues q, que depende de p.
        return q;
    }

    /**
     * Rota a la izquierda sobre p: sube su hijo derecho y devuelve la
     * nueva raiz del subarbol. Es el espejo exacto de rotarDerecha.
     */
    public static Nodo rotarIzquierda(Nodo p) {
        Nodo q = p.der;      // q va a ser la nueva raiz.
        p.der = q.izq;       // El subarbol del medio cambia de padre.
        q.izq = p;           // p pasa a ser el hijo izquierdo de q.
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }

    /** Demostracion: enderezar un zig 30-20-10 con una sola rotacion. */
    public static void main(String[] args) {
        // Armamos 30 <- 20 <- 10, todo colgando por la izquierda.
        Nodo raiz = new Nodo(30);
        raiz.izq = new Nodo(20);
        raiz.izq.izq = new Nodo(10);
        raiz.izq.altura = 1;
        raiz.altura = 2;

        raiz = rotarDerecha(raiz);
        System.out.println("raiz  = " + raiz.valor + " (altura " + raiz.altura + ")");
        System.out.println("izq   = " + raiz.izq.valor + " (altura " + raiz.izq.altura + ")");
        System.out.println("der   = " + raiz.der.valor + " (altura " + raiz.der.altura + ")");
    }
}

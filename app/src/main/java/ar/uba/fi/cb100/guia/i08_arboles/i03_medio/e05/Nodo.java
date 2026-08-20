package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e05;

/**
 * Nodo de un árbol binario: un valor entero y referencias a los
 * subárboles izquierdo y derecho (que pueden ser {@code null}).
 */
class Nodo {

    int valor;
    Nodo izquierdo;
    Nodo derecho;

    Nodo(int valor) {
        this(valor, null, null);
    }

    Nodo(int valor, Nodo izquierdo, Nodo derecho) {
        this.valor = valor;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e09;

/**
 * ABB aumentado: cada nodo cachea el tamanio de su subarbol para poder
 * responder "cual es el k-esimo menor?" en O(h), sin recorrer todo.
 *
 * La idea de la busqueda: parado en un nodo, si su subarbol izquierdo
 * tiene t valores, entonces el nodo es el (t+1)-esimo de su subarbol.
 *  - Si k == t+1, es este nodo.
 *  - Si k <= t, el k-esimo esta a la izquierda (mismo k).
 *  - Si k > t+1, esta a la derecha, pero alla es el (k - t - 1)-esimo
 *    porque ya "dejamos atras" los t de la izquierda y el nodo actual.
 *
 * Esta tecnica de guardar datos extra en los nodos se llama AUMENTAR
 * la estructura: cuesta mantener el dato al insertar, pero habilita
 * consultas nuevas sin cambiar el costo de las viejas.
 */
public class AbbConKEsimo<T extends Comparable<T>> {

    /** Nodo que ademas de sus hijos recuerda cuantos nodos tiene abajo. */
    private static class Nodo<T> {
        final T valor;
        Nodo<T> izq;
        Nodo<T> der;
        int tamanioSubarbol; // Este nodo + todos sus descendientes.

        Nodo(T valor) {
            this.valor = valor;
            this.tamanioSubarbol = 1;
        }
    }

    private Nodo<T> raiz;

    /** Tamanio de un subarbol, tolerando null. O(1) gracias al cache. */
    private static int tamanio(Nodo<?> nodo) {
        return (nodo == null) ? 0 : nodo.tamanioSubarbol;
    }

    /** Cantidad de valores del arbol: el tamanio cacheado de la raiz. O(1). */
    public int tamanio() {
        return tamanio(raiz);
    }

    /** Indica si el valor esta en el arbol. O(h). */
    public boolean contiene(T valor) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = valor.compareTo(actual.valor);
            if (comparacion == 0) {
                return true;
            }
            actual = (comparacion < 0) ? actual.izq : actual.der;
        }
        return false;
    }

    /**
     * Inserta el valor sin duplicados, actualizando los tamanios
     * cacheados de todo el camino. O(h).
     */
    public void insertar(T valor) {
        if (contiene(valor)) {
            // Chequeo previo: si es duplicado no tocamos ningun contador.
            return;
        }
        raiz = insertar(raiz, valor);
    }

    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            return new Nodo<>(valor);
        }
        if (valor.compareTo(nodo.valor) < 0) {
            nodo.izq = insertar(nodo.izq, valor);
        } else {
            nodo.der = insertar(nodo.der, valor);
        }
        // Al volver de la recursion, cada nodo del camino suma el nuevo.
        nodo.tamanioSubarbol = 1 + tamanio(nodo.izq) + tamanio(nodo.der);
        return nodo;
    }

    /**
     * Devuelve el k-esimo menor valor del arbol (k arranca en 1).
     * O(h): en cada paso descarta un subarbol entero mirando el cache.
     *
     * @throws IllegalArgumentException si k esta fuera de [1, tamanio()].
     */
    public T kEsimo(int k) {
        if (k < 1 || k > tamanio()) {
            throw new IllegalArgumentException(
                    "k debe estar entre 1 y " + tamanio() + ", vino " + k);
        }
        Nodo<T> actual = raiz;
        while (actual != null) {
            int enLaIzquierda = tamanio(actual.izq);
            if (k == enLaIzquierda + 1) {
                return actual.valor; // Este nodo es exactamente el k-esimo.
            }
            if (k <= enLaIzquierda) {
                actual = actual.izq; // Esta entre los menores, mismo k.
            } else {
                k -= enLaIzquierda + 1; // Descontamos la izquierda y el nodo.
                actual = actual.der;
            }
        }
        // Con k validado esto no puede pasar: los tamanios cierran siempre.
        throw new IllegalStateException("Tamanios de subarbol inconsistentes");
    }

    /** Demostracion: la mediana de 1..7 sin ordenar nada. */
    public static void main(String[] args) {
        AbbConKEsimo<Integer> arbol = new AbbConKEsimo<>();
        for (int valor : new int[] {4, 2, 6, 1, 3, 5, 7}) {
            arbol.insertar(valor);
        }
        System.out.println("tamanio    = " + arbol.tamanio());
        System.out.println("kEsimo(1)  = " + arbol.kEsimo(1) + " (el minimo)");
        System.out.println("kEsimo(4)  = " + arbol.kEsimo(4) + " (la mediana)");
        System.out.println("kEsimo(7)  = " + arbol.kEsimo(7) + " (el maximo)");
    }
}

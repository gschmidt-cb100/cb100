package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e07;

import java.util.ArrayList;
import java.util.List;

/**
 * AVL con quitar: la misma logica de borrado del ABB (hoja, un hijo,
 * dos hijos con sucesor), pero RE-BALANCEANDO al volver de la recursion.
 *
 * La diferencia clave con insertar: al insertar, una sola rotacion
 * arregla todo el camino; al quitar, borrar un nodo puede desbalancear
 * a VARIOS ancestros, por eso cada nodo del camino de vuelta pasa por
 * balancear(). Sigue siendo O(log n) porque el camino mide eso.
 */
public class ArbolAvlConQuitar<T extends Comparable<T>> {

    /** Nodo con altura cacheada. El valor no es final: dos hijos lo pisa. */
    private static class Nodo<T> {
        T valor;
        Nodo<T> izq;
        Nodo<T> der;
        int altura; // Una hoja mide 0.

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> raiz;
    private int tamanio;

    /** Cantidad de valores almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Altura del arbol: -1 si esta vacio. O(1). */
    public int altura() {
        return altura(raiz);
    }

    /** Valor de la raiz, o null si el arbol esta vacio. */
    public T raiz() {
        return (raiz == null) ? null : raiz.valor;
    }

    private static int altura(Nodo<?> nodo) {
        return (nodo == null) ? -1 : nodo.altura;
    }

    /** Factor de equilibrio: positivo = cargado a la izquierda. */
    private static int factorEquilibrio(Nodo<?> nodo) {
        return altura(nodo.izq) - altura(nodo.der);
    }

    private static <T> void actualizarAltura(Nodo<T> nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
    }

    /** Inserta el valor sin duplicados, re-balanceando. O(log n). */
    public void insertar(T valor) {
        raiz = insertar(raiz, valor);
    }

    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            tamanio++;
            return new Nodo<>(valor);
        }
        int comparacion = valor.compareTo(nodo.valor);
        if (comparacion < 0) {
            nodo.izq = insertar(nodo.izq, valor);
        } else if (comparacion > 0) {
            nodo.der = insertar(nodo.der, valor);
        } else {
            return nodo;
        }
        return balancear(nodo);
    }

    /**
     * Quita el valor re-balanceando el camino de vuelta.
     * Devuelve true si efectivamente se quito algo. O(log n).
     */
    public boolean quitar(T valor) {
        int tamanioAntes = tamanio;
        raiz = quitar(raiz, valor);
        return tamanio < tamanioAntes;
    }

    private Nodo<T> quitar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            return null; // No estaba.
        }
        int comparacion = valor.compareTo(nodo.valor);
        if (comparacion < 0) {
            nodo.izq = quitar(nodo.izq, valor);
        } else if (comparacion > 0) {
            nodo.der = quitar(nodo.der, valor);
        } else if (nodo.izq != null && nodo.der != null) {
            // Dos hijos: copiamos el sucesor y lo quitamos de la derecha.
            T sucesor = minimo(nodo.der);
            nodo.valor = sucesor;
            nodo.der = quitar(nodo.der, sucesor);
        } else {
            // Hoja o un solo hijo: el hijo (o null) sube directo.
            // Un subarbol AVL suelto ya esta balanceado, no hay que tocarlo.
            tamanio--;
            return (nodo.izq != null) ? nodo.izq : nodo.der;
        }
        // Aca esta la diferencia con el ABB comun: cada ancestro del
        // nodo borrado se revisa y se rota si quedo con FE 2 o -2.
        return balancear(nodo);
    }

    private T minimo(Nodo<T> nodo) {
        while (nodo.izq != null) {
            nodo = nodo.izq;
        }
        return nodo.valor;
    }

    /** Revisa el FE y aplica el caso que corresponda (LL, RR, LR, RL). */
    private Nodo<T> balancear(Nodo<T> nodo) {
        actualizarAltura(nodo);
        int fe = factorEquilibrio(nodo);
        if (fe > 1) {
            if (factorEquilibrio(nodo.izq) < 0) {
                nodo.izq = rotarIzquierda(nodo.izq); // Caso LR.
            }
            return rotarDerecha(nodo); // Caso LL.
        }
        if (fe < -1) {
            if (factorEquilibrio(nodo.der) > 0) {
                nodo.der = rotarDerecha(nodo.der); // Caso RL.
            }
            return rotarIzquierda(nodo); // Caso RR.
        }
        return nodo;
    }

    private Nodo<T> rotarDerecha(Nodo<T> p) {
        Nodo<T> q = p.izq;
        p.izq = q.der;
        q.der = p;
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }

    private Nodo<T> rotarIzquierda(Nodo<T> p) {
        Nodo<T> q = p.der;
        p.der = q.izq;
        q.izq = p;
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }

    /** Indica si el valor esta en el arbol. O(log n). */
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

    /** Devuelve los valores ordenados de menor a mayor. O(n). */
    public List<T> enOrden() {
        List<T> resultado = new ArrayList<>(tamanio);
        enOrden(raiz, resultado);
        return resultado;
    }

    private void enOrden(Nodo<T> nodo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izq, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.der, resultado);
    }

    /** Demostracion: borrar una hoja puede obligar a rotar un ancestro. */
    public static void main(String[] args) {
        ArbolAvlConQuitar<Integer> avl = new ArbolAvlConQuitar<>();
        for (int valor : new int[] {20, 10, 30, 40}) {
            avl.insertar(valor);
        }
        System.out.println("raiz antes  = " + avl.raiz() + ", altura " + avl.altura());
        avl.quitar(10); // El 20 queda FE -2: rota y sube el 30.
        System.out.println("quito 10 -> raiz " + avl.raiz() + ", altura " + avl.altura());
        System.out.println("enOrden = " + avl.enOrden());
    }
}

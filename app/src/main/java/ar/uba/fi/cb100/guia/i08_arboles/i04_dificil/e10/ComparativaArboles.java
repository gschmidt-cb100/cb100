package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e10;

import java.util.ArrayList;
import java.util.List;

/**
 * Comparativa experimental: que pasa al insertar 1..n EN ORDEN en un
 * ABB comun y en un AVL.
 *
 * El ABB comun sufre su peor caso: cada valor nuevo es mayor que todos
 * los anteriores, siempre cuelga a la derecha y el "arbol" degenera en
 * una lista de altura n-1. Buscar ahi es O(n), como en una lista.
 *
 * El AVL, con el MISMO orden de insercion, se va rotando solo y la
 * altura queda en O(log n): con n=100 mide 6 en vez de 99.
 *
 * Los dos devuelven el mismo enOrden: guardan lo mismo, lo que cambia
 * es la FORMA, y la forma es lo que define el costo de busqueda.
 */
public class ComparativaArboles {

    /** ABB minimo de enteros, sin balancear: el que degenera. */
    static class AbbMinimo {

        private static class Nodo {
            final int valor;
            Nodo izq;
            Nodo der;

            Nodo(int valor) {
                this.valor = valor;
            }
        }

        private Nodo raiz;

        /**
         * Insercion ITERATIVA sin duplicados. La hacemos iterativa a
         * proposito: con el arbol degenerado la version recursiva
         * apilaria n llamadas y con n grande revienta la pila.
         */
        void insertar(int valor) {
            if (raiz == null) {
                raiz = new Nodo(valor);
                return;
            }
            Nodo actual = raiz;
            while (true) {
                if (valor < actual.valor) {
                    if (actual.izq == null) {
                        actual.izq = new Nodo(valor);
                        return;
                    }
                    actual = actual.izq;
                } else if (valor > actual.valor) {
                    if (actual.der == null) {
                        actual.der = new Nodo(valor);
                        return;
                    }
                    actual = actual.der;
                } else {
                    return; // Duplicado.
                }
            }
        }

        /**
         * Altura medida SIN recursion, con un recorrido por niveles:
         * con el arbol degenerado, la version recursiva apilaria n
         * llamadas. Cuenta cuantos niveles hay y resta uno.
         */
        int altura() {
            List<Nodo> nivel = new ArrayList<>();
            if (raiz != null) {
                nivel.add(raiz);
            }
            int altura = -1; // Vacio: -1. Una hoja sola: 0.
            while (!nivel.isEmpty()) {
                altura++;
                List<Nodo> siguiente = new ArrayList<>();
                for (Nodo nodo : nivel) {
                    if (nodo.izq != null) {
                        siguiente.add(nodo.izq);
                    }
                    if (nodo.der != null) {
                        siguiente.add(nodo.der);
                    }
                }
                nivel = siguiente;
            }
            return altura;
        }

        /** En orden ITERATIVO con pila explicita, por el mismo motivo. */
        List<Integer> enOrden() {
            List<Integer> resultado = new ArrayList<>();
            java.util.Deque<Nodo> pila = new java.util.ArrayDeque<>();
            Nodo actual = raiz;
            while (actual != null || !pila.isEmpty()) {
                while (actual != null) {
                    pila.push(actual);
                    actual = actual.izq;
                }
                actual = pila.pop();
                resultado.add(actual.valor);
                actual = actual.der;
            }
            return resultado;
        }
    }

    /** AVL minimo de enteros: insertar con los cuatro casos de rotacion. */
    static class AvlMinimo {

        private static class Nodo {
            final int valor;
            Nodo izq;
            Nodo der;
            int altura; // Una hoja mide 0.

            Nodo(int valor) {
                this.valor = valor;
            }
        }

        private Nodo raiz;

        private static int altura(Nodo nodo) {
            return (nodo == null) ? -1 : nodo.altura;
        }

        private static void actualizarAltura(Nodo nodo) {
            nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        }

        private static int factorEquilibrio(Nodo nodo) {
            return altura(nodo.izq) - altura(nodo.der);
        }

        void insertar(int valor) {
            raiz = insertar(raiz, valor);
        }

        /** Recursivo tranquilo: la altura AVL es O(log n), la pila aguanta. */
        private Nodo insertar(Nodo nodo, int valor) {
            if (nodo == null) {
                return new Nodo(valor);
            }
            if (valor < nodo.valor) {
                nodo.izq = insertar(nodo.izq, valor);
            } else if (valor > nodo.valor) {
                nodo.der = insertar(nodo.der, valor);
            } else {
                return nodo; // Duplicado.
            }
            return balancear(nodo);
        }

        private Nodo balancear(Nodo nodo) {
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

        private Nodo rotarDerecha(Nodo p) {
            Nodo q = p.izq;
            p.izq = q.der;
            q.der = p;
            actualizarAltura(p);
            actualizarAltura(q);
            return q;
        }

        private Nodo rotarIzquierda(Nodo p) {
            Nodo q = p.der;
            p.der = q.izq;
            q.izq = p;
            actualizarAltura(p);
            actualizarAltura(q);
            return q;
        }

        int altura() {
            return altura(raiz);
        }

        List<Integer> enOrden() {
            List<Integer> resultado = new ArrayList<>();
            enOrden(raiz, resultado);
            return resultado;
        }

        private void enOrden(Nodo nodo, List<Integer> resultado) {
            if (nodo == null) {
                return;
            }
            enOrden(nodo.izq, resultado);
            resultado.add(nodo.valor);
            enOrden(nodo.der, resultado);
        }
    }

    /**
     * Inserta 1..n EN ORDEN en un ABB comun y en un AVL, y devuelve
     * {alturaABB, alturaAVL}. Con n=100 esperamos {99, 6}.
     */
    public static int[] alturas(int n) {
        AbbMinimo abb = new AbbMinimo();
        AvlMinimo avl = new AvlMinimo();
        for (int valor = 1; valor <= n; valor++) {
            abb.insertar(valor);
            avl.insertar(valor);
        }
        return new int[] {abb.altura(), avl.altura()};
    }

    /** Demostracion: la diferencia entre O(n) y O(log n) hecha arbol. */
    public static void main(String[] args) {
        for (int n : new int[] {10, 100, 1000}) {
            int[] resultado = alturas(n);
            System.out.println("n = " + n
                    + " -> altura ABB = " + resultado[0]
                    + ", altura AVL = " + resultado[1]);
        }
    }
}

package ar.uba.fi.cb100.material.i08_arboles;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Árbol AVL</b> (Adelson-Velsky y Landis, 1962): un ABB que se
 * <b>auto-balancea</b>. Tras cada inserción verifica el <b>factor de
 * equilibrio</b> de cada nodo (altura izq − altura der); si alguno queda fuera
 * de {−1, 0, 1}, lo corrige con <b>rotaciones</b>. Resultado: la altura se
 * mantiene O(log n) SIEMPRE, y con ella todas las operaciones.
 */
public class ArbolAVL<T extends Comparable<T>> {

    private static class Nodo<T> {
        T valor;
        Nodo<T> izquierdo, derecho;
        int altura;                      // se cachea para no recalcular
        Nodo(T valor) { this.valor = valor; }
    }

    private Nodo<T> raiz;
    private int cantidad;

    private int altura(Nodo<T> nodo) { return nodo == null ? -1 : nodo.altura; }

    /** Factor de equilibrio: altura(izq) − altura(der). AVL exige −1, 0 o 1. */
    private int factorDeEquilibrio(Nodo<T> nodo) {
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private void actualizarAltura(Nodo<T> nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    // ------------------------------------------------------------ rotaciones
    /** Rotación a la derecha: el hijo izquierdo sube, el nodo baja a su derecha. */
    private Nodo<T> rotarDerecha(Nodo<T> nodo) {
        Nodo<T> nuevaRaiz = nodo.izquierdo;
        nodo.izquierdo = nuevaRaiz.derecho;   // el subárbol "del medio" cambia de padre
        nuevaRaiz.derecho = nodo;
        actualizarAltura(nodo);               // primero el que quedó abajo
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    /** Rotación a la izquierda: simétrica. */
    private Nodo<T> rotarIzquierda(Nodo<T> nodo) {
        Nodo<T> nuevaRaiz = nodo.derecho;
        nodo.derecho = nuevaRaiz.izquierdo;
        nuevaRaiz.izquierdo = nodo;
        actualizarAltura(nodo);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    /** Re-balancea un nodo si su factor quedó en ±2 (los 4 casos). */
    private Nodo<T> balancear(Nodo<T> nodo) {
        actualizarAltura(nodo);
        int factor = factorDeEquilibrio(nodo);

        if (factor > 1) {                                  // pesado a la IZQUIERDA
            if (factorDeEquilibrio(nodo.izquierdo) < 0) {  // caso Izq-Der (LR):
                nodo.izquierdo = rotarIzquierda(nodo.izquierdo);  // 1.ª rotación
            }
            return rotarDerecha(nodo);                     // caso Izq-Izq (LL)
        }
        if (factor < -1) {                                 // pesado a la DERECHA
            if (factorDeEquilibrio(nodo.derecho) > 0) {    // caso Der-Izq (RL)
                nodo.derecho = rotarDerecha(nodo.derecho);
            }
            return rotarIzquierda(nodo);                   // caso Der-Der (RR)
        }
        return nodo;                                       // equilibrado: nada que hacer
    }

    // ------------------------------------------------------------ operaciones
    public void insertar(T valor) { raiz = insertar(raiz, valor); }

    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) { cantidad++; return new Nodo<>(valor); }
        int cmp = valor.compareTo(nodo.valor);
        if (cmp < 0)      nodo.izquierdo = insertar(nodo.izquierdo, valor);
        else if (cmp > 0) nodo.derecho   = insertar(nodo.derecho, valor);
        else return nodo;                          // sin duplicados
        return balancear(nodo);                    // al volver, cada nodo se revisa
    }

    public void quitar(T valor) { raiz = quitar(raiz, valor); }

    private Nodo<T> quitar(Nodo<T> nodo, T valor) {
        if (nodo == null) return null;
        int cmp = valor.compareTo(nodo.valor);
        if (cmp < 0)      nodo.izquierdo = quitar(nodo.izquierdo, valor);
        else if (cmp > 0) nodo.derecho   = quitar(nodo.derecho, valor);
        else {
            cantidad--;
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null)   return nodo.izquierdo;
            Nodo<T> sucesor = nodo.derecho;
            while (sucesor.izquierdo != null) sucesor = sucesor.izquierdo;
            nodo.valor = sucesor.valor;
            cantidad++;
            nodo.derecho = quitar(nodo.derecho, sucesor.valor);
        }
        return balancear(nodo);                    // también al quitar
    }

    public boolean contiene(T valor) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int cmp = valor.compareTo(actual.valor);
            if (cmp == 0) return true;
            actual = (cmp < 0) ? actual.izquierdo : actual.derecho;
        }
        return false;
    }

    public List<T> enOrden() {
        List<T> resultado = new ArrayList<>();
        enOrden(raiz, resultado);
        return resultado;
    }

    private void enOrden(Nodo<T> nodo, List<T> r) {
        if (nodo == null) return;
        enOrden(nodo.izquierdo, r);
        r.add(nodo.valor);
        enOrden(nodo.derecho, r);
    }

    public int tamanio() { return cantidad; }
    public int altura()  { return altura(raiz); }
    public T raiz()      { return raiz == null ? null : raiz.valor; }

    public static void main(String[] args) {
        // El caso que rompe al ABB: insertar ORDENADO.
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int v = 1; v <= 15; v++) avl.insertar(v);
        System.out.println("altura con 15 ordenados: " + avl.altura());  // 3 (¡no 14!)
        System.out.println("raíz: " + avl.raiz());                        // 8
        System.out.println(avl.enOrden());   // 1..15 ordenados

        // La rotación mínima: 10, 20, 30 fuerza una rotación izquierda en 10.
        ArbolAVL<Integer> chico = new ArbolAVL<>();
        chico.insertar(10); chico.insertar(20); chico.insertar(30);
        System.out.println("raíz tras 10,20,30: " + chico.raiz());        // 20
        System.out.println("altura: " + chico.altura());                  // 1
    }
}

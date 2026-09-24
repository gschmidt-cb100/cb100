package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * La lista enlazada de la API: {@code java.util.LinkedList}. Es una lista
 * <b>doblemente</b> enlazada, por eso se recorre en los dos sentidos y agrega
 * o quita en los dos extremos en O(1).
 *
 * <p>Lo que hay que ver: los recorridos con Iterator y ListIterator son
 * idénticos a los de ArrayList, porque las dos implementan {@code List}. Lo
 * que cambia es el <b>costo</b>: {@code get(i)} acá es O(n).
 */
public class DemoRecorridosLinkedList {

    public static void main(String[] args) {
        LinkedList<String> lista = new LinkedList<>(List.of("b", "c", "d"));

        // Los dos extremos en O(1): esto es lo que ArrayList no tiene.
        lista.addFirst("a");
        lista.addLast("e");
        System.out.println("addFirst/addLast:   " + lista);
        System.out.println("getFirst/getLast:   " + lista.getFirst() + " / " + lista.getLast());

        // 1. for-each: hacia adelante, siguiendo las flechas. O(n) en total.
        System.out.print("1. for-each:        ");
        for (String x : lista) {
            System.out.print(x + " ");
        }
        System.out.println();

        // 2. Iterator con remove.
        Iterator<String> it = lista.iterator();
        while (it.hasNext()) {
            if (it.next().equals("c")) {
                it.remove();
            }
        }
        System.out.println("2. it.remove(c):    " + lista);

        // 3. ListIterator hacia ATRÁS: posible porque cada nodo conoce al anterior.
        System.out.print("3. hacia atras:     ");
        ListIterator<String> li = lista.listIterator(lista.size());
        while (li.hasPrevious()) {
            System.out.print(li.previous() + " ");
        }
        System.out.println();

        // 4. ListIterator con add: inserta DONDE ESTÁ el cursor, en O(1).
        //    En ArrayList esta misma inserción correría todo lo que sigue.
        ListIterator<String> editor = lista.listIterator();
        while (editor.hasNext()) {
            if (editor.next().equals("b")) {
                editor.add("b2");
            }
        }
        System.out.println("4. add tras b:      " + lista);

        // 5. Con índice: FUNCIONA, pero cada get(i) camina desde un extremo.
        System.out.print("5. get(i):          ");
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(lista.get(i) + " ");
        }
        System.out.println("  <- O(n^2) en total: evitarlo");

        // 6. Como cola (Queue) y como pila (Deque): LinkedList implementa las dos.
        lista.removeFirst();
        lista.removeLast();
        System.out.println("6. removeFirst/Last:" + lista);
    }
}

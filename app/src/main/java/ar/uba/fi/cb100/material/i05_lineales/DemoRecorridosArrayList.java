package ar.uba.fi.cb100.material.i05_lineales;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Todas las formas de <b>recorrer</b> un {@code ArrayList}, con cuándo conviene
 * cada una. Sin Streams: eso es de la Unidad 12.
 *
 * <p>La lista de trabajo es {@code [10, 20, 30, 40, 50]}. Cada bloque la
 * recorre de una forma distinta e imprime lo mismo, para que se vea que son
 * equivalentes en el resultado y distintas en lo que permiten hacer.
 */
public class DemoRecorridosArrayList {

    public static void main(String[] args) {
        List<Integer> lista = new ArrayList<>(List.of(10, 20, 30, 40, 50));

        // 1. for con índice: cuando necesitás la POSICIÓN. Es el único que la da.
        System.out.print("1. for con indice:    ");
        for (int i = 0; i < lista.size(); i++) {
            System.out.print("[" + i + "]=" + lista.get(i) + " ");
        }
        System.out.println();

        // 2. for-each: cuando sólo querés los VALORES. El más legible. No permite
        //    modificar la lista mientras se recorre.
        System.out.print("2. for-each:          ");
        for (Integer valor : lista) {
            System.out.print(valor + " ");
        }
        System.out.println();

        // 3. Iterator: como el for-each pero permite ELIMINAR mientras se recorre.
        //    Es lo que el for-each usa por dentro.
        System.out.print("3. Iterator:          ");
        Iterator<Integer> it = lista.iterator();
        while (it.hasNext()) {
            Integer valor = it.next();
            System.out.print(valor + " ");
        }
        System.out.println();

        // 4. Recorrido inverso con índice: de atrás para adelante.
        System.out.print("4. inverso (indice):  ");
        for (int i = lista.size() - 1; i >= 0; i--) {
            System.out.print(lista.get(i) + " ");
        }
        System.out.println();

        // 5. ListIterator: para adelante Y para atrás, y permite set() y add().
        System.out.print("5. ListIterator atras:");
        ListIterator<Integer> li = lista.listIterator(lista.size());   // parado al final
        while (li.hasPrevious()) {
            System.out.print(" " + li.previous());
        }
        System.out.println();

        // 6. while con índice: cuando la condición de corte no es "hasta el final".
        System.out.print("6. while hasta > 30:  ");
        int i = 0;
        while (i < lista.size() && lista.get(i) <= 30) {
            System.out.print(lista.get(i) + " ");
            i++;
        }
        System.out.println();

        // ------------------------------------------------------------------
        //  Modificar mientras se recorre
        // ------------------------------------------------------------------

        // MAL: eliminar dentro de un for-each rompe con ConcurrentModificationException.
        System.out.print("7. eliminar en for-each: ");
        try {
            for (Integer valor : lista) {
                if (valor == 30) {
                    lista.remove(valor);          // <- acá explota en la vuelta siguiente
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException (como se esperaba)");
        }
        lista = new ArrayList<>(List.of(10, 20, 30, 40, 50));   // la dejamos como estaba

        // BIEN: con el Iterator, it.remove() borra el último devuelto por next().
        Iterator<Integer> borrador = lista.iterator();
        while (borrador.hasNext()) {
            if (borrador.next() == 30) {
                borrador.remove();
            }
        }
        System.out.println("8. it.remove() del 30:   " + lista);

        // BIEN: con ListIterator se puede reemplazar (set) y agregar (add) en el lugar.
        ListIterator<Integer> editor = lista.listIterator();
        while (editor.hasNext()) {
            int valor = editor.next();
            if (valor == 20) {
                editor.set(25);                   // reemplaza el 20
            }
            if (valor == 40) {
                editor.add(45);                   // inserta después del 40
            }
        }
        System.out.println("9. set(25) y add(45):    " + lista);

        // También BIEN: recorrer con índice de atrás para adelante y eliminar.
        // Al ir hacia atrás, borrar no desplaza las posiciones que faltan visitar.
        for (int k = lista.size() - 1; k >= 0; k--) {
            if (lista.get(k) % 2 == 1) {
                lista.remove(k);                  // remove(int): por POSICIÓN
            }
        }
        System.out.println("10. sin impares (atras): " + lista);

        // ------------------------------------------------------------------
        //  La trampa de remove con Integer
        // ------------------------------------------------------------------
        List<Integer> trampa = new ArrayList<>(List.of(5, 10, 1));
        trampa.remove(1);                          // remove(int): borra la POSICIÓN 1 -> el 10
        System.out.println("11. remove(1):           " + trampa);
        trampa.remove(Integer.valueOf(1));         // remove(Object): borra el VALOR 1
        System.out.println("12. remove(valueOf(1)):  " + trampa);
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s06;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Eg05m05 {

    public static List<Integer> insertarOrdenado(List<Integer> lista, Integer valor) {
        ListIterator<Integer> it = lista.listIterator();
        while (it.hasNext()) {
            if (it.next() > valor) {
                it.previous(); // Retroceder para insertar antes del elemento mayor
                it.add(valor);
                System.out.println(it.next()); // Imprimir el elemento insertado
                return lista;
            }
        }
        // Si llegamos al final, insertamos al final
        it.add(valor);
        return lista;
    }

    //5. Insertar ordenado. Insertá un valor en una List ordenada manteniéndola ordenada. Test.
    public static void main() {
        List<Integer> ordenada = new LinkedList<>(List.of(1, 2, 3, 5, 6));
        //List<Integer> ordenada = List.of(1, 2, 3, 5, 6);
        insertarOrdenado(ordenada, 4);
    }
}

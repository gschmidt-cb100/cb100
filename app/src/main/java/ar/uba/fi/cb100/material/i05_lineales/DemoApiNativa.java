package ar.uba.fi.cb100.material.i05_lineales;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * De nuestras implementaciones a las <b>estructuras nativas de la API de Java</b>.
 * Entender cómo funcionan por dentro (lo que hicimos a mano) te permite elegir
 * bien la de la biblioteca. <b>En los parciales, TPs y finales se usan las de la
 * API</b>, no las nuestras.
 */
public class DemoApiNativa {

    public static void main(String[] args) {
        // --- Lista sobre arreglo dinámico: ArrayList (nuestro VectorDinamico) ---
        List<String> arrayList = new ArrayList<>();
        arrayList.add("a"); arrayList.add("b"); arrayList.add("c");
        arrayList.add(1, "X");                 // insertar en posición
        arrayList.remove("a");                 // eliminar por valor
        System.out.println("ArrayList: " + arrayList
                + "  get(0)=" + arrayList.get(0) + "  contains(b)=" + arrayList.contains("b"));

        // --- Lista enlazada: LinkedList (nuestras listas enlazadas) ---
        List<Integer> linkedList = new LinkedList<>();
        linkedList.add(1); linkedList.add(2); linkedList.add(3);
        linkedList.addAll(List.of(4, 5));      // insertar todos
        System.out.println("LinkedList: " + linkedList);

        // --- Conjuntos: HashSet (sin orden), LinkedHashSet (orden de inserción),
        //     TreeSet (ordenado) ---
        Set<String> hashSet = new HashSet<>();
        hashSet.add("b"); hashSet.add("a"); hashSet.add("b");   // el segundo "b" se ignora
        System.out.println("HashSet (sin duplicados): " + hashSet + "  size=" + hashSet.size());

        Set<String> treeSet = new TreeSet<>();
        treeSet.addAll(List.of("pera", "manzana", "banana"));   // unión + orden natural
        System.out.println("TreeSet (ordenado): " + treeSet);

        Set<String> linkedHashSet = new LinkedHashSet<>(List.of("z", "a", "m"));
        System.out.println("LinkedHashSet (orden de inserción): " + linkedHashSet);
    }
}

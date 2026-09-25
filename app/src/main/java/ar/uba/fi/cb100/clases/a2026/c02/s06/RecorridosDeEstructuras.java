package ar.uba.fi.cb100.clases.a2026.c02.s06;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Clase de la semana 6: insertar y recorrer sobre las dos listas de la API.
 *
 * <p>Las dos clases implementan la MISMA interfaz {@code List}, así que el
 * código de los recorridos es idéntico. Lo que cambia es lo que hay adentro, y
 * por eso cambia el COSTO de cada operación:
 *
 * <pre>
 *   ArrayList:   [ a ][ b ][ c ][ d ][   ][   ]      un arreglo que crece
 *                  0    1    2    3                   get(i): directo, O(1)
 *
 *   LinkedList:  first -> [a] <-> [b] <-> [c] <-> [d] <- last
 *                nodos con anterior y siguiente      get(i): caminar i nodos, O(n)
 * </pre>
 *
 * <p><b>¿Hay lista doblemente enlazada en Java?</b> Sí: es {@code LinkedList}.
 * Cada nodo tiene {@code prev} y {@code next}, y la clase guarda {@code first}
 * y {@code last}. No existe una simplemente enlazada en la API. La versión
 * "a mano" está en el material: {@code ListaDoblementeEnlazada}.
 *
 * <p>Sin Streams: es de la Unidad 12.
 */
public class RecorridosDeEstructuras {

    // Tamaños para las mediciones. Son chicos a propósito: alcanza para que la
    // diferencia se vea y el programa termine en un par de segundos.
    private static final int N = 40_000;

    public static void main(String[] args) {
        ArrayList<String> vector = new ArrayList<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        vector.add("d");
        vector.add("e");

        //Recorrido 1
        for(int i = 0; i < vector.size(); i++){
            System.out.println(vector.get(i) + " en posición " + i);
        }

        //Recorrido 2: for each
        for(String letra : vector){
            System.out.println(letra + " en posición " + vector.indexOf(letra));
        }

        //Recorrido 3: Iterator
        Iterator<String> it = vector.iterator();
        while(it.hasNext()){
            String letra = it.next();
            System.out.println(letra + " en posición " + vector.indexOf(letra));
        }



        System.out.println("================ 1. INSERTAR ================");
        insertar(new ArrayList<>(), "ArrayList ");
        insertar(new LinkedList<>(), "LinkedList");
        soloLinkedList();

        System.out.println();
        System.out.println("================ 2. RECORRER ================");
        List<String> letras = List.of("a", "b", "c", "d", "e");
        recorrer(new ArrayList<>(letras), "ArrayList");
        recorrer(new LinkedList<>(letras), "LinkedList");

        System.out.println();
        System.out.println("================ 2b. INSERTAR ORDENADO CON ListIterator ================");
        insertarOrdenadoDemo(new ArrayList<>(List.of("ana", "carla", "elena", "gaston")), "ArrayList ");
        insertarOrdenadoDemo(new LinkedList<>(List.of("ana", "carla", "elena", "gaston")), "LinkedList");

        System.out.println();
        System.out.println("================ 3. LO QUE NO SE PUEDE ================");
        modificarDuranteForEach();

        System.out.println();
        System.out.println("================ 4. MEDIR (n = " + N + ") ================");
        medir();

        System.out.println();
        cuadroResumen();
    }

    // ------------------------------------------------------------------
    // 1. Insertar: mismo código para las dos, distinto costo
    // ------------------------------------------------------------------

    static void insertar(List<String> lista, String nombre) {
        lista.add("b");            // al final
        lista.add("d");            // al final
        lista.add(0, "a");         // al principio: índice 0
        lista.add(2, "c");         // en el medio: corre lo que sigue (ArrayList)
                                   //              o reengancha punteros (LinkedList)
        lista.add(lista.size(), "e"); // al final, con índice: vale i == size()
        System.out.println(nombre + " tras add(\"b\"), add(\"d\"), add(0,\"a\"), add(2,\"c\"), add(size,\"e\"): " + lista);
    }

    /** Estos métodos NO están en List: son de LinkedList (por implementar Deque). */
    static void soloLinkedList() {
        LinkedList<String> lista = new LinkedList<>(List.of("b", "c", "d"));
        lista.addFirst("a");       // O(1): sólo toca first
        lista.addLast("e");        // O(1): sólo toca last
        System.out.println("LinkedList addFirst(\"a\") / addLast(\"e\"): " + lista
                + "   getFirst=" + lista.getFirst() + " getLast=" + lista.getLast());
        System.out.println("   (ArrayList no tiene addFirst: add(0, x) corre TODOS los elementos, O(n))");
    }

    // ------------------------------------------------------------------
    // 2. Los cuatro recorridos
    // ------------------------------------------------------------------

    static void recorrer(List<String> lista, String nombre) {
        System.out.println("--- " + nombre + " " + lista + " ---");

        // (a) for clásico con índice. Tenés la posición i, podés saltear, ir
        //     de a dos, ir al revés. PERO usa get(i): O(1) en ArrayList,
        //     O(n) en LinkedList (camina desde el extremo más cercano cada vez).
        System.out.print("(a) for con índice:      ");
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(i + ":" + lista.get(i) + " ");
        }
        System.out.println();

        // (b) for-each. El más simple. No hay índice y NO se puede modificar la
        //     lista adentro (sección 3). Por dentro usa un Iterator, así que es
        //     O(n) en las dos.
        System.out.print("(b) for-each:            ");
        for (String x : lista) {
            System.out.print(x + " ");
        }
        System.out.println();

        // (c) Iterator explícito. Lo mismo que for-each, pero con la ventaja de
        //     poder ELIMINAR el elemento actual sin romper el recorrido.
        System.out.print("(c) Iterator + remove:   ");
        Iterator<String> it = lista.iterator();
        while (it.hasNext()) {
            String x = it.next();
            if (x.equals("c")) {
                it.remove();       // elimina el último devuelto por next()
            } else {
                System.out.print(x + " ");
            }
        }
        System.out.println("  -> " + lista);

        // (d) ListIterator. El más completo: adelante Y atrás, reemplazar (set),
        //     insertar (add) en la posición actual, y saber el índice.
        System.out.print("(d) ListIterator:        ");
        ListIterator<String> li = lista.listIterator();
        while (li.hasNext()) {
            String x = li.next();
            if (x.equals("b")) {
                li.set("B");       // reemplaza el último devuelto
                li.add("bb");      // inserta después, sin romper nada
            }
        }
        System.out.print("adelante: " + lista + "   atrás: ");
        while (li.hasPrevious()) {  // el cursor quedó al final: volvemos
            System.out.print(li.previous() + " ");
        }
        System.out.println("(índice " + li.nextIndex() + ")");
    }

    // ------------------------------------------------------------------
    // 2b. Recorrer con ListIterator e insertar en orden
    // ------------------------------------------------------------------

    /**
     * Inserta {@code x} en una lista YA ordenada, en el lugar que le corresponde,
     * recorriendo con un ListIterator. La lista sigue ordenada después.
     *
     * <p>La idea: avanzar mientras lo que viene sea menor que {@code x}. Cuando
     * aparece el primero que es mayor o igual, ya nos pasamos UN lugar, así que
     * retrocedemos con {@code previous()} y ahí insertamos con {@code add()}.
     * Si nunca aparece uno mayor, el cursor quedó al final y {@code add()}
     * agrega al final: también es correcto.
     *
     * <pre>
     *   lista:  [ana] [carla] [elena] [gaston]      x = "diego"
     *            ^ next()="ana"   &lt; diego, sigo
     *                  ^ next()="carla"  &lt; diego, sigo
     *                          ^ next()="elena"  &gt;= diego: me pasé
     *                  previous()  <- vuelvo un lugar (el cursor queda ANTES de "elena")
     *                  add("diego")            -> [ana, carla, diego, elena, gaston]
     * </pre>
     *
     * <p>Cuesta O(n) por el recorrido, y NO usa get(i): en LinkedList también
     * es O(n), no O(n²). Eso es lo que no se puede hacer con un for con índice.
     */
    static void insertarOrdenado(List<String> lista, String x) {
        ListIterator<String> it = lista.listIterator();
        while (it.hasNext()) {
            String actual = it.next();
            if (actual.compareTo(x) >= 0) {   // encontré el primero que va DESPUÉS de x
                it.previous();                // vuelvo: el cursor queda antes de "actual"
                break;
            }
        }
        it.add(x);                            // inserta en la posición del cursor
    }

    static void insertarOrdenadoDemo(List<String> lista, String nombre) {
        System.out.println(nombre + " inicial:            " + lista);
        insertarOrdenado(lista, "diego");
        System.out.println(nombre + " + \"diego\" (medio):   " + lista);
        insertarOrdenado(lista, "abel");
        System.out.println(nombre + " + \"abel\" (principio): " + lista);
        insertarOrdenado(lista, "zoe");
        System.out.println(nombre + " + \"zoe\" (final):      " + lista);
        insertarOrdenado(lista, "carla");
        System.out.println(nombre + " + \"carla\" (repetido): " + lista + "   <- queda antes de la que ya estaba");
    }

    // ------------------------------------------------------------------
    // 3. Modificar la lista dentro de un for-each
    // ------------------------------------------------------------------

    static void modificarDuranteForEach() {
        List<String> lista = new ArrayList<>(List.of("a", "b", "c"));
        try {
            for (String x : lista) {
                if (x.equals("a")) {
                    lista.remove(x);   // la lista cambia por afuera del iterador
                }
            }
            System.out.println("for-each + lista.remove(x): terminó sin excepción, lista = " + lista);
        } catch (ConcurrentModificationException e) {
            System.out.println("for-each + lista.remove(\"a\"): " + e.getClass().getSimpleName());
            System.out.println("   El iterador detecta que la lista cambió sin avisarle. Solución: Iterator.remove() (2c).");
            System.out.println("   Trampa extra: si se borra el ANTEÚLTIMO, el for-each termina sin excepción y sin visitar el último.");
        }

        // Con for e índice tampoco falla, pero SALTEA: al borrar el i, el i+1
        // pasa a ocupar la posición i y el i++ se lo come.
        List<String> otra = new ArrayList<>(List.of("a", "b", "b", "c"));
        for (int i = 0; i < otra.size(); i++) {
            if (otra.get(i).equals("b")) {
                otra.remove(i);
            }
        }
        System.out.println("for con índice borrando \"b\" de [a, b, b, c]: " + otra + "   <- quedó una b: salteó");
    }

    // ------------------------------------------------------------------
    // 4. Medir: el mismo código, tiempos muy distintos
    // ------------------------------------------------------------------

    static void medir() {
        System.out.printf("%-34s %12s %12s%n", "operación", "ArrayList", "LinkedList");
        System.out.printf("%-34s %12s %12s%n", "add(x) al final, n veces",
                ms(() -> agregarAlFinal(new ArrayList<>())),
                ms(() -> agregarAlFinal(new LinkedList<>())));
        System.out.printf("%-34s %12s %12s%n", "add(0, x) al principio, n veces",
                ms(() -> agregarAlPrincipio(new ArrayList<>())),
                ms(() -> agregarAlPrincipio(new LinkedList<>())));

        List<Integer> al = llena(new ArrayList<>());
        List<Integer> ll = llena(new LinkedList<>());
        System.out.printf("%-34s %12s %12s%n", "for con get(i)",
                ms(() -> sumaConIndice(al)), ms(() -> sumaConIndice(ll)));
        System.out.printf("%-34s %12s %12s%n", "for-each",
                ms(() -> sumaForEach(al)), ms(() -> sumaForEach(ll)));
        System.out.printf("%-34s %12s %12s%n", "Iterator.remove() de los pares",
                ms(() -> borrarParesConIterator(llena(new ArrayList<>()))),
                ms(() -> borrarParesConIterator(llena(new LinkedList<>()))));
        System.out.printf("%-34s %12s %12s%n", "remove(i) de los pares, con índice",
                ms(() -> borrarParesConIndice(llena(new ArrayList<>()))),
                ms(() -> borrarParesConIndice(llena(new LinkedList<>()))));
        System.out.println("(milisegundos; varía según la máquina, lo que importa es la proporción)");
    }

    static void agregarAlFinal(List<Integer> l)     { for (int i = 0; i < N; i++) l.add(i); }
    static void agregarAlPrincipio(List<Integer> l) { for (int i = 0; i < N; i++) l.add(0, i); }
    static List<Integer> llena(List<Integer> l)     { agregarAlFinal(l); return l; }

    static long sumaConIndice(List<Integer> l) {
        long s = 0;
        for (int i = 0; i < l.size(); i++) s += l.get(i);   // LinkedList: O(n) por get -> O(n²)
        return s;
    }

    static long sumaForEach(List<Integer> l) {
        long s = 0;
        for (int x : l) s += x;                              // O(n) en las dos
        return s;
    }

    static void borrarParesConIterator(List<Integer> l) {
        Iterator<Integer> it = l.iterator();
        while (it.hasNext()) {
            if (it.next() % 2 == 0) it.remove();             // LinkedList: O(1) por borrado
        }                                                    // ArrayList: corre el resto, O(n) por borrado
    }

    static void borrarParesConIndice(List<Integer> l) {
        for (int i = l.size() - 1; i >= 0; i--) {            // de atrás para adelante: no saltea
            if (l.get(i) % 2 == 0) l.remove(i);              // LinkedList: get O(n) + remove O(n)
        }
    }

    /** Ejecuta la acción y devuelve el tiempo en milisegundos, formateado. */
    static String ms(Runnable accion) {
        long inicio = System.nanoTime();
        accion.run();
        return String.format("%.1f ms", (System.nanoTime() - inicio) / 1_000_000.0);
    }

    // ------------------------------------------------------------------
    // 5. Cuadro
    // ------------------------------------------------------------------

    static void cuadroResumen() {
        System.out.println("================ RESUMEN ================");
        System.out.println("Recorrido       | Ventajas                                   | Desventajas");
        System.out.println("----------------+--------------------------------------------+-----------------------------------------------");
        System.out.println("for con índice  | tenés i; saltear, ir al revés, de a dos    | usa get(i): O(n²) en LinkedList; borrar saltea");
        System.out.println("for-each        | el más corto y legible; O(n) en las dos    | sin índice; modificar la lista lanza excepción");
        System.out.println("Iterator        | remove() seguro mientras recorrés          | sólo hacia adelante; sin índice; más verboso");
        System.out.println("ListIterator    | adelante y atrás, set, add, nextIndex      | sólo en List; es el más largo de escribir");
        System.out.println();
        System.out.println("Estructura      | Ventajas                                   | Desventajas");
        System.out.println("----------------+--------------------------------------------+-----------------------------------------------");
        System.out.println("ArrayList       | get(i) O(1); memoria compacta; la elección | insertar/borrar al principio o medio corre todo");
        System.out.println("                | por defecto                                | (O(n)); crecer copia el arreglo");
        System.out.println("LinkedList      | addFirst/removeFirst O(1); borrar con      | get(i) O(n); cada nodo gasta 2 punteros más;");
        System.out.println("                | Iterator O(1); es doblemente enlazada      | recorrer con índice es una trampa");
    }
}

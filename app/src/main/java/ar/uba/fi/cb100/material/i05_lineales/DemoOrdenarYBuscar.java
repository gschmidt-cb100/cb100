package ar.uba.fi.cb100.material.i05_lineales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Cómo se <b>ordena</b> y cómo se <b>busca</b> en un {@code ArrayList}, y qué
 * cuesta cada cosa. Sin Streams.
 */
public class DemoOrdenarYBuscar {

    /** Una clase de valor común, para mostrar ordenar por un atributo. */
    record Alumno(String nombre, int padron, double promedio) {
    }

    public static void main(String[] args) {

        // ==================================================================
        //  ORDENAR
        // ==================================================================
        List<Integer> numeros = new ArrayList<>(List.of(42, 7, 19, 3, 25));
        System.out.println("original:              " + numeros);

        // 1. Orden natural (el compareTo del tipo). O(n log n): Java usa TimSort,
        //    un MergeSort mejorado. Es ESTABLE: los iguales conservan su orden.
        Collections.sort(numeros);
        System.out.println("Collections.sort:      " + numeros);

        // 2. Lo mismo, como método de la lista (desde Java 8). null = orden natural.
        numeros.sort(null);
        System.out.println("lista.sort(null):      " + numeros);

        // 3. Orden inverso.
        numeros.sort(Comparator.reverseOrder());
        System.out.println("reverseOrder:          " + numeros);

        // 4. Por un criterio propio: un Comparator dice quién va antes.
        List<Alumno> alumnos = new ArrayList<>(List.of(
                new Alumno("Bruno", 39876, 7.5),
                new Alumno("Ana", 41234, 9.0),
                new Alumno("Carla", 42001, 7.5),
                new Alumno("Diego", 40555, 8.2)));

        alumnos.sort(Comparator.comparing(Alumno::nombre));
        System.out.println("por nombre:            " + nombres(alumnos));

        alumnos.sort(Comparator.comparingDouble(Alumno::promedio).reversed());
        System.out.println("por promedio desc:     " + nombres(alumnos));

        // 5. Dos criterios: promedio descendente, y a igual promedio, nombre.
        alumnos.sort(Comparator.comparingDouble(Alumno::promedio).reversed()
                .thenComparing(Alumno::nombre));
        System.out.println("promedio, luego nombre:" + nombres(alumnos));

        // 6. Invertir sin ordenar, y mezclar.
        Collections.reverse(numeros);
        System.out.println("reverse:               " + numeros);

        // ==================================================================
        //  BUSCAR
        // ==================================================================
        List<Integer> datos = new ArrayList<>(List.of(3, 7, 19, 25, 42));   // ordenada

        // 7. contains / indexOf: búsqueda LINEAL, O(n). No necesita orden.
        System.out.println("contains(19):          " + datos.contains(19));
        System.out.println("indexOf(25):           " + datos.indexOf(25));
        System.out.println("indexOf(99):           " + datos.indexOf(99) + "   (-1 = no está)");

        // 8. binarySearch: O(log n), pero SOLO sobre una lista ORDENADA.
        //    Devuelve la posición si está, y un negativo si no está:
        //    -(punto de inserción) - 1, o sea, dónde habría que insertarlo.
        System.out.println("binarySearch(25):      " + Collections.binarySearch(datos, 25));
        int noEsta = Collections.binarySearch(datos, 20);
        System.out.println("binarySearch(20):      " + noEsta
                + "   -> iría en la posición " + (-noEsta - 1));

        // 9. binarySearch con Comparator: la lista tiene que estar ordenada con ESE
        //    comparator, si no el resultado es basura sin avisar.
        alumnos.sort(Comparator.comparingInt(Alumno::padron));
        Alumno buscado = new Alumno("?", 41234, 0);
        int pos = Collections.binarySearch(alumnos, buscado, Comparator.comparingInt(Alumno::padron));
        System.out.println("padrón 41234:          posición " + pos + " -> " + alumnos.get(pos).nombre());

        // 10. Búsqueda del máximo y del mínimo: O(n), sin ordenar.
        System.out.println("max / min:             " + Collections.max(datos) + " / " + Collections.min(datos));
    }

    private static String nombres(List<Alumno> alumnos) {
        StringBuilder texto = new StringBuilder("[");
        for (int i = 0; i < alumnos.size(); i++) {
            if (i > 0) {
                texto.append(", ");
            }
            texto.append(alumnos.get(i).nombre()).append(' ').append(alumnos.get(i).promedio());
        }
        return texto.append("]").toString();
    }
}

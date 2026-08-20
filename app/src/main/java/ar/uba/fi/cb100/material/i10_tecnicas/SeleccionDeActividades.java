package ar.uba.fi.cb100.material.i10_tecnicas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <b>Greedy</b> que SÍ es óptimo: selección de actividades. Dadas actividades
 * con inicio y fin, elegir la mayor cantidad posible sin solapamientos.
 * <p>
 * La elección golosa correcta: tomar siempre la actividad que TERMINA antes
 * (deja el máximo lugar libre para las demás). Se demuestra por intercambio:
 * cualquier solución óptima se puede transformar en la greedy sin perder
 * actividades.
 */
public final class SeleccionDeActividades {

    private SeleccionDeActividades() {}

    public record Actividad(String nombre, int inicio, int fin) {}

    public static List<Actividad> seleccionar(List<Actividad> actividades) {
        List<Actividad> porFin = new ArrayList<>(actividades);
        porFin.sort(Comparator.comparingInt(Actividad::fin));   // 1) ordenar por FIN

        List<Actividad> elegidas = new ArrayList<>();
        int finDeLaUltima = Integer.MIN_VALUE;
        for (Actividad a : porFin) {                            // 2) tomar la que
            if (a.inicio() >= finDeLaUltima) {                  //    entre sin solapar
                elegidas.add(a);
                finDeLaUltima = a.fin();
            }                                                   // 3) NUNCA volver atrás
        }
        return elegidas;
    }

    public static void main(String[] args) {
        List<Actividad> agenda = List.of(
                new Actividad("A", 1, 3), new Actividad("B", 2, 5),
                new Actividad("C", 4, 7), new Actividad("D", 1, 8),
                new Actividad("E", 5, 9), new Actividad("F", 8, 10));
        List<Actividad> elegidas = seleccionar(agenda);
        for (Actividad a : elegidas) System.out.print(a.nombre() + " ");
        System.out.println();                     // A C F  (3 actividades: el máximo)
    }
}

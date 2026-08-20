package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * e07: selección de actividades. Dado un conjunto de actividades con
 * horario de inicio y fin, elegir la mayor cantidad posible de
 * actividades que no se solapen (un aula, una sola actividad por vez).
 *
 * <p><b>Técnica: greedy (algoritmo goloso), óptimo con demostración.</b>
 * El criterio ganador es ordenar por <i>fin más temprano</i> y tomar cada
 * actividad compatible con la última elegida. Terminar temprano deja el
 * mayor tiempo libre posible para el resto: si una solución óptima
 * eligiera otra primera actividad, cambiarla por la que termina antes
 * sigue siendo válido y no pierde actividades (argumento de intercambio).
 * Ojo: otros criterios golosos tentadores, como "la más corta" o "la que
 * empieza antes", NO son óptimos. Costo: O(n log n) por el ordenamiento.</p>
 */
public final class SeleccionActividades {

    private SeleccionActividades() {
    }

    /**
     * Devuelve un subconjunto de máxima cantidad de actividades
     * mutuamente compatibles, elegidas por fin más temprano.
     *
     * @param actividades lista de actividades (no se modifica)
     * @return las actividades seleccionadas, en orden de realización
     */
    public static List<Actividad> seleccionar(List<Actividad> actividades) {
        List<Actividad> porFin = new ArrayList<>(actividades);
        porFin.sort(Comparator.comparingInt(Actividad::fin));

        List<Actividad> elegidas = new ArrayList<>();
        int finUltima = Integer.MIN_VALUE;
        for (Actividad a : porFin) {
            if (a.inicio() >= finUltima) {
                // Compatible con la última elegida: la tomamos (decisión
                // golosa) y nunca la reconsideramos.
                elegidas.add(a);
                finUltima = a.fin();
            }
        }
        return elegidas;
    }

    public static void main(String[] args) {
        List<Actividad> agenda = List.of(
                new Actividad("A", 1, 3),
                new Actividad("B", 2, 5),
                new Actividad("C", 4, 7),
                new Actividad("D", 1, 8),
                new Actividad("E", 5, 9),
                new Actividad("F", 8, 10));
        System.out.println("Agenda: " + agenda);
        System.out.println("Seleccionadas: " + seleccionar(agenda));
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e06;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * e06: agenda de eventos que se lista SIEMPRE en orden cronológico y
 * responde consultas por rango de fechas.
 * <p>
 * <b>Elección de estructura:</b> {@link TreeMap} fecha → títulos. Las fechas
 * en formato ISO ("2026-08-11") ordenadas como texto quedan en orden
 * cronológico, y un árbol autobalanceado (U8) mantiene las claves SIEMPRE
 * ordenadas: insertar cuesta O(log n) y recorrer en orden sale gratis, sin
 * re-ordenar en cada listado. Lo decisivo es la consulta por RANGO:
 * {@code subMap(desde, hasta)} devuelve una vista en O(log n) + O(k). Un
 * {@code HashMap} (U7) tiene {@code get} O(1) pero sus claves no tienen
 * ningún orden: cada listado o rango obligaría a ordenar todo, O(n log n).
 * Cada fecha guarda una lista porque puede haber varios eventos el mismo día.
 */
public class Agenda {

    private final TreeMap<String, List<String>> eventosPorFecha = new TreeMap<>();

    /**
     * Agrega un evento. O(log n) por la búsqueda en el árbol.
     *
     * @param fecha  fecha en formato ISO, por ejemplo "2026-08-11"
     * @param titulo título del evento
     */
    public void agregar(String fecha, String titulo) {
        eventosPorFecha.computeIfAbsent(fecha, f -> new ArrayList<>()).add(titulo);
    }

    /**
     * Todos los eventos como "fecha: título", siempre en orden cronológico
     * (el TreeMap ya itera sus claves ordenadas).
     */
    public List<String> listado() {
        return aLista(eventosPorFecha);
    }

    /**
     * Eventos con fecha en el rango [desde, hasta], ambos inclusive.
     * {@code subMap} es una VISTA del árbol: O(log n) para ubicar los bordes
     * y O(k) para recorrer los k eventos del rango, sin copiar el resto.
     *
     * @param desde primera fecha incluida (formato ISO)
     * @param hasta última fecha incluida (formato ISO)
     * @return eventos del rango como "fecha: título", en orden cronológico
     */
    public List<String> proximos(String desde, String hasta) {
        return aLista(eventosPorFecha.subMap(desde, true, hasta, true));
    }

    private static List<String> aLista(Map<String, List<String>> porFecha) {
        return porFecha.entrySet().stream()
                .flatMap(e -> e.getValue().stream()
                        .map(titulo -> e.getKey() + ": " + titulo))
                .toList();
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.agregar("2026-09-15", "Parcial");
        agenda.agregar("2026-08-11", "Clase de streams");
        agenda.agregar("2026-08-25", "Entrega TP");
        agenda.agregar("2026-08-11", "Consultas");
        System.out.println("Todo:   " + agenda.listado());
        System.out.println("Agosto: " + agenda.proximos("2026-08-01", "2026-08-31"));
    }
}

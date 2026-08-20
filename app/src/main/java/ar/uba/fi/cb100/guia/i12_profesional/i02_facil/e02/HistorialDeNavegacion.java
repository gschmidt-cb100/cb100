package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e02;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * e02: historial de un navegador con "visitar" y "volver".
 * <p>
 * <b>Elección de estructura:</b> {@link ArrayDeque} usado como pila (LIFO).
 * "Volver" siempre deshace la <i>última</i> visita, o sea que sólo tocamos
 * un extremo: {@code push}, {@code pop} y {@code peek} son O(1) en las
 * puntas de un deque (U5). {@code ArrayDeque} es la implementación
 * recomendada en Java para pilas (mejor que la clase legada {@code Stack},
 * que sincroniza cada operación sin necesidad, y que {@code LinkedList},
 * que paga un nodo por elemento).
 * <p>
 * <b>Decisión documentada:</b> si se pide {@code volver()} sin páginas
 * anteriores, el navegador se queda donde está (no lanza excepción), igual
 * que el botón "atrás" deshabilitado de un navegador real. Y
 * {@code paginaActual()} sin visitas devuelve {@code null}.
 */
public class HistorialDeNavegacion {

    // El tope de la pila es siempre la página actual.
    private final Deque<String> historial = new ArrayDeque<>();

    /** Navega a una nueva página, que pasa a ser la actual. O(1). */
    public void visitar(String url) {
        historial.push(url);
    }

    /**
     * Vuelve a la página anterior, si existe. Si no hay historial para
     * retroceder, se queda en la página actual (misma decisión que el botón
     * "atrás" gris de un navegador).
     *
     * @return la página actual después de volver, o {@code null} si nunca
     *         se visitó nada
     */
    public String volver() {
        if (historial.size() > 1) {
            historial.pop();   // descarta la actual; la anterior queda en el tope
        }
        return paginaActual();
    }

    /** @return la página actual, o {@code null} si no se visitó ninguna */
    public String paginaActual() {
        return historial.peek();
    }

    public static void main(String[] args) {
        HistorialDeNavegacion navegador = new HistorialDeNavegacion();
        navegador.visitar("uba.ar");
        navegador.visitar("fi.uba.ar");
        navegador.visitar("campus.fi.uba.ar");
        System.out.println("Actual:  " + navegador.paginaActual()); // campus.fi.uba.ar
        System.out.println("Volver:  " + navegador.volver());       // fi.uba.ar
        System.out.println("Volver:  " + navegador.volver());       // uba.ar
        System.out.println("Volver:  " + navegador.volver());       // uba.ar (no hay más atrás)
    }
}

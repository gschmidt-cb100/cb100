package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e08;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * e08: simulador de pull request — las reglas de merge de un repo real.
 *
 * <p>Un PR puede mergearse si tiene al menos DOS aprobaciones de personas
 * distintas del autor y todos los checks de CI en verde.</p>
 *
 * <p><b>Elección de estructuras:</b> las aprobaciones van en un
 * {@code HashSet} (U7): si la misma persona aprieta "Approve" cinco veces
 * cuenta UNA — exactamente la semántica de conjunto, con add/contains O(1).
 * Los checks van en un {@code LinkedHashMap} nombre → estado: búsqueda O(1)
 * y conserva el orden en que se registraron, como los lista GitHub.</p>
 *
 * <p><b>Decisión documentada:</b> {@code aprobar(autor)} se IGNORA en
 * silencio en vez de lanzar excepción: auto-aprobarse no es un error de
 * programación sino una acción sin efecto, igual que en GitHub, donde el
 * botón directamente no está. Lanzar sería la otra opción defendible;
 * lo importante es elegir una y documentarla.</p>
 */
public final class PullRequest {

    private final String autor;
    private final Set<String> aprobaciones = new HashSet<>();
    private final Map<String, Boolean> checks = new LinkedHashMap<>();

    public PullRequest(String autor) {
        this.autor = autor;
    }

    /** Registra una aprobación. La del propio autor se ignora en silencio. */
    public void aprobar(String quien) {
        if (!autor.equals(quien)) {
            aprobaciones.add(quien);                // el Set deduplica solo
        }
    }

    /** Registra (o actualiza) el resultado de un check de CI. */
    public void registrarCheck(String nombre, boolean paso) {
        checks.put(nombre, paso);
    }

    /** ≥ 2 aprobaciones de personas distintas del autor y todo el CI verde. */
    public boolean puedeMergearse() {
        boolean todosLosChecksEnVerde = checks.values().stream()
                .allMatch(Boolean::booleanValue);
        return aprobaciones.size() >= 2 && todosLosChecksEnVerde;
    }

    public String autor() {
        return autor;
    }

    /** Vista de sólo lectura, para inspeccionar sin poder romper (U3). */
    public Set<String> aprobaciones() {
        return Collections.unmodifiableSet(aprobaciones);
    }

    public static void main(String[] args) {
        PullRequest pr = new PullRequest("Ana");
        pr.registrarCheck("build", true);
        pr.registrarCheck("tests", true);

        pr.aprobar("Ana");                          // se ignora: es la autora
        pr.aprobar("Beto");
        System.out.println(pr.puedeMergearse());    // false: falta una aprobación
        pr.aprobar("Beto");                         // repetida: sigue contando una
        System.out.println(pr.puedeMergearse());    // false
        pr.aprobar("Carla");
        System.out.println(pr.puedeMergearse());    // true

        pr.registrarCheck("lint", false);
        System.out.println(pr.puedeMergearse());    // false: CI en rojo
    }
}

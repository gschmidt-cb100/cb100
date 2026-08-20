package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e09;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * e09: pipeline de CI en miniatura — etapas en orden, corte en la primera falla.
 *
 * <p>Un pipeline real (GitHub Actions, GitLab CI) corre compilar → testear →
 * empaquetar y, si una etapa falla, las siguientes NI SE EJECUTAN: no tiene
 * sentido empaquetar lo que no compila, y cada etapa cuesta tiempo y plata.</p>
 *
 * <p><b>Elección de estructuras:</b> {@code ArrayList} de etapas (U5): sólo
 * agregamos al final y recorremos en orden — el caso ideal del arreglo
 * dinámico, con add O(1) amortizado. Cada etapa guarda un
 * {@code Supplier<Boolean>}: la acción NO se ejecuta al agregarla sino
 * recién cuando el pipeline la llama — evaluación perezosa, la misma idea
 * que los streams de esta unidad.</p>
 */
public final class PipelineDeCi {

    /** Una etapa: nombre y acción diferida que dice si pasó. */
    private record Etapa(String nombre, Supplier<Boolean> accion) {}

    private final List<Etapa> etapas = new ArrayList<>();

    /** Agrega una etapa al final. La acción no se ejecuta todavía. */
    public void agregarEtapa(String nombre, Supplier<Boolean> accion) {
        etapas.add(new Etapa(nombre, accion));
    }

    /**
     * Corre las etapas en orden y devuelve el reporte: "nombre: OK" o
     * "nombre: FALLA". En la primera falla CORTA: las etapas siguientes
     * no aparecen en el reporte ni se ejecutan.
     */
    public List<String> ejecutar() {
        List<String> reporte = new ArrayList<>();
        for (Etapa etapa : etapas) {
            boolean paso = etapa.accion().get();    // recién ACÁ se ejecuta
            reporte.add(etapa.nombre() + (paso ? ": OK" : ": FALLA"));
            if (!paso) {
                break;                              // corte: lo que sigue ni corre
            }
        }
        return reporte;
    }

    public static void main(String[] args) {
        PipelineDeCi pipeline = new PipelineDeCi();
        pipeline.agregarEtapa("compilar", () -> true);
        pipeline.agregarEtapa("tests", () -> false);
        pipeline.agregarEtapa("empaquetar", () -> {
            System.out.println("esto no debería verse");
            return true;
        });

        pipeline.ejecutar().forEach(System.out::println);
        // compilar: OK
        // tests: FALLA        (y "empaquetar" ni se ejecutó)
    }
}

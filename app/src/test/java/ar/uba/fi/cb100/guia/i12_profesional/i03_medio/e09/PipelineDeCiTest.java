package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PipelineDeCiTest {

    @Test
    @DisplayName("Si todo pasa, el reporte tiene todas las etapas en OK")
    void todoEnVerde() {
        PipelineDeCi pipeline = new PipelineDeCi();
        pipeline.agregarEtapa("compilar", () -> true);
        pipeline.agregarEtapa("tests", () -> true);
        pipeline.agregarEtapa("empaquetar", () -> true);

        assertEquals(List.of("compilar: OK", "tests: OK", "empaquetar: OK"),
                pipeline.ejecutar());
    }

    @Test
    @DisplayName("La primera falla corta el pipeline y las siguientes no corren")
    void corteEnLaPrimeraFalla() {
        AtomicInteger vecesQueCorrio = new AtomicInteger(0);
        PipelineDeCi pipeline = new PipelineDeCi();
        pipeline.agregarEtapa("compilar", () -> true);
        pipeline.agregarEtapa("tests", () -> false);
        pipeline.agregarEtapa("empaquetar", () -> {
            vecesQueCorrio.incrementAndGet();       // NO debería pasar por acá
            return true;
        });

        assertEquals(List.of("compilar: OK", "tests: FALLA"), pipeline.ejecutar());
        assertEquals(0, vecesQueCorrio.get(),
                "la etapa posterior a la falla no debe ejecutarse");
    }

    @Test
    @DisplayName("Las acciones no se ejecutan al agregarlas, sólo en ejecutar()")
    void evaluacionPerezosa() {
        AtomicInteger ejecuciones = new AtomicInteger(0);
        PipelineDeCi pipeline = new PipelineDeCi();
        pipeline.agregarEtapa("compilar", () -> {
            ejecuciones.incrementAndGet();
            return true;
        });

        assertEquals(0, ejecuciones.get(), "agregar no ejecuta nada");
        pipeline.ejecutar();
        assertEquals(1, ejecuciones.get());
        pipeline.ejecutar();                        // se puede volver a correr
        assertEquals(2, ejecuciones.get());
    }

    @Test
    @DisplayName("Un pipeline vacío devuelve un reporte vacío")
    void pipelineVacio() {
        assertTrue(new PipelineDeCi().ejecutar().isEmpty());
    }
}

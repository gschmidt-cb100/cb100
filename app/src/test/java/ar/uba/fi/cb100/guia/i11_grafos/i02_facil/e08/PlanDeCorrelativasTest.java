package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e08;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.OrdenTopologico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanDeCorrelativasTest {

    private PlanDeCorrelativas correlativas;

    @BeforeEach
    void armarPlan() {
        correlativas = new PlanDeCorrelativas();
    }

    @Test
    @DisplayName("Para CADA correlativa u->v, u aparece antes que v en el orden")
    void respetaTodasLasCorrelativas() {
        Grafo plan = correlativas.grafo();
        List<Integer> orden = correlativas.ordenDeCursada();

        // posicion[m] = en que lugar del orden quedo la materia m
        int[] posicion = new int[plan.cantidadDeVertices()];
        for (int i = 0; i < orden.size(); i++) {
            posicion[orden.get(i)] = i;
        }
        for (int u = 0; u < plan.cantidadDeVertices(); u++) {
            for (Grafo.Arista arista : plan.vecinos(u)) {
                assertTrue(posicion[u] < posicion[arista.destino()],
                        PlanDeCorrelativas.MATERIAS[u] + " deberia ir antes que "
                                + PlanDeCorrelativas.MATERIAS[arista.destino()]);
            }
        }
    }

    @Test
    @DisplayName("El orden incluye las 6 materias, cada una exactamente una vez")
    void incluyeTodasLasMaterias() {
        List<Integer> orden = correlativas.ordenDeCursada();
        assertEquals(6, orden.size());
        for (int materia = 0; materia < 6; materia++) {
            assertTrue(orden.contains(materia),
                    "falta la materia " + PlanDeCorrelativas.MATERIAS[materia]);
        }
    }

    @Test
    @DisplayName("El plan no tiene ciclos: siempre existe un orden de cursada")
    void sinCiclos() {
        assertFalse(OrdenTopologico.tieneCiclo(correlativas.grafo()));
        assertDoesNotThrow(() -> correlativas.ordenDeCursada());
    }
}

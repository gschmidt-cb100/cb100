package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e07;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KahnConDesempateTest {

    @Test
    @DisplayName("Con varios órdenes válidos, elige siempre el menor número listo")
    void desempatePorMenorNumero() {
        // DAG con tres fuentes (0, 1 y 4) y varios órdenes topológicos válidos.
        Grafo dag = new Grafo(6, true);
        dag.agregarArista(0, 2);
        dag.agregarArista(1, 2);
        dag.agregarArista(2, 3);
        dag.agregarArista(4, 3);
        dag.agregarArista(1, 5);

        // [4, 1, 0, 2, 5, 3] también sería topológico, pero el desempate
        // fuerza EXACTAMENTE este (el lexicográficamente menor):
        assertEquals(List.of(0, 1, 2, 4, 3, 5), KahnConDesempate.ordenar(dag));
    }

    @Test
    @DisplayName("El resultado es reproducible: dos corridas dan lo mismo")
    void deterministico() {
        Grafo dag = new Grafo(5, true);
        dag.agregarArista(4, 0);
        dag.agregarArista(3, 0);
        dag.agregarArista(3, 1);
        dag.agregarArista(0, 2);

        assertEquals(KahnConDesempate.ordenar(dag), KahnConDesempate.ordenar(dag));
        assertEquals(List.of(3, 1, 4, 0, 2), KahnConDesempate.ordenar(dag));
    }

    @Test
    @DisplayName("Sin aristas, el orden es simplemente 0, 1, 2, ...")
    void sinAristas() {
        assertEquals(List.of(0, 1, 2, 3), KahnConDesempate.ordenar(new Grafo(4, true)));
    }

    @Test
    @DisplayName("Con un ciclo no hay orden: lanza IllegalStateException")
    void conCiclo() {
        Grafo ciclo = new Grafo(3, true);
        ciclo.agregarArista(0, 1);
        ciclo.agregarArista(1, 2);
        ciclo.agregarArista(2, 0);

        assertThrows(IllegalStateException.class, () -> KahnConDesempate.ordenar(ciclo));
    }

    @Test
    @DisplayName("Con un grafo no dirigido lanza IllegalArgumentException")
    void noDirigido() {
        assertThrows(IllegalArgumentException.class,
                () -> KahnConDesempate.ordenar(new Grafo(2, false)));
    }
}

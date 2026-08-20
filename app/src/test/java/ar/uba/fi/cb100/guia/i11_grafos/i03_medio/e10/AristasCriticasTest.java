package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e10;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AristasCriticasTest {

    /** La red de fibra del apunte: A=0, B=1, C=2, D=3, E=4, F=5. */
    private Grafo redDeFibra() {
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7

        return red;
    }

    @Test
    @DisplayName("En la red de fibra, las 5 aristas del MST son críticas")
    void todasLasDelMstSonCriticas() {
        // Verificado a mano (ver javadoc de AristasCriticas): el MST es
        // {B-C 1, A-B 2, E-F 3, B-D 4, D-E 5} (costo 15) y cada reemplazo
        // posible es estrictamente más caro: sin B-C entra A-C 3 (17), sin
        // A-B entra A-C 3 (16), sin E-F sólo queda D-F 7 (19), sin B-D entra
        // C-E 6 (17) y sin D-E entra C-E 6 (16).
        List<Tramo> criticas = AristasCriticas.encontrar(redDeFibra());

        assertEquals(5, criticas.size());
        assertEquals(List.of(
                new Tramo(1, 2, 1),   // B-C
                new Tramo(0, 1, 2),   // A-B
                new Tramo(4, 5, 3),   // E-F
                new Tramo(1, 3, 4),   // B-D
                new Tramo(3, 4, 5)),  // D-E
                criticas);
    }

    @Test
    @DisplayName("Con un reemplazo gratis del mismo peso, la arista no es crítica")
    void empateNoEsCritico() {
        // Triángulo 0-1 (5), 1-2 (5), 0-2 (5): el MST usa dos de las tres,
        // y al quitar cualquiera la tercera la reemplaza SIN sobrecosto.
        Grafo triangulo = new Grafo(3, false);
        triangulo.agregarArista(0, 1, 5);
        triangulo.agregarArista(1, 2, 5);
        triangulo.agregarArista(0, 2, 5);

        assertEquals(List.of(), AristasCriticas.encontrar(triangulo));
    }

    @Test
    @DisplayName("Un puente sin alternativa (desconecta el grafo) es crítico")
    void puenteEsCritico() {
        // Dos triángulos baratos unidos por un único puente 2-3.
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1, 1);
        grafo.agregarArista(1, 2, 1);
        grafo.agregarArista(0, 2, 1);
        grafo.agregarArista(3, 4, 1);
        grafo.agregarArista(4, 5, 1);
        grafo.agregarArista(3, 5, 1);
        grafo.agregarArista(2, 3, 10);   // el puente: sin él, dos islas

        List<Tramo> criticas = AristasCriticas.encontrar(grafo);
        assertTrue(criticas.contains(new Tramo(2, 3, 10)),
                "el puente tiene que ser crítico");
        // Las aristas de los triángulos de peso repetido no lo son.
        assertEquals(1, criticas.size());
    }
}

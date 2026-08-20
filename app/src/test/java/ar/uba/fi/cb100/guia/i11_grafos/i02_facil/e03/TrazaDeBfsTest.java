package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e03;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrazaDeBfsTest {

    @Test
    @DisplayName("BFS desde C da [2, 0, 3, 4, 1, 5]: el mismo orden que la traza a mano")
    void bfsDesdeCCoincideConLaTraza() {
        Grafo grafo = TrazaDeBfs.grafoDelApunte();
        assertEquals(List.of(2, 0, 3, 4, 1, 5), Recorridos.bfs(grafo, 2));
        assertEquals(TrazaDeBfs.ORDEN_A_MANO, Recorridos.bfs(grafo, 2));
    }

    @Test
    @DisplayName("El orden respeta los niveles: C, despues A/D/E, despues B/F")
    void respetaLosNiveles() {
        // Las distancias en aristas desde C confirman los niveles de la traza.
        int[] distancias = Recorridos.distanciasDesde(TrazaDeBfs.grafoDelApunte(), 2);
        assertArrayEquals(new int[] {1, 2, 0, 1, 1, 2}, distancias);
    }

    @Test
    @DisplayName("Desde A el mismo grafo da el BFS del apunte: [0, 1, 2, 3, 4, 5]")
    void bfsDesdeA() {
        assertEquals(List.of(0, 1, 2, 3, 4, 5),
                Recorridos.bfs(TrazaDeBfs.grafoDelApunte(), 0));
    }
}

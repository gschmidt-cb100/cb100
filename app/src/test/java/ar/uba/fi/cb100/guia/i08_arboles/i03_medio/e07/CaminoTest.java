package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CaminoTest {

    private Nodo abbDeEjemplo() {
        return new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
    }

    @Test
    @DisplayName("El camino hasta la raíz es solo la raíz")
    void caminoHastaLaRaiz() {
        assertEquals(List.of(50), Camino.caminoHasta(abbDeEjemplo(), 50));
    }

    @Test
    @DisplayName("El camino hasta una hoja sigue el invariante del ABB")
    void caminoHastaUnaHoja() {
        assertEquals(List.of(50, 30, 40), Camino.caminoHasta(abbDeEjemplo(), 40));
        assertEquals(List.of(50, 70, 60), Camino.caminoHasta(abbDeEjemplo(), 60));
    }

    @Test
    @DisplayName("Si el valor no está, el camino es una lista vacía")
    void valorAusente() {
        assertTrue(Camino.caminoHasta(abbDeEjemplo(), 65).isEmpty());
        assertTrue(Camino.caminoHasta(abbDeEjemplo(), 5).isEmpty());
    }

    @Test
    @DisplayName("En el árbol vacío no hay camino a ningún valor")
    void arbolVacio() {
        assertTrue(Camino.caminoHasta(null, 10).isEmpty());
    }
}

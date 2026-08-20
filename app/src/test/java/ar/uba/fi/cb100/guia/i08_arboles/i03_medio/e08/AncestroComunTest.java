package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AncestroComunTest {

    private Nodo abbDeEjemplo() {
        return new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
    }

    @Test
    @DisplayName("Dos hojas del mismo subárbol: el ancestro es su padre")
    void hermanas() {
        assertEquals(30, AncestroComun.ancestroComun(abbDeEjemplo(), 20, 40));
        assertEquals(70, AncestroComun.ancestroComun(abbDeEjemplo(), 60, 80));
    }

    @Test
    @DisplayName("Valores en subárboles distintos: el ancestro es la raíz")
    void enSubarbolesDistintos() {
        assertEquals(50, AncestroComun.ancestroComun(abbDeEjemplo(), 20, 60));
        assertEquals(50, AncestroComun.ancestroComun(abbDeEjemplo(), 40, 80));
    }

    @Test
    @DisplayName("Si un valor es ancestro del otro, el ancestro común es él mismo")
    void unoEsAncestroDelOtro() {
        assertEquals(30, AncestroComun.ancestroComun(abbDeEjemplo(), 30, 40));
        assertEquals(50, AncestroComun.ancestroComun(abbDeEjemplo(), 50, 80));
    }

    @Test
    @DisplayName("No importa el orden en que se pasan los valores")
    void esSimetrico() {
        assertEquals(AncestroComun.ancestroComun(abbDeEjemplo(), 20, 60),
                AncestroComun.ancestroComun(abbDeEjemplo(), 60, 20));
    }
}

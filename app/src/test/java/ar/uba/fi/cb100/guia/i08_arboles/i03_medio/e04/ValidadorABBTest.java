package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorABBTest {

    @Test
    @DisplayName("El árbol vacío y una hoja sola son ABB")
    void casosBase() {
        assertTrue(ValidadorABB.esABB(null));
        assertTrue(ValidadorABB.esABB(new Nodo(42)));
    }

    @Test
    @DisplayName("El árbol de ejemplo de la clase es un ABB válido")
    void abbValido() {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
        assertTrue(ValidadorABB.esABB(raiz));
    }

    @Test
    @DisplayName("El árbol tramposo (55 bajo el 30) NO es ABB aunque cada padre-hijo parezca bien")
    void arbolTramposo() {
        // 55 > 30 (su padre), pero 55 > 50 y está en el subárbol
        // izquierdo de 50: viola el invariante global.
        Nodo tramposo = new Nodo(50,
                new Nodo(30, null, new Nodo(55)),
                new Nodo(70));
        assertFalse(ValidadorABB.esABB(tramposo));
    }

    @Test
    @DisplayName("Un valor repetido rompe el invariante estricto")
    void valorRepetido() {
        Nodo repetido = new Nodo(50, new Nodo(50), new Nodo(70));
        assertFalse(ValidadorABB.esABB(repetido));
    }

    @Test
    @DisplayName("Funciona con los valores extremos de int")
    void valoresExtremos() {
        Nodo extremos = new Nodo(0,
                new Nodo(Integer.MIN_VALUE),
                new Nodo(Integer.MAX_VALUE));
        assertTrue(ValidadorABB.esABB(extremos));
    }
}

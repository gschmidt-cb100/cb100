package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorAVLTest {

    @Test
    @DisplayName("El árbol vacío y una hoja sola son AVL")
    void casosBase() {
        assertTrue(ValidadorAVL.esAVL(null));
        assertTrue(ValidadorAVL.esAVL(new Nodo(42)));
    }

    @Test
    @DisplayName("El árbol completo 50/30/70/20/40/60/80 es AVL")
    void arbolCompleto() {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));
        assertTrue(ValidadorAVL.esAVL(raiz));
    }

    @Test
    @DisplayName("La lista 10 → 20 → 30 por derecha es ABB pero NO es AVL")
    void listaDegenerada() {
        Nodo lista = new Nodo(10, null, new Nodo(20, null, new Nodo(30)));
        assertFalse(ValidadorAVL.esAVL(lista));
    }

    @Test
    @DisplayName("Un desequilibrio profundo también se detecta, no solo en la raíz")
    void desequilibrioProfundo() {
        //        50
        //       /  \
        //     30    70
        //    /
        //  20            <- bajo el 20 cuelga 10: el 30 queda con factor 2
        //  /
        // 10
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20, new Nodo(10), null), null),
                new Nodo(70));
        assertFalse(ValidadorAVL.esAVL(raiz));
    }

    @Test
    @DisplayName("Un árbol equilibrado que no es ABB tampoco es AVL")
    void equilibradoPeroNoABB() {
        // Perfectamente balanceado, pero 90 > 50 a la izquierda.
        Nodo raiz = new Nodo(50, new Nodo(90), new Nodo(70));
        assertFalse(ValidadorAVL.esAVL(raiz));
    }
}

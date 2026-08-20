package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ComparativaArbolesTest {

    @Test
    @DisplayName("Con 1..100 en orden, el ABB degenera a altura 99 y el AVL queda en 7 o menos")
    void alturasConCien() {
        int[] resultado = ComparativaArboles.alturas(100);
        assertEquals(99, resultado[0], "El ABB deberia degenerar en una lista");
        assertTrue(resultado[1] <= 7,
                "El AVL deberia medir a lo sumo 7 y midio " + resultado[1]);
    }

    @Test
    @DisplayName("Ambos arboles devuelven el mismo enOrden: 1..100")
    void mismoEnOrden() {
        ComparativaArboles.AbbMinimo abb = new ComparativaArboles.AbbMinimo();
        ComparativaArboles.AvlMinimo avl = new ComparativaArboles.AvlMinimo();
        for (int valor = 1; valor <= 100; valor++) {
            abb.insertar(valor);
            avl.insertar(valor);
        }
        List<Integer> deAbb = abb.enOrden();
        List<Integer> deAvl = avl.enOrden();
        assertEquals(100, deAbb.size());
        assertEquals(deAbb, deAvl); // Guardan lo mismo; cambia la forma.
        assertEquals(1, deAbb.get(0));
        assertEquals(100, deAbb.get(99));
    }

    @Test
    @DisplayName("Con un solo valor las dos alturas son 0")
    void unSoloValor() {
        int[] resultado = ComparativaArboles.alturas(1);
        assertEquals(0, resultado[0]);
        assertEquals(0, resultado[1]);
    }

    @Test
    @DisplayName("La altura del AVL crece como log(n): con n=1000 sigue chica")
    void avlCreceLogaritmicamente() {
        int[] conMil = ComparativaArboles.alturas(1000);
        assertEquals(999, conMil[0]); // El ABB siempre degenera igual.
        // log2(1000) es ~10; el limite teorico AVL es 1.44*log2(n) ~ 14.
        assertTrue(conMil[1] <= 14, "altura AVL " + conMil[1] + " demasiado grande");
    }

    @Test
    @DisplayName("La altura del ABB degenerado es exactamente n-1 para varios n")
    void abbDegeneraSiempre() {
        for (int n : new int[] {2, 5, 10, 50}) {
            int[] resultado = ComparativaArboles.alturas(n);
            assertEquals(n - 1, resultado[0], "fallo con n=" + n);
        }
    }
}

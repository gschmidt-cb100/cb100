package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConstructorBalanceadoTest {

    // Auxiliares para verificar la forma del árbol construido.

    private static int altura(Nodo nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    private static void enOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izquierdo, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.derecho, resultado);
    }

    @Test
    @DisplayName("Con los valores 1..15 el árbol queda perfecto: altura 3")
    void alturaConQuinceValores() {
        int[] entrada = new int[15];
        for (int i = 0; i < 15; i++) {
            entrada[i] = i + 1;
        }
        Nodo raiz = ConstructorBalanceado.desdeOrdenado(entrada);
        assertEquals(3, altura(raiz));
        assertEquals(8, raiz.valor);
    }

    @Test
    @DisplayName("El en-orden del árbol construido es exactamente la entrada")
    void enOrdenIgualALaEntrada() {
        int[] entrada = new int[15];
        List<Integer> esperado = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            entrada[i] = i + 1;
            esperado.add(i + 1);
        }
        List<Integer> obtenido = new ArrayList<>();
        enOrden(ConstructorBalanceado.desdeOrdenado(entrada), obtenido);
        assertEquals(esperado, obtenido);
    }

    @Test
    @DisplayName("El arreglo vacío produce el árbol vacío")
    void arregloVacio() {
        assertNull(ConstructorBalanceado.desdeOrdenado(new int[0]));
    }

    @Test
    @DisplayName("Con un solo valor el árbol es una hoja")
    void unSoloValor() {
        Nodo raiz = ConstructorBalanceado.desdeOrdenado(new int[] {7});
        assertEquals(7, raiz.valor);
        assertEquals(0, altura(raiz));
    }

    @Test
    @DisplayName("Con cantidades que no llenan el último nivel la altura sigue siendo logarítmica")
    void cantidadNoPotencia() {
        int[] entrada = {1, 2, 3, 4, 5, 6};
        Nodo raiz = ConstructorBalanceado.desdeOrdenado(entrada);
        assertEquals(2, altura(raiz));
        List<Integer> obtenido = new ArrayList<>();
        enOrden(raiz, obtenido);
        assertEquals(List.of(1, 2, 3, 4, 5, 6), obtenido);
    }
}

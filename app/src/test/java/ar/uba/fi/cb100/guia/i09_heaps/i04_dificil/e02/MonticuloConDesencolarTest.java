package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class MonticuloConDesencolarTest {

    @Test
    @DisplayName("Desencolar sobre un monticulo vacio lanza IllegalStateException")
    void desencolarVacioLanza() {
        MonticuloConDesencolar<Integer> monticulo = new MonticuloConDesencolar<>();
        assertThrows(IllegalStateException.class, monticulo::desencolarMinimo);
    }

    @Test
    @DisplayName("Encolar desordenado y vaciar el monticulo entrega los valores ordenados")
    void vaciarEntregaOrdenado() {
        MonticuloConDesencolar<Integer> monticulo = new MonticuloConDesencolar<>();
        for (int valor : new int[] {42, 17, 99, 3, 25, 60, 8, 71}) {
            monticulo.encolar(valor);
        }
        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolarMinimo());
        }
        assertEquals(List.of(3, 8, 17, 25, 42, 60, 71, 99), salida);
    }

    @Test
    @DisplayName("Caza el bug del un solo hijo: tras desencolar queda raiz 9 con hijos 8 y 2, y hundir debe elegir el 2 (el derecho)")
    void bugDelUnSoloHijo() {
        // Encolamos 1, 8, 2, 9: ninguno flota salvo quedar donde cae, y el
        // arreglo interno resulta [1, 8, 2, 9] (estilo apunte, dibujado por niveles).
        MonticuloConDesencolar<Integer> monticulo = new MonticuloConDesencolar<>();
        monticulo.encolar(1);
        monticulo.encolar(8);
        monticulo.encolar(2);
        monticulo.encolar(9);

        // desencolarMinimo saca el 1 y sube la ultima hoja (el 9) a la raiz:
        // queda 9 con hijos 8 (izquierdo) y 2 (derecho). Una implementacion
        // que solo compara con el hijo izquierdo intercambia 9 con 8 y deja
        // como nuevo minimo al 8, con el 2 enterrado abajo. La correcta
        // compara con AMBOS y baja el 9 por el lado del 2.
        assertEquals(1, monticulo.desencolarMinimo());
        assertEquals(2, monticulo.verMinimo(), "Hundir debe elegir el MENOR de los dos hijos (el derecho)");

        assertEquals(2, monticulo.desencolarMinimo());
        assertEquals(8, monticulo.desencolarMinimo());
        assertEquals(9, monticulo.desencolarMinimo());
        assertTrue(monticulo.estaVacio());
    }

    @Test
    @DisplayName("Con un solo elemento, desencolar lo devuelve y deja el monticulo vacio")
    void unSoloElemento() {
        MonticuloConDesencolar<String> monticulo = new MonticuloConDesencolar<>();
        monticulo.encolar("unico");
        assertEquals("unico", monticulo.desencolarMinimo());
        assertTrue(monticulo.estaVacio());
        assertEquals(0, monticulo.tamanio());
    }

    @Test
    @DisplayName("Los duplicados salen tantas veces como entraron y en orden")
    void conDuplicados() {
        MonticuloConDesencolar<Integer> monticulo = new MonticuloConDesencolar<>();
        for (int valor : new int[] {5, 3, 5, 1, 3, 5}) {
            monticulo.encolar(valor);
        }
        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolarMinimo());
        }
        assertEquals(List.of(1, 3, 3, 5, 5, 5), salida);
    }
}

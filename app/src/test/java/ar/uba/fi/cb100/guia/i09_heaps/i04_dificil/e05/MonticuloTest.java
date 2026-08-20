package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class MonticuloTest {

    @Test
    @DisplayName("Con Comparator.naturalOrder se comporta como min-heap: sale de menor a mayor")
    void conNaturalOrderEsMinHeap() {
        Monticulo<Integer> monticulo = new Monticulo<>(Comparator.<Integer>naturalOrder());
        for (int valor : new int[] {42, 17, 99, 3, 25}) {
            monticulo.encolar(valor);
        }
        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolar());
        }
        assertEquals(List.of(3, 17, 25, 42, 99), salida);
    }

    @Test
    @DisplayName("Con Comparator.reverseOrder se comporta como max-heap: sale de mayor a menor")
    void conReverseOrderEsMaxHeap() {
        Monticulo<Integer> monticulo = new Monticulo<>(Comparator.<Integer>reverseOrder());
        for (int valor : new int[] {42, 17, 99, 3, 25}) {
            monticulo.encolar(valor);
        }
        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolar());
        }
        assertEquals(List.of(99, 42, 25, 17, 3), salida);
    }

    @Test
    @DisplayName("Un comparador por longitud prioriza las palabras mas cortas")
    void comparadorPorLongitud() {
        Monticulo<String> monticulo = new Monticulo<>(Comparator.comparingInt(String::length));
        monticulo.encolar("bandoneon");
        monticulo.encolar("sol");
        monticulo.encolar("guitarra");
        assertEquals("sol", monticulo.desencolar());
        assertEquals("guitarra", monticulo.desencolar());
        assertEquals("bandoneon", monticulo.desencolar());
    }

    @Test
    @DisplayName("verPrimero y desencolar sobre un monticulo vacio lanzan IllegalStateException")
    void vacioLanza() {
        Monticulo<Integer> monticulo = new Monticulo<>(Comparator.<Integer>naturalOrder());
        assertTrue(monticulo.estaVacio());
        assertThrows(IllegalStateException.class, monticulo::verPrimero);
        assertThrows(IllegalStateException.class, monticulo::desencolar);
    }

    @Test
    @DisplayName("Construirlo con comparador null lanza IllegalArgumentException")
    void comparadorNullLanza() {
        assertThrows(IllegalArgumentException.class, () -> new Monticulo<Integer>(null));
    }
}

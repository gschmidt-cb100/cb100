package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class MonticuloDarioTest {

    @Test
    @DisplayName("Con d = 3, encolar desordenado y vaciar entrega los valores ordenados")
    void ternarioOrdena() {
        MonticuloDario<Integer> monticulo = new MonticuloDario<>(3);
        for (int valor : new int[] {42, 17, 99, 3, 25, 60, 8, 71, 3}) {
            monticulo.encolar(valor);
        }
        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolarMinimo());
        }
        assertEquals(List.of(3, 3, 8, 17, 25, 42, 60, 71, 99), salida);
    }

    @Test
    @DisplayName("Con d = 4, un lote aleatorio de semilla fija sale ordenado")
    void cuaternarioOrdena() {
        Random azar = new Random(9);
        int[] valores = new int[300];
        MonticuloDario<Integer> monticulo = new MonticuloDario<>(4);
        for (int i = 0; i < valores.length; i++) {
            valores[i] = azar.nextInt(5000);
            monticulo.encolar(valores[i]);
        }
        Arrays.sort(valores);
        for (int valor : valores) {
            assertEquals(valor, monticulo.desencolarMinimo());
        }
        assertTrue(monticulo.estaVacio());
    }

    @Test
    @DisplayName("El minimo historico queda en la raiz tras cada insercion, tambien con d = 3")
    void minimoEnLaRaiz() {
        MonticuloDario<Integer> monticulo = new MonticuloDario<>(3);
        int[] valores = {50, 40, 60, 10, 70, 5, 80};
        int minimoEsperado = Integer.MAX_VALUE;
        for (int valor : valores) {
            monticulo.encolar(valor);
            minimoEsperado = Math.min(minimoEsperado, valor);
            assertEquals(minimoEsperado, monticulo.verMinimo());
        }
    }

    @Test
    @DisplayName("La aridad debe ser al menos 2: d = 1 y d = 0 lanzan IllegalArgumentException")
    void aridadInvalidaLanza() {
        assertThrows(IllegalArgumentException.class, () -> new MonticuloDario<Integer>(1));
        assertThrows(IllegalArgumentException.class, () -> new MonticuloDario<Integer>(0));
    }

    @Test
    @DisplayName("Ver o desencolar el minimo de un monticulo vacio lanza IllegalStateException")
    void vacioLanza() {
        MonticuloDario<String> monticulo = new MonticuloDario<>(3);
        assertThrows(IllegalStateException.class, monticulo::verMinimo);
        assertThrows(IllegalStateException.class, monticulo::desencolarMinimo);
    }
}

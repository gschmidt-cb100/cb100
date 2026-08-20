package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonticuloMinimoTest {

    @Test
    @DisplayName("Un monticulo nuevo esta vacio y verMinimo lanza IllegalStateException")
    void monticuloVacio() {
        MonticuloMinimo<Integer> monticulo = new MonticuloMinimo<>();
        assertTrue(monticulo.estaVacio());
        assertEquals(0, monticulo.tamanio());
        assertThrows(IllegalStateException.class, monticulo::verMinimo);
    }

    @Test
    @DisplayName("Tras cada insercion el minimo historico queda en la raiz")
    void minimoSiempreEnLaRaiz() {
        MonticuloMinimo<Integer> monticulo = new MonticuloMinimo<>();
        int[] valores = {42, 17, 99, 3, 25, 3, 1, 88};
        int minimoEsperado = Integer.MAX_VALUE;
        for (int valor : valores) {
            monticulo.encolar(valor);
            minimoEsperado = Math.min(minimoEsperado, valor);
            assertEquals(minimoEsperado, monticulo.verMinimo());
        }
        assertEquals(valores.length, monticulo.tamanio());
    }

    @Test
    @DisplayName("Insertar en orden decreciente hace flotar cada nuevo elemento hasta la raiz")
    void insercionDecreciente() {
        MonticuloMinimo<Integer> monticulo = new MonticuloMinimo<>();
        for (int valor = 10; valor >= 1; valor--) {
            monticulo.encolar(valor);
            assertEquals(valor, monticulo.verMinimo());
        }
        assertEquals(10, monticulo.tamanio());
    }

    @Test
    @DisplayName("La redimension x2 permite superar la capacidad inicial sin perder el minimo")
    void redimensiona() {
        MonticuloMinimo<Integer> monticulo = new MonticuloMinimo<>();
        for (int valor = 100; valor >= 1; valor--) {
            monticulo.encolar(valor);
        }
        assertEquals(100, monticulo.tamanio());
        assertEquals(1, monticulo.verMinimo());
    }

    @Test
    @DisplayName("Funciona con Strings porque son Comparable")
    void conStrings() {
        MonticuloMinimo<String> monticulo = new MonticuloMinimo<>();
        monticulo.encolar("pera");
        monticulo.encolar("manzana");
        monticulo.encolar("uva");
        assertEquals("manzana", monticulo.verMinimo());
    }
}

package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SubarregloMaximoTest {

    /** Referencia O(n^2): prueba todas las porciones contiguas. */
    private static int fuerzaBruta(int[] a) {
        int mejor = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            int suma = 0;
            for (int j = i; j < a.length; j++) {
                suma += a[j];
                mejor = Math.max(mejor, suma);
            }
        }
        return mejor;
    }

    @Test
    @DisplayName("ejemplo clasico da 6")
    void ejemploClasico() {
        assertEquals(6, SubarregloMaximo.sumaMaxima(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    @DisplayName("un solo elemento devuelve ese elemento")
    void unElemento() {
        assertEquals(5, SubarregloMaximo.sumaMaxima(new int[]{5}));
        assertEquals(-3, SubarregloMaximo.sumaMaxima(new int[]{-3}));
    }

    @Test
    @DisplayName("todos negativos: devuelve el menos negativo")
    void todosNegativos() {
        assertEquals(-1, SubarregloMaximo.sumaMaxima(new int[]{-5, -1, -8, -3}));
    }

    @Test
    @DisplayName("todos positivos: devuelve la suma total")
    void todosPositivos() {
        assertEquals(10, SubarregloMaximo.sumaMaxima(new int[]{1, 2, 3, 4}));
    }

    @Test
    @DisplayName("arreglo vacio lanza IllegalArgumentException")
    void vacioLanza() {
        assertThrows(IllegalArgumentException.class, () -> SubarregloMaximo.sumaMaxima(new int[]{}));
    }

    @Test
    @DisplayName("coincide con la fuerza bruta sobre arreglos aleatorios")
    void coincideConFuerzaBruta() {
        Random r = new Random(2024);
        for (int caso = 0; caso < 200; caso++) {
            int[] a = new int[1 + r.nextInt(40)];
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(40) - 20;
            }
            assertEquals(fuerzaBruta(a), SubarregloMaximo.sumaMaxima(a));
        }
    }
}

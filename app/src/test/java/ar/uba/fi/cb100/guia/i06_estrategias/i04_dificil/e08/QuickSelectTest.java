package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuickSelectTest {

    @Test
    @DisplayName("coincide con el arreglo ordenado para todo k")
    void coincideConOrden() {
        int[] datos = {7, 2, 9, 4, 1, 8, 3};
        int[] ordenado = datos.clone();
        Arrays.sort(ordenado);
        for (int k = 1; k <= datos.length; k++) {
            assertEquals(ordenado[k - 1], QuickSelect.kEsimoMenor(datos, k),
                    "fallo para k = " + k);
        }
    }

    @Test
    @DisplayName("minimo y maximo (k=1 y k=n)")
    void minimoYMaximo() {
        int[] datos = {5, 3, 8, 1, 9, 2};
        assertEquals(1, QuickSelect.kEsimoMenor(datos, 1));
        assertEquals(9, QuickSelect.kEsimoMenor(datos, datos.length));
    }

    @Test
    @DisplayName("funciona con elementos repetidos")
    void conRepetidos() {
        int[] datos = {4, 4, 4, 2, 2, 8};
        int[] ordenado = datos.clone();
        Arrays.sort(ordenado);
        for (int k = 1; k <= datos.length; k++) {
            assertEquals(ordenado[k - 1], QuickSelect.kEsimoMenor(datos, k));
        }
    }

    @Test
    @DisplayName("no modifica el arreglo original")
    void noModificaOriginal() {
        int[] datos = {3, 1, 2};
        QuickSelect.kEsimoMenor(datos, 2);
        assertArrayEquals(new int[]{3, 1, 2}, datos);
    }

    @Test
    @DisplayName("k fuera de rango lanza excepcion")
    void kInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.kEsimoMenor(new int[]{1, 2, 3}, 0));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.kEsimoMenor(new int[]{1, 2, 3}, 4));
    }
}

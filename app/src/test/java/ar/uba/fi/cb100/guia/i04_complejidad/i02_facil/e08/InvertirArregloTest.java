package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class InvertirArregloTest {

    @Test
    @DisplayName("Invierte un arreglo par")
    void arregloPar() {
        assertArrayEquals(new int[]{4, 3, 2, 1},
                InvertirArreglo.invertir(new int[]{1, 2, 3, 4}));
    }

    @Test
    @DisplayName("Invierte un arreglo impar")
    void arregloImpar() {
        assertArrayEquals(new int[]{5, 4, 3, 2, 1},
                InvertirArreglo.invertir(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("Arreglo vacio invertido sigue vacio")
    void arregloVacio() {
        assertArrayEquals(new int[0], InvertirArreglo.invertir(new int[0]));
    }

    @Test
    @DisplayName("Un solo elemento queda igual")
    void unSoloElemento() {
        assertArrayEquals(new int[]{9}, InvertirArreglo.invertir(new int[]{9}));
    }

    @Test
    @DisplayName("No modifica el arreglo original")
    void noModificaOriginal() {
        int[] original = {1, 2, 3};
        InvertirArreglo.invertir(original);
        assertArrayEquals(new int[]{1, 2, 3}, original);
    }

    @Test
    @DisplayName("Arreglo nulo lanza excepcion")
    void arregloNulo() {
        assertThrows(NullPointerException.class,
                () -> InvertirArreglo.invertir(null));
    }
}

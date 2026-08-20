package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MergeKArreglosTest {

    @Test
    @DisplayName("Fusiona varios arreglos intercalados")
    void fusionaIntercalados() {
        int[][] datos = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9},
            {0, 10}
        };
        int[] esperado = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(esperado, MergeKArreglos.fusionar(datos));
    }

    @Test
    @DisplayName("Maneja arreglos vacíos y con repetidos")
    void vaciosYRepetidos() {
        int[][] datos = {
            {},
            {2, 2, 5},
            {},
            {1, 2, 5}
        };
        int[] esperado = {1, 2, 2, 2, 5, 5};
        assertArrayEquals(esperado, MergeKArreglos.fusionar(datos));
    }

    @Test
    @DisplayName("Un solo arreglo se devuelve igual (copia)")
    void unSoloArreglo() {
        int[][] datos = {{3, 6, 9}};
        assertArrayEquals(new int[]{3, 6, 9}, MergeKArreglos.fusionar(datos));
    }

    @Test
    @DisplayName("Casos borde: conjunto vacío y todos vacíos")
    void casosBorde() {
        assertArrayEquals(new int[]{}, MergeKArreglos.fusionar(new int[][]{}));
        assertArrayEquals(new int[]{}, MergeKArreglos.fusionar(new int[][]{{}, {}, {}}));
    }

    @Test
    @DisplayName("Con negativos")
    void conNegativos() {
        int[][] datos = {
            {-5, -1, 3},
            {-4, 0, 2},
            {-10, 8}
        };
        int[] esperado = {-10, -5, -4, -1, 0, 2, 3, 8};
        assertArrayEquals(esperado, MergeKArreglos.fusionar(datos));
    }

    @Test
    @DisplayName("null como conjunto o como subarreglo lanza excepción")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> MergeKArreglos.fusionar(null));
        assertThrows(IllegalArgumentException.class,
                () -> MergeKArreglos.fusionar(new int[][]{{1, 2}, null}));
    }
}

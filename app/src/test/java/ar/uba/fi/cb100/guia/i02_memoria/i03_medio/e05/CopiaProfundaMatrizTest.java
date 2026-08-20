package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CopiaProfundaMatrizTest {

    @Test
    @DisplayName("modificar la copia no toca el original")
    void modificarCopiaNoAfectaOriginal() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copia = CopiaProfundaMatriz.copiaProfunda(original);
        copia[0][0] = 99;
        copia[1][1] = 77;
        assertArrayEquals(new int[]{1, 2}, original[0]);
        assertArrayEquals(new int[]{3, 4}, original[1]);
    }

    @Test
    @DisplayName("la copia tiene el mismo contenido que el original")
    void copiaTieneMismoContenido() {
        int[][] original = {{5, 6, 7}, {8}};
        int[][] copia = CopiaProfundaMatriz.copiaProfunda(original);
        assertArrayEquals(new int[]{5, 6, 7}, copia[0]);
        assertArrayEquals(new int[]{8}, copia[1]);
    }

    @Test
    @DisplayName("las filas de la copia son objetos distintos a las del original")
    void filasSonObjetosDistintos() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copia = CopiaProfundaMatriz.copiaProfunda(original);
        assertNotSame(original[0], copia[0]);
        assertNotSame(original, copia);
    }
}

package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubsecuenciaCrecienteTest {

    @Test
    @DisplayName("Caso clásico [10,9,2,5,3,7,101,18] -> 4")
    void casoClasico() {
        assertEquals(4, SubsecuenciaCreciente.lis(
                new int[] {10, 9, 2, 5, 3, 7, 101, 18}));
    }

    @Test
    @DisplayName("Secuencia estrictamente decreciente: la LIS mide 1")
    void decreciente() {
        assertEquals(1, SubsecuenciaCreciente.lis(new int[] {9, 7, 5, 3, 1}));
    }

    @Test
    @DisplayName("Secuencia ya creciente: la LIS es toda la secuencia")
    void yaCreciente() {
        assertEquals(5, SubsecuenciaCreciente.lis(new int[] {1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("Todos iguales: creciente estricta, la LIS mide 1")
    void todosIguales() {
        assertEquals(1, SubsecuenciaCreciente.lis(new int[] {4, 4, 4, 4}));
    }

    @Test
    @DisplayName("Arreglo vacío -> 0 y de un elemento -> 1")
    void casosBorde() {
        assertEquals(0, SubsecuenciaCreciente.lis(new int[] {}));
        assertEquals(1, SubsecuenciaCreciente.lis(new int[] {42}));
    }
}

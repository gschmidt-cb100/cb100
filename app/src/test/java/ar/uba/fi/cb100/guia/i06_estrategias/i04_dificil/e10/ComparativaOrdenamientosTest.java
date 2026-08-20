package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ComparativaOrdenamientosTest {

    @Test
    @DisplayName("los cuatro algoritmos coinciden en un caso general")
    void casoGeneral() {
        assertTrue(ComparativaOrdenamientos.todosCoinciden(new int[]{5, 2, 9, 1, 5, 6, 3}));
    }

    @Test
    @DisplayName("coinciden en numeros de varios digitos")
    void variosDigitos() {
        assertTrue(ComparativaOrdenamientos.todosCoinciden(
                new int[]{170, 45, 75, 90, 802, 24, 2, 66}));
    }

    @Test
    @DisplayName("coinciden en casos borde: vacio, un elemento, todos iguales")
    void casosBorde() {
        assertTrue(ComparativaOrdenamientos.todosCoinciden(new int[]{}));
        assertTrue(ComparativaOrdenamientos.todosCoinciden(new int[]{7}));
        assertTrue(ComparativaOrdenamientos.todosCoinciden(new int[]{4, 4, 4, 4}));
    }

    @Test
    @DisplayName("coinciden con arreglo ya ordenado y en orden inverso")
    void ordenadoEInverso() {
        assertTrue(ComparativaOrdenamientos.todosCoinciden(new int[]{0, 1, 2, 3, 4, 5}));
        assertTrue(ComparativaOrdenamientos.todosCoinciden(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}));
    }

    @Test
    @DisplayName("no modifica el arreglo original")
    void noModificaOriginal() {
        int[] datos = {3, 1, 2};
        ComparativaOrdenamientos.todosCoinciden(datos);
        assertArrayEquals(new int[]{3, 1, 2}, datos);
    }

    @Test
    @DisplayName("valores negativos lanzan excepcion")
    void negativosLanzan() {
        assertThrows(IllegalArgumentException.class,
                () -> ComparativaOrdenamientos.todosCoinciden(new int[]{1, -2, 3}));
    }
}

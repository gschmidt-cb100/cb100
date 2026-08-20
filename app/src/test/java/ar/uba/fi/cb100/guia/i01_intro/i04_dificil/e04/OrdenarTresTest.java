package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 04 - Ordenar tres numeros. */
class OrdenarTresTest {

    @Test
    @DisplayName("Ordena entradas en distintos ordenes")
    void distintosOrdenes() {
        assertArrayEquals(new int[] {1, 2, 3}, OrdenarTres.ordenar(3, 1, 2));
        assertArrayEquals(new int[] {1, 2, 3}, OrdenarTres.ordenar(1, 2, 3));
        assertArrayEquals(new int[] {1, 2, 3}, OrdenarTres.ordenar(3, 2, 1));
    }

    @Test
    @DisplayName("Maneja valores repetidos")
    void valoresRepetidos() {
        assertArrayEquals(new int[] {2, 2, 5}, OrdenarTres.ordenar(5, 2, 2));
        assertArrayEquals(new int[] {7, 7, 7}, OrdenarTres.ordenar(7, 7, 7));
    }

    @Test
    @DisplayName("Maneja negativos")
    void conNegativos() {
        assertArrayEquals(new int[] {-3, -1, 4}, OrdenarTres.ordenar(4, -1, -3));
    }
}

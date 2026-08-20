package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SumaConSignosTest {

    private final SumaConSignos contadora = new SumaConSignos();

    @Test
    @DisplayName("[1,1,1,1,1] con objetivo 3: 5 formas (por ambas tecnicas)")
    void ejemploClasico() {
        int[] datos = {1, 1, 1, 1, 1};
        assertEquals(5, contadora.formasBacktracking(datos, 3));
        assertEquals(5, contadora.formasDp(datos, 3));
    }

    @Test
    @DisplayName("Backtracking y PD coinciden en varios casos distintos")
    void tecnicasCoinciden() {
        int[][] arreglos = {
                {1, 2, 3, 4, 5},
                {2, 2},
                {1},
                {5, 5, 5, 5},
                {3, 1, 4, 1, 5, 9, 2, 6}};
        int[] objetivos = {3, 0, 2, 10, 7};
        for (int i = 0; i < arreglos.length; i++) {
            long esperado = contadora.formasBacktracking(arreglos[i], objetivos[i]);
            assertEquals(esperado, contadora.formasDp(arreglos[i], objetivos[i]),
                    "Difieren en el caso " + i);
        }
    }

    @Test
    @DisplayName("Objetivo con paridad imposible: 0 formas")
    void paridadImposible() {
        // Suma total 3: solo se alcanzan objetivos impares entre -3 y 3.
        assertEquals(0, contadora.formasBacktracking(new int[] {1, 1, 1}, 2));
        assertEquals(0, contadora.formasDp(new int[] {1, 1, 1}, 2));
    }

    @Test
    @DisplayName("Los ceros duplican las formas (cada 0 admite ambos signos)")
    void cerosDuplican() {
        // [1] objetivo 1: 1 forma. Cada 0 agregado multiplica por 2.
        assertEquals(1, contadora.formasDp(new int[] {1}, 1));
        assertEquals(2, contadora.formasDp(new int[] {1, 0}, 1));
        assertEquals(4, contadora.formasDp(new int[] {1, 0, 0}, 1));
        assertEquals(4, contadora.formasBacktracking(new int[] {1, 0, 0}, 1));
    }

    @Test
    @DisplayName("Objetivo negativo: por simetria hay tantas formas como para el positivo")
    void objetivoNegativo() {
        int[] datos = {1, 2, 3, 4, 5};
        assertEquals(contadora.formasDp(datos, 3), contadora.formasDp(datos, -3));
        assertEquals(contadora.formasBacktracking(datos, -3), contadora.formasDp(datos, -3));
    }

    @Test
    @DisplayName("Un valor negativo en el arreglo lanza IllegalArgumentException")
    void valorNegativoInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> contadora.formasDp(new int[] {1, -2}, 0));
    }
}

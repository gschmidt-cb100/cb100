package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class SaltoMinimoTest {

    private final SaltoMinimo saltarina = new SaltoMinimo();

    @Test
    @DisplayName("[2,3,1,1,4] se resuelve con 2 saltos (0 -> 1 -> 4)")
    void ejemploClasico() {
        assertEquals(2, saltarina.saltosMinimos(new int[] {2, 3, 1, 1, 4}));
        assertEquals(2, saltarina.saltosMinimosDp(new int[] {2, 3, 1, 1, 4}));
    }

    @Test
    @DisplayName("Greedy y PD coinciden en cinco casos variados")
    void greedyYDpCoinciden() {
        int[][] casos = {
                {2, 3, 1, 1, 4},
                {1, 1, 1, 1},
                {5, 1, 1, 1, 1, 1},
                {1, 3, 2, 1, 4, 1, 1, 2, 1},
                {2, 1, 3, 1, 1, 1, 2, 1}};
        for (int[] caso : casos) {
            assertEquals(saltarina.saltosMinimosDp(caso), saltarina.saltosMinimos(caso),
                    "Difieren en " + Arrays.toString(caso));
        }
    }

    @Test
    @DisplayName("Un solo elemento: 0 saltos")
    void unElemento() {
        assertEquals(0, saltarina.saltosMinimos(new int[] {0}));
        assertEquals(0, saltarina.saltosMinimosDp(new int[] {7}));
    }

    @Test
    @DisplayName("Con un 0 infranqueable ambas versiones devuelven -1")
    void inalcanzable() {
        int[] atascado = {1, 0, 4};
        assertEquals(-1, saltarina.saltosMinimos(atascado));
        assertEquals(-1, saltarina.saltosMinimosDp(atascado));
    }

    @Test
    @DisplayName("Si el primer salto cubre todo, alcanza con 1")
    void unSaltoAlcanza() {
        assertEquals(1, saltarina.saltosMinimos(new int[] {4, 1, 1, 1, 1}));
        assertEquals(1, saltarina.saltosMinimosDp(new int[] {4, 1, 1, 1, 1}));
    }

    @Test
    @DisplayName("Arreglo vacio o con alcances negativos lanza IllegalArgumentException")
    void entradasInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> saltarina.saltosMinimos(new int[] {}));
        assertThrows(IllegalArgumentException.class, () -> saltarina.saltosMinimos(new int[] {1, -1}));
    }
}

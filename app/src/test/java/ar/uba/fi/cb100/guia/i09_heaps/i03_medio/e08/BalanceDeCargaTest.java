package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceDeCargaTest {

    @Test
    @DisplayName("Seis tareas y 3 trabajadores: carga máxima 9")
    void repartoTipico() {
        // Cargas paso a paso: 2→[2,0,0]; 3→[2,3,0]; 7→[2,3,7];
        // 1→[3,3,7]; 4→[7,3,7]; 6→[7,9,7]. Máximo: 9.
        assertEquals(9, BalanceDeCarga.cargaMaxima(new int[] {2, 3, 7, 1, 4, 6}, 3));
    }

    @Test
    @DisplayName("Con un solo trabajador la carga máxima es la suma total")
    void unSoloTrabajador() {
        assertEquals(15, BalanceDeCarga.cargaMaxima(new int[] {4, 5, 6}, 1));
    }

    @Test
    @DisplayName("Con más trabajadores que tareas, la máxima es la tarea más larga")
    void sobranTrabajadores() {
        assertEquals(6, BalanceDeCarga.cargaMaxima(new int[] {2, 6, 3}, 10));
    }

    @Test
    @DisplayName("Tareas iguales entre trabajadores iguales se reparten parejo")
    void repartoParejo() {
        // 4 tareas de 5 entre 2 trabajadores → 10 y 10.
        assertEquals(10, BalanceDeCarga.cargaMaxima(new int[] {5, 5, 5, 5}, 2));
    }

    @Test
    @DisplayName("Sin tareas la carga máxima es 0")
    void sinTareas() {
        assertEquals(0, BalanceDeCarga.cargaMaxima(new int[] {}, 4));
    }

    @Test
    @DisplayName("m = 0 lanza IllegalArgumentException")
    void mInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> BalanceDeCarga.cargaMaxima(new int[] {1}, 0));
    }
}

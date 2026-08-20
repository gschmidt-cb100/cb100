package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EquipoTest {

    @Test
    @DisplayName("mutar el arreglo original no afecta el estado interno")
    void mutarOriginalNoAfecta() {
        int[] original = {7, 10, 23};
        Equipo equipo = new Equipo(original);
        original[0] = 99;
        assertArrayEquals(new int[]{7, 10, 23}, equipo.getNumeros());
    }

    @Test
    @DisplayName("mutar el arreglo devuelto por el getter no afecta el estado interno")
    void mutarCopiaDevueltaNoAfecta() {
        int[] original = {7, 10, 23};
        Equipo equipo = new Equipo(original);
        int[] copia = equipo.getNumeros();
        copia[1] = 99;
        assertArrayEquals(new int[]{7, 10, 23}, equipo.getNumeros());
    }

    @Test
    @DisplayName("cada llamada al getter devuelve un arreglo distinto")
    void getterDevuelveCopiasDistintas() {
        Equipo equipo = new Equipo(new int[]{1, 2, 3});
        assertNotSame(equipo.getNumeros(), equipo.getNumeros());
    }
}

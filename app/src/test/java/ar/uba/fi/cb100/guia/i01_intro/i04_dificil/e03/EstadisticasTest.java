package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 03 - Estadisticas. */
class EstadisticasTest {

    @Test
    @DisplayName("Calcula min, max y promedio de un arreglo conocido")
    void arregloConocido() {
        Estadisticas e = Estadisticas.calcular(new int[] {4, 8, 15, 16, 23, 42});
        assertEquals(4, e.min());
        assertEquals(42, e.max());
        assertEquals(18.0, e.promedio(), 0.0001);
    }

    @Test
    @DisplayName("Arreglo de un solo elemento")
    void unSoloElemento() {
        Estadisticas e = Estadisticas.calcular(new int[] {7});
        assertEquals(7, e.min());
        assertEquals(7, e.max());
        assertEquals(7.0, e.promedio(), 0.0001);
    }

    @Test
    @DisplayName("Arreglo con negativos")
    void conNegativos() {
        Estadisticas e = Estadisticas.calcular(new int[] {-5, 0, 5});
        assertEquals(-5, e.min());
        assertEquals(5, e.max());
        assertEquals(0.0, e.promedio(), 0.0001);
    }

    @Test
    @DisplayName("Arreglo vacio lanza excepcion")
    void arregloVacio() {
        assertThrows(IllegalArgumentException.class, () -> Estadisticas.calcular(new int[] {}));
    }
}

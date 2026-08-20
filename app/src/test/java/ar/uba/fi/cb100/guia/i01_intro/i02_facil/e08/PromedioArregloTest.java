package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PromedioArregloTest {

    @Test
    @DisplayName("Promedio de un caso conocido")
    void promedioConocido() {
        assertEquals(4.0, PromedioArreglo.promedio(new double[]{2.0, 4.0, 6.0}), 1e-9);
        assertEquals(5.0, PromedioArreglo.promedio(new double[]{5.0}), 1e-9);
    }

    @Test
    @DisplayName("Arreglo vacio o null lanza excepcion")
    void arregloInvalido() {
        assertThrows(IllegalArgumentException.class, () -> PromedioArreglo.promedio(new double[0]));
        assertThrows(IllegalArgumentException.class, () -> PromedioArreglo.promedio(null));
    }
}

package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ArregloInmutableTest {

    @Test
    @DisplayName("mutar la entrada no afecta el contenido interno")
    void mutarEntradaNoAfecta() {
        int[] entrada = {10, 20, 30};
        ArregloInmutable inm = new ArregloInmutable(entrada);
        entrada[0] = 99;
        assertEquals(10, inm.get(0));
        assertArrayEquals(new int[]{10, 20, 30}, inm.aArreglo());
    }

    @Test
    @DisplayName("mutar el arreglo devuelto no afecta el contenido interno")
    void mutarSalidaNoAfecta() {
        ArregloInmutable inm = new ArregloInmutable(new int[]{10, 20, 30});
        int[] salida = inm.aArreglo();
        salida[1] = 99;
        assertArrayEquals(new int[]{10, 20, 30}, inm.aArreglo());
    }

    @Test
    @DisplayName("get y tamanio reflejan el contenido")
    void getYTamanio() {
        ArregloInmutable inm = new ArregloInmutable(new int[]{5, 6, 7});
        assertEquals(3, inm.tamanio());
        assertEquals(5, inm.get(0));
        assertEquals(7, inm.get(2));
    }
}

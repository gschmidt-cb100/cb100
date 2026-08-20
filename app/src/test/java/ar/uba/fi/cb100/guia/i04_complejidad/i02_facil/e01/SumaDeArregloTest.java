package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SumaDeArregloTest {

    @Test
    @DisplayName("Suma de varios elementos")
    void sumaDeVariosElementos() {
        assertEquals(15L, SumaDeArreglo.sumar(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("Arreglo vacio suma 0")
    void arregloVacio() {
        assertEquals(0L, SumaDeArreglo.sumar(new int[0]));
    }

    @Test
    @DisplayName("Un solo elemento")
    void unSoloElemento() {
        assertEquals(42L, SumaDeArreglo.sumar(new int[]{42}));
    }

    @Test
    @DisplayName("Incluye numeros negativos")
    void conNegativos() {
        assertEquals(-3L, SumaDeArreglo.sumar(new int[]{-5, 2, 0, 0}));
    }

    @Test
    @DisplayName("No desborda con valores grandes gracias a long")
    void noDesborda() {
        assertEquals(4L * Integer.MAX_VALUE,
                SumaDeArreglo.sumar(new int[]{
                        Integer.MAX_VALUE, Integer.MAX_VALUE,
                        Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    @DisplayName("Lanza excepcion con arreglo nulo")
    void arregloNulo() {
        assertThrows(NullPointerException.class, () -> SumaDeArreglo.sumar(null));
    }
}

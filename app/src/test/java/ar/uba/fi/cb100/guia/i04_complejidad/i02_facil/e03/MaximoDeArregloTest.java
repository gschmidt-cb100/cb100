package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MaximoDeArregloTest {

    @Test
    @DisplayName("Un solo elemento es su propio maximo")
    void unSoloElemento() {
        assertEquals(-7, MaximoDeArreglo.maximo(new int[]{-7}));
    }

    @Test
    @DisplayName("Varios elementos, maximo repetido")
    void variosElementos() {
        assertEquals(9, MaximoDeArreglo.maximo(new int[]{3, 9, 1, 9, 2}));
    }

    @Test
    @DisplayName("Maximo al final")
    void maximoAlFinal() {
        assertEquals(10, MaximoDeArreglo.maximo(new int[]{1, 2, 3, 10}));
    }

    @Test
    @DisplayName("Todos negativos")
    void todosNegativos() {
        assertEquals(-1, MaximoDeArreglo.maximo(new int[]{-5, -1, -3}));
    }

    @Test
    @DisplayName("Arreglo vacio lanza excepcion")
    void arregloVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> MaximoDeArreglo.maximo(new int[0]));
    }

    @Test
    @DisplayName("Arreglo nulo lanza excepcion")
    void arregloNulo() {
        assertThrows(NullPointerException.class, () -> MaximoDeArreglo.maximo(null));
    }
}

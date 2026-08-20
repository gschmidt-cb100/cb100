package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ParesIgualesTest {

    @Test
    @DisplayName("Ejemplo del enunciado: {1,2,2,3,3,3} -> 4 pares")
    void ejemploEnunciado() {
        assertEquals(4, ParesIguales.paresIguales(new int[]{1, 2, 2, 3, 3, 3}));
    }

    @Test
    @DisplayName("Todos distintos -> 0 pares")
    void todosDistintos() {
        assertEquals(0, ParesIguales.paresIguales(new int[]{1, 2, 3, 4}));
    }

    @Test
    @DisplayName("Todos iguales: {5,5,5,5} -> C(4,2) = 6 pares")
    void todosIguales() {
        assertEquals(6, ParesIguales.paresIguales(new int[]{5, 5, 5, 5}));
    }

    @Test
    @DisplayName("Arreglo vacio -> 0 pares")
    void arregloVacio() {
        assertEquals(0, ParesIguales.paresIguales(new int[0]));
    }

    @Test
    @DisplayName("Un solo elemento -> 0 pares")
    void unSoloElemento() {
        assertEquals(0, ParesIguales.paresIguales(new int[]{7}));
    }

    @Test
    @DisplayName("Lanza excepcion con arreglo nulo")
    void arregloNulo() {
        assertThrows(NullPointerException.class, () -> ParesIguales.paresIguales(null));
    }
}

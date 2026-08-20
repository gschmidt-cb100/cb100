package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ExtremosTest {

    @Test
    @DisplayName("encuentra el minimo y el maximo de una lista desordenada")
    void listaDesordenada() {
        assertArrayEquals(new int[] {11, 30}, Extremos.minimoYMaximo(List.of(18, 25, 11, 30, 22)));
    }

    @Test
    @DisplayName("con un solo elemento, minimo y maximo coinciden")
    void unSoloElemento() {
        assertArrayEquals(new int[] {7, 7}, Extremos.minimoYMaximo(List.of(7)));
    }

    @Test
    @DisplayName("funciona con numeros negativos")
    void conNegativos() {
        assertArrayEquals(new int[] {-10, 3}, Extremos.minimoYMaximo(List.of(-3, 3, -10, 0)));
    }

    @Test
    @DisplayName("coleccion vacia lanza NoSuchElementException")
    void coleccionVacia() {
        assertThrows(NoSuchElementException.class, () -> Extremos.minimoYMaximo(List.of()));
    }
}

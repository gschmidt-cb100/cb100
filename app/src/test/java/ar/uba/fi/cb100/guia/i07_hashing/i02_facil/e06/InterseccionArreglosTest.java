package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InterseccionArreglosTest {

    @Test
    @DisplayName("devuelve los elementos en comun sin duplicados")
    void elementosEnComun() {
        assertEquals(Set.of(2, 4),
                InterseccionArreglos.interseccion(new int[]{1, 2, 2, 3, 4}, new int[]{2, 4, 4, 6}));
    }

    @Test
    @DisplayName("sin elementos en comun devuelve conjunto vacio")
    void sinComunes() {
        assertTrue(InterseccionArreglos.interseccion(new int[]{1, 3}, new int[]{2, 4}).isEmpty());
    }

    @Test
    @DisplayName("con un arreglo vacio la interseccion es vacia")
    void arregloVacio() {
        assertTrue(InterseccionArreglos.interseccion(new int[]{}, new int[]{1, 2}).isEmpty());
    }

    @Test
    @DisplayName("arreglos iguales devuelven todos los valores distintos")
    void arreglosIguales() {
        assertEquals(Set.of(1, 2, 3),
                InterseccionArreglos.interseccion(new int[]{1, 2, 3}, new int[]{3, 2, 1}));
    }
}

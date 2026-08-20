package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InterseccionConjuntosTest {

    @Test
    @DisplayName("interseccion con elementos comunes")
    void conComunes() {
        assertEquals(Set.of(3, 4), InterseccionConjuntos.interseccion(Set.of(1, 2, 3, 4), Set.of(3, 4, 5, 6)));
    }

    @Test
    @DisplayName("sin elementos comunes devuelve conjunto vacio")
    void sinComunes() {
        assertEquals(Set.of(), InterseccionConjuntos.interseccion(Set.of(1, 2), Set.of(3, 4)));
    }

    @Test
    @DisplayName("conjuntos iguales devuelve el mismo conjunto")
    void conjuntosIguales() {
        assertEquals(Set.of(1, 2, 3), InterseccionConjuntos.interseccion(Set.of(1, 2, 3), Set.of(1, 2, 3)));
    }

    @Test
    @DisplayName("con un conjunto vacio devuelve vacio")
    void unoVacio() {
        assertEquals(Set.of(), InterseccionConjuntos.interseccion(Set.of(1, 2, 3), Set.of()));
    }

    @Test
    @DisplayName("no modifica el conjunto original")
    void noModificaOriginal() {
        Set<Integer> a = new java.util.HashSet<>(Set.of(1, 2, 3));
        InterseccionConjuntos.interseccion(a, Set.of(2));
        assertEquals(Set.of(1, 2, 3), a);
    }
}

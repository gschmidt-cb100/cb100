package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class RangoDeLegajosTest {

    private TreeMap<Integer, String> padron() {
        TreeMap<Integer, String> legajos = new TreeMap<>();
        legajos.put(101, "Ana");
        legajos.put(205, "Beto");
        legajos.put(310, "Carla");
        legajos.put(412, "Diego");
        return legajos;
    }

    @Test
    @DisplayName("Devuelve sólo los legajos dentro del rango")
    void rangoIntermedio() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(padron(), 200, 400);

        assertEquals(2, rango.size());
        assertEquals("Beto", rango.get(205));
        assertEquals("Carla", rango.get(310));
    }

    @Test
    @DisplayName("Los extremos del rango son inclusivos")
    void extremosInclusivos() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(padron(), 101, 412);

        assertEquals(4, rango.size());
        assertEquals("Ana", rango.get(101));
        assertEquals("Diego", rango.get(412));
    }

    @Test
    @DisplayName("El resultado mantiene las claves ordenadas")
    void mantieneOrden() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(padron(), 101, 412);

        assertEquals(List.of(101, 205, 310, 412), List.copyOf(rango.keySet()));
    }

    @Test
    @DisplayName("Un rango sin legajos devuelve un mapa vacío")
    void rangoVacio() {
        assertTrue(RangoDeLegajos.enRango(padron(), 500, 600).isEmpty());
    }

    @Test
    @DisplayName("Un rango de un solo valor devuelve ese legajo si existe")
    void rangoPuntual() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(padron(), 205, 205);

        assertEquals(1, rango.size());
        assertEquals("Beto", rango.get(205));
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class RangoDeLegajosTest {

    private TreeMap<Integer, String> alumnosDeEjemplo() {
        TreeMap<Integer, String> alumnos = new TreeMap<>();
        alumnos.put(101234, "Rocío");
        alumnos.put(102500, "Tomás");
        alumnos.put(103800, "Valentina");
        alumnos.put(105999, "Nahuel");
        return alumnos;
    }

    @Test
    @DisplayName("devuelve solo los legajos dentro del rango")
    void rangoIntermedio() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(alumnosDeEjemplo(), 102000, 104000);
        assertEquals(2, rango.size());
        assertEquals("Tomás", rango.get(102500));
        assertEquals("Valentina", rango.get(103800));
    }

    @Test
    @DisplayName("los limites del rango son inclusivos")
    void limitesInclusivos() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(alumnosDeEjemplo(), 101234, 105999);
        assertEquals(4, rango.size());
        assertTrue(rango.containsKey(101234));
        assertTrue(rango.containsKey(105999));
    }

    @Test
    @DisplayName("un rango sin legajos devuelve un mapa vacio")
    void rangoVacio() {
        assertTrue(RangoDeLegajos.enRango(alumnosDeEjemplo(), 200000, 300000).isEmpty());
    }

    @Test
    @DisplayName("las claves del resultado quedan en orden ascendente")
    void ordenAscendente() {
        SortedMap<Integer, String> rango = RangoDeLegajos.enRango(alumnosDeEjemplo(), 100000, 110000);
        assertEquals(101234, rango.firstKey());
        assertEquals(105999, rango.lastKey());
    }
}

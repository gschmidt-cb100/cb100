package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class FrecuenciasOrdenadasTest {

    @Test
    @DisplayName("cuenta bien las palabras ignorando mayusculas y puntuacion")
    void cuentaPalabras() {
        TreeMap<String, Integer> f = FrecuenciasOrdenadas.frecuencias("El perro y el gato. ¡El perro ladra!");
        assertEquals(3, f.get("el"));
        assertEquals(2, f.get("perro"));
        assertEquals(1, f.get("gato"));
        assertEquals(1, f.get("ladra"));
    }

    @Test
    @DisplayName("las claves quedan en orden alfabetico")
    void clavesOrdenadas() {
        TreeMap<String, Integer> f = FrecuenciasOrdenadas.frecuencias("pera manzana banana");
        assertEquals(List.of("banana", "manzana", "pera"), List.copyOf(f.keySet()));
    }

    @Test
    @DisplayName("texto vacio devuelve mapa vacio")
    void textoVacio() {
        assertTrue(FrecuenciasOrdenadas.frecuencias("").isEmpty());
    }

    @Test
    @DisplayName("solo signos y espacios devuelve mapa vacio")
    void soloSignos() {
        assertTrue(FrecuenciasOrdenadas.frecuencias("... , ; 123 !").isEmpty());
    }
}

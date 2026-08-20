package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UltimaAparicionTest {

    @Test
    @DisplayName("guarda el indice de la ultima aparicion, no de la primera")
    void ultimaNoLaPrimera() {
        Map<String, Integer> m = UltimaAparicion.ultimaPosicion(
                new String[]{"sol", "luna", "sol", "mar"});
        assertEquals(2, m.get("sol"));
        assertEquals(1, m.get("luna"));
        assertEquals(3, m.get("mar"));
    }

    @Test
    @DisplayName("arreglo vacio devuelve mapa vacio")
    void arregloVacio() {
        assertTrue(UltimaAparicion.ultimaPosicion(new String[]{}).isEmpty());
    }

    @Test
    @DisplayName("sin repetidos cada palabra queda con su unico indice")
    void sinRepetidos() {
        Map<String, Integer> m = UltimaAparicion.ultimaPosicion(new String[]{"a", "b", "c"});
        assertEquals(Map.of("a", 0, "b", 1, "c", 2), m);
    }

    @Test
    @DisplayName("todas iguales queda solo el ultimo indice")
    void todasIguales() {
        assertEquals(Map.of("eco", 3),
                UltimaAparicion.ultimaPosicion(new String[]{"eco", "eco", "eco", "eco"}));
    }
}

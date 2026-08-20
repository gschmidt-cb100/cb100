package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheLruTest {

    private CacheLru<String, Integer> cache;

    @BeforeEach
    public void prepararCache() {
        cache = new CacheLru<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
    }

    @Test
    @DisplayName("sin accesos intermedios expulsa al primero que entro")
    public void expulsaAlMasViejoSinAccesos() {
        cache.put("d", 4);
        assertFalse(cache.containsKey("a"));
        assertEquals(3, cache.size());
        assertTrue(cache.containsKey("b"));
        assertTrue(cache.containsKey("c"));
        assertTrue(cache.containsKey("d"));
    }

    @Test
    @DisplayName("un get refresca la entrada y cambia quien sale")
    public void elAccesoCambiaLaExpulsion() {
        cache.get("a");            // "a" pasa a ser la mas reciente
        cache.put("d", 4);         // ahora la menos usada es "b"
        assertTrue(cache.containsKey("a"));
        assertFalse(cache.containsKey("b"));
        assertEquals(List.of("c", "a", "d"), List.copyOf(cache.keySet()));
    }

    @Test
    @DisplayName("un put sobre clave existente tambien la refresca")
    public void elPutTambienRefresca() {
        cache.put("a", 10);        // actualiza y refresca "a"
        cache.put("d", 4);         // expulsa a "b"
        assertEquals(10, cache.get("a"));
        assertFalse(cache.containsKey("b"));
    }

    @Test
    @DisplayName("secuencia larga: solo sobreviven las 3 mas usadas recientemente")
    public void secuenciaLarga() {
        cache.get("b");
        cache.get("a");            // orden de uso: c, b, a
        cache.put("d", 4);         // expulsa c
        cache.put("e", 5);         // expulsa b
        assertEquals(List.of("a", "d", "e"), List.copyOf(cache.keySet()));
    }

    @Test
    @DisplayName("capacidad invalida lanza IllegalArgumentException")
    public void capacidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new CacheLru<String, Integer>(0));
    }
}

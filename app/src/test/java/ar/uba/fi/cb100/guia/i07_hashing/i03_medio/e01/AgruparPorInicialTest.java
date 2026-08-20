package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AgruparPorInicialTest {

    @Test
    @DisplayName("Agrupa palabras por su inicial")
    void agrupaPorInicial() {
        Map<Character, List<String>> grupos =
                AgruparPorInicial.agrupar(List.of("mate", "mesa", "bondi"));

        assertEquals(2, grupos.size());
        assertEquals(List.of("mate", "mesa"), grupos.get('m'));
        assertEquals(List.of("bondi"), grupos.get('b'));
    }

    @Test
    @DisplayName("Normaliza la inicial a minúscula: Mate y mesa van juntas")
    void normalizaMinuscula() {
        Map<Character, List<String>> grupos =
                AgruparPorInicial.agrupar(List.of("Mate", "mesa"));

        assertEquals(1, grupos.size());
        assertEquals(List.of("Mate", "mesa"), grupos.get('m'));
        assertNull(grupos.get('M'));
    }

    @Test
    @DisplayName("Con lista vacía devuelve un mapa vacío")
    void listaVacia() {
        assertTrue(AgruparPorInicial.agrupar(List.of()).isEmpty());
    }

    @Test
    @DisplayName("Conserva el orden de aparición dentro de cada grupo")
    void conservaOrden() {
        Map<Character, List<String>> grupos =
                AgruparPorInicial.agrupar(List.of("zorro", "zapato", "zeta"));

        assertEquals(List.of("zorro", "zapato", "zeta"), grupos.get('z'));
    }
}

package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PalabrasLargasTest {

    @Test
    @DisplayName("devuelve las tres mas largas, de mayor a menor longitud")
    void devuelveLasTresMasLargas() {
        assertEquals(List.of("computadora", "algoritmo", "heladera"),
                PalabrasLargas.tresMasLargas(
                        List.of("sol", "computadora", "mate", "algoritmo", "rio", "heladera")));
    }

    @Test
    @DisplayName("a igual longitud desempata alfabeticamente")
    void empateAlfabetico() {
        assertEquals(List.of("casa", "gato", "pato"),
                PalabrasLargas.tresMasLargas(List.of("pato", "gato", "casa", "sol")));
    }

    @Test
    @DisplayName("con menos de tres palabras devuelve todas")
    void menosDeTresDevuelveTodas() {
        assertEquals(List.of("mate", "sol"),
                PalabrasLargas.tresMasLargas(List.of("sol", "mate")));
    }

    @Test
    @DisplayName("con lista vacia devuelve lista vacia")
    void listaVaciaDaVacio() {
        assertTrue(PalabrasLargas.tresMasLargas(List.of()).isEmpty());
    }
}

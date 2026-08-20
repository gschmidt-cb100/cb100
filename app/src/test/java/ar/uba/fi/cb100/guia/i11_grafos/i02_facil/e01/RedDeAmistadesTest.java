package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedDeAmistadesTest {

    private RedDeAmistades red;

    @BeforeEach
    void armarRed() {
        red = new RedDeAmistades();
    }

    @Test
    @DisplayName("El grado de cada persona es su cantidad de amigos")
    void gradosDeTodos() {
        assertEquals(2, red.cantidadDeAmigos("Ana"));    // Beto y Carla
        assertEquals(3, red.cantidadDeAmigos("Beto"));   // Ana, Carla y Dana
        assertEquals(3, red.cantidadDeAmigos("Carla"));  // Ana, Beto y Eva
        assertEquals(2, red.cantidadDeAmigos("Dana"));   // Beto y Eva
        assertEquals(2, red.cantidadDeAmigos("Eva"));    // Carla y Dana
    }

    @Test
    @DisplayName("Ana y Dana NO son amigas directas (no hay arista)")
    void anaYDanaNoSonAmigas() {
        assertFalse(red.sonAmigos("Ana", "Dana"));
        assertFalse(red.sonAmigos("Dana", "Ana"));       // tampoco al reves, claro
    }

    @Test
    @DisplayName("La amistad es mutua: la arista vale en ambos sentidos")
    void amistadMutua() {
        assertTrue(red.sonAmigos("Ana", "Beto"));
        assertTrue(red.sonAmigos("Beto", "Ana"));
    }

    @Test
    @DisplayName("Hay 6 amistades en total")
    void cantidadDeAmistades() {
        assertEquals(6, red.cantidadDeAmistades());
    }

    @Test
    @DisplayName("Preguntar por alguien que no esta en la red lanza excepcion")
    void personaDesconocida() {
        assertThrows(IllegalArgumentException.class, () -> red.cantidadDeAmigos("Zoe"));
    }
}

package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TextoCOWTest {

    @Test
    @DisplayName("agregar no modifica la instancia original (copy-on-write)")
    void agregarNoModificaOriginal() {
        TextoCOW original = new TextoCOW("Hola");
        TextoCOW extendido = original.agregar(" mundo");

        assertEquals("Hola", original.get());          // la original no cambio
        assertEquals("Hola mundo", extendido.get());   // el cambio esta en otra
    }

    @Test
    @DisplayName("agregar devuelve una instancia distinta")
    void agregarDevuelveInstanciaDistinta() {
        TextoCOW original = new TextoCOW("a");
        TextoCOW otro = original.agregar("");
        assertNotSame(original, otro);
    }

    @Test
    @DisplayName("Caso borde: agregar sobre texto vacio")
    void agregarSobreVacio() {
        TextoCOW original = new TextoCOW("");
        TextoCOW resultado = original.agregar("x");
        assertEquals("", original.get());
        assertEquals("x", resultado.get());
    }
}

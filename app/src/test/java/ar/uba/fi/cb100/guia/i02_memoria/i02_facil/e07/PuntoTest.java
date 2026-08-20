package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("conX crea un nuevo Punto y no modifica el original")
    void conXNoModificaOriginal() {
        Punto original = new Punto(1, 2);
        Punto modificado = original.conX(99);

        // el original queda intacto
        assertEquals(1, original.x());
        assertEquals(2, original.y());

        // el nuevo tiene la x cambiada y la misma y
        assertEquals(99, modificado.x());
        assertEquals(2, modificado.y());

        // son objetos distintos
        assertNotSame(original, modificado);
    }

    @Test
    @DisplayName("Dos Punto con los mismos componentes son equals")
    void igualdadPorContenido() {
        assertEquals(new Punto(3, 4), new Punto(3, 4));
    }
}

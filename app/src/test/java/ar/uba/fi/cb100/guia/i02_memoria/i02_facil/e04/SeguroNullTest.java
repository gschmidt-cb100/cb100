package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SeguroNullTest {

    @Test
    @DisplayName("null se convierte en cadena vacia")
    void nullDaVacia() {
        assertEquals("", SeguroNull.seguro(null));
    }

    @Test
    @DisplayName("Un texto no nulo se devuelve tal cual")
    void textoSeMantiene() {
        assertEquals("hola", SeguroNull.seguro("hola"));
    }

    @Test
    @DisplayName("La cadena vacia se devuelve tal cual")
    void vaciaSeMantiene() {
        assertEquals("", SeguroNull.seguro(""));
    }
}

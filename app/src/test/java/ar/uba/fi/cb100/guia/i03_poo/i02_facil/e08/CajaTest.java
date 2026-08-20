package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CajaTest {

    @Test
    @DisplayName("La caja funciona con String y permite cambiar su contenido")
    void cajaDeString() {
        Caja<String> caja = new Caja<>("hola");
        assertEquals("hola", caja.get());
        caja.set("chau");
        assertEquals("chau", caja.get());
    }

    @Test
    @DisplayName("La misma clase generica funciona con Integer")
    void cajaDeInteger() {
        Caja<Integer> caja = new Caja<>(42);
        assertEquals(42, caja.get());
    }
}

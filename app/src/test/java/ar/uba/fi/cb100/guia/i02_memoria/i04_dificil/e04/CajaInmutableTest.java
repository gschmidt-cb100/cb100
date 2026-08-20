package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CajaInmutableTest {

    @Test
    @DisplayName("Mutable: set cambia la misma instancia")
    void mutableCambiaEnElLugar() {
        CajaMutable m = new CajaMutable(1);
        m.set(2);
        assertEquals(2, m.get());
    }

    @Test
    @DisplayName("Inmutable: con(...) no modifica la instancia original")
    void inmutableNoCambiaOriginal() {
        CajaInmutable original = new CajaInmutable(1);
        CajaInmutable nueva = original.con(2);

        assertEquals(1, original.get()); // la original sigue igual
        assertEquals(2, nueva.get());    // el nuevo valor esta en otra caja
    }

    @Test
    @DisplayName("con(...) devuelve una instancia distinta")
    void conDevuelveInstanciaDistinta() {
        CajaInmutable original = new CajaInmutable(5);
        CajaInmutable nueva = original.con(5);
        assertNotSame(original, nueva);
    }
}

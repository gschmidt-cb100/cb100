package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ClasificadorTest {

    @Test
    @DisplayName("Clasifica correctamente cada categoría")
    void clasificaCategorias() {
        assertEquals("insuficiente", Clasificador.clasificar(0));
        assertEquals("insuficiente", Clasificador.clasificar(3));
        assertEquals("aprobado", Clasificador.clasificar(4));
        assertEquals("aprobado", Clasificador.clasificar(7));
        assertEquals("distinguido", Clasificador.clasificar(8));
        assertEquals("distinguido", Clasificador.clasificar(10));
    }

    @Test
    @DisplayName("Nota fuera de rango lanza NotaInvalidaException")
    void notaInvalida() {
        assertThrows(NotaInvalidaException.class, () -> Clasificador.clasificar(-1));
        assertThrows(NotaInvalidaException.class, () -> Clasificador.clasificar(11));
    }
}

package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    @Test
    @DisplayName("preparar() ejecuta los pasos del té en el orden definido por la plantilla")
    void prepararTe() {
        Bebida te = new Te();
        String esperado =
                "Hervir agua\n" +
                "Poner saquito de té en infusión\n" +
                "Servir en taza con rodaja de limón";
        assertEquals(esperado, te.preparar());
    }

    @Test
    @DisplayName("preparar() reutiliza el mismo esqueleto para el café")
    void prepararCafe() {
        Bebida cafe = new Cafe();
        String esperado =
                "Hervir agua\n" +
                "Filtrar café molido\n" +
                "Servir en pocillo con azúcar aparte";
        assertEquals(esperado, cafe.preparar());
    }

    @Test
    @DisplayName("Ambas bebidas comparten el primer paso común (hervir agua)")
    void pasoComun() {
        assertTrue(new Te().preparar().startsWith("Hervir agua"));
        assertTrue(new Cafe().preparar().startsWith("Hervir agua"));
    }
}

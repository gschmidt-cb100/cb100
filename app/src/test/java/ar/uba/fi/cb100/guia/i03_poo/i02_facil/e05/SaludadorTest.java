package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SaludadorTest {

    @Test
    @DisplayName("Polimorfismo: cada implementacion saluda a su manera")
    void polimorfismoSaludo() {
        Saludador formal = new Formal();
        Saludador informal = new Informal();
        assertTrue(formal.saludar().contains("Buenos dias"));
        assertTrue(informal.saludar().contains("Hola"));
        assertNotEquals(formal.saludar(), informal.saludar());
    }
}

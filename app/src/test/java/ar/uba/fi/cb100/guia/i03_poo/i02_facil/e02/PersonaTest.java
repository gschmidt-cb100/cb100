package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    @Test
    @DisplayName("El toString contiene el nombre de la persona")
    void toStringContieneNombre() {
        Persona p = new Persona("Ana", 30);
        String texto = p.toString();
        assertTrue(texto.contains("Ana"));
        assertTrue(texto.contains("30"));
    }
}

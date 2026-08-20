package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    @Test
    @DisplayName("compareTo es negativo cuando la persona es mas joven")
    void compareToPorEdad() {
        Persona joven = new Persona("Ana", 20);
        Persona mayor = new Persona("Beto", 40);
        assertTrue(joven.compareTo(mayor) < 0);
        assertTrue(mayor.compareTo(joven) > 0);
    }

    @Test
    @DisplayName("Arrays.sort ordena las personas de menor a mayor edad")
    void ordenaPorEdad() {
        Persona[] personas = {
                new Persona("Beto", 40),
                new Persona("Ana", 20),
                new Persona("Caro", 30)
        };
        Arrays.sort(personas);
        assertEquals(20, personas[0].getEdad());
        assertEquals(40, personas[2].getEdad());
    }
}

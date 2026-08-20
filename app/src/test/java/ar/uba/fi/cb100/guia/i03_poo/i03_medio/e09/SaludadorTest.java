package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SaludadorTest {

    @Test
    @DisplayName("La clase implementadora usa el método default saludar()")
    void usaMetodoDefault() {
        Saludador s = new Persona("Carla");
        assertEquals("Hola, soy Carla", s.saludar());
    }

    @Test
    @DisplayName("El default se recalcula según nombre() de cada instancia")
    void defaultDependeDeNombre() {
        assertEquals("Hola, soy Ana", new Persona("Ana").saludar());
        assertEquals("Hola, soy Beto", new Persona("Beto").saludar());
    }

    @Test
    @DisplayName("Una implementación puede sobrescribir el método default")
    void sePuedeSobrescribirDefault() {
        Saludador formal = new Saludador() {
            @Override
            public String nombre() {
                return "Dr. Pérez";
            }

            @Override
            public String saludar() {
                return "Buenos días, " + nombre();
            }
        };
        assertEquals("Buenos días, Dr. Pérez", formal.saludar());
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SaludoTest {

    @Test
    @DisplayName("El saludo incluye el nombre y el legajo")
    void saludoIncluyeNombreYLegajo() {
        var resultado = Saludo.saludar("Ada", "12345");
        assertTrue(resultado.contains("Ada"));
        assertTrue(resultado.contains("12345"));
    }

    @Test
    @DisplayName("El saludo empieza con 'Hola'")
    void saludoEmpiezaConHola() {
        var resultado = Saludo.saludar("Alan", "67890");
        assertTrue(resultado.startsWith("Hola"));
        assertTrue(resultado.contains("Alan"));
    }
}

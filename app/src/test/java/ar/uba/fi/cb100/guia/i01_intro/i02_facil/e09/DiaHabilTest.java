package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DiaHabilTest {

    @Test
    @DisplayName("Dias habiles")
    void diasHabiles() {
        assertEquals("habil", DiaHabil.tipoDeDia('L'));
        assertEquals("habil", DiaHabil.tipoDeDia('V'));
    }

    @Test
    @DisplayName("Fin de semana")
    void finDeSemana() {
        assertEquals("fin de semana", DiaHabil.tipoDeDia('S'));
        assertEquals("fin de semana", DiaHabil.tipoDeDia('D'));
    }

    @Test
    @DisplayName("Dia desconocido")
    void desconocido() {
        assertEquals("desconocido", DiaHabil.tipoDeDia('Z'));
        assertEquals("desconocido", DiaHabil.tipoDeDia('1'));
    }
}

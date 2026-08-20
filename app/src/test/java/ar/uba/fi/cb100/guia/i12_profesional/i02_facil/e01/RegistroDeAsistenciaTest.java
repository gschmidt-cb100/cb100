package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistroDeAsistenciaTest {

    private RegistroDeAsistencia registro;

    @BeforeEach
    void preparar() {
        registro = new RegistroDeAsistencia();
        registro.registrar("Ana");
        registro.registrar("Beto");
    }

    @Test
    @DisplayName("estuvo devuelve true para un alumno registrado")
    void estuvoRegistrado() {
        assertTrue(registro.estuvo("Ana"));
        assertTrue(registro.estuvo("Beto"));
    }

    @Test
    @DisplayName("estuvo devuelve false para un alumno que no vino")
    void noEstuvo() {
        assertFalse(registro.estuvo("Carla"));
    }

    @Test
    @DisplayName("registrar dos veces al mismo alumno no lo duplica")
    void sinRepetidos() {
        assertFalse(registro.registrar("Ana"), "la segunda vez debe devolver false");
        assertEquals(2, registro.cantidadDePresentes());
    }

    @Test
    @DisplayName("registrar por primera vez devuelve true")
    void primeraVez() {
        assertTrue(registro.registrar("Carla"));
        assertEquals(3, registro.cantidadDePresentes());
    }

    @Test
    @DisplayName("registro recien creado esta vacio")
    void registroVacio() {
        RegistroDeAsistencia vacio = new RegistroDeAsistencia();
        assertEquals(0, vacio.cantidadDePresentes());
        assertFalse(vacio.estuvo("Ana"));
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistorialDeNavegacionTest {

    private HistorialDeNavegacion navegador;

    @BeforeEach
    void preparar() {
        navegador = new HistorialDeNavegacion();
    }

    @Test
    @DisplayName("la pagina actual es la ultima visitada")
    void actualEsLaUltima() {
        navegador.visitar("a.com");
        navegador.visitar("b.com");
        assertEquals("b.com", navegador.paginaActual());
    }

    @Test
    @DisplayName("volver retrocede a la pagina anterior")
    void volverRetrocede() {
        navegador.visitar("a.com");
        navegador.visitar("b.com");
        navegador.visitar("c.com");
        assertEquals("b.com", navegador.volver());
        assertEquals("a.com", navegador.volver());
    }

    @Test
    @DisplayName("volver en la primera pagina se queda en ella (decision documentada)")
    void volverSinAnterior() {
        navegador.visitar("a.com");
        assertEquals("a.com", navegador.volver());
        assertEquals("a.com", navegador.paginaActual());
    }

    @Test
    @DisplayName("sin visitas, paginaActual y volver devuelven null sin explotar")
    void sinHistorial() {
        assertNull(navegador.paginaActual());
        assertNull(navegador.volver());
    }

    @Test
    @DisplayName("despues de volver, visitar arranca un camino nuevo")
    void visitarDespuesDeVolver() {
        navegador.visitar("a.com");
        navegador.visitar("b.com");
        navegador.volver();
        navegador.visitar("c.com");
        assertEquals("c.com", navegador.paginaActual());
        assertEquals("a.com", navegador.volver());
    }
}

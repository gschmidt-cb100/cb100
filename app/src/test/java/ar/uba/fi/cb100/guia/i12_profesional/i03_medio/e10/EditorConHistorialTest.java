package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditorConHistorialTest {

    private EditorConHistorial editor;

    @BeforeEach
    void preparar() {
        editor = new EditorConHistorial();
    }

    @Test
    @DisplayName("La secuencia clásica: escribir a,b,c; deshacer x2; rehacer")
    void secuenciaClasica() {
        editor.escribir("a");
        editor.escribir("b");
        editor.escribir("c");
        assertEquals("c", editor.textoActual());

        editor.deshacer();
        assertEquals("b", editor.textoActual());
        editor.deshacer();
        assertEquals("a", editor.textoActual());

        editor.rehacer();
        assertEquals("b", editor.textoActual());
    }

    @Test
    @DisplayName("Escribir después de deshacer borra el futuro de redo")
    void escribirBorraElRedo() {
        editor.escribir("a");
        editor.escribir("b");
        editor.escribir("c");
        editor.deshacer();
        editor.deshacer();                          // estamos en "a", redo tiene b y c
        editor.rehacer();                           // "b"
        assertTrue(editor.sePuedeRehacer());        // "c" todavía era alcanzable

        editor.escribir("d");                       // historia alternativa
        assertEquals("d", editor.textoActual());
        assertFalse(editor.sePuedeRehacer());       // "c" se perdió para siempre
        editor.rehacer();                           // no hace nada
        assertEquals("d", editor.textoActual());
    }

    @Test
    @DisplayName("Deshacer todo vuelve al texto vacío inicial")
    void deshacerHastaElPrincipio() {
        editor.escribir("hola");
        editor.deshacer();
        assertEquals("", editor.textoActual());
    }

    @Test
    @DisplayName("Deshacer y rehacer sin historia no hacen nada")
    void sinHistoriaNoPasaNada() {
        editor.deshacer();
        assertEquals("", editor.textoActual());
        editor.rehacer();
        assertEquals("", editor.textoActual());

        editor.escribir("x");
        editor.rehacer();                           // no hay nada deshecho
        assertEquals("x", editor.textoActual());
    }

    @Test
    @DisplayName("Ida y vuelta completa: deshacer todo y rehacer todo")
    void idaYVuelta() {
        editor.escribir("a");
        editor.escribir("b");
        editor.deshacer();
        editor.deshacer();
        assertEquals("", editor.textoActual());
        editor.rehacer();
        editor.rehacer();
        assertEquals("b", editor.textoActual());
    }
}

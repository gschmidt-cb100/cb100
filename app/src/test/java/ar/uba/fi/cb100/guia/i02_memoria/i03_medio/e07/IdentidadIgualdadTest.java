package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class IdentidadIgualdadTest {

    @Test
    @DisplayName("dos objetos con mismo contenido pero distinta instancia: iguales pero no el mismo")
    void contenidoIgualPeroDistintaInstancia() {
        String x = new String("hola");
        String y = new String("hola");
        assertFalse(IdentidadIgualdad.sonElMismo(x, y));
        assertTrue(IdentidadIgualdad.sonIguales(x, y));
    }

    @Test
    @DisplayName("un alias es el mismo objeto y ademas igual")
    void aliasEsElMismoObjeto() {
        String x = new String("hola");
        String alias = x;
        assertTrue(IdentidadIgualdad.sonElMismo(x, alias));
        assertTrue(IdentidadIgualdad.sonIguales(x, alias));
    }

    @Test
    @DisplayName("dos null son el mismo e iguales")
    void dosNull() {
        assertTrue(IdentidadIgualdad.sonElMismo(null, null));
        assertTrue(IdentidadIgualdad.sonIguales(null, null));
    }

    @Test
    @DisplayName("contenidos distintos: ni el mismo ni iguales")
    void contenidosDistintos() {
        String x = new String("hola");
        String z = new String("chau");
        assertFalse(IdentidadIgualdad.sonElMismo(x, z));
        assertFalse(IdentidadIgualdad.sonIguales(x, z));
    }
}

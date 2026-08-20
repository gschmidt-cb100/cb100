package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MismoObjetoTest {

    @Test
    @DisplayName("Un alias del mismo objeto da true")
    void aliasDaTrue() {
        String s = new String("hola");
        String alias = s;
        assertTrue(MismoObjeto.mismoObjeto(s, alias));
    }

    @Test
    @DisplayName("Dos objetos distintos con igual contenido dan false")
    void distintosObjetosDanFalse() {
        String a = new String("hola");
        String b = new String("hola");
        assertFalse(MismoObjeto.mismoObjeto(a, b));
    }
}

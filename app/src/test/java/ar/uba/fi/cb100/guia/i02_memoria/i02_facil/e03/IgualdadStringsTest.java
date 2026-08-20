package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class IgualdadStringsTest {

    @Test
    @DisplayName("Dos String creados con new: == es false, equals es true")
    void distintaReferenciaMismoContenido() {
        String a = new String("x");
        String b = new String("x");
        boolean[] r = IgualdadStrings.comparar(a, b);
        assertArrayEquals(new boolean[]{false, true}, r);
    }

    @Test
    @DisplayName("Misma referencia: == y equals son ambos true")
    void mismaReferencia() {
        String a = new String("x");
        boolean[] r = IgualdadStrings.comparar(a, a);
        assertArrayEquals(new boolean[]{true, true}, r);
    }
}

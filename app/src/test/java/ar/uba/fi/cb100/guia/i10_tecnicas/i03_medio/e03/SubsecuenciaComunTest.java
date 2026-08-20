package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubsecuenciaComunTest {

    @Test
    @DisplayName("Caso clásico: AGGTAB y GXTXAYB comparten GTAB (longitud 4)")
    void casoClasico() {
        assertEquals(4, SubsecuenciaComun.lcs("AGGTAB", "GXTXAYB"));
    }

    @Test
    @DisplayName("Contra la cadena vacía la LCS es 0")
    void cadenaVacia() {
        assertEquals(0, SubsecuenciaComun.lcs("", "ABC"));
        assertEquals(0, SubsecuenciaComun.lcs("ABC", ""));
        assertEquals(0, SubsecuenciaComun.lcs("", ""));
    }

    @Test
    @DisplayName("Dos cadenas iguales: la LCS es la cadena entera")
    void cadenasIguales() {
        assertEquals(7, SubsecuenciaComun.lcs("ALGORIT", "ALGORIT"));
    }

    @Test
    @DisplayName("Sin caracteres en común la LCS es 0")
    void sinComunes() {
        assertEquals(0, SubsecuenciaComun.lcs("ABC", "XYZ"));
    }

    @Test
    @DisplayName("La LCS es simétrica: lcs(a,b) == lcs(b,a)")
    void simetria() {
        assertEquals(SubsecuenciaComun.lcs("AGGTAB", "GXTXAYB"),
                SubsecuenciaComun.lcs("GXTXAYB", "AGGTAB"));
    }
}

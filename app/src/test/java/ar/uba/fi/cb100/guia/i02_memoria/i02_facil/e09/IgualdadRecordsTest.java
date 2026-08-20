package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class IgualdadRecordsTest {

    @Test
    @DisplayName("Dos Par(1,2) distintos: == es false, equals es true")
    void distintaReferenciaMismoContenido() {
        boolean[] r = IgualdadRecords.comparar();
        assertArrayEquals(new boolean[]{false, true}, r);
    }

    @Test
    @DisplayName("Par con distinto contenido no son equals")
    void contenidoDistintoNoEquals() {
        IgualdadRecords.Par p1 = new IgualdadRecords.Par(1, 2);
        IgualdadRecords.Par p2 = new IgualdadRecords.Par(3, 4);
        assertNotEquals(p1, p2);
    }
}

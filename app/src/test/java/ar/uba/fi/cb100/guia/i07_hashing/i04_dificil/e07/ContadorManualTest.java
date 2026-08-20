package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContadorManualTest {

    private static final String REFRAN =
            "el que lee mucho y anda mucho, ve mucho y sabe mucho";

    @Test
    @DisplayName("En el refran: mucho aparece 4 veces e y aparece 2")
    void frecuenciasDelRefran() {
        ContadorManual contador = new ContadorManual();
        contador.contar(REFRAN);
        assertEquals(4, contador.frecuenciaDe("mucho"));
        assertEquals(2, contador.frecuenciaDe("y"));
        assertEquals(1, contador.frecuenciaDe("lee"));
    }

    @Test
    @DisplayName("La tabla devuelta tiene las palabras distintas del texto")
    void tablaDevuelta() {
        ContadorManual contador = new ContadorManual();
        TablaHash<String, Integer> tabla = contador.contar(REFRAN);
        // el, que, lee, mucho, y, anda, ve, sabe -> 8 palabras distintas.
        assertEquals(8, tabla.tamanio());
        assertEquals(4, tabla.obtener("mucho"));
        assertEquals(2, tabla.obtener("y"));
        assertNull(tabla.obtener("gato"));
    }

    @Test
    @DisplayName("Ignora mayusculas y signos de puntuacion")
    void normaliza() {
        ContadorManual contador = new ContadorManual();
        contador.contar("Hola, HOLA... hola! chau?");
        assertEquals(3, contador.frecuenciaDe("hola"));
        assertEquals(1, contador.frecuenciaDe("CHAU"));
    }

    @Test
    @DisplayName("Una palabra ausente tiene frecuencia 0")
    void palabraAusente() {
        ContadorManual contador = new ContadorManual();
        contador.contar(REFRAN);
        assertEquals(0, contador.frecuenciaDe("poco"));
    }

    @Test
    @DisplayName("Contar de nuevo reemplaza el conteo anterior")
    void contarReemplaza() {
        ContadorManual contador = new ContadorManual();
        contador.contar(REFRAN);
        contador.contar("solo una palabra: palabra");
        assertEquals(2, contador.frecuenciaDe("palabra"));
        assertEquals(0, contador.frecuenciaDe("mucho"));
    }

    @Test
    @DisplayName("Antes de contar, toda frecuencia es 0")
    void sinContar() {
        ContadorManual contador = new ContadorManual();
        assertEquals(0, contador.frecuenciaDe("mucho"));
    }
}

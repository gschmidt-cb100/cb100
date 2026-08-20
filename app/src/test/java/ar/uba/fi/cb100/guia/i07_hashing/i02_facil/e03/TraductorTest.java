package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TraductorTest {

    private static final Map<String, String> DIC = Map.of(
            "hola", "hello",
            "mundo", "world",
            "gato", "cat");

    @Test
    @DisplayName("traduce todas las palabras conocidas")
    void traduceConocidas() {
        assertEquals("hello world", Traductor.traducir("hola mundo", DIC));
    }

    @Test
    @DisplayName("las palabras desconocidas quedan igual")
    void desconocidasQuedanIgual() {
        assertEquals("hello mundo cruel", Traductor.traducir("hola mundo cruel",
                Map.of("hola", "hello")));
    }

    @Test
    @DisplayName("frase vacia devuelve cadena vacia")
    void fraseVacia() {
        assertEquals("", Traductor.traducir("", DIC));
    }

    @Test
    @DisplayName("una sola palabra se traduce sin espacios extra")
    void unaSolaPalabra() {
        assertEquals("cat", Traductor.traducir("gato", DIC));
    }

    @Test
    @DisplayName("con diccionario vacio la frase no cambia")
    void diccionarioVacio() {
        assertEquals("hola mundo", Traductor.traducir("hola mundo", Map.of()));
    }
}

package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de Cadena usando {@code String} como ORÁCULO: para cada caso, el
 * resultado de nuestra Cadena tiene que coincidir con el de la operación
 * equivalente de String (con indexOf mapeando -1 a null).
 */
class CadenaTest {

    private static final String[] TEXTOS = {
            "", "a", "hola", "hola mundo", "abcabcabc", "aaa", "banana"
    };

    @Test
    @DisplayName("concatenar coincide con String.concat para varios pares")
    void concatenarContraOraculo() {
        for (String izquierda : TEXTOS) {
            for (String derecha : TEXTOS) {
                String esperado = izquierda.concat(derecha);
                Cadena resultado = new Cadena(izquierda).concatenar(new Cadena(derecha));
                assertEquals(esperado, resultado.toString(),
                        "concatenar('" + izquierda + "', '" + derecha + "')");
            }
        }
    }

    @Test
    @DisplayName("subcadena coincide con String.substring en todos los rangos válidos")
    void subcadenaContraOraculo() {
        for (String texto : TEXTOS) {
            for (int desde = 0; desde <= texto.length(); desde++) {
                for (int hasta = desde; hasta <= texto.length(); hasta++) {
                    String esperado = texto.substring(desde, hasta);
                    Cadena resultado = new Cadena(texto).subcadena(desde, hasta);
                    assertEquals(esperado, resultado.toString(),
                            "subcadena('" + texto + "', " + desde + ", " + hasta + ")");
                }
            }
        }
    }

    @Test
    @DisplayName("indiceDe coincide con String.indexOf, mapeando -1 a null")
    void indiceDeContraOraculo() {
        String[] buscados = {"", "a", "an", "ana", "nan", "hola", "mundo", "xyz", "abcabcabcd"};
        for (String texto : TEXTOS) {
            for (String buscado : buscados) {
                int indiceOraculo = texto.indexOf(buscado);
                Integer esperado = (indiceOraculo == -1) ? null : indiceOraculo;
                Integer resultado = new Cadena(texto).indiceDe(new Cadena(buscado));
                assertEquals(esperado, resultado,
                        "indiceDe('" + texto + "', '" + buscado + "')");
            }
        }
    }

    @Test
    @DisplayName("reemplazar coincide con String.replace(char, char)")
    void reemplazarContraOraculo() {
        char[][] reemplazos = {{'a', 'x'}, {'o', '0'}, {'z', 'q'}, {'n', 'n'}};
        for (String texto : TEXTOS) {
            for (char[] par : reemplazos) {
                String esperado = texto.replace(par[0], par[1]);
                Cadena resultado = new Cadena(texto).reemplazar(par[0], par[1]);
                assertEquals(esperado, resultado.toString(),
                        "reemplazar('" + texto + "', '" + par[0] + "', '" + par[1] + "')");
            }
        }
    }

    @Test
    @DisplayName("todas las operaciones devuelven cadenas nuevas: la original no cambia")
    void inmutabilidad() {
        Cadena original = new Cadena("banana");
        original.concatenar(new Cadena("!"));
        original.subcadena(1, 4);
        original.reemplazar('a', 'o');
        assertEquals("banana", original.toString(), "la original quedó intacta");
    }

    @Test
    @DisplayName("parámetros inválidos lanzan excepciones claras")
    void invalidos() {
        Cadena cadena = new Cadena("hola");
        assertThrows(IllegalArgumentException.class, () -> new Cadena((String) null));
        assertThrows(IllegalArgumentException.class, () -> new Cadena((char[]) null));
        assertThrows(IllegalArgumentException.class, () -> cadena.concatenar(null));
        assertThrows(IllegalArgumentException.class, () -> cadena.indiceDe(null));
        assertThrows(IllegalArgumentException.class, () -> cadena.subcadena(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> cadena.subcadena(0, 5));
        assertThrows(IllegalArgumentException.class, () -> cadena.subcadena(3, 2));
    }

    @Test
    @DisplayName("equals compara por contenido")
    void equalsPorContenido() {
        assertEquals(new Cadena("abc"), new Cadena(new char[]{'a', 'b', 'c'}));
        assertNotEquals(new Cadena("abc"), new Cadena("abd"));
        assertNotEquals(new Cadena("abc"), "abc");
    }
}

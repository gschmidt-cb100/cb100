package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CadenaTest {

    @Test
    @DisplayName("largo y caracterEn responden sobre el contenido construido")
    void largoYCaracterEn() {
        Cadena cadena = new Cadena("hola");
        assertEquals(4, cadena.largo());
        assertEquals('h', cadena.caracterEn(0));
        assertEquals('a', cadena.caracterEn(3));
    }

    @Test
    @DisplayName("la cadena vacía tiene largo 0")
    void cadenaVacia() {
        Cadena vacia = new Cadena("");
        assertEquals(0, vacia.largo());
        assertFalse(vacia.contiene('a'));
        assertEquals("", vacia.toString());
    }

    @Test
    @DisplayName("contiene encuentra caracteres presentes y no los ausentes")
    void contiene() {
        Cadena cadena = new Cadena("programar");
        assertTrue(cadena.contiene('g'));
        assertTrue(cadena.contiene('r'));
        assertFalse(cadena.contiene('z'));
    }

    @Test
    @DisplayName("enMayusculas devuelve una cadena nueva y no modifica la original")
    void enMayusculasEsInmutable() {
        Cadena original = new Cadena("Hola");
        Cadena mayusculas = original.enMayusculas();
        assertEquals("HOLA", mayusculas.toString());
        assertEquals("Hola", original.toString());
        assertNotSame(original, mayusculas);
    }

    @Test
    @DisplayName("invertida devuelve una cadena nueva y no modifica la original")
    void invertidaEsInmutable() {
        Cadena original = new Cadena("neuquen");
        Cadena invertida = original.invertida();
        assertEquals("neuquen", invertida.toString());
        assertEquals("neuquen", original.toString());

        Cadena otra = new Cadena("abc");
        assertEquals("cba", otra.invertida().toString());
        assertEquals("abc", otra.toString());
    }

    @Test
    @DisplayName("equals compara por contenido y toString muestra todo")
    void equalsYToString() {
        Cadena una = new Cadena("test");
        Cadena otra = new Cadena(new char[]{'t', 'e', 's', 't'});
        assertEquals(una, otra);
        assertEquals(una.hashCode(), otra.hashCode());
        assertNotEquals(una, new Cadena("otra"));
        assertNotEquals(una, "test");
        assertEquals("test", una.toString());
    }

    @Test
    @DisplayName("el constructor desde char[] hace copia defensiva")
    void copiaDefensivaEnConstructor() {
        char[] origen = {'a', 'b', 'c'};
        Cadena cadena = new Cadena(origen);
        origen[0] = 'z';
        assertEquals('a', cadena.caracterEn(0), "modificar el arreglo original no debe afectar la cadena");
    }

    @Test
    @DisplayName("parámetros inválidos lanzan excepciones claras")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Cadena((String) null));
        assertThrows(IllegalArgumentException.class, () -> new Cadena((char[]) null));

        Cadena cadena = new Cadena("hola");
        assertThrows(IllegalArgumentException.class, () -> cadena.caracterEn(-1));
        assertThrows(IllegalArgumentException.class, () -> cadena.caracterEn(4));
    }
}

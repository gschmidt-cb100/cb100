package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IndiceInvertidoTest {

    @Test
    @DisplayName("Cada palabra apunta a los documentos que la contienen")
    void indexaBasico() {
        Map<String, Set<String>> indice = IndiceInvertido.indexar(Map.of(
                "a.txt", "hola mundo",
                "b.txt", "chau mundo"));

        assertEquals(Set.of("a.txt", "b.txt"), indice.get("mundo"));
        assertEquals(Set.of("a.txt"), indice.get("hola"));
        assertEquals(Set.of("b.txt"), indice.get("chau"));
    }

    @Test
    @DisplayName("Normaliza a minúsculas: Hola y hola son la misma palabra")
    void normalizaMinusculas() {
        Map<String, Set<String>> indice = IndiceInvertido.indexar(Map.of(
                "a.txt", "Hola",
                "b.txt", "hola"));

        assertEquals(Set.of("a.txt", "b.txt"), indice.get("hola"));
        assertNull(indice.get("Hola"));
    }

    @Test
    @DisplayName("Una palabra repetida en el mismo documento no duplica el nombre")
    void noDuplicaDocumentos() {
        Map<String, Set<String>> indice = IndiceInvertido.indexar(Map.of(
                "a.txt", "bondi bondi bondi"));

        assertEquals(Set.of("a.txt"), indice.get("bondi"));
    }

    @Test
    @DisplayName("Separa por caracteres que no son letras (comas, números, signos)")
    void separaPorNoLetras() {
        Map<String, Set<String>> indice = IndiceInvertido.indexar(Map.of(
                "a.txt", "uno,dos... tres123cuatro"));

        assertTrue(indice.containsKey("uno"));
        assertTrue(indice.containsKey("dos"));
        assertTrue(indice.containsKey("tres"));
        assertTrue(indice.containsKey("cuatro"));
        assertEquals(4, indice.size());
    }

    @Test
    @DisplayName("Con documentos vacíos devuelve un índice vacío")
    void documentosVacios() {
        assertTrue(IndiceInvertido.indexar(Map.of("a.txt", "")).isEmpty());
    }
}

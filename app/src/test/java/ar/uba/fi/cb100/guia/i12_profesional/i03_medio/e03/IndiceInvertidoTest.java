package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndiceInvertidoTest {

    @Test
    @DisplayName("Cada palabra apunta a los documentos donde aparece")
    void indexaVariosDocumentos() {
        Map<String, Set<Integer>> indice = IndiceInvertido.indexar(List.of(
                "java es un lenguaje",
                "python es otro lenguaje",
                "java usa la jvm"));

        assertEquals(Set.of(0, 2), indice.get("java"));
        assertEquals(Set.of(0, 1), indice.get("lenguaje"));
        assertEquals(Set.of(0, 1), indice.get("es"));
        assertEquals(Set.of(2), indice.get("jvm"));
        assertNull(indice.get("cobol"));
    }

    @Test
    @DisplayName("Normaliza a minúsculas y no repite índices")
    void minusculasYSinRepetidos() {
        Map<String, Set<Integer>> indice = IndiceInvertido.indexar(List.of(
                "Java JAVA java",
                "Java otra vez"));

        // Las tres variantes de mayúsculas son la MISMA palabra...
        assertEquals(Set.of(0, 1), indice.get("java"));
        assertNull(indice.get("Java"));
        // ...y aunque aparezca tres veces en el documento 0, el índice 0
        // figura una sola vez (para eso el Set).
        assertEquals(2, indice.get("java").size());
    }

    @Test
    @DisplayName("Lista vacía y espacios múltiples no rompen nada")
    void casosBorde() {
        assertTrue(IndiceInvertido.indexar(List.of()).isEmpty());

        Map<String, Set<Integer>> indice =
                IndiceInvertido.indexar(List.of("  hola   mundo  "));
        assertEquals(Set.of(0), indice.get("hola"));
        assertEquals(Set.of(0), indice.get("mundo"));
        assertEquals(2, indice.size());              // ninguna "palabra" vacía
    }
}

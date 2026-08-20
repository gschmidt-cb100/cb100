package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GruposDeAnagramasTest {

    @Test
    @DisplayName("Junta en un grupo las palabras que son anagramas")
    void agrupaAnagramas() {
        Collection<List<String>> grupos =
                GruposDeAnagramas.grupos(List.of("roma", "amor", "sol"));

        assertEquals(2, grupos.size());
        assertTrue(grupos.contains(List.of("roma", "amor")));
        assertTrue(grupos.contains(List.of("sol")));
    }

    @Test
    @DisplayName("Palabras sin anagramas quedan cada una en su propio grupo")
    void sinAnagramas() {
        Collection<List<String>> grupos =
                GruposDeAnagramas.grupos(List.of("uno", "dos", "tres"));

        assertEquals(3, grupos.size());
    }

    @Test
    @DisplayName("Con lista vacía devuelve una colección vacía")
    void listaVacia() {
        assertTrue(GruposDeAnagramas.grupos(List.of()).isEmpty());
    }

    @Test
    @DisplayName("Palabras repetidas van juntas al mismo grupo")
    void palabrasRepetidas() {
        Collection<List<String>> grupos =
                GruposDeAnagramas.grupos(List.of("ala", "ala", "aal"));

        assertEquals(1, grupos.size());
        assertTrue(grupos.contains(List.of("ala", "ala", "aal")));
    }

    @Test
    @DisplayName("No mezcla palabras con letras distintas aunque compartan longitud")
    void mismaLongitudNoEsAnagrama() {
        Collection<List<String>> grupos =
                GruposDeAnagramas.grupos(List.of("abc", "abd"));

        assertEquals(2, grupos.size());
    }
}

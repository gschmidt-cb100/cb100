package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PalabrasOrdenadasTest {

    @Test
    @DisplayName("elimina repetidos y ordena alfabeticamente")
    void eliminaYOrdena() {
        List<String> resultado = PalabrasOrdenadas.unicasOrdenadas(
                List.of("pera", "manzana", "pera", "banana", "manzana", "kiwi"));
        assertEquals(List.of("banana", "kiwi", "manzana", "pera"), resultado);
    }

    @Test
    @DisplayName("lista vacia devuelve lista vacia")
    void listaVacia() {
        assertTrue(PalabrasOrdenadas.unicasOrdenadas(List.of()).isEmpty());
    }

    @Test
    @DisplayName("una lista ya ordenada y sin repetidos queda igual")
    void yaOrdenada() {
        List<String> resultado = PalabrasOrdenadas.unicasOrdenadas(List.of("ana", "beto", "caro"));
        assertEquals(List.of("ana", "beto", "caro"), resultado);
    }

    @Test
    @DisplayName("todas las palabras iguales dejan un solo elemento")
    void todasIguales() {
        assertEquals(List.of("hola"), PalabrasOrdenadas.unicasOrdenadas(List.of("hola", "hola", "hola")));
    }
}

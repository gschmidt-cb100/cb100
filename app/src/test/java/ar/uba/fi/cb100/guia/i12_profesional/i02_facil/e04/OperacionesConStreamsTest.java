package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OperacionesConStreamsTest {

    private static final List<Integer> DATOS = List.of(3, 8, 5, 2, 7, 4);

    @Test
    @DisplayName("sumaDePares suma solo los pares")
    void sumaDePares() {
        assertEquals(14, OperacionesConStreams.sumaDePares(DATOS));  // 8 + 2 + 4
    }

    @Test
    @DisplayName("sumaDePares de una lista sin pares es 0")
    void sumaSinPares() {
        assertEquals(0, OperacionesConStreams.sumaDePares(List.of(1, 3, 5)));
    }

    @Test
    @DisplayName("imparesAlCuadradoDesc filtra, eleva y ordena de mayor a menor")
    void imparesAlCuadrado() {
        assertEquals(List.of(49, 25, 9), OperacionesConStreams.imparesAlCuadradoDesc(DATOS));
    }

    @Test
    @DisplayName("imparesAlCuadradoDesc sin impares devuelve lista vacia")
    void sinImpares() {
        assertTrue(OperacionesConStreams.imparesAlCuadradoDesc(List.of(2, 4)).isEmpty());
    }

    @Test
    @DisplayName("maximo devuelve el mayor envuelto en Optional")
    void maximoPresente() {
        Optional<Integer> max = OperacionesConStreams.maximo(DATOS);
        assertTrue(max.isPresent());
        assertEquals(8, max.get());
    }

    @Test
    @DisplayName("maximo de lista vacia es Optional.empty, no null ni excepcion")
    void maximoDeVacia() {
        assertEquals(Optional.empty(), OperacionesConStreams.maximo(List.of()));
    }
}

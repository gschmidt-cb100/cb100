package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeduplicadorTest {

    @Test
    @DisplayName("Elimina duplicados conservando la primera aparición")
    void conservaPrimeraAparicion() {
        assertEquals(List.of(3, 1, 2), Deduplicador.sinDuplicados(List.of(3, 1, 3, 2, 1, 3)));
        assertEquals(List.of("ana", "beto", "carla"),
                Deduplicador.sinDuplicados(List.of("ana", "beto", "ana", "carla", "beto")));
    }

    @Test
    @DisplayName("Lista sin duplicados y lista vacía quedan iguales")
    void casosSinCambios() {
        assertEquals(List.of(1, 2, 3), Deduplicador.sinDuplicados(List.of(1, 2, 3)));
        assertTrue(Deduplicador.sinDuplicados(List.of()).isEmpty());
        assertEquals(List.of(7), Deduplicador.sinDuplicados(List.of(7, 7, 7, 7)));
    }

    @Test
    @DisplayName("Con muchos elementos coincide con la versión O(n cuadrado)")
    void coincideConLaVersionLenta() {
        Random azar = new Random(42);               // semilla fija: determinista
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            datos.add(azar.nextInt(80));            // muchos repetidos seguro
        }

        // La versión lenta pero obviamente correcta, como oráculo.
        List<Integer> esperado = new ArrayList<>();
        for (Integer x : datos) {
            if (!esperado.contains(x)) {            // contains O(n) -> total O(n^2)
                esperado.add(x);
            }
        }

        assertEquals(esperado, Deduplicador.sinDuplicados(datos));
    }
}

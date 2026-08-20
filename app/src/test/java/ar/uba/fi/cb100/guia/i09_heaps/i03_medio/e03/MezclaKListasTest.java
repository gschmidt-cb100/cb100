package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MezclaKListasTest {

    @Test
    @DisplayName("Tres listas, una de ellas vacía, se mezclan en orden")
    void tresListasConUnaVacia() {
        List<List<Integer>> listas = List.of(
                List.of(1, 4, 7),
                List.of(),
                List.of(2, 3, 8));
        assertEquals(List.of(1, 2, 3, 4, 7, 8), MezclaKListas.mezclar(listas));
    }

    @Test
    @DisplayName("Listas de largos distintos: no se pierde ningún elemento")
    void largosDistintos() {
        List<List<Integer>> listas = List.of(
                List.of(10),
                List.of(1, 2, 3, 4, 5),
                List.of(0, 6));
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 10),
                MezclaKListas.mezclar(listas));
    }

    @Test
    @DisplayName("Valores repetidos entre listas aparecen todas las veces")
    void valoresRepetidos() {
        List<List<Integer>> listas = List.of(
                List.of(2, 2, 5),
                List.of(2, 5));
        assertEquals(List.of(2, 2, 2, 5, 5), MezclaKListas.mezclar(listas));
    }

    @Test
    @DisplayName("Una sola lista sale igual que entró")
    void unaSolaLista() {
        List<List<Integer>> listas = List.of(List.of(3, 9, 27));
        assertEquals(List.of(3, 9, 27), MezclaKListas.mezclar(listas));
    }

    @Test
    @DisplayName("Sin listas (o todas vacías) el resultado es vacío")
    void todasVacias() {
        assertEquals(List.of(), MezclaKListas.mezclar(List.of()));
        assertEquals(List.of(), MezclaKListas.mezclar(List.of(List.of(), List.of())));
    }
}

package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnionListasTest {

    @Test
    @DisplayName("union con elementos comunes sin duplicados")
    void unionConComunes() {
        assertEquals(List.of(1, 2, 3, 4, 5), UnionListas.union(List.of(1, 2, 3), List.of(3, 4, 5)));
    }

    @Test
    @DisplayName("union con una lista vacia")
    void unaVacia() {
        assertEquals(List.of(1, 2, 3), UnionListas.union(List.of(1, 2, 3), List.of()));
    }

    @Test
    @DisplayName("ambas vacias devuelve lista vacia")
    void ambasVacias() {
        assertEquals(List.of(), UnionListas.union(List.of(), List.of()));
    }

    @Test
    @DisplayName("respeta el orden estable: primero a, luego los nuevos de b")
    void ordenEstable() {
        assertEquals(List.of(5, 3, 1, 2, 4), UnionListas.union(List.of(5, 3, 1), List.of(1, 2, 3, 4)));
    }
}

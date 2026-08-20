package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanciaDeEdicionTest {

    @Test
    @DisplayName("De casa a calle hay 3 ediciones")
    void casaACalle() {
        assertEquals(3, DistanciaDeEdicion.distancia("casa", "calle"));
    }

    @Test
    @DisplayName("Cadenas iguales: distancia 0")
    void iguales() {
        assertEquals(0, DistanciaDeEdicion.distancia("algoritmo", "algoritmo"));
        assertEquals(0, DistanciaDeEdicion.distancia("", ""));
    }

    @Test
    @DisplayName("Contra la cadena vacía la distancia es el largo de la otra")
    void contraVacia() {
        assertEquals(5, DistanciaDeEdicion.distancia("", "gatos"));
        assertEquals(5, DistanciaDeEdicion.distancia("perro", ""));
    }

    @Test
    @DisplayName("Un solo reemplazo: gato -> pato es distancia 1")
    void unReemplazo() {
        assertEquals(1, DistanciaDeEdicion.distancia("gato", "pato"));
    }

    @Test
    @DisplayName("La distancia es simétrica: d(a,b) == d(b,a)")
    void simetria() {
        assertEquals(DistanciaDeEdicion.distancia("casa", "calle"),
                DistanciaDeEdicion.distancia("calle", "casa"));
    }
}

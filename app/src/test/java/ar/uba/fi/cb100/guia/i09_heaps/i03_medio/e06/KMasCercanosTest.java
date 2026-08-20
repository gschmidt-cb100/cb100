package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KMasCercanosTest {

    @Test
    @DisplayName("De cuatro puntos, los 2 más cercanos al origen")
    void dosMasCercanos() {
        List<Punto> puntos = List.of(
                new Punto(3, 4),    // distancia 5
                new Punto(1, 1),    // distancia ~1.41
                new Punto(0, 5),    // distancia 5
                new Punto(-2, 0));  // distancia 2
        assertEquals(List.of(new Punto(1, 1), new Punto(-2, 0)),
                KMasCercanos.kMasCercanos(puntos, 2));
    }

    @Test
    @DisplayName("El resultado viene ordenado de más cercano a más lejano")
    void ordenPorDistancia() {
        List<Punto> puntos = List.of(
                new Punto(0, 3), new Punto(0, 1), new Punto(0, 2));
        assertEquals(
                List.of(new Punto(0, 1), new Punto(0, 2), new Punto(0, 3)),
                KMasCercanos.kMasCercanos(puntos, 3));
    }

    @Test
    @DisplayName("Con k mayor que la cantidad de puntos devuelve todos")
    void kMayorQueN() {
        List<Punto> puntos = List.of(new Punto(1, 0), new Punto(0, -4));
        assertEquals(2, KMasCercanos.kMasCercanos(puntos, 10).size());
    }

    @Test
    @DisplayName("Las coordenadas negativas cuentan por su distancia, no su signo")
    void coordenadasNegativas() {
        List<Punto> puntos = List.of(new Punto(-1, -1), new Punto(3, 0));
        assertEquals(List.of(new Punto(-1, -1)),
                KMasCercanos.kMasCercanos(puntos, 1));
    }

    @Test
    @DisplayName("k = 0 lanza IllegalArgumentException")
    void kInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> KMasCercanos.kMasCercanos(List.of(new Punto(1, 1)), 0));
    }
}

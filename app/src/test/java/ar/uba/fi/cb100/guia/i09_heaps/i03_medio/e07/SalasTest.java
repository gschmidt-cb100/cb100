package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalasTest {

    @Test
    @DisplayName("[(0,30), (5,10), (15,20)] necesita 2 salas")
    void ejemploClasico() {
        List<Reunion> reuniones = List.of(
                new Reunion(0, 30), new Reunion(5, 10), new Reunion(15, 20));
        assertEquals(2, Salas.salasNecesarias(reuniones));
    }

    @Test
    @DisplayName("Reuniones sin solapamiento comparten una sola sala")
    void sinSolapamiento() {
        List<Reunion> reuniones = List.of(
                new Reunion(0, 5), new Reunion(5, 10), new Reunion(10, 15));
        assertEquals(1, Salas.salasNecesarias(reuniones));
    }

    @Test
    @DisplayName("Tres reuniones todas superpuestas necesitan 3 salas")
    void todasSuperpuestas() {
        List<Reunion> reuniones = List.of(
                new Reunion(0, 10), new Reunion(2, 12), new Reunion(4, 14));
        assertEquals(3, Salas.salasNecesarias(reuniones));
    }

    @Test
    @DisplayName("El orden en la lista de entrada no cambia el resultado")
    void entradaDesordenada() {
        List<Reunion> reuniones = List.of(
                new Reunion(15, 20), new Reunion(0, 30), new Reunion(5, 10));
        assertEquals(2, Salas.salasNecesarias(reuniones));
    }

    @Test
    @DisplayName("Una que termina a las 10 y otra que empieza a las 10 no chocan")
    void bordeExacto() {
        List<Reunion> reuniones = List.of(new Reunion(0, 10), new Reunion(10, 20));
        assertEquals(1, Salas.salasNecesarias(reuniones));
    }

    @Test
    @DisplayName("Sin reuniones no hace falta ninguna sala")
    void sinReuniones() {
        assertEquals(0, Salas.salasNecesarias(List.of()));
    }
}

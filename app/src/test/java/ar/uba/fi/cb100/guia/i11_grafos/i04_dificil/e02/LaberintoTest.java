package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaberintoTest {

    @Test
    @DisplayName("El camino mas corto tiene la cantidad exacta de pasos (5, calculado a mano)")
    void caminoMasCorto() {
        // E..#     E esta en (0,0) y S en (2,3). Distancia Manhattan = 2 + 3 = 5,
        // .#.#     y existe un camino de 5 pasos (por ejemplo bajar dos veces y
        // ...S     doblar a la derecha), asi que el minimo es exactamente 5.
        Laberinto laberinto = new Laberinto(
                "E..#",
                ".#.#",
                "...S");
        assertEquals(5, laberinto.resolver().pasos());
    }

    @Test
    @DisplayName("El camino queda marcado con '*' y E y S conservan su letra")
    void caminoMarcado() {
        Laberinto laberinto = new Laberinto(
                "E..#",
                ".#.#",
                "...S");
        char[][] marcado = laberinto.resolver().marcado();

        assertEquals('E', marcado[0][0]);
        assertEquals('S', marcado[2][3]);
        // Un camino de 5 pasos toca exactamente 4 celdas intermedias.
        int marcas = 0;
        for (char[] fila : marcado) {
            for (char celda : fila) {
                if (celda == Laberinto.MARCA) {
                    marcas++;
                }
            }
        }
        assertEquals(4, marcas, "pasos - 1 celdas intermedias marcadas");
    }

    @Test
    @DisplayName("Las celdas marcadas forman un camino conexo de E a S")
    void marcasContiguas() {
        Laberinto laberinto = new Laberinto(
                "E...#",
                "####.",
                "....S");   // sin camino corto: hay que rodear por la derecha... no, esta cortado
        // Ese laberinto no tiene solucion por la fila de paredes; usamos otro conexo.
        Laberinto conSolucion = new Laberinto(
                "E...#",
                "###.#",
                "..S..");
        Laberinto.Solucion solucion = conSolucion.resolver();
        // Camino unico: (0,0)->(0,1)->(0,2)->(0,3)->(1,3)->(2,3)->(2,2) = 6 pasos.
        assertEquals(6, solucion.pasos());
        assertEquals(-1, laberinto.resolver().pasos());
        char[][] marcado = solucion.marcado();
        assertEquals(Laberinto.MARCA, marcado[0][1]);
        assertEquals(Laberinto.MARCA, marcado[0][2]);
        assertEquals(Laberinto.MARCA, marcado[0][3]);
        assertEquals(Laberinto.MARCA, marcado[1][3]);
        assertEquals(Laberinto.MARCA, marcado[2][3]);
        assertEquals('S', marcado[2][2]);
    }

    @Test
    @DisplayName("Un laberinto sin salida devuelve -1 (convencion documentada) y no marca nada")
    void sinSalida() {
        Laberinto imposible = new Laberinto(
                "E#S",
                ".#.");
        Laberinto.Solucion solucion = imposible.resolver();
        assertEquals(-1, solucion.pasos());
        for (char[] fila : solucion.marcado()) {
            for (char celda : fila) {
                assertNotEquals(Laberinto.MARCA, celda);
            }
        }
    }

    @Test
    @DisplayName("Entrada y salida vecinas: un solo paso")
    void unPaso() {
        assertEquals(1, new Laberinto("ES").resolver().pasos());
    }

    @Test
    @DisplayName("Un laberinto sin entrada o sin salida es invalido")
    void grillaInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Laberinto("...", ".S.").resolver());
        assertThrows(IllegalArgumentException.class,
                () -> new Laberinto("E..", "...").resolver());
    }
}

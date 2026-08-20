package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopDeProductosTest {

    @Test
    @DisplayName("Devuelve los K productos de mayor total, en orden")
    void casoChicoAMano() {
        List<TopDeProductos.Venta> ventas = List.of(
                new TopDeProductos.Venta("Ana", "cuaderno", 3500),
                new TopDeProductos.Venta("Ana", "mochila", 25000),
                new TopDeProductos.Venta("Carla", "calculadora", 30000),
                new TopDeProductos.Venta("Beto", "resma", 8000),
                new TopDeProductos.Venta("Carla", "resma", 8000),   // resma: 16000
                new TopDeProductos.Venta("Beto", "cuaderno", 3500)); // cuaderno: 7000

        assertEquals(List.of("calculadora"), TopDeProductos.topProductos(ventas, 1));
        assertEquals(List.of("calculadora", "mochila"),
                TopDeProductos.topProductos(ventas, 2));
        assertEquals(List.of("calculadora", "mochila", "resma", "cuaderno"),
                TopDeProductos.topProductos(ventas, 4));
    }

    @Test
    @DisplayName("K mayor que la cantidad de productos devuelve todos")
    void kGrandeYCasosBorde() {
        List<TopDeProductos.Venta> ventas = List.of(
                new TopDeProductos.Venta("Ana", "cuaderno", 100),
                new TopDeProductos.Venta("Ana", "lapicera", 200));
        assertEquals(List.of("lapicera", "cuaderno"),
                TopDeProductos.topProductos(ventas, 10));
        assertTrue(TopDeProductos.topProductos(ventas, 0).isEmpty());
        assertTrue(TopDeProductos.topProductos(List.of(), 3).isEmpty());
    }

    @Test
    @DisplayName("Coincide con ordenar todo y cortar, en datos aleatorios")
    void coincideConElOraculo() {
        Random azar = new Random(123);              // semilla fija: determinista
        List<TopDeProductos.Venta> ventas = new ArrayList<>();
        String[] vendedores = {"Ana", "Beto", "Carla"};
        for (int i = 0; i < 500; i++) {
            ventas.add(new TopDeProductos.Venta(
                    vendedores[azar.nextInt(vendedores.length)],
                    "producto" + azar.nextInt(40),
                    100 + azar.nextInt(5000)));
        }
        for (int k : new int[] {1, 3, 5, 10, 40, 100}) {
            assertEquals(TopDeProductos.topProductosOrdenandoTodo(ventas, k),
                    TopDeProductos.topProductos(ventas, k),
                    "difieren para k=" + k);
        }
    }
}

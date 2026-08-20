package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    @DisplayName("compareTo ordena por precio ascendente")
    void compareToPorPrecio() {
        Producto barato = new Producto("A", 100.0);
        Producto caro = new Producto("B", 500.0);

        assertTrue(barato.compareTo(caro) < 0);
        assertTrue(caro.compareTo(barato) > 0);
        assertEquals(0, barato.compareTo(new Producto("C", 100.0)));
    }

    @Test
    @DisplayName("Arrays.sort ordena el arreglo por precio usando el orden natural")
    void arraysSortPorOrdenNatural() {
        Producto[] productos = {
                new Producto("Monitor", 180000.0),
                new Producto("Cable", 3000.0),
                new Producto("Mouse", 12000.0)
        };

        Arrays.sort(productos);

        String[] nombresEsperados = {"Cable", "Mouse", "Monitor"};
        String[] nombresObtenidos = new String[productos.length];
        for (int i = 0; i < productos.length; i++) {
            nombresObtenidos[i] = productos[i].getNombre();
        }
        assertArrayEquals(nombresEsperados, nombresObtenidos);
    }

    @Test
    @DisplayName("Se puede ordenar en sentido inverso con un Comparator explícito")
    void ordenInversoConComparator() {
        Producto[] productos = {
                new Producto("A", 100.0),
                new Producto("B", 300.0),
                new Producto("C", 200.0)
        };

        Arrays.sort(productos, Comparator.reverseOrder());

        assertEquals(300.0, productos[0].getPrecio());
        assertEquals(200.0, productos[1].getPrecio());
        assertEquals(100.0, productos[2].getPrecio());
    }
}

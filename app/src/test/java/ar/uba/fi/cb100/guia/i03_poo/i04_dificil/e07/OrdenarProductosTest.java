package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

class OrdenarProductosTest {

    @Test
    @DisplayName("Ordena por precio ascendente con un Comparator lambda")
    void ordenaAscendente() {
        Producto[] productos = {
                new Producto("Café", 1500.0),
                new Producto("Azúcar", 800.0),
                new Producto("Leche", 1200.0)
        };

        Arrays.sort(productos, (a, b) -> Double.compare(a.precio(), b.precio()));

        String[] nombresEsperados = {"Azúcar", "Leche", "Café"};
        String[] nombresObtenidos = {
                productos[0].nombre(), productos[1].nombre(), productos[2].nombre()
        };
        assertArrayEquals(nombresEsperados, nombresObtenidos);
    }

    @Test
    @DisplayName("Ordena por precio descendente invirtiendo el comparador")
    void ordenaDescendente() {
        Producto[] productos = {
                new Producto("A", 10.0),
                new Producto("B", 30.0),
                new Producto("C", 20.0)
        };

        Comparator<Producto> porPrecio = Comparator.comparingDouble(Producto::precio);
        Arrays.sort(productos, porPrecio.reversed());

        assertEquals(30.0, productos[0].precio());
        assertEquals(20.0, productos[1].precio());
        assertEquals(10.0, productos[2].precio());
    }

    @Test
    @DisplayName("Un arreglo de un solo elemento queda igual")
    void unSoloElemento() {
        Producto[] productos = { new Producto("Único", 5.0) };
        Arrays.sort(productos, Comparator.comparingDouble(Producto::precio));
        assertEquals("Único", productos[0].nombre());
    }
}

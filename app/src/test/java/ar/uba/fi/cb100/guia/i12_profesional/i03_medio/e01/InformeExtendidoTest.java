package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InformeExtendidoTest {

    private InformeExtendido informe;

    @BeforeEach
    void preparar() {
        informe = new InformeExtendido(InformeExtendido.ventasDelApunte());
    }

    @Test
    @DisplayName("El total por producto coincide con la cuenta a mano")
    void totalPorProducto() {
        Map<String, Integer> totales = informe.totalPorProducto();
        assertEquals(10500, totales.get("cuaderno"));      // 3500 x 3
        assertEquals(2400, totales.get("lapicera"));       // 1200 x 2
        assertEquals(16000, totales.get("resma"));         // 8000 x 2
        assertEquals(25000, totales.get("mochila"));
        assertEquals(30000, totales.get("calculadora"));
        assertEquals(900, totales.get("corrector"));
        assertEquals(6, totales.size());
        // La suma de los productos tiene que dar el total general del apunte.
        int suma = totales.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(84800, suma);
    }

    @Test
    @DisplayName("La mayor venta de cada vendedor sale del merge de toMap")
    void mayorVentaPorVendedor() {
        Map<String, InformeExtendido.Venta> mayores = informe.mayorVentaPorVendedor();
        assertEquals(3, mayores.size());
        assertEquals("mochila", mayores.get("Ana").producto());
        assertEquals(25000, mayores.get("Ana").monto());
        assertEquals("resma", mayores.get("Beto").producto());
        assertEquals(8000, mayores.get("Beto").monto());
        assertEquals("calculadora", mayores.get("Carla").producto());
        assertEquals(30000, mayores.get("Carla").monto());
    }

    @Test
    @DisplayName("vendedoresConTotalDesde filtra por el total acumulado")
    void vendedoresConTotalDesde() {
        // Totales del apunte: Ana=29700, Beto=13600, Carla=41500.
        assertEquals(List.of("Ana", "Beto", "Carla"), informe.vendedoresConTotalDesde(0));
        assertEquals(List.of("Ana", "Carla"), informe.vendedoresConTotalDesde(20000));
        assertEquals(List.of("Carla"), informe.vendedoresConTotalDesde(41500));
        assertTrue(informe.vendedoresConTotalDesde(50000).isEmpty());
    }
}

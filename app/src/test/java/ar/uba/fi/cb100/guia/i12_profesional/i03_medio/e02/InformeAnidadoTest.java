package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InformeAnidadoTest {

    @Test
    @DisplayName("El mapa anidado responde vendedor -> producto -> total")
    void celdasPuntuales() {
        InformeAnidado informe = new InformeAnidado(InformeAnidado.ventasDelApunte());
        Map<String, Map<String, Integer>> tabla = informe.totalPorVendedorYProducto();

        assertEquals(1200, tabla.get("Ana").get("lapicera"));
        assertEquals(3500, tabla.get("Beto").get("cuaderno"));
        assertEquals(30000, tabla.get("Carla").get("calculadora"));
        // Ana nunca vendió resmas: la clave directamente no está.
        assertNull(tabla.get("Ana").get("resma"));
    }

    @Test
    @DisplayName("Los subtotales de cada vendedor suman su total del apunte")
    void consistenciaConTotales() {
        InformeAnidado informe = new InformeAnidado(InformeAnidado.ventasDelApunte());
        Map<String, Map<String, Integer>> tabla = informe.totalPorVendedorYProducto();

        assertEquals(3, tabla.size());
        assertEquals(29700, sumar(tabla.get("Ana")));
        assertEquals(13600, sumar(tabla.get("Beto")));
        assertEquals(41500, sumar(tabla.get("Carla")));
    }

    @Test
    @DisplayName("Ventas repetidas del mismo producto se acumulan en la celda")
    void acumulaRepetidos() {
        InformeAnidado informe = new InformeAnidado(java.util.List.of(
                new InformeAnidado.Venta("Ana", "cuaderno", 100),
                new InformeAnidado.Venta("Ana", "cuaderno", 250)));
        assertEquals(350,
                informe.totalPorVendedorYProducto().get("Ana").get("cuaderno"));
    }

    private static int sumar(Map<String, Integer> porProducto) {
        return porProducto.values().stream().mapToInt(Integer::intValue).sum();
    }
}

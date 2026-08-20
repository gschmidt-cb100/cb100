package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e01;

import ar.uba.fi.cb100.material.i12_profesional.InformeDeVentas.Venta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColectorDePorcentajesTest {

    private static final List<Venta> VENTAS = List.of(
            new Venta("Ana", "cuaderno", 3500),
            new Venta("Beto", "lapicera", 1200),
            new Venta("Ana", "mochila", 25000),
            new Venta("Carla", "cuaderno", 3500),
            new Venta("Beto", "resma", 8000),
            new Venta("Ana", "lapicera", 1200),
            new Venta("Carla", "calculadora", 30000),
            new Venta("Beto", "cuaderno", 3500),
            new Venta("Carla", "resma", 8000),
            new Venta("Beto", "corrector", 900));

    @Test
    @DisplayName("coincide con el calculo en dos pasadas sobre los datos del apunte")
    public void coincideConDosPasadas() {
        // Referencia: dos pasadas (total primero, parciales despues).
        double total = VENTAS.stream().mapToInt(Venta::monto).sum();
        Map<String, Double> esperado = VENTAS.stream()
                .collect(Collectors.groupingBy(Venta::vendedor,
                        Collectors.summingDouble(Venta::monto)));

        Map<String, Double> obtenido =
                VENTAS.stream().collect(ColectorDePorcentajes.porcentajePorVendedor());

        assertEquals(3, obtenido.size());
        for (Map.Entry<String, Double> entrada : esperado.entrySet()) {
            double porcentajeEsperado = 100.0 * entrada.getValue() / total;
            assertEquals(porcentajeEsperado, obtenido.get(entrada.getKey()), 1e-9,
                    "porcentaje de " + entrada.getKey());
        }
    }

    @Test
    @DisplayName("valores del apunte: Ana~35.02, Beto~16.04, Carla~48.94, suman 100")
    public void valoresDelApunte() {
        Map<String, Double> porcentajes =
                VENTAS.stream().collect(ColectorDePorcentajes.porcentajePorVendedor());

        assertEquals(100.0 * 29700 / 84800, porcentajes.get("Ana"), 1e-9);
        assertEquals(100.0 * 13600 / 84800, porcentajes.get("Beto"), 1e-9);
        assertEquals(100.0 * 41500 / 84800, porcentajes.get("Carla"), 1e-9);
        assertEquals(35.02, porcentajes.get("Ana"), 0.01);
        assertEquals(16.04, porcentajes.get("Beto"), 0.01);
        assertEquals(48.94, porcentajes.get("Carla"), 0.01);

        double suma = porcentajes.values().stream().mapToDouble(Double::doubleValue).sum();
        assertEquals(100.0, suma, 1e-9);
    }

    @Test
    @DisplayName("un solo vendedor se lleva el 100%")
    public void unSoloVendedor() {
        List<Venta> ventas = List.of(
                new Venta("Ana", "cuaderno", 100),
                new Venta("Ana", "mochila", 900));
        Map<String, Double> porcentajes =
                ventas.stream().collect(ColectorDePorcentajes.porcentajePorVendedor());
        assertEquals(1, porcentajes.size());
        assertTrue(Math.abs(porcentajes.get("Ana") - 100.0) < 1e-9);
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e07;

import ar.uba.fi.cb100.material.i12_profesional.InformeDeVentas.Venta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsultaTest {

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
    @DisplayName("top 2 de Carla: calculadora 30000 y resma 8000")
    public void topDosDeCarla() {
        List<Venta> top = Consulta.desde(VENTAS)
                .donde(venta -> venta.vendedor().equals("Carla"))
                .ordenadoPor(Comparator.comparingInt(Venta::monto).reversed())
                .tomar(2)
                .lista();

        assertEquals(2, top.size());
        assertEquals(new Venta("Carla", "calculadora", 30000), top.get(0));
        assertEquals(new Venta("Carla", "resma", 8000), top.get(1));
    }

    @Test
    @DisplayName("agrupado por producto: el cuaderno aparece 3 veces")
    public void agrupadoPorProducto() {
        Map<String, List<Venta>> porProducto =
                Consulta.desde(VENTAS).agrupadoPor(Venta::producto);

        assertEquals(6, porProducto.size());
        assertEquals(3, porProducto.get("cuaderno").size());
        assertEquals(2, porProducto.get("lapicera").size());
        assertEquals(1, porProducto.get("mochila").size());
        assertTrue(porProducto.get("cuaderno").stream()
                .allMatch(venta -> venta.monto() == 3500));
    }

    @Test
    @DisplayName("los pasos tambien se aplican antes de agrupar")
    public void filtrarYAgrupar() {
        Map<String, List<Venta>> grandesPorVendedor = Consulta.desde(VENTAS)
                .donde(venta -> venta.monto() >= 8000)
                .agrupadoPor(Venta::vendedor);

        assertEquals(List.of(new Venta("Ana", "mochila", 25000)),
                grandesPorVendedor.get("Ana"));
        assertEquals(2, grandesPorVendedor.get("Carla").size());
        assertEquals(1, grandesPorVendedor.get("Beto").size());
    }

    @Test
    @DisplayName("la consulta es inmutable: agregar pasos no toca la original")
    public void consultaReutilizable() {
        Consulta<Venta> deAna = Consulta.desde(VENTAS)
                .donde(venta -> venta.vendedor().equals("Ana"));

        List<Venta> soloUna = deAna
                .ordenadoPor(Comparator.comparingInt(Venta::monto).reversed())
                .tomar(1)
                .lista();

        assertEquals(1, soloUna.size());
        assertEquals("mochila", soloUna.get(0).producto());
        // La consulta base sigue devolviendo las 3 ventas de Ana.
        assertEquals(3, deAna.lista().size());
    }
}

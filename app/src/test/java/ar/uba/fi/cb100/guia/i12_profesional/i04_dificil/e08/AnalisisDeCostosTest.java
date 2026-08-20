package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e08;

import ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e08.AnalisisDeCostos.Escenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalisisDeCostosTest {

    @Test
    @DisplayName("escenario del apunte: lista ~10^9, hash ~10^4, gana HashSet")
    public void escenarioDelApunte() {
        Escenario escenario = new Escenario(10_000, 0, 100_000);
        Map<String, Long> costos = AnalisisDeCostos.estimaciones(escenario);

        assertEquals(500_000_000L, costos.get(AnalisisDeCostos.ARRAY_LIST));  // orden 10^9
        assertEquals(10_000L, costos.get(AnalisisDeCostos.HASH_SET));         // orden 10^4
        assertEquals(160_000L, costos.get(AnalisisDeCostos.TREE_SET));        // 10^4 * log2(10^5)
        assertEquals(AnalisisDeCostos.HASH_SET, AnalisisDeCostos.optima(escenario));
        // La brecha del modelo (~50.000x) explica la medida (3236 ms vs 10 ms).
        assertTrue(costos.get(AnalisisDeCostos.ARRAY_LIST)
                / costos.get(AnalisisDeCostos.HASH_SET) > 10_000);
    }

    @Test
    @DisplayName("muchas busquedas sobre colecciones grandes: gana HashSet")
    public void ganaHashSet() {
        assertEquals(AnalisisDeCostos.HASH_SET,
                AnalisisDeCostos.optima(new Escenario(1_000, 1_000, 10_000)));
    }

    @Test
    @DisplayName("solo insertar al final, sin busquedas: gana ArrayList")
    public void ganaArrayList() {
        // Sin busquedas la lista es imbatible: insertar al final es O(1)
        // amortizado sin calcular hash ni rebalancear (empata con hash y
        // el modelo prefiere la estructura mas simple).
        Escenario soloCarga = new Escenario(0, 100_000, 50_000);
        assertEquals(AnalisisDeCostos.ARRAY_LIST, AnalisisDeCostos.optima(soloCarga));
        Map<String, Long> costos = AnalisisDeCostos.estimaciones(soloCarga);
        assertTrue(costos.get(AnalisisDeCostos.ARRAY_LIST)
                < costos.get(AnalisisDeCostos.TREE_SET));
    }

    @Test
    @DisplayName("coleccion de un elemento: gana TreeSet (log2(1)=0 en el modelo)")
    public void ganaTreeSet() {
        // Caso borde que expone los limites del modelo: con tamanio 1 el
        // descenso del arbol cuesta log2(1)=0, menos que el acceso "1" que
        // el modelo le cobra al hash.
        assertEquals(AnalisisDeCostos.TREE_SET,
                AnalisisDeCostos.optima(new Escenario(100, 10, 1)));
    }

    @Test
    @DisplayName("escenarios invalidos lanzan IllegalArgumentException")
    public void escenarioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Escenario(-1, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Escenario(0, 0, 0));
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResumenDeMontosTest {

    @Test
    @DisplayName("El resumen de los montos del apunte da los valores verificados")
    void montosDelApunte() {
        ResumenDeMontos resumen = new ResumenDeMontos(ResumenDeMontos.montosDelApunte());

        assertEquals(900, resumen.minimo());
        assertEquals(30000, resumen.maximo());
        assertEquals(84800L, resumen.suma());
        assertEquals(8480.0, resumen.promedio(), 1e-9);
        assertEquals(10L, resumen.cantidad());
    }

    @Test
    @DisplayName("Con un solo monto, min, max y promedio coinciden")
    void unSoloMonto() {
        ResumenDeMontos resumen = new ResumenDeMontos(List.of(500));

        assertEquals(500, resumen.minimo());
        assertEquals(500, resumen.maximo());
        assertEquals(500L, resumen.suma());
        assertEquals(500.0, resumen.promedio(), 1e-9);
        assertEquals(1L, resumen.cantidad());
    }

    @Test
    @DisplayName("La lista vacía tiene cantidad 0, suma 0 y promedio 0.0")
    void listaVacia() {
        ResumenDeMontos resumen = new ResumenDeMontos(List.of());

        assertEquals(0L, resumen.cantidad());
        assertEquals(0L, resumen.suma());
        assertEquals(0.0, resumen.promedio(), 1e-9);
        // Convención de IntSummaryStatistics sin datos:
        assertEquals(Integer.MAX_VALUE, resumen.minimo());
        assertEquals(Integer.MIN_VALUE, resumen.maximo());
    }
}

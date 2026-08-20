package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e06;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RedDeVuelosTest {

    // 0=Buenos Aires, 1=Rosario, 2=Córdoba, 3=Mendoza,
    // 4=Neuquén, 5=Bariloche, 6=Mar del Plata
    private Grafo red;

    @BeforeEach
    void armarRedDeCiudades() {
        red = new Grafo(7, false);
        red.agregarArista(0, 1, 300);     // BA - Rosario
        red.agregarArista(0, 6, 400);     // BA - Mar del Plata
        red.agregarArista(1, 2, 400);     // Rosario - Córdoba
        red.agregarArista(2, 3, 600);     // Córdoba - Mendoza
        red.agregarArista(0, 4, 1150);    // BA - Neuquén
        red.agregarArista(3, 4, 800);     // Mendoza - Neuquén
        red.agregarArista(4, 5, 430);     // Neuquén - Bariloche
    }

    @Test
    @DisplayName("Con 0 escalas desde BA: sólo los vuelos directos")
    void sinEscalas() {
        // 0 escalas = 1 tramo: Rosario, Neuquén y Mar del Plata.
        assertEquals(List.of(1, 4, 6), RedDeVuelos.destinosConHasta(red, 0, 0));
    }

    @Test
    @DisplayName("Con 1 escala desde BA se llega a toda la red")
    void unaEscala() {
        // 2 tramos ya alcanzan: Córdoba (vía Rosario), Mendoza y Bariloche (vía Neuquén).
        assertEquals(List.of(1, 2, 3, 4, 5, 6), RedDeVuelos.destinosConHasta(red, 0, 1));
    }

    @Test
    @DisplayName("Desde Bariloche con 1 escala: Neuquén, Mendoza y BA")
    void desdeBariloche() {
        // Bariloche-Neuquén (directo), y con una escala: Mendoza y BA.
        assertEquals(List.of(0, 3, 4), RedDeVuelos.destinosConHasta(red, 5, 1));
    }

    @Test
    @DisplayName("El origen nunca aparece en la lista de destinos")
    void sinElOrigen() {
        for (int escalas = 0; escalas <= 5; escalas++) {
            assertFalse(RedDeVuelos.destinosConHasta(red, 0, escalas).contains(0),
                    "el origen no es un destino");
        }
    }
}

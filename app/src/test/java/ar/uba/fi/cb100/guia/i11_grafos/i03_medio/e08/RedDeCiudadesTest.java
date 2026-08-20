package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RedDeCiudadesTest {

    private RedDeCiudades red;

    @BeforeEach
    void armarRedArgentina() {
        red = new RedDeCiudades(new String[] {
                "Buenos Aires", "Rosario", "Córdoba", "Mendoza",
                "Neuquén", "Bariloche", "Mar del Plata"});
        red.agregarRuta("Buenos Aires", "Rosario", 300);
        red.agregarRuta("Buenos Aires", "Mar del Plata", 400);
        red.agregarRuta("Rosario", "Córdoba", 400);
        red.agregarRuta("Córdoba", "Mendoza", 600);
        red.agregarRuta("Buenos Aires", "Neuquén", 1150);
        red.agregarRuta("Mendoza", "Neuquén", 800);
        red.agregarRuta("Neuquén", "Bariloche", 430);
    }

    @Test
    @DisplayName("BA -> Bariloche va por Neuquén: 1580 km")
    void rutaABariloche() {
        assertEquals("Buenos Aires -> Neuquén -> Bariloche (1580 km)",
                red.rutaMasCorta("Buenos Aires", "Bariloche"));
    }

    @Test
    @DisplayName("BA -> Mendoza: gana el camino con más tramos pero menos km")
    void rutaAMendoza() {
        // Vía Neuquén son 2 tramos pero 1950 km; vía Rosario y Córdoba, 1300 km.
        assertEquals("Buenos Aires -> Rosario -> Córdoba -> Mendoza (1300 km)",
                red.rutaMasCorta("Buenos Aires", "Mendoza"));
    }

    @Test
    @DisplayName("El viaje de una ciudad a sí misma es de 0 km")
    void rutaTrivial() {
        assertEquals("Rosario (0 km)", red.rutaMasCorta("Rosario", "Rosario"));
    }

    @Test
    @DisplayName("Una ciudad desconocida lanza IllegalArgumentException")
    void ciudadDesconocida() {
        assertThrows(IllegalArgumentException.class,
                () -> red.rutaMasCorta("Buenos Aires", "Ushuaia"));
    }
}

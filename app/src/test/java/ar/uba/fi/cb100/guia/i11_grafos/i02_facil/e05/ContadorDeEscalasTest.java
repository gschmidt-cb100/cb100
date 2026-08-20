package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e05;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e05.ContadorDeEscalas.*;
import static org.junit.jupiter.api.Assertions.*;

class ContadorDeEscalasTest {

    private Grafo red;

    @BeforeEach
    void armarRed() {
        red = redDeCiudades();
    }

    @Test
    @DisplayName("De Bariloche a Mar del Plata hay 2 escalas (Neuquen y Buenos Aires)")
    void barilocheAMarDelPlata() {
        assertEquals(2, escalas(red, BARILOCHE, MAR_DEL_PLATA));
        assertEquals(2, escalas(red, MAR_DEL_PLATA, BARILOCHE));   // simetrico
    }

    @Test
    @DisplayName("Un vuelo directo tiene 0 escalas")
    void vueloDirecto() {
        assertEquals(0, escalas(red, BUENOS_AIRES, ROSARIO));
        assertEquals(0, escalas(red, NEUQUEN, BARILOCHE));
    }

    @Test
    @DisplayName("De Rosario a Mendoza hay 1 escala (Cordoba)")
    void unaEscala() {
        assertEquals(1, escalas(red, ROSARIO, MENDOZA));
    }

    @Test
    @DisplayName("Quedarse en la misma ciudad son 0 escalas y una ciudad aislada da -1")
    void casosBorde() {
        assertEquals(0, escalas(red, CORDOBA, CORDOBA));
        Grafo conAislada = new Grafo(3, false);
        conAislada.agregarArista(0, 1);
        assertEquals(-1, escalas(conAislada, 0, 2));   // el vertice 2 quedo suelto
    }
}

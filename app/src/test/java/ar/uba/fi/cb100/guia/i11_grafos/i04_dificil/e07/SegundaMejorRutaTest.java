package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e07;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegundaMejorRutaTest {

    // Indices de la red de ciudades:
    private static final int BUENOS_AIRES = 0;
    private static final int ROSARIO = 1;
    private static final int CORDOBA = 2;
    private static final int MENDOZA = 3;
    private static final int NEUQUEN = 4;
    private static final int BARILOCHE = 5;

    @Test
    @DisplayName("El optimo BA->Bariloche es 1580 km via Neuquen (control previo)")
    void optimoDeControl() {
        Dijkstra.Resultado optimo =
                Dijkstra.caminosMinimos(SegundaMejorRuta.redDeCiudades(), BUENOS_AIRES);
        assertEquals(1580, optimo.distancia()[BARILOCHE]);
        assertEquals(List.of(BUENOS_AIRES, NEUQUEN, BARILOCHE), optimo.caminoHasta(BARILOCHE));
    }

    @Test
    @DisplayName("La segunda mejor BA->Bariloche es 2530 km (calculada a mano)")
    void segundaMejorABariloche() {
        // Quitando BA-Neuquen: BA-Ros-Cba-Mza-Nqn-Bch = 300+400+600+800+430 = 2530.
        // Quitando Neuquen-Bariloche: no hay otra llegada a Bariloche (INFINITO).
        // El mejor resultado distinto del optimo es 2530.
        SegundaMejorRuta.Resultado segunda = SegundaMejorRuta.calcular(
                SegundaMejorRuta.redDeCiudades(), BUENOS_AIRES, BARILOCHE);

        assertEquals(2530, segunda.distancia());
        assertEquals(List.of(BUENOS_AIRES, ROSARIO, CORDOBA, MENDOZA, NEUQUEN, BARILOCHE),
                segunda.camino());
    }

    @Test
    @DisplayName("La segunda mejor BA->Mendoza es 1950 km via Neuquen")
    void segundaMejorAMendoza() {
        // Optimo: BA-Ros-Cba-Mza = 300+400+600 = 1300.
        // Quitando BA-Ros o Ros-Cba o Cba-Mza, la alternativa es
        // BA-Nqn-Mza = 1150+800 = 1950 (verificado a mano en las tres remociones).
        SegundaMejorRuta.Resultado segunda = SegundaMejorRuta.calcular(
                SegundaMejorRuta.redDeCiudades(), BUENOS_AIRES, MENDOZA);
        assertEquals(1950, segunda.distancia());
        assertEquals(List.of(BUENOS_AIRES, NEUQUEN, MENDOZA), segunda.camino());
    }

    @Test
    @DisplayName("Si no existe ruta alternativa, devuelve INFINITO y camino vacio")
    void sinAlternativa() {
        // BA -> Mar del Plata: la unica ruta es la directa; sin ella no hay llegada.
        SegundaMejorRuta.Resultado segunda = SegundaMejorRuta.calcular(
                SegundaMejorRuta.redDeCiudades(), BUENOS_AIRES, 6);
        assertEquals(SegundaMejorRuta.INFINITO, segunda.distancia());
        assertTrue(segunda.camino().isEmpty());
    }

    @Test
    @DisplayName("La segunda mejor nunca es mas corta que la optima")
    void nuncaMejorQueLaOptima() {
        Grafo red = SegundaMejorRuta.redDeCiudades();
        Dijkstra.Resultado optimo = Dijkstra.caminosMinimos(red, BUENOS_AIRES);
        for (int destino = 1; destino < red.cantidadDeVertices(); destino++) {
            SegundaMejorRuta.Resultado segunda =
                    SegundaMejorRuta.calcular(red, BUENOS_AIRES, destino);
            assertTrue(segunda.distancia() >= optimo.distancia()[destino],
                    "fallo para el destino " + destino);
        }
    }

    @Test
    @DisplayName("Sin ningun camino al destino lanza IllegalArgumentException")
    void sinPrimerCamino() {
        Grafo grafo = new Grafo(3, false);
        grafo.agregarArista(0, 1, 10);           // el 2 queda aislado
        assertThrows(IllegalArgumentException.class,
                () -> SegundaMejorRuta.calcular(grafo, 0, 2));
    }
}

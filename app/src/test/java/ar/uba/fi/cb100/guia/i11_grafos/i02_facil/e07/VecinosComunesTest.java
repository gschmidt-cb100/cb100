package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e07;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VecinosComunesTest {

    private static final int ANA = 0, BETO = 1, CARLA = 2, DANA = 3, EVA = 4;

    private Grafo red;

    @BeforeEach
    void armarRed() {
        red = new Grafo(5, false);
        red.agregarArista(ANA, BETO);
        red.agregarArista(ANA, CARLA);
        red.agregarArista(BETO, CARLA);
        red.agregarArista(BETO, DANA);
        red.agregarArista(CARLA, EVA);
        red.agregarArista(DANA, EVA);
    }

    @Test
    @DisplayName("El unico amigo en comun de Ana y Dana es Beto")
    void anaYDana() {
        assertEquals(List.of(BETO), VecinosComunes.vecinosComunes(red, ANA, DANA));
    }

    @Test
    @DisplayName("La operacion es simetrica en no dirigidos: mismos comunes al reves")
    void simetrica() {
        assertEquals(VecinosComunes.vecinosComunes(red, ANA, DANA),
                VecinosComunes.vecinosComunes(red, DANA, ANA));
    }

    @Test
    @DisplayName("Dos amigos directos tambien pueden tener amigos en comun")
    void amigosDirectos() {
        // Ana y Beto son amigos, y ademas comparten a Carla.
        assertEquals(List.of(CARLA), VecinosComunes.vecinosComunes(red, ANA, BETO));
    }

    @Test
    @DisplayName("Sin amigos compartidos la lista queda vacia")
    void sinComunes() {
        Grafo cadena = new Grafo(4, false);   // 0-1-2-3: los extremos no comparten a nadie
        cadena.agregarArista(0, 1);
        cadena.agregarArista(1, 2);
        cadena.agregarArista(2, 3);
        assertTrue(VecinosComunes.vecinosComunes(cadena, 0, 3).isEmpty());
    }
}

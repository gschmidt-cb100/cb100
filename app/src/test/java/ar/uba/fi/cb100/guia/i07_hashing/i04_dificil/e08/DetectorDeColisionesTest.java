package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e08.DetectorDeColisiones.colisiones;

class DetectorDeColisionesTest {

    @Test
    @DisplayName("juan, eva y sol con m=8 caen las tres en 0: dos colisiones")
    void lasTresChocan() {
        assertEquals(2, colisiones(new String[] { "juan", "eva", "sol" }, 8));
    }

    @Test
    @DisplayName("ana, mia y leo con m=8 caen en indices distintos: cero colisiones")
    void ningunaChoca() {
        assertEquals(0, colisiones(new String[] { "ana", "mia", "leo" }, 8));
    }

    @Test
    @DisplayName("Con m=1 todas las claves menos la primera colisionan")
    void todasAlMismoIndice() {
        assertEquals(3, colisiones(new String[] { "a", "b", "c", "d" }, 1));
    }

    @Test
    @DisplayName("Sin claves no hay colisiones")
    void sinClaves() {
        assertEquals(0, colisiones(new String[] {}, 8));
    }

    @Test
    @DisplayName("Una clave repetida tambien cuenta como colision de indice")
    void claveRepetida() {
        // La definicion es sobre indices usados: la segunda "ana" cae en un
        // indice ya usado, asi que n - indicesDistintos = 2 - 1 = 1.
        assertEquals(1, colisiones(new String[] { "ana", "ana" }, 8));
    }

    @Test
    @DisplayName("Una capacidad invalida lanza excepcion")
    void capacidadInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> colisiones(new String[] { "ana" }, 0));
    }
}

package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColaAcotadaTest {

    @Test
    @DisplayName("Mientras hay lugar, todo elemento ofrecido entra")
    void conLugarEntraTodo() {
        ColaAcotada<Integer> cola = new ColaAcotada<>(3);
        assertTrue(cola.ofrecer(50));
        assertTrue(cola.ofrecer(10));
        assertTrue(cola.ofrecer(30));
        assertEquals(List.of(10, 30, 50), cola.contenidoOrdenado());
    }

    @Test
    @DisplayName("Llena, un elemento mejor (menor) desplaza al peor")
    void mejorDesplazaAlPeor() {
        ColaAcotada<Integer> cola = new ColaAcotada<>(3);
        cola.ofrecer(50);
        cola.ofrecer(10);
        cola.ofrecer(30);
        assertTrue(cola.ofrecer(20));   // 20 < 50: desplaza al 50
        assertEquals(List.of(10, 20, 30), cola.contenidoOrdenado());
        assertEquals(3, cola.tamanio());
    }

    @Test
    @DisplayName("Llena, un elemento peor que todos es rechazado")
    void peorEsRechazado() {
        ColaAcotada<Integer> cola = new ColaAcotada<>(2);
        cola.ofrecer(5);
        cola.ofrecer(8);
        assertFalse(cola.ofrecer(9));
        assertEquals(List.of(5, 8), cola.contenidoOrdenado());
    }

    @Test
    @DisplayName("Un elemento igual al peor no lo desplaza")
    void igualAlPeorNoEntra() {
        ColaAcotada<Integer> cola = new ColaAcotada<>(2);
        cola.ofrecer(5);
        cola.ofrecer(8);
        assertFalse(cola.ofrecer(8));
    }

    @Test
    @DisplayName("Funciona con Strings usando el orden alfabético")
    void conStrings() {
        ColaAcotada<String> cola = new ColaAcotada<>(2);
        cola.ofrecer("pera");
        cola.ofrecer("manzana");
        cola.ofrecer("ciruela");   // desplaza a "pera"
        assertEquals(List.of("ciruela", "manzana"), cola.contenidoOrdenado());
    }

    @Test
    @DisplayName("Capacidad 0 lanza IllegalArgumentException")
    void capacidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new ColaAcotada<Integer>(0));
    }
}

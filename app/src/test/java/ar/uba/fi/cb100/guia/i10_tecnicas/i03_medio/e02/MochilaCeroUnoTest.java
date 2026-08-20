package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MochilaCeroUnoTest {

    @Test
    @DisplayName("Caso clásico: pesos {2,3,4}, valores {3,4,5}, capacidad 5 -> 7")
    void casoClasico() {
        assertEquals(7, MochilaCeroUno.valorMaximo(
                new int[] {2, 3, 4}, new int[] {3, 4, 5}, 5));
    }

    @Test
    @DisplayName("La reconstrucción del caso clásico elige los objetos 0 y 1")
    void reconstruccion() {
        assertEquals(List.of(0, 1), MochilaCeroUno.indicesElegidos(
                new int[] {2, 3, 4}, new int[] {3, 4, 5}, 5));
    }

    @Test
    @DisplayName("Capacidad 0: no entra nada y el valor es 0")
    void capacidadCero() {
        assertEquals(0, MochilaCeroUno.valorMaximo(
                new int[] {1, 2}, new int[] {10, 20}, 0));
        assertTrue(MochilaCeroUno.indicesElegidos(
                new int[] {1, 2}, new int[] {10, 20}, 0).isEmpty());
    }

    @Test
    @DisplayName("Si todos los objetos entran, se lleva todo")
    void entraTodo() {
        assertEquals(12, MochilaCeroUno.valorMaximo(
                new int[] {1, 2, 3}, new int[] {3, 4, 5}, 10));
        assertEquals(List.of(0, 1, 2), MochilaCeroUno.indicesElegidos(
                new int[] {1, 2, 3}, new int[] {3, 4, 5}, 10));
    }

    @Test
    @DisplayName("Los índices elegidos respetan la capacidad y suman el valor máximo")
    void consistenciaDeLaReconstruccion() {
        int[] pesos = {5, 4, 6, 3};
        int[] valores = {10, 40, 30, 50};
        int capacidad = 10;
        List<Integer> elegidos =
                MochilaCeroUno.indicesElegidos(pesos, valores, capacidad);
        int peso = 0;
        int valor = 0;
        for (int i : elegidos) {
            peso += pesos[i];
            valor += valores[i];
        }
        assertTrue(peso <= capacidad);
        assertEquals(MochilaCeroUno.valorMaximo(pesos, valores, capacidad), valor);
    }
}

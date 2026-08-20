package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContarAparicionesTest {

    @Test
    @DisplayName("Cuenta un valor repetido")
    void valorRepetido() {
        int[] a = {1, 2, 2, 2, 3, 5, 5, 8};
        assertEquals(3, ContarApariciones.contar(a, 2));
        assertEquals(2, ContarApariciones.contar(a, 5));
    }

    @Test
    @DisplayName("Cuenta un valor unico y valores en los bordes")
    void valorUnicoYBordes() {
        int[] a = {1, 2, 2, 2, 3, 5, 5, 8};
        assertEquals(1, ContarApariciones.contar(a, 3));
        assertEquals(1, ContarApariciones.contar(a, 1)); // primer elemento
        assertEquals(1, ContarApariciones.contar(a, 8)); // ultimo elemento
    }

    @Test
    @DisplayName("Devuelve 0 cuando el valor no esta")
    void valorAusente() {
        int[] a = {1, 2, 2, 2, 3, 5, 5, 8};
        assertEquals(0, ContarApariciones.contar(a, 4));
        assertEquals(0, ContarApariciones.contar(a, 0));
        assertEquals(0, ContarApariciones.contar(a, 100));
    }

    @Test
    @DisplayName("Arreglo con todos los elementos iguales")
    void todosIguales() {
        int[] a = {7, 7, 7, 7, 7};
        assertEquals(5, ContarApariciones.contar(a, 7));
    }

    @Test
    @DisplayName("Casos borde: arreglo vacio")
    void arregloVacio() {
        assertEquals(0, ContarApariciones.contar(new int[]{}, 5));
    }
}

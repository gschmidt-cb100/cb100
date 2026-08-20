package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeraNoRepetidaTest {

    @Test
    @DisplayName("En 'banana' la primera no repetida es 'b'")
    void casoBanana() {
        assertEquals('b', PrimeraNoRepetida.primeraNoRepetida("banana"));
    }

    @Test
    @DisplayName("Importa el orden de aparición, no el alfabético")
    void ordenDeAparicion() {
        // Tanto 'z' como 'a' aparecen una vez, pero 'z' aparece primero.
        assertEquals('z', PrimeraNoRepetida.primeraNoRepetida("zbba"));
    }

    @Test
    @DisplayName("Si todos los caracteres se repiten devuelve null")
    void todosRepetidos() {
        assertNull(PrimeraNoRepetida.primeraNoRepetida("aabbcc"));
    }

    @Test
    @DisplayName("Con cadena vacía devuelve null")
    void cadenaVacia() {
        assertNull(PrimeraNoRepetida.primeraNoRepetida(""));
    }

    @Test
    @DisplayName("Distingue mayúsculas de minúsculas")
    void distingueMayusculas() {
        // 'A' y 'a' son caracteres distintos: 'A' no se repite.
        assertEquals('A', PrimeraNoRepetida.primeraNoRepetida("Aabb"));
    }
}

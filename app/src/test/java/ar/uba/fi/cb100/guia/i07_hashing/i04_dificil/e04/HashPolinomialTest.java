package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e04.HashPolinomial.hashPolinomial;

class HashPolinomialTest {

    @Test
    @DisplayName("Con base 31 y modulo grande coincide con String.hashCode")
    void coincideConHashCodeDeJava() {
        // Para palabras cortas el hashCode de Java no desborda el int,
        // asi que con base 31 el polinomio da exactamente lo mismo.
        int modulo = 1_000_000_007;
        for (String palabra : new String[] { "ana", "hash", "tabla", "clave", "sol" }) {
            assertEquals(Math.floorMod(palabra.hashCode(), modulo),
                    hashPolinomial(palabra, 31, modulo),
                    "difiere para: " + palabra);
        }
    }

    @Test
    @DisplayName("Valores concretos: 'ana' con base 31 da 96724")
    void valorConcreto() {
        // 'a'=97, 'n'=110: ((97*31 + 110)*31 + 97) = 96724.
        assertEquals(96724, hashPolinomial("ana", 31, 1_000_000_007));
    }

    @Test
    @DisplayName("El resultado siempre queda en [0, modulo)")
    void resultadoAcotado() {
        // Cadena larga: sin aritmetica long esto desbordaria.
        String larga = "abcdefghijklmnopqrstuvwxyz".repeat(10);
        int modulo = 1_000_000_007;
        int h = hashPolinomial(larga, 31, modulo);
        assertTrue(h >= 0 && h < modulo, "h=" + h);
        int chico = hashPolinomial(larga, 31, 8);
        assertTrue(chico >= 0 && chico < 8, "chico=" + chico);
    }

    @Test
    @DisplayName("La cadena vacia da 0 y una letra da su codigo")
    void casosBase() {
        assertEquals(0, hashPolinomial("", 31, 1_000_000_007));
        assertEquals(97, hashPolinomial("a", 31, 1_000_000_007));
    }

    @Test
    @DisplayName("El orden de los caracteres importa")
    void ordenImporta() {
        int modulo = 1_000_000_007;
        assertNotEquals(hashPolinomial("amor", 31, modulo),
                hashPolinomial("roma", 31, modulo));
    }

    @Test
    @DisplayName("Un modulo invalido lanza excepcion")
    void moduloInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> hashPolinomial("ana", 31, 0));
        assertThrows(IllegalArgumentException.class,
                () -> hashPolinomial("ana", 31, -5));
    }
}

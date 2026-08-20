package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTest {

    @Test
    @DisplayName("El ejemplo clásico {a:5, b:9, c:12, d:13, e:16, f:45} cuesta 224")
    void ejemploClasico() {
        Map<Character, Integer> frecuencias = Map.of(
                'a', 5, 'b', 9, 'c', 12, 'd', 13, 'e', 16, 'f', 45);
        assertEquals(224, Huffman.costoHuffman(frecuencias));
    }

    @Test
    @DisplayName("Dos símbolos: un bit cada uno, costo igual a la suma")
    void dosSimbolos() {
        // Árbol de una fusión: cada carácter usa 1 bit → 3 + 7 = 10.
        assertEquals(10, Huffman.costoHuffman(Map.of('x', 3, 'y', 7)));
    }

    @Test
    @DisplayName("Frecuencias iguales: árbol balanceado, 2 bits por símbolo")
    void frecuenciasIguales() {
        // 4 símbolos de frecuencia 1 → profundidad 2 para todos: 4 × 1 × 2 = 8.
        Map<Character, Integer> frecuencias = Map.of('a', 1, 'b', 1, 'c', 1, 'd', 1);
        assertEquals(8, Huffman.costoHuffman(frecuencias));
    }

    @Test
    @DisplayName("Un solo símbolo no necesita bits: costo 0")
    void unSoloSimbolo() {
        assertEquals(0, Huffman.costoHuffman(Map.of('z', 100)));
    }

    @Test
    @DisplayName("Sin símbolos el costo es 0")
    void mapaVacio() {
        assertEquals(0, Huffman.costoHuffman(Map.of()));
    }
}

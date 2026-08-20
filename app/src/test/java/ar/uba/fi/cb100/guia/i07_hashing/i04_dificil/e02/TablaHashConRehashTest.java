package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TablaHashConRehashTest {

    @Test
    @DisplayName("Con 20 claves desde capacidad 4 la tabla crece y todas se encuentran")
    void creceYConservaTodasLasClaves() {
        TablaHashConRehash<String, Integer> tabla = new TablaHashConRehash<>(4);
        for (int i = 0; i < 20; i++) {
            tabla.poner("clave" + i, i * 100);
        }
        assertEquals(20, tabla.tamanio());
        // 20 claves con alfa maximo 0.75 exigen capacidad > 26: 4 -> 8 -> 16 -> 32.
        assertTrue(tabla.capacidad() > 4, "la capacidad tenia que crecer");
        assertEquals(32, tabla.capacidad());
        // El rehash no puede perder ninguna clave.
        for (int i = 0; i < 20; i++) {
            assertEquals(i * 100, tabla.obtener("clave" + i));
        }
    }

    @Test
    @DisplayName("El factor de carga nunca supera 0.75 despues de poner")
    void factorDeCargaAcotado() {
        TablaHashConRehash<String, Integer> tabla = new TablaHashConRehash<>(4);
        for (int i = 0; i < 50; i++) {
            tabla.poner("k" + i, i);
            assertTrue(tabla.factorDeCarga() <= 0.75,
                    "alfa=" + tabla.factorDeCarga() + " con n=" + tabla.tamanio());
        }
    }

    @Test
    @DisplayName("Reemplazar un valor no dispara rehash ni duplica")
    void reemplazarNoAgranda() {
        TablaHashConRehash<String, Integer> tabla = new TablaHashConRehash<>(4);
        tabla.poner("ana", 1);
        tabla.poner("ana", 2);
        tabla.poner("ana", 3);
        assertEquals(1, tabla.tamanio());
        assertEquals(4, tabla.capacidad());
        assertEquals(3, tabla.obtener("ana"));
    }

    @Test
    @DisplayName("Quitar funciona despues de varios rehash")
    void quitarDespuesDeRehash() {
        TablaHashConRehash<String, Integer> tabla = new TablaHashConRehash<>(4);
        for (int i = 0; i < 20; i++) {
            tabla.poner("clave" + i, i);
        }
        assertEquals(7, tabla.quitar("clave7"));
        assertNull(tabla.obtener("clave7"));
        assertEquals(19, tabla.tamanio());
        assertNull(tabla.quitar("inexistente"));
    }

    @Test
    @DisplayName("Capacidad y factor de carga iniciales")
    void estadoInicial() {
        TablaHashConRehash<String, Integer> tabla = new TablaHashConRehash<>(4);
        assertEquals(4, tabla.capacidad());
        assertEquals(0.0, tabla.factorDeCarga());
        tabla.poner("juan", 1);
        assertEquals(0.25, tabla.factorDeCarga());
    }
}

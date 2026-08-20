package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TablaAbiertaTest {

    @Test
    @DisplayName("Borrar una clave del medio del sondeo no pierde a las siguientes")
    void lapidaNoCortaLaBusqueda() {
        // Con capacidad 8, "juan", "eva" y "sol" dan las tres indice 0
        // (hashCode real de Java): quedan sondeadas en 0, 1 y 2.
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        tabla.poner("juan", 25);
        tabla.poner("eva", 30);
        tabla.poner("sol", 28);
        assertEquals(30, tabla.quitar("eva"));
        // Si quitar hubiera puesto null en vez de lapida, "sol" se perderia.
        assertEquals(28, tabla.obtener("sol"));
        assertEquals(25, tabla.obtener("juan"));
        assertNull(tabla.obtener("eva"));
        assertEquals(2, tabla.tamanio());
    }

    @Test
    @DisplayName("Poner reutiliza el casillero de una lapida")
    void reutilizaLapidas() {
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        tabla.poner("juan", 25);
        tabla.poner("eva", 30);
        tabla.poner("sol", 28);
        tabla.quitar("eva");
        // "sol" tambien sondea desde 0: si vuelvo a ponerla tiene que
        // reemplazar la viva del casillero 2, no duplicarse en la lapida.
        tabla.poner("sol", 99);
        assertEquals(2, tabla.tamanio());
        assertEquals(99, tabla.obtener("sol"));
        // "lu" da indice 1 (el casillero de la lapida): lo ocupa.
        tabla.poner("lu", 1);
        assertEquals(3, tabla.tamanio());
        assertEquals(1, tabla.obtener("lu"));
    }

    @Test
    @DisplayName("Poner reemplaza el valor de una clave existente")
    void ponerReemplaza() {
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        tabla.poner("ana", 1);
        tabla.poner("ana", 2);
        assertEquals(1, tabla.tamanio());
        assertEquals(2, tabla.obtener("ana"));
    }

    @Test
    @DisplayName("Rehash con alfa > 0.5: crece y conserva todas las claves")
    void rehashConservaClaves() {
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        for (int i = 0; i < 12; i++) {
            tabla.poner("clave" + i, i);
        }
        assertEquals(12, tabla.tamanio());
        assertTrue(tabla.capacidad() > 8, "la capacidad tenia que crecer");
        for (int i = 0; i < 12; i++) {
            assertEquals(i, tabla.obtener("clave" + i));
        }
    }

    @Test
    @DisplayName("Las lapidas cuentan para la carga y el rehash las limpia")
    void lapidasCuentanParaLaCarga() {
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        // Alterno poner y quitar: quedan pocas vivas pero muchas lapidas.
        for (int i = 0; i < 10; i++) {
            tabla.poner("k" + i, i);
            if (i % 2 == 0) {
                tabla.quitar("k" + i);
            }
        }
        assertEquals(5, tabla.tamanio());
        // El rehash se disparo por la suma vivas + lapidas.
        assertTrue(tabla.capacidad() > 8);
        for (int i = 1; i < 10; i += 2) {
            assertEquals(i, tabla.obtener("k" + i));
        }
        for (int i = 0; i < 10; i += 2) {
            assertNull(tabla.obtener("k" + i));
        }
    }

    @Test
    @DisplayName("Quitar una clave ausente devuelve null")
    void quitarAusente() {
        TablaAbierta<String, Integer> tabla = new TablaAbierta<>(8);
        tabla.poner("juan", 25);
        assertNull(tabla.quitar("eva"));
        assertEquals(1, tabla.tamanio());
    }
}

package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ColaDeImpresionTest {

    @Test
    @DisplayName("imprime primero el trabajo con menos paginas")
    void imprimeElMasCorto() {
        ColaDeImpresion cola = new ColaDeImpresion();
        cola.agregar("tesis.pdf", 120);
        cola.agregar("recibo.pdf", 1);
        cola.agregar("apunte.pdf", 35);
        assertEquals("recibo.pdf", cola.imprimirSiguiente());
        assertEquals("apunte.pdf", cola.imprimirSiguiente());
        assertEquals("tesis.pdf", cola.imprimirSiguiente());
    }

    @Test
    @DisplayName("no importa el orden de llegada, gana el mas corto")
    void noEsFifo() {
        ColaDeImpresion cola = new ColaDeImpresion();
        cola.agregar("largo.pdf", 200);
        cola.agregar("corto.pdf", 2);
        assertEquals("corto.pdf", cola.imprimirSiguiente());
    }

    @Test
    @DisplayName("imprimir con la cola vacia lanza NoSuchElementException")
    void imprimirVaciaFalla() {
        assertThrows(NoSuchElementException.class,
                () -> new ColaDeImpresion().imprimirSiguiente());
    }

    @Test
    @DisplayName("agregar con paginas no positivas lanza IllegalArgumentException")
    void paginasInvalidasFalla() {
        assertThrows(IllegalArgumentException.class,
                () -> new ColaDeImpresion().agregar("roto.pdf", 0));
    }

    @Test
    @DisplayName("pendientes refleja cuantos trabajos quedan")
    void pendientesCuentaBien() {
        ColaDeImpresion cola = new ColaDeImpresion();
        cola.agregar("a.pdf", 3);
        cola.agregar("b.pdf", 7);
        assertEquals(2, cola.pendientes());
        cola.imprimirSiguiente();
        assertEquals(1, cola.pendientes());
    }
}

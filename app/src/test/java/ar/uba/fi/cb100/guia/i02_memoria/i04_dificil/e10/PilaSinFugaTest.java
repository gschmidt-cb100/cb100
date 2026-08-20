package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PilaSinFugaTest {

    @Test
    @DisplayName("Tras desapilar, la posicion liberada queda en null")
    void desapilarLimpiaLaPosicion() {
        PilaSinFuga pila = new PilaSinFuga(4);
        pila.apilar("a");
        pila.apilar("b");

        Object sacado = pila.desapilar();

        assertEquals("b", sacado);
        assertEquals(1, pila.tamanio());
        assertNull(pila.espiar(1)); // la posicion liberada no retiene el objeto
        assertEquals("a", pila.espiar(0)); // lo que queda sigue estando
    }

    @Test
    @DisplayName("apilar y desapilar respetan el orden LIFO")
    void ordenLifo() {
        PilaSinFuga pila = new PilaSinFuga(3);
        pila.apilar("x");
        pila.apilar("y");
        pila.apilar("z");
        assertEquals("z", pila.desapilar());
        assertEquals("y", pila.desapilar());
        assertEquals("x", pila.desapilar());
        assertEquals(0, pila.tamanio());
    }

    @Test
    @DisplayName("Caso borde: desapilar una pila vacia lanza IllegalStateException")
    void desapilarVaciaLanza() {
        PilaSinFuga pila = new PilaSinFuga(2);
        assertThrows(IllegalStateException.class, pila::desapilar);
    }

    @Test
    @DisplayName("Todas las posiciones usadas quedan en null tras vaciar la pila")
    void vaciarDejaTodoNull() {
        PilaSinFuga pila = new PilaSinFuga(2);
        pila.apilar("a");
        pila.apilar("b");
        pila.desapilar();
        pila.desapilar();
        assertNull(pila.espiar(0));
        assertNull(pila.espiar(1));
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AbbConKEsimoTest {

    /** Inserta 1..7 desordenados, como pide la consigna. */
    private AbbConKEsimo<Integer> armarArbol() {
        AbbConKEsimo<Integer> arbol = new AbbConKEsimo<>();
        for (int valor : new int[] {4, 2, 6, 1, 3, 5, 7}) {
            arbol.insertar(valor);
        }
        return arbol;
    }

    @Test
    @DisplayName("Con 1..7 desordenados: kEsimo(1)=1, kEsimo(4)=4 y kEsimo(7)=7")
    void kEsimosDeLaConsigna() {
        AbbConKEsimo<Integer> arbol = armarArbol();
        assertEquals(1, arbol.kEsimo(1));
        assertEquals(4, arbol.kEsimo(4));
        assertEquals(7, arbol.kEsimo(7));
    }

    @Test
    @DisplayName("Todos los k del 1 al 7 devuelven exactamente k")
    void todosLosKEsimos() {
        AbbConKEsimo<Integer> arbol = armarArbol();
        for (int k = 1; k <= 7; k++) {
            assertEquals(k, arbol.kEsimo(k), "fallo el k-esimo con k=" + k);
        }
    }

    @Test
    @DisplayName("k fuera de rango (0, negativo o mayor que el tamanio) lanza excepcion")
    void kFueraDeRango() {
        AbbConKEsimo<Integer> arbol = armarArbol();
        assertThrows(IllegalArgumentException.class, () -> arbol.kEsimo(0));
        assertThrows(IllegalArgumentException.class, () -> arbol.kEsimo(-3));
        assertThrows(IllegalArgumentException.class, () -> arbol.kEsimo(8));
    }

    @Test
    @DisplayName("Los duplicados no rompen los tamanios cacheados")
    void duplicadosNoRompenLosTamanios() {
        AbbConKEsimo<Integer> arbol = armarArbol();
        arbol.insertar(4); // Duplicado: no debe tocar ningun contador.
        arbol.insertar(1);
        assertEquals(7, arbol.tamanio());
        assertEquals(4, arbol.kEsimo(4)); // Sigue dando la mediana correcta.
        assertEquals(7, arbol.kEsimo(7));
    }

    @Test
    @DisplayName("En un arbol degenerado (insertado en orden) tambien funciona")
    void arbolDegenerado() {
        AbbConKEsimo<Integer> arbol = new AbbConKEsimo<>();
        for (int valor = 10; valor <= 50; valor += 10) {
            arbol.insertar(valor); // Todo hacia la derecha.
        }
        assertEquals(10, arbol.kEsimo(1));
        assertEquals(30, arbol.kEsimo(3));
        assertEquals(50, arbol.kEsimo(5));
    }

    @Test
    @DisplayName("Sobre un arbol vacio cualquier k lanza excepcion")
    void arbolVacio() {
        AbbConKEsimo<Integer> vacio = new AbbConKEsimo<>();
        assertEquals(0, vacio.tamanio());
        assertThrows(IllegalArgumentException.class, () -> vacio.kEsimo(1));
    }
}

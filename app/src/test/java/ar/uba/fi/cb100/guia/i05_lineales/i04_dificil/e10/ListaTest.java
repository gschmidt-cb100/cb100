package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica que AMBAS implementaciones del TAD Lista se comporten igual.
 * La idea es correr exactamente la misma secuencia de operaciones sobre las
 * dos y comprobar que producen los mismos resultados observables.
 */
class ListaTest {

    /** Aplica la misma secuencia de operaciones y compara estado. */
    private void verificarComportamiento(Lista<Integer> lista) {
        assertEquals(0, lista.tamanio());

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);
        assertEquals(3, lista.tamanio());
        assertEquals(10, lista.obtener(0));
        assertEquals(30, lista.obtener(2));

        lista.insertar(1, 15);      // [10,15,20,30]
        assertEquals(4, lista.tamanio());
        assertEquals(15, lista.obtener(1));
        assertEquals(20, lista.obtener(2));

        lista.insertar(0, 5);       // [5,10,15,20,30]
        lista.insertar(5, 40);      // insertar al final [.,.,.,.,.,40]
        assertEquals(5, lista.obtener(0));
        assertEquals(40, lista.obtener(5));
        assertEquals(6, lista.tamanio());

        assertEquals(15, lista.eliminar(2)); // saco el 15
        assertEquals(5, lista.tamanio());
        assertEquals(20, lista.obtener(2));

        assertEquals(5, lista.eliminar(0));  // saco la cabeza
        assertEquals(10, lista.obtener(0));
        assertEquals(4, lista.tamanio());
    }

    @Test
    @DisplayName("VectorDinamico cumple el contrato de Lista")
    void vectorDinamico() {
        verificarComportamiento(new VectorDinamico<>());
    }

    @Test
    @DisplayName("ListaEnlazada cumple el contrato de Lista")
    void listaEnlazada() {
        verificarComportamiento(new ListaEnlazada<>());
    }

    @Test
    @DisplayName("Ambas implementaciones producen el mismo resultado paso a paso")
    void ambasSeComportanIgual() {
        Lista<String> vector = new VectorDinamico<>();
        Lista<String> enlazada = new ListaEnlazada<>();

        String[] aInsertar = {"a", "b", "c", "d", "e"};
        for (String s : aInsertar) {
            vector.agregar(s);
            enlazada.agregar(s);
        }
        vector.insertar(2, "X");
        enlazada.insertar(2, "X");
        vector.eliminar(0);
        enlazada.eliminar(0);

        assertEquals(vector.tamanio(), enlazada.tamanio());
        for (int i = 0; i < vector.tamanio(); i++) {
            assertEquals(vector.obtener(i), enlazada.obtener(i),
                    "Difieren en la posicion " + i);
        }
    }

    @Test
    @DisplayName("Ambas lanzan excepcion ante indices invalidos")
    void ambasValidanIndices() {
        Lista<Integer> vector = new VectorDinamico<>();
        Lista<Integer> enlazada = new ListaEnlazada<>();
        vector.agregar(1);
        enlazada.agregar(1);
        assertThrows(IndexOutOfBoundsException.class, () -> vector.obtener(5));
        assertThrows(IndexOutOfBoundsException.class, () -> enlazada.obtener(5));
        assertThrows(IndexOutOfBoundsException.class, () -> vector.insertar(9, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> enlazada.insertar(9, 0));
    }
}

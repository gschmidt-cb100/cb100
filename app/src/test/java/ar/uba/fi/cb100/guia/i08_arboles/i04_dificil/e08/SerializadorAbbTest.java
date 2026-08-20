package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class SerializadorAbbTest {

    /** Arma el arbol de la consigna: 50,30,70,20,40. */
    private ArbolBusqueda armarArbol() {
        ArbolBusqueda arbol = new ArbolBusqueda();
        for (int valor : new int[] {50, 30, 70, 20, 40}) {
            arbol.insertar(valor);
        }
        return arbol;
    }

    @Test
    @DisplayName("El pre-orden de 50,30,70,20,40 se serializa como 50,30,20,40,70")
    void serializarEnPreOrden() {
        assertEquals("50,30,20,40,70", SerializadorAbb.aPreOrden(armarArbol()));
    }

    @Test
    @DisplayName("Serializar y reconstruir devuelve un arbol con el mismo preOrden y enOrden")
    void idaYVuelta() {
        ArbolBusqueda original = armarArbol();
        String texto = SerializadorAbb.aPreOrden(original);
        ArbolBusqueda copia = SerializadorAbb.reconstruir(texto);

        // Mismo preOrden => misma FORMA; mismo enOrden => mismos valores.
        assertEquals(original.preOrden(), copia.preOrden());
        assertEquals(original.enOrden(), copia.enOrden());
        assertEquals(original.tamanio(), copia.tamanio());
    }

    @Test
    @DisplayName("Reconstruir inserta en el orden del texto")
    void reconstruirDesdeTexto() {
        ArbolBusqueda arbol = SerializadorAbb.reconstruir("50,30,20,40,70");
        assertEquals(List.of(50, 30, 20, 40, 70), arbol.preOrden());
        assertEquals(List.of(20, 30, 40, 50, 70), arbol.enOrden());
        assertEquals(5, arbol.tamanio());
    }

    @Test
    @DisplayName("Un arbol vacio va y vuelve como cadena vacia")
    void arbolVacio() {
        ArbolBusqueda vacio = new ArbolBusqueda();
        assertEquals("", SerializadorAbb.aPreOrden(vacio));
        ArbolBusqueda reconstruido = SerializadorAbb.reconstruir("");
        assertEquals(0, reconstruido.tamanio());
        assertTrue(reconstruido.enOrden().isEmpty());
    }

    @Test
    @DisplayName("Reconstruir tolera espacios alrededor de las comas")
    void reconstruirConEspacios() {
        ArbolBusqueda arbol = SerializadorAbb.reconstruir(" 50 , 30 , 70 ");
        assertEquals(List.of(30, 50, 70), arbol.enOrden());
    }

    @Test
    @DisplayName("Serializar la copia da exactamente el mismo texto")
    void serializarDosVecesDaLoMismo() {
        String texto = SerializadorAbb.aPreOrden(armarArbol());
        ArbolBusqueda copia = SerializadorAbb.reconstruir(texto);
        assertEquals(texto, SerializadorAbb.aPreOrden(copia));
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class AbbConPisoYTechoTest {

    /** Arma un arbol con las decenas del 20 al 80. */
    private AbbConPisoYTecho<Integer> armarArbol() {
        AbbConPisoYTecho<Integer> arbol = new AbbConPisoYTecho<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        return arbol;
    }

    @Test
    @DisplayName("Minimo y maximo son los extremos del arbol")
    void minimoYMaximo() {
        AbbConPisoYTecho<Integer> arbol = armarArbol();
        assertEquals(20, arbol.minimo());
        assertEquals(80, arbol.maximo());
    }

    @Test
    @DisplayName("Minimo y maximo sobre un arbol vacio lanzan excepcion")
    void extremosDeArbolVacio() {
        AbbConPisoYTecho<Integer> vacio = new AbbConPisoYTecho<>();
        assertThrows(NoSuchElementException.class, vacio::minimo);
        assertThrows(NoSuchElementException.class, vacio::maximo);
    }

    @Test
    @DisplayName("Piso y techo de un valor intermedio son los vecinos mas cercanos")
    void pisoYTechoIntermedios() {
        AbbConPisoYTecho<Integer> arbol = armarArbol();
        assertEquals(40, arbol.piso(45));
        assertEquals(50, arbol.techo(45));
        assertEquals(70, arbol.piso(79));
        assertEquals(80, arbol.techo(71));
    }

    @Test
    @DisplayName("Si el valor esta en el arbol, es su propio piso y techo")
    void valorExactoEsSuPropioPisoYTecho() {
        AbbConPisoYTecho<Integer> arbol = armarArbol();
        assertEquals(60, arbol.piso(60));
        assertEquals(60, arbol.techo(60));
    }

    @Test
    @DisplayName("Piso de algo menor que todo y techo de algo mayor que todo dan null")
    void fueraDeRango() {
        AbbConPisoYTecho<Integer> arbol = armarArbol();
        assertNull(arbol.piso(10));   // Todo el arbol es mayor que 10.
        assertNull(arbol.techo(90));  // Todo el arbol es menor que 90.
        // En el otro extremo si hay respuesta:
        assertEquals(20, arbol.techo(10));
        assertEquals(80, arbol.piso(90));
    }
}

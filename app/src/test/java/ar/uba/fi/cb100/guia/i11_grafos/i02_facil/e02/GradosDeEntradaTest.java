package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e02;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradosDeEntradaTest {

    private Grafo seguidores;   // P=0, Q=1, R=2, S=3

    @BeforeEach
    void armarRed() {
        seguidores = new Grafo(4, true);
        seguidores.agregarArista(0, 1);   // P -> Q
        seguidores.agregarArista(1, 2);   // Q -> R
        seguidores.agregarArista(2, 0);   // R -> P
        seguidores.agregarArista(1, 3);   // Q -> S
        seguidores.agregarArista(2, 3);   // R -> S
    }

    @Test
    @DisplayName("En la red de seguidores las entradas son P=1, Q=1, R=1, S=2")
    void entradasDeLaRed() {
        assertArrayEquals(new int[] {1, 1, 1, 2}, GradosDeEntrada.gradosDeEntrada(seguidores));
    }

    @Test
    @DisplayName("Entrada y salida son grados distintos: S recibe 2 y no emite ninguna")
    void entradaNoEsSalida() {
        assertEquals(2, GradosDeEntrada.gradosDeEntrada(seguidores)[3]);
        assertEquals(0, seguidores.grado(3));    // grado(v) cuenta las SALIENTES
    }

    @Test
    @DisplayName("La suma de los grados de entrada es la cantidad de aristas")
    void sumaIgualAristas() {
        int suma = 0;
        for (int entrada : GradosDeEntrada.gradosDeEntrada(seguidores)) {
            suma += entrada;
        }
        assertEquals(seguidores.cantidadDeAristas(), suma);
    }

    @Test
    @DisplayName("Pedir grados de entrada de un grafo no dirigido lanza excepcion")
    void rechazaNoDirigidos() {
        Grafo noDirigido = new Grafo(3, false);
        noDirigido.agregarArista(0, 1);
        assertThrows(IllegalArgumentException.class,
                () -> GradosDeEntrada.gradosDeEntrada(noDirigido));
    }
}

package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MochilaFraccionariaTest {

    @Test
    @DisplayName("el ejemplo clásico: pesos {10,20,30}, valores {60,100,120}, capacidad 50 da 240.0")
    void ejemploClasico() {
        assertEquals(240.0,
                MochilaFraccionaria.valorMaximo(
                        new double[]{10, 20, 30},
                        new double[]{60, 100, 120},
                        50),
                1e-9);
    }

    @Test
    @DisplayName("si todo entra en la mochila, se lleva todo el valor")
    void entraTodo() {
        assertEquals(30.0,
                MochilaFraccionaria.valorMaximo(
                        new double[]{1, 2, 3},
                        new double[]{5, 10, 15},
                        100),
                1e-9);
    }

    @Test
    @DisplayName("con capacidad 0 el valor es 0")
    void capacidadCero() {
        assertEquals(0.0,
                MochilaFraccionaria.valorMaximo(
                        new double[]{10}, new double[]{60}, 0),
                1e-9);
    }

    @Test
    @DisplayName("de un único objeto que no entra se lleva la fracción proporcional")
    void fraccionDeUnObjeto() {
        // Entra la mitad del objeto: la mitad del valor.
        assertEquals(30.0,
                MochilaFraccionaria.valorMaximo(
                        new double[]{10}, new double[]{60}, 5),
                1e-9);
    }

    @Test
    @DisplayName("arreglos de distinto largo lanzan IllegalArgumentException")
    void largosDistintosLanzan() {
        assertThrows(IllegalArgumentException.class,
                () -> MochilaFraccionaria.valorMaximo(
                        new double[]{1, 2}, new double[]{1}, 10));
    }
}

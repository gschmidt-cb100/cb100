package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class MonticuloConDisminuirTest {

    @Test
    @DisplayName("Disminuir una hoja hasta volverla minimo la lleva a la raiz")
    void disminuirHojaHastaMinimo() {
        MonticuloConDisminuir<Integer> monticulo = new MonticuloConDisminuir<>();
        for (int valor : new int[] {10, 30, 20, 50, 40}) {
            monticulo.encolar(valor);
        }
        assertEquals(10, monticulo.verMinimo());

        // El 50 es una hoja; al volverse 5 tiene que flotar hasta la raiz.
        monticulo.disminuirClave(50, 5);
        assertEquals(5, monticulo.verMinimo());
        assertTrue(monticulo.contiene(5));
        assertFalse(monticulo.contiene(50));
    }

    @Test
    @DisplayName("Despues de disminuir, vaciar el monticulo sigue entregando ordenado")
    void ordenTrasDisminuir() {
        MonticuloConDisminuir<Integer> monticulo = new MonticuloConDisminuir<>();
        for (int valor : new int[] {10, 30, 20, 50, 40}) {
            monticulo.encolar(valor);
        }
        monticulo.disminuirClave(40, 15);
        monticulo.disminuirClave(50, 5);

        List<Integer> salida = new ArrayList<>();
        while (!monticulo.estaVacio()) {
            salida.add(monticulo.desencolarMinimo());
        }
        assertEquals(List.of(5, 10, 15, 20, 30), salida);
    }

    @Test
    @DisplayName("El indice sigue siendo correcto despues de varios desencolar (los swaps lo actualizan)")
    void indiceConsistenteTrasDesencolar() {
        MonticuloConDisminuir<Integer> monticulo = new MonticuloConDisminuir<>();
        for (int valor : new int[] {10, 30, 20, 50, 40, 60, 25}) {
            monticulo.encolar(valor);
        }
        monticulo.desencolarMinimo(); // Saca el 10 y reacomoda con swaps.
        monticulo.desencolarMinimo(); // Saca el 20.

        // Si el HashMap quedo desactualizado, este disminuir toca la posicion equivocada.
        monticulo.disminuirClave(60, 1);
        assertEquals(1, monticulo.verMinimo());
    }

    @Test
    @DisplayName("Encolar una clave repetida lanza IllegalArgumentException (claves unicas)")
    void claveRepetidaLanza() {
        MonticuloConDisminuir<Integer> monticulo = new MonticuloConDisminuir<>();
        monticulo.encolar(10);
        assertThrows(IllegalArgumentException.class, () -> monticulo.encolar(10));
    }

    @Test
    @DisplayName("Disminuir con una clave inexistente, no menor o ya existente lanza IllegalArgumentException")
    void disminuirInvalidoLanza() {
        MonticuloConDisminuir<Integer> monticulo = new MonticuloConDisminuir<>();
        monticulo.encolar(10);
        monticulo.encolar(20);
        assertThrows(IllegalArgumentException.class, () -> monticulo.disminuirClave(99, 1));  // No esta.
        assertThrows(IllegalArgumentException.class, () -> monticulo.disminuirClave(20, 25)); // No es menor.
        assertThrows(IllegalArgumentException.class, () -> monticulo.disminuirClave(20, 20)); // Igual tampoco.
        assertThrows(IllegalArgumentException.class, () -> monticulo.disminuirClave(20, 10)); // Ya existe.
    }
}

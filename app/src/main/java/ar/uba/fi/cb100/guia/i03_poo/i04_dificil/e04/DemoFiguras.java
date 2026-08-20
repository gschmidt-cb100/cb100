package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e04;

import java.util.Arrays;

/** Programa de prueba que muestra polimorfismo y orden natural de las figuras. */
public class DemoFiguras {

    public static void main(String[] args) {
        // Polimorfismo: tratamos distintas figuras a través de la interfaz Figura.
        Figura[] figuras = {
                new Circulo(2),        // area ~= 12.57
                new Rectangulo(2, 3),  // area = 6
                new Circulo(1),        // area ~= 3.14
                new Rectangulo(5, 5)   // area = 25
        };

        System.out.println("Sin ordenar:");
        for (Figura f : figuras) {
            System.out.println("  " + f);
        }

        // Orden natural (por área) usando Comparable/compareTo.
        Arrays.sort(figuras);

        System.out.println("Ordenadas por área:");
        for (Figura f : figuras) {
            System.out.println("  " + f);
        }
    }
}

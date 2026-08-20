package ar.uba.fi.cb100.material.i03_poo;

import java.util.Arrays;

/**
 * Muestra <b>polimorfismo</b> (cada figura calcula su área a su manera, detrás de
 * una misma referencia {@code Figura}) y el orden natural con {@code compareTo}.
 */
public class DemoFiguras {

    public static void main(String[] args) {
        // Una referencia del tipo del contrato (Figura) apunta a distintas figuras.
        Figura[] figuras = {new Circulo(2), new Rectangulo(3, 4), new Circulo(1)};
        for (Figura f : figuras) {
            System.out.println(f);   // despacho dinámico: usa el toString y el area() reales
        }

        // Ordenar por área usando el orden natural (Comparable / compareTo).
        FiguraBase[] ordenables = {new Circulo(2), new Rectangulo(3, 4), new Circulo(1)};
        Arrays.sort(ordenables);
        System.out.println("Ordenadas por área: " + Arrays.toString(ordenables));
    }
}

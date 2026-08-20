package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e03;

/** Programa de prueba que muestra el polimorfismo entre figuras. */
public class DemoFiguras {

    public static void main(String[] args) {
        // El arreglo es de tipo Figura pero guarda distintos tipos concretos.
        Figura[] figuras = {
                new Circulo(1.0),
                new Rectangulo(2.0, 3.0),
                new Circulo(2.0)
        };

        for (Figura f : figuras) {
            System.out.printf("Área: %.4f%n", f.area());
        }

        System.out.printf("Suma total de áreas: %.4f%n", Figura.sumaAreas(figuras));
    }
}

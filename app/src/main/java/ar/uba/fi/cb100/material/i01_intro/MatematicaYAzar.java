package ar.uba.fi.cb100.material.i01_intro;

import java.util.Random;

/**
 * Uso básico de la clase utilitaria {@link Math} y de {@link Random}.
 */
public class MatematicaYAzar {

    public static void main(String[] args) {
        // --- Math: constantes y funciones más usadas ---
        System.out.println("PI = " + Math.PI + " | E = " + Math.E);
        System.out.println("abs(-5) = " + Math.abs(-5));
        System.out.println("max(3,7) = " + Math.max(3, 7) + " | min(3,7) = " + Math.min(3, 7));
        System.out.println("pow(2,10) = " + Math.pow(2, 10));
        System.out.println("sqrt(144) = " + Math.sqrt(144));
        System.out.println("ceil(2.1) = " + Math.ceil(2.1)
                + " | floor(2.9) = " + Math.floor(2.9)
                + " | round(2.5) = " + Math.round(2.5));

        // --- Random: números al azar (con semilla fija para que sea reproducible) ---
        Random r = new Random(42);
        System.out.println("dado (1..6): " + (r.nextInt(6) + 1));
        System.out.println("moneda: " + (r.nextBoolean() ? "cara" : "cruz"));
        System.out.printf("real [0,1): %.3f%n", r.nextDouble());
    }
}

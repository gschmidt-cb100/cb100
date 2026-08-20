package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e03;

import java.util.Arrays;

/**
 * Poligono definido por un arreglo de puntos.
 *
 * Ojo: un record guarda la MISMA referencia al arreglo que recibe. Por eso
 * dos poligonos pueden compartir el mismo arreglo y verse afectados entre si.
 * copiaProfunda rompe ese vinculo creando un arreglo nuevo.
 */
public record Poligono(Punto[] puntos) {

    /**
     * Devuelve un poligono con un arreglo nuevo e independiente.
     * Los Punto son inmutables, asi que alcanza con copiar el arreglo
     * (no hace falta clonar cada punto).
     */
    public static Poligono copiaProfunda(Poligono p) {
        Punto[] copia = Arrays.copyOf(p.puntos(), p.puntos().length);
        return new Poligono(copia);
    }

    public static void main(String[] args) {
        Punto[] ps = { new Punto(0, 0), new Punto(1, 1) };
        Poligono original = new Poligono(ps);
        Poligono copia = copiaProfunda(original);

        ps[0] = new Punto(99, 99); // muto el arreglo original
        System.out.println("Original[0]: " + original.puntos()[0]);
        System.out.println("Copia[0]:    " + copia.puntos()[0]);
    }
}

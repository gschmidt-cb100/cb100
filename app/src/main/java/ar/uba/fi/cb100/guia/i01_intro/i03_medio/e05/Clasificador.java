package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e05;

/**
 * Ejercicio 05: Clasificador de notas.
 * Clasifica una nota entera del 0 al 10.
 */
public class Clasificador {

    /**
     * Clasifica una nota: insuficiente (0-3), aprobado (4-7), distinguido (8-10).
     *
     * @param nota nota entre 0 y 10
     * @return categoría textual
     * @throws NotaInvalidaException si nota < 0 o nota > 10
     */
    public static String clasificar(int nota) {
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota fuera de rango [0,10]: " + nota);
        }
        return switch (nota) {
            case 0, 1, 2, 3 -> "insuficiente";
            case 4, 5, 6, 7 -> "aprobado";
            default -> "distinguido";
        };
    }

    public static void main(String[] args) {
        System.out.println("Nota 2: " + clasificar(2));
        System.out.println("Nota 6: " + clasificar(6));
        System.out.println("Nota 9: " + clasificar(9));
    }
}

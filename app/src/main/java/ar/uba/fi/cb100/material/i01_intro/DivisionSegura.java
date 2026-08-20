package ar.uba.fi.cb100.material.i01_intro;

/**
 * Manejo de excepciones. Una excepción señala una situación excepcional
 * (acá, dividir por cero) para que quien llama decida cómo reaccionar.
 */
public class DivisionSegura {

    public static double dividir(int dividendo, int divisor) {
        if (divisor == 0) {
            throw new DivisionPorCeroException(dividendo);
        }
        return (double) dividendo / divisor;   // cast a double: división real
    }

    public static void main(String[] args) {
        int[][] casos = { {10, 2}, {7, 0}, {9, 4} };
        for (var caso : casos) {
            try {
                double r = dividir(caso[0], caso[1]);
                System.out.printf("%d / %d = %.2f%n", caso[0], caso[1], r);
            } catch (DivisionPorCeroException e) {
                System.out.println("No se pudo dividir: " + e.getMessage());
            }
        }
    }
}

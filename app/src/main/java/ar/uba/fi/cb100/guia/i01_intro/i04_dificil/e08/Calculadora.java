package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e08;

/**
 * Ejercicio 08 - Calculadora.
 * Realiza las cuatro operaciones basicas y valida division por cero
 * y operadores invalidos mediante excepciones propias.
 */
public class Calculadora {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private Calculadora() {
    }

    /**
     * Aplica la operacion indicada sobre a y b.
     *
     * @param a  operando izquierdo
     * @param b  operando derecho
     * @param op operador: '+', '-', '*' o '/'
     * @return resultado de la operacion
     */
    public static double operar(double a, double b, char op) {
        // switch expression: seleccionamos la operacion segun el operador
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new DivisionPorCeroException("No se puede dividir por cero");
                }
                yield a / b;
            }
            default -> throw new OperadorInvalidoException("Operador invalido: " + op);
        };
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        System.out.println("2 + 3 = " + operar(2, 3, '+'));
        System.out.println("10 / 4 = " + operar(10, 4, '/'));
        System.out.println("6 * 7 = " + operar(6, 7, '*'));
    }
}

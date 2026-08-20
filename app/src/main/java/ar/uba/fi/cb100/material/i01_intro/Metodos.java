package ar.uba.fi.cb100.material.i01_intro;

/**
 * Métodos (funciones) en Java: cómo se declaran, parámetros, retorno,
 * sobrecarga y {@code varargs}.
 */
public class Metodos {

    // Un método con parámetros y un valor de retorno.
    static int cuadrado(int x) {
        return x * x;
    }

    // Sobrecarga: mismo nombre, distintos parámetros.
    static double area(double lado) {              // cuadrado
        return lado * lado;
    }

    static double area(double base, double altura) { // rectángulo
        return base * altura;
    }

    // varargs: cantidad variable de argumentos.
    static int sumar(int... valores) {
        int acumulado = 0;
        for (int v : valores) {
            acumulado += v;
        }
        return acumulado;
    }

    public static void main(String[] args) {
        System.out.println("cuadrado(5) = " + cuadrado(5));
        System.out.println("area(3) = " + area(3));
        System.out.println("area(3, 4) = " + area(3, 4));
        System.out.println("sumar(1,2,3,4) = " + sumar(1, 2, 3, 4));
    }
}

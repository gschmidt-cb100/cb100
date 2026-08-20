package ar.uba.fi.cb100.material.i01_intro;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Expresiones lambda: funciones anónimas y breves. Una lambda implementa una
 * <b>interfaz funcional</b> (una interfaz con un solo método abstracto).
 * <p>
 * El uso intensivo de lambdas con <i>streams</i> lo vemos en la unidad de
 * estructuras lineales.
 */
public class FuncionesLambda {

    // Una interfaz funcional propia: un único método.
    interface Operacion {
        int aplicar(int a, int b);
    }

    public static void main(String[] args) {
        // La lambda (a, b) -> a + b "es" una Operacion.
        Operacion suma = (a, b) -> a + b;
        Operacion producto = (a, b) -> a * b;
        System.out.println("suma: " + suma.aplicar(3, 4));
        System.out.println("producto: " + producto.aplicar(3, 4));

        // Interfaces funcionales estándar de la biblioteca java.util.function
        Predicate<Integer> esPar = n -> n % 2 == 0;
        Function<String, Integer> largo = s -> s.length();
        System.out.println("¿4 es par?: " + esPar.test(4));
        System.out.println("largo de 'hola': " + largo.apply("hola"));

        // Runnable también es una interfaz funcional.
        Runnable saludo = () -> System.out.println("¡hola desde una lambda!");
        saludo.run();
    }
}

package ar.uba.fi.cb100.material.i01_intro;

import java.util.Scanner;

/**
 * Entrada y salida por consola. En un programa real se usa
 * {@code new Scanner(System.in)} para leer del teclado; acá usamos un
 * {@code Scanner} sobre un texto fijo para que el ejemplo sea reproducible.
 */
public class EntradaSalida {

    public static void main(String[] args) {
        String entradaSimulada = "Ada\n37\n";     // simula lo que teclearía el usuario
        Scanner sc = new Scanner(entradaSimulada); // real: new Scanner(System.in)

        System.out.print("¿Cómo te llamás? ");
        String nombre = sc.nextLine();

        System.out.print("¿Cuántos años tenés? ");
        int edad = sc.nextInt();

        System.out.printf("Hola %s, el año que viene vas a tener %d.%n",
                nombre, edad + 1);
        sc.close();
    }
}

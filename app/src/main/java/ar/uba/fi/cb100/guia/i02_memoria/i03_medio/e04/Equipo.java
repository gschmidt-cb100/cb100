package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e04;

import java.util.Arrays;

/**
 * Ejercicio 04 (Equipo).
 *
 * Ejemplo de encapsulamiento defensivo: la clase COPIA el arreglo en el
 * constructor y devuelve una COPIA en el getter. Asi, quien tiene el
 * arreglo original o el devuelto no puede modificar el estado interno.
 */
public final class Equipo {

    private final int[] numeros;

    public Equipo(int[] numeros) {
        // Copia defensiva de entrada: nos desligamos del arreglo del llamador.
        this.numeros = Arrays.copyOf(numeros, numeros.length);
    }

    /**
     * Devuelve una COPIA para que nadie pueda mutar el estado interno.
     */
    public int[] getNumeros() {
        return Arrays.copyOf(numeros, numeros.length);
    }

    public static void main(String[] args) {
        int[] original = {7, 10, 23};
        Equipo equipo = new Equipo(original);

        original[0] = 99; // mutar el original no debe afectar al equipo
        int[] copia = equipo.getNumeros();
        copia[1] = 99;    // mutar la copia devuelta tampoco

        System.out.println("Estado interno del equipo: " + Arrays.toString(equipo.getNumeros())); // [7, 10, 23]
    }
}

package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e06;

/**
 * Gerente: es un Empleado que además cobra un bono fijo.
 * Redefine sueldo() reutilizando el cálculo de la clase base con super.sueldo().
 */
public class Gerente extends Empleado {

    private final double bono;

    public Gerente(String nombre, double sueldoBase, double bono) {
        super(nombre, sueldoBase);
        if (bono < 0) {
            throw new IllegalArgumentException("El bono no puede ser negativo");
        }
        this.bono = bono;
    }

    public double getBono() {
        return bono;
    }

    /** El sueldo del gerente es el de un empleado común más el bono. */
    @Override
    public double sueldo() {
        return super.sueldo() + bono;
    }

    public static void main(String[] args) {
        Empleado empleado = new Empleado("Ana", 500000.0);
        Gerente gerente = new Gerente("Beto", 500000.0, 200000.0);

        System.out.println("Sueldo empleado: " + empleado.sueldo()); // 500000
        System.out.println("Sueldo gerente:  " + gerente.sueldo());   // 700000

        // Polimorfismo: se los trata a ambos como Empleado.
        Empleado[] plantel = {empleado, gerente};
        double total = 0;
        for (Empleado e : plantel) {
            total += e.sueldo();
        }
        System.out.println("Total a pagar: " + total); // 1200000
    }
}

package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e06;

import java.util.Objects;

/**
 * Empleado con un sueldo base. El método sueldo() devuelve la remuneración
 * total; las subclases pueden redefinirlo reutilizando super.sueldo().
 */
public class Empleado {

    private final String nombre;
    private final double sueldoBase;

    public Empleado(String nombre, double sueldoBase) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
        if (sueldoBase < 0) {
            throw new IllegalArgumentException("El sueldo base no puede ser negativo");
        }
        this.sueldoBase = sueldoBase;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    /** Sueldo total del empleado. En la clase base es simplemente el sueldo base. */
    public double sueldo() {
        return sueldoBase;
    }
}

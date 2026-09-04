package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.Cuidador;

import java.util.Arrays;

/**
 * Representa una jaula de un zoológico, que puede alojar animales y tener un cuidador a cargo.
 */
public class Jaula {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int numero;
    private Animal[] animales;
    private Cuidador cuidador;   // null hasta que se asigne uno
    private Integer cantidadMaximaDeAnimales = null; // Capacidad máxima de la jaula

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public Jaula(int numero, Integer cantidadMaximaDeAnimales) {
        if (numero <= 0) {
            throw new IllegalArgumentException("el número de jaula debe ser positivo");
        }
        if ((cantidadMaximaDeAnimales != null) &&
            (cantidadMaximaDeAnimales <= 0)) {
            throw new IllegalArgumentException("la capacidad de la jaula debe ser positiva");
        }
        this.numero = numero;
        this.cantidadMaximaDeAnimales = cantidadMaximaDeAnimales;
        this.animales = null;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Jaula " + numero + " (" + getCantidadDeAnimales() + " animales)"
                + (cuidador == null ? " sin cuidador" : ", a cargo de " + cuidador.nombre());
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    public boolean estaVacia() {
        return getCantidadDeAnimales() == 0;
    }

    public boolean tieneCuidador() {
        return cuidador != null;
    }

    /** Asigna (o reemplaza) el cuidador a cargo. Regla R10. */
    public void asignarCuidador(Cuidador cuidador) {
        if (cuidador == null) {
            throw new IllegalArgumentException("el cuidador no puede ser nulo");
        }
        if (tieneAnimales() && !cuidador.puedeAtender(getAnimales()[0])) {
            throw new IllegalStateException("no se puede asignar " + cuidador + " porque no puede atender a los animales alojados");
        }
        this.cuidador = cuidador;
    }

    /** Aloja un animal. Regla R9: no se excede la capacidad. */
    public void alojar(Animal... animales) {
        if (animales == null) {
            throw new IllegalArgumentException("los animales no pueden ser nulos");
        }
        if ((cantidadMaximaDeAnimales != null) &&
            (this.cantidadMaximaDeAnimales < animales.length)) {
            throw new IllegalArgumentException("no se puede alojar más animales de los que permite la jaula");
        }
        // Primero se valida TODO, y recién después se toca el estado. Si se
        // asignara el arreglo antes de validar, un animal rechazado dejaría
        // la jaula con un null adentro y "medio ocupada".
        for (Animal animal : animales) {
            if (animal == null) {
                throw new IllegalArgumentException("no se puede alojar un animal nulo");
            }
            if (tieneCuidador() && !getCuidador().puedeAtender(animal)) {
                throw new IllegalStateException("no se puede alojar " + animal + " porque el cuidador " + getCuidador() + " no puede atenderlo");
            }
        }
        this.animales = Arrays.copyOf(animales, animales.length);
    }

    public boolean tieneAnimales() {
        return getCantidadDeAnimales() > 0;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return animales.length;
    }

    public int getCantidadDeAnimales() {
        if (this.animales == null) {
            return 0;
        }
        return this.animales.length;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public Animal[] getAnimales() {
        if (this.animales == null) {
            return new Animal[0];
        }
        return Arrays.copyOf( animales, animales.length );
    }

    //SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

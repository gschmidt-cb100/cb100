package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.AdministradorDeZoologico;

/**
 * TDA <b>Zoológico</b>: un nombre y sus jaulas. Es sólo <b>datos</b>: no sabe
 * alimentar a nadie. De eso se ocupa el {@link AdministradorDeZoologico}.
 *
 * <p>Separar los TDA de datos ({@code Zoologico}, {@code Jaula}, {@code Animal})
 * de los TDA de funcionalidad ({@code Administrador}, {@code Cuidador}) hace
 * que cada clase tenga una sola razón para cambiar. Si mañana cambia el
 * procedimiento de alimentación, {@code Zoologico} no se toca.
 */
public class Zoologico {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String nombre;
    private final Jaula[] jaulas;
    private Integer cantidadMaximaDeAnimalesPorJaula; // cantidad máxima de animales por jaula
//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea un zoológico con un nombre y una cantidad máxima de jaulas. Cada jaula
     * @param nombre: nombre del zoológico
     * @param cantidadMaximaDeJaulas: cantidad máxima de jaulas que puede tener el zoológico
     */
    public Zoologico(String nombre,
                     int cantidadMaximaDeJaulas) {
        this(nombre, cantidadMaximaDeJaulas, null); // valor por defecto de 10 animales por jaula
    }

    /**
     * Crea un zoológico con un nombre, una cantidad máxima de jaulas y una cantidad máxima de animales por jaula.
     * @param nombre: nombre del zoológico
     * @param cantidadMaximaDeJaulas:   cantidad máxima de jaulas que puede tener el zoológico
     * @param cantidadMaximaDeAnimalesPorJaula:     cantidad máxima de animales que puede tener cada jaula
     */
    public Zoologico(String nombre,
                     int cantidadMaximaDeJaulas,
                     Integer cantidadMaximaDeAnimalesPorJaula) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el zoológico necesita un nombre");
        }
        if (cantidadMaximaDeJaulas <= 0) {
            throw new IllegalArgumentException("la cantidad de jaulas debe ser positiva");
        }
        if ((cantidadMaximaDeAnimalesPorJaula != null) &&
            (cantidadMaximaDeAnimalesPorJaula <= 0)) {
            throw new IllegalArgumentException("la cantidad de animales por jaula debe ser positiva");
        }
        this.cantidadMaximaDeAnimalesPorJaula = cantidadMaximaDeAnimalesPorJaula;
        this.nombre = nombre;
        this.jaulas = new Jaula[cantidadMaximaDeJaulas];
        for(int i = 0; i < cantidadMaximaDeJaulas; i++) {
            jaulas[i] = new Jaula(i+1, cantidadMaximaDeAnimalesPorJaula);
        }
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return nombre + " — " + getCantidadDeJaulas() + " jaulas, " + getCantidadDeAnimales() + " animales";
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @return Indica si el zoológico tiene un límite de animales por jaula. Si no tiene límite, se puede agregar cualquier cantidad de animales a cada jaula.
     */
    public boolean tieneLimiteDeAnimalesPorJaula() {
        return cantidadMaximaDeAnimalesPorJaula != null;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve la jaula en la posición i (1..cantidadDeJaulas). Lanza
     * @param i: indica la posicion de la jaula. Las jaulas van de la posicion 1 a cantidadDeJaulas del zoologico
     * @return la jaula en la posición i solicitada
     * @throws IndexOutOfBoundsException si i es menor o igual a 0 o mayor que la cantidad de jaulas
     */
    public Jaula getJaula(int i) {
        if (i <= 0 || i > getCantidadDeJaulas()) {
            throw new IndexOutOfBoundsException(
                    "el zoológico tiene " + getCantidadDeJaulas() + " jaulas; no hay posición " + i);
        }
        return jaulas[i-1];
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadDeJaulas() {
        return this.jaulas.length;
    }

    public int getCantidadDeAnimales() {
        int total = 0;
        for (int i = 0; i < getCantidadDeJaulas(); i++) {
            total += jaulas[i].getCantidadDeAnimales();
        }
        return total;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

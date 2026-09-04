package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;

import java.time.LocalDate;
import java.util.Arrays;

public abstract class Animal {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    /**
     * Cuántas raciones recuerda cada animal. Cuando el historial se llena, la
     * más vieja se descarta para hacerle lugar a la nueva.
     */
    private static final int CAPACIDAD_DEL_HISTORIAL = 10;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String nombre;
    private double peso;
    private double pesoOptimo;

    /** Ordenado de la más vieja (posición 0) a la más nueva. */
    private final Racion[] historialDeRaciones = new Racion[CAPACIDAD_DEL_HISTORIAL];

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Constructor de la clase base. Es {@code protected} porque sólo tiene
     * sentido que lo invoquen las subclases con {@code super(nombre)}.
     *
     * @param nombre nombre del animal; no puede ser nulo ni vacío
     * @param peso peso del animal
     * @param pesoOptimo peso óptimo del animal
     */
    protected Animal(String nombre, double peso, double pesoOptimo) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el animal necesita un nombre");
        }
        this.nombre = nombre;
        this.peso = peso;
        this.pesoOptimo = pesoOptimo;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("%s (%s) peso=%.2f%s",
                nombre, especie(), peso,
                estaBienAlimentado() ? "" : "  [BAJA]");
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /** Nombre de la especie, para los informes. */
    public abstract String especie();

    /** {@code true} si come carne. Lo resuelve cada rama de la jerarquía. */
    public abstract boolean esCarnivoro();

    protected abstract int desgasteDiario();

    /** Regla R2: está bien alimentado si llega al umbral. */
    public boolean estaBienAlimentado() {
        return peso >= pesoOptimo * 0.95;
    }

    /**
     * Registra una ración: el animal la come (sube de peso) y la ración queda
     * en el historial, ordenado de la más vieja a la más nueva.
     *
     * <p>El historial tiene lugar para {@value #CAPACIDAD_DEL_HISTORIAL}
     * raciones. Cuando está lleno, se descarta la más vieja (la posición 0),
     * las demás se corren un lugar hacia la izquierda y la nueva entra al
     * final. Así la posición 0 es <b>siempre</b> la más antigua y la última
     * usada es <b>siempre</b> la más reciente, sin importar cuántas raciones
     * haya comido el animal en su vida.
     *
     * @param racion la ración que acaba de comer; no puede ser nula
     */
    public void registrarRacion(Racion racion) {
        if (racion == null) {
            throw new IllegalArgumentException("la ración no puede ser nula");
        }

        if (getCantidadDeRaciones() == historialDeRaciones.length) {
            // Lleno: cada ración pisa a la anterior. La de la posición 0 se
            // pierde, y la última posición queda libre para la nueva.
            for (int i = 1; i < historialDeRaciones.length; i++) {
                historialDeRaciones[i - 1] = historialDeRaciones[i];
            }
            // Sin esto, la última posición conserva la ración vieja: el
            // historial sigue "lleno", getCantidadDeRaciones() devuelve 10
            // y la línea de abajo se va de rango.
            historialDeRaciones[historialDeRaciones.length - 1] = null;
        }

        historialDeRaciones[getCantidadDeRaciones()] = racion;
        peso += racion.pesoQueAporta();
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public double getPesoOptimo() {
        return pesoOptimo;
    }

    public int getCantidadDeRaciones() {
        for(int i = 0; i < historialDeRaciones.length; i++) {
            if(historialDeRaciones[i] == null) {
                return i;
            }
        }
        return historialDeRaciones.length;
    }

    /**
     * El historial, de la más vieja a la más nueva, con sólo las posiciones
     * usadas. Devuelve una <b>copia</b>: si devolviéramos el arreglo interno,
     * cualquiera podría escribir {@code getHistorialDeRaciones()[0] = null}
     * desde afuera y romper el orden sin pasar por {@link #registrarRacion}.
     */
    public Racion[] getHistorialDeRaciones() {
        return Arrays.copyOf(historialDeRaciones, getCantidadDeRaciones());
    }

    /** La última ración que comió, o {@code null} si todavía no comió ninguna. */
    public Racion getUltimaRacion() {
        return getCantidadDeRaciones() == 0 ? null : historialDeRaciones[getCantidadDeRaciones() - 1];
    }

    /**
     * Cuántas raciones comió en un día dado, según el historial. Es lo que
     * usa el cuidador para decidir si ya cumplió su cuota de hoy (regla R5).
     *
     * <p>El historial guarda sólo las últimas {@value #CAPACIDAD_DEL_HISTORIAL}
     * raciones, pero como ninguna especie come más de unas pocas veces por
     * día, para "hoy" el número es siempre exacto.
     */
    public int getCantidadDeRaciones(LocalDate dia) {
        if (dia == null) {
            throw new IllegalArgumentException("el día no puede ser nulo");
        }
        int total = 0;
        for (int i = 0; i < getCantidadDeRaciones(); i++) {
            if (historialDeRaciones[i].fecha().toLocalDate().equals(dia)) {
                total++;
            }
        }
        return total;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}

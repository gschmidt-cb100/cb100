package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.tda.datos;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Bicicleta (clase de datos).
 * Representa una bicicleta del sistema de alquiler, identificada por su número de serie.
 * Conoce los kilómetros de uso acumulados, su estado y cuántas veces se usó (retiró).
 *
 * Invariantes:
 *  - numeroDeSerie no es null ni vacío, es alfanumérico y no cambia.
 *  - kilometros >= 0 y nunca disminuye.
 *  - cantidadDeUsos >= 0 y nunca disminuye.
 *  - estado no es null.
 */
public class Bicicleta {

//ATRIBUTOS

    private final String numeroDeSerie;
    private double kilometros;
    private EstadoDeBicicleta estado;
    private int cantidadDeUsos;

//CONSTRUCTORES

    /**
     * Crea una bicicleta nueva: sin kilómetros, sin usos y DISPONIBLE.
     * @param numeroDeSerie número de serie alfanumérico (solo letras y dígitos), no null ni vacío
     * @throws IllegalArgumentException si el número de serie es null, vacío o no es alfanumérico
     */
    public Bicicleta(String numeroDeSerie) {
        this(numeroDeSerie, 0);
    }

    /**
     * Crea una bicicleta con kilómetros de uso previos, sin usos registrados y DISPONIBLE.
     * @param numeroDeSerie número de serie alfanumérico (solo letras y dígitos), no null ni vacío
     * @param kilometros kilómetros de uso, mayor o igual a cero
     * @throws IllegalArgumentException si el número de serie es null, vacío o no es alfanumérico,
     *                                  o si los km son negativos
     */
    public Bicicleta(String numeroDeSerie, double kilometros) {
        validarNumeroDeSerie(numeroDeSerie);
        ValidacionesUtiles.validarFalso(kilometros < 0, "Los kilómetros no pueden ser negativos");
        this.numeroDeSerie = numeroDeSerie;
        this.kilometros = kilometros;
        this.estado = EstadoDeBicicleta.DISPONIBLE;
        this.cantidadDeUsos = 0;
    }

//METODOS DE COMPORTAMIENTO

    /**
     * Registra un uso de la bicicleta (se la retiró de una estación).
     * @throws IllegalStateException si la bicicleta está en reparación
     * post: la cantidad de usos aumenta en uno
     */
    public void registrarUso() {
        ValidacionesUtiles.validarEstadoFalso(estaEnReparacion(), "La bicicleta " + this.numeroDeSerie + " está en reparación, no se puede usar");
        this.cantidadDeUsos++;
    }

    /**
     * Suma kilómetros de uso a la bicicleta (por ejemplo, al terminar un viaje).
     * @param kilometrosRecorridos kilómetros a sumar, mayor que cero
     * @throws IllegalArgumentException si kilometrosRecorridos no es mayor que cero
     * post: los kilómetros de la bicicleta aumentan en kilometrosRecorridos
     */
    public void agregarKilometros(double kilometrosRecorridos) {
        ValidacionesUtiles.esMayorQueCero(kilometrosRecorridos, "kilometrosRecorridos");
        this.kilometros += kilometrosRecorridos;
    }

    /**
     * Pasa la bicicleta al estado EN_REPARACION.
     * @throws IllegalStateException si ya estaba en reparación
     */
    public void enviarAReparacion() {
        ValidacionesUtiles.validarEstadoFalso(estaEnReparacion(), "La bicicleta " + this.numeroDeSerie + " ya está en reparación");
        this.estado = EstadoDeBicicleta.EN_REPARACION;
    }

    /**
     * Pasa la bicicleta al estado DISPONIBLE.
     * @throws IllegalStateException si no estaba en reparación
     */
    public void finalizarReparacion() {
        ValidacionesUtiles.validarEstadoFalso(!estaEnReparacion(), "La bicicleta " + this.numeroDeSerie + " no está en reparación");
        this.estado = EstadoDeBicicleta.DISPONIBLE;
    }

//CONSULTAS

    /**
     * @return true si la bicicleta está en reparación
     */
    public boolean estaEnReparacion() {
        return this.estado == EstadoDeBicicleta.EN_REPARACION;
    }

//GETTERS

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public double getKilometros() {
        return kilometros;
    }

    public EstadoDeBicicleta getEstado() {
        return estado;
    }

    public int getCantidadDeUsos() {
        return cantidadDeUsos;
    }

//METODOS PRIVADOS

    /**
     * Valida que el número de serie no sea null ni vacío y que tenga solo letras y dígitos.
     * @param numeroDeSerie número de serie a validar
     * @throws IllegalArgumentException si no cumple
     */
    private static void validarNumeroDeSerie(String numeroDeSerie) {
        ValidacionesUtiles.validarDistintoDeNull(numeroDeSerie, "numeroDeSerie");
        ValidacionesUtiles.validarFalso(numeroDeSerie.isEmpty(), "El número de serie no puede estar vacío");
        for (char caracter : numeroDeSerie.toCharArray()) {
            ValidacionesUtiles.validarFalso(!Character.isLetterOrDigit(caracter),
                    "El número de serie '" + numeroDeSerie + "' debe ser alfanumérico");
        }
    }

//METODOS GENERALES

    /**
     * Dos bicicletas son iguales si tienen el mismo número de serie.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bicicleta otra = (Bicicleta) o;
        return this.numeroDeSerie.equals(otra.numeroDeSerie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroDeSerie);
    }

    @Override
    public String toString() {
        return "Bicicleta " + numeroDeSerie + " (" + kilometros + " km, " + cantidadDeUsos + " usos, " + estado + ")";
    }
}

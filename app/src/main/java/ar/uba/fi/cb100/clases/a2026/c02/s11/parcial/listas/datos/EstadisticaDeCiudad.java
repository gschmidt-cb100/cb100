package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA EstadisticaDeCiudad (clase de datos).
 * Acumula las mediciones válidas de UNA ciudad y guarda solo lo necesario para filtrar:
 * cantidad de mediciones válidas, temperatura mínima y temperatura máxima.
 *
 * Invariantes:
 *  - ciudad no es null y no cambia.
 *  - cantidadDeMedicionesValidas >= 1.
 *  - temperaturaMinima <= temperaturaMaxima.
 */
public class EstadisticaDeCiudad {

//ATRIBUTOS

    private final Ciudad ciudad;
    private int cantidadDeMedicionesValidas;
    private double temperaturaMinima;
    private double temperaturaMaxima;

//CONSTRUCTORES

    /**
     * Crea la estadística de la ciudad de la medición, a partir de esa primera medición.
     * @param medicion primera medición, no null y VALIDA
     * @throws IllegalArgumentException si la medición es null o no es válida
     */
    public EstadisticaDeCiudad(Medicion medicion) {
        validarMedicion(medicion);
        this.ciudad = medicion.getCiudad();
        this.cantidadDeMedicionesValidas = 1;
        this.temperaturaMinima = medicion.getTemperatura();
        this.temperaturaMaxima = medicion.getTemperatura();
    }

//METODOS DE COMPORTAMIENTO

    /**
     * Incorpora una medición válida de la misma ciudad.
     * @param medicion medición a agregar, no null, VALIDA y de la misma ciudad
     * @throws IllegalArgumentException si la medición es null, no es válida o es de otra ciudad
     * post: aumenta la cantidad de mediciones y se actualizan mínima y máxima
     */
    public void agregar(Medicion medicion) {
        validarMedicion(medicion);
        ValidacionesUtiles.validarFalso(!esDe(medicion.getCiudad()), "La medición no es de " + this.ciudad);
        this.cantidadDeMedicionesValidas++;
        this.temperaturaMinima = Math.min(this.temperaturaMinima, medicion.getTemperatura());
        this.temperaturaMaxima = Math.max(this.temperaturaMaxima, medicion.getTemperatura());
    }

//CONSULTAS

    /**
     * @return la amplitud térmica: temperatura máxima menos temperatura mínima
     */
    public double getAmplitudTermica() {
        return this.temperaturaMaxima - this.temperaturaMinima;
    }

    /**
     * @param ciudad ciudad a comparar
     * @return true si esta estadística es de esa ciudad
     */
    public boolean esDe(Ciudad ciudad) {
        return this.ciudad.equals(ciudad);
    }

//GETTERS

    public Ciudad getCiudad() {
        return ciudad;
    }

    public int getCantidadDeMedicionesValidas() {
        return cantidadDeMedicionesValidas;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

//METODOS PRIVADOS

    private static void validarMedicion(Medicion medicion) {
        ValidacionesUtiles.validarDistintoDeNull(medicion, "medicion");
        ValidacionesUtiles.validarFalso(!medicion.esValida(), "Solo se pueden agregar mediciones válidas");
    }

//METODOS GENERALES

    /**
     * Dos estadísticas son iguales si son de la misma ciudad.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticaDeCiudad otra = (EstadisticaDeCiudad) o;
        return this.ciudad.equals(otra.ciudad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciudad);
    }

    @Override
    public String toString() {
        return ciudad + ": " + cantidadDeMedicionesValidas + " mediciones, min " + temperaturaMinima
                + "°, max " + temperaturaMaxima + "°, amplitud " + getAmplitudTermica() + "°";
    }
}

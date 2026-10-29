package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos;

import java.util.List;
import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Ciudad (clase de datos).
 * Una ciudad se identifica por su nombre y su provincia (puede haber dos ciudades
 * con el mismo nombre en provincias distintas).
 */
public class Ciudad {

//ATRIBUTOS

    private final String nombre;
    private final String provincia;

//CONSTRUCTORES

    /**
     * @param nombre nombre de la ciudad, no null ni vacío
     * @param provincia provincia de la ciudad, no null ni vacía
     * @throws IllegalArgumentException si alguno es null o vacío
     */
    public Ciudad(String nombre, String provincia) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        ValidacionesUtiles.validarDistintoDeNull(provincia, "provincia");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre de la ciudad no puede estar vacío");
        ValidacionesUtiles.validarFalso(provincia.isBlank(), "La provincia no puede estar vacía");
        this.nombre = nombre;
        this.provincia = provincia;
    }

//CONSULTAS

    /**
     * @param provincias provincias a considerar, no null
     * @return true si la provincia de la ciudad está entre las indicadas
     */
    public boolean perteneceAAlgunaDe(List<String> provincias) {
        ValidacionesUtiles.validarDistintoDeNull(provincias, "provincias");
        return provincias.contains(this.provincia);
    }

//GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getProvincia() {
        return provincia;
    }

//METODOS GENERALES

    /**
     * Dos ciudades son iguales si tienen el mismo nombre y la misma provincia.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudad otra = (Ciudad) o;
        return this.nombre.equals(otra.nombre) && this.provincia.equals(otra.provincia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, provincia);
    }

    @Override
    public String toString() {
        return nombre + " (" + provincia + ")";
    }
}

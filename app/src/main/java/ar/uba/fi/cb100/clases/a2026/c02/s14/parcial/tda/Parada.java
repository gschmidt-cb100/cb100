package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Parada: un punto de un recorrido donde el vehículo se detiene.
 *
 * Responsabilidad: conocer sus datos y garantizar que son válidos. Es
 * inmutable: una vez creada no cambia, por eso se puede compartir entre el
 * recorrido y quien la consulta sin riesgo.
 *
 * Hipótesis:
 *  H1. Dos paradas son la misma si coinciden nombre y dirección, sin
 *      distinguir mayúsculas. La distancia y el tiempo no identifican a la
 *      parada: dependen del recorrido que pasa por ella.
 *  H2. La distancia y el tiempo pueden ser cero (la primera parada está en
 *      el inicio del recorrido), pero no negativos.
 */
public class Parada {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String nombre;
    private final String direccion;
    private final double distanciaDesdeElInicio;
    private final int tiempoDesdeLaParadaAnterior;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code nombre} y {@code direccion} no son null ni están vacíos;
     *      {@code distanciaDesdeElInicio} (km) y
     *      {@code tiempoDesdeLaParadaAnterior} (minutos) son mayores o iguales a cero.
     * post: crea la parada con esos datos. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Parada(String nombre, String direccion, double distanciaDesdeElInicio, int tiempoDesdeLaParadaAnterior) {
        validarTexto(nombre, "nombre");
        validarTexto(direccion, "dirección");
        ValidacionesUtiles.validarFalso(distanciaDesdeElInicio < 0, "La distancia desde el inicio no puede ser negativa.");
        ValidacionesUtiles.validarFalso(tiempoDesdeLaParadaAnterior < 0, "El tiempo desde la parada anterior no puede ser negativo.");
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
        this.distanciaDesdeElInicio = distanciaDesdeElInicio;
        this.tiempoDesdeLaParadaAnterior = tiempoDesdeLaParadaAnterior;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code nombre} no es null.
     * post: devuelve true si el nombre de la parada es el recibido, sin
     *       distinguir mayúsculas ni espacios en los extremos.
     */
    public boolean tieneNombre(String nombre) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        return this.nombre.equalsIgnoreCase(nombre.trim());
    }

    private static void validarTexto(String texto, String nombreDelDato) {
        ValidacionesUtiles.validarDistintoDeNull(texto, nombreDelDato);
        ValidacionesUtiles.validarFalso(texto.isBlank(), "El dato " + nombreDelDato + " no puede estar vacío.");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /**
     * post: devuelve true si el otro objeto es una parada con el mismo
     *       nombre y la misma dirección (H1).
     */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (otro == null || getClass() != otro.getClass()) {
            return false;
        }
        Parada otra = (Parada) otro;
        return nombre.equalsIgnoreCase(otra.nombre) && direccion.equalsIgnoreCase(otra.direccion);
    }

    @Override
    public int hashCode() {
        return 31 * nombre.toLowerCase().hashCode() + direccion.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return nombre + " (" + direccion + "), km " + distanciaDesdeElInicio + ", " + tiempoDesdeLaParadaAnterior + " min";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    /** post: devuelve la distancia desde el inicio del recorrido, en km. */
    public double getDistanciaDesdeElInicio() {
        return distanciaDesdeElInicio;
    }

    /** post: devuelve el tiempo estimado desde la parada anterior, en minutos. */
    public int getTiempoDesdeLaParadaAnterior() {
        return tiempoDesdeLaParadaAnterior;
    }
}

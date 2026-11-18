package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Una fila del Excel. Es un valor inmutable: dos personas con los mismos
 * datos son la misma persona. Los DNI de padre y madre son {@code null}
 * cuando no se conocen (checklist de TDA: "sin valor" es null, nunca 0 ni -1).
 */
public record Persona(long dni, String nombre, String apellido, int edad,
                      String ciudad, String provincia, String pais,
                      String telefono, String email, String direccion, String codigoPostal,
                      Geocodificacion geocodificacion, Long dniDelPadre, Long dniDeLaMadre) {

    public static final int EDAD_MAXIMA = 120;

    public Persona {
        ValidacionesUtiles.validarFalso(dni <= 0, "El DNI debe ser positivo: " + dni);
        validarTexto(nombre, "nombre");
        validarTexto(apellido, "apellido");
        ValidacionesUtiles.validarRango(edad, 0, EDAD_MAXIMA, "edad");
        validarTexto(ciudad, "ciudad");
        validarTexto(provincia, "provincia");
        validarTexto(pais, "país");
        ValidacionesUtiles.validarDistintoDeNull(telefono, "teléfono");
        ValidacionesUtiles.validarDistintoDeNull(email, "email");
        ValidacionesUtiles.validarDistintoDeNull(direccion, "dirección");
        ValidacionesUtiles.validarDistintoDeNull(codigoPostal, "código postal");
        ValidacionesUtiles.validarDistintoDeNull(geocodificacion, "geocodificación");
        ValidacionesUtiles.validarFalso(dniDelPadre != null && dniDelPadre == dni, "Una persona no puede ser su propio padre");
        ValidacionesUtiles.validarFalso(dniDeLaMadre != null && dniDeLaMadre == dni, "Una persona no puede ser su propia madre");
    }

    private static void validarTexto(String valor, String nombreDelCampo) {
        ValidacionesUtiles.validarDistintoDeNull(valor, nombreDelCampo);
        ValidacionesUtiles.validarFalso(valor.isBlank(), "El campo " + nombreDelCampo + " no puede estar vacío");
    }

    /** post: devuelve "Apellido, Nombre". */
    public String getNombreCompleto() {
        return apellido + ", " + nombre;
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e05;

/**
 * Excepción propia para notas fuera del rango válido [0, 10].
 */
public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException(String mensaje) {
        super(mensaje);
    }
}

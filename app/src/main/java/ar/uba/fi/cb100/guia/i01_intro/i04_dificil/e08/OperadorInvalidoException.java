package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e08;

/** Excepcion lanzada al recibir un operador no soportado. */
public class OperadorInvalidoException extends RuntimeException {
    public OperadorInvalidoException(String mensaje) {
        super(mensaje);
    }
}

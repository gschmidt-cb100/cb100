package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e08;

/** Excepcion lanzada al intentar dividir por cero. */
public class DivisionPorCeroException extends RuntimeException {
    public DivisionPorCeroException(String mensaje) {
        super(mensaje);
    }
}

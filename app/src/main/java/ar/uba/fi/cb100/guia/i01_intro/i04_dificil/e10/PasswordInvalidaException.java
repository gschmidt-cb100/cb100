package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e10;

/** Excepcion lanzada cuando la contraseña no cumple alguna regla. */
public class PasswordInvalidaException extends RuntimeException {
    public PasswordInvalidaException(String mensaje) {
        super(mensaje);
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e10;

/**
 * Excepción propia lanzada cuando el saldo no alcanza para extraer.
 */
public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

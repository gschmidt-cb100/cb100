package ar.uba.fi.cb100.material.i01_intro;

/**
 * Excepción PROPIA y <b>chequeada</b> (extiende {@code Exception}): el
 * compilador obliga a quien llame a {@code extraer} a hacerse cargo — con un
 * {@code catch} o declarando {@code throws}. Ideal para situaciones que son
 * parte del negocio y que el que llama DEBE contemplar.
 * <p>
 * Además de un buen mensaje, transporta datos útiles para quien la capture
 * (cuánto faltó), como cualquier otro objeto.
 */
public class SaldoInsuficienteException extends Exception {

    private final int faltante;

    public SaldoInsuficienteException(int montoPedido, int saldoDisponible) {
        super("pediste $" + montoPedido + " pero el saldo es $" + saldoDisponible);
        this.faltante = montoPedido - saldoDisponible;
    }

    /** Cuántos pesos faltaron para poder extraer. */
    public int faltante() {
        return faltante;
    }
}

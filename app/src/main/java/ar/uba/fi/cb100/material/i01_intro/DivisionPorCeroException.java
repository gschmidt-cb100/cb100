package ar.uba.fi.cb100.material.i01_intro;

/**
 * Excepción propia (de dominio). Extender {@link RuntimeException} la hace
 * "no chequeada": no obliga a declararla con {@code throws}.
 */
public class DivisionPorCeroException extends RuntimeException {

    public DivisionPorCeroException(int dividendo) {
        super("intento de dividir " + dividendo + " por cero");
    }
}

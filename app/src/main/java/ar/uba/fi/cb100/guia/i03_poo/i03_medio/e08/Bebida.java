package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e08;

/**
 * Patrón Método Plantilla (Template Method).
 * preparar() es un método CONCRETO que fija el esqueleto del algoritmo
 * (el orden de los pasos). Los pasos que varían se declaran abstractos
 * y los completan las subclases.
 */
public abstract class Bebida {

    /**
     * Método plantilla: define la secuencia invariante de preparación.
     * Es final para que las subclases no puedan alterar el orden de los pasos.
     */
    public final String preparar() {
        StringBuilder pasos = new StringBuilder();
        pasos.append(hervirAgua()).append("\n");
        pasos.append(agregarPrincipal()).append("\n");
        pasos.append(servir());
        return pasos.toString();
    }

    /** Paso común a todas las bebidas de infusión. */
    protected String hervirAgua() {
        return "Hervir agua";
    }

    /** Paso variable: cada bebida agrega su ingrediente principal. */
    protected abstract String agregarPrincipal();

    /** Paso variable: cada bebida se sirve a su manera. */
    protected abstract String servir();
}

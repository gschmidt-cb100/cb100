package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

/**
 * Contrato de un vagón en el que se puede poner y sacar {@link Carga}. Es lo
 * único que el {@link Tren} usa para cargar y descargar: no sabe qué tipos de
 * carga existen ni qué acepta cada vagón, sólo pregunta
 * {@link #aceptaCarga(Carga)} y actúa.
 */
public interface VagonCargable {

    /**
     * pre: {@code carga} no es null.
     * post: indica si la carga entra en este vagón AHORA: es de un tipo que el
     *       vagón admite, hay lugar y el peso no supera la capacidad. No
     *       modifica nada.
     */
    boolean aceptaCarga(Carga carga);

    /**
     * pre: {@link #aceptaCarga(Carga)} es verdadero para {@code carga}.
     * post: la carga queda a bordo. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException} (tipo o peso) o
     *       {@link IllegalStateException} (lleno) y no cambia nada.
     */
    void agregarCarga(Carga carga);

    /**
     * pre: {@code carga} está a bordo.
     * post: la carga deja el vagón. Si no estaba, lanza {@link IllegalArgumentException}.
     */
    void quitarCarga(Carga carga);

    /** post: indica si {@code carga} está a bordo de este vagón. */
    boolean contiene(Carga carga);

    /** post: indica si no hay ninguna carga a bordo. */
    boolean estaVacio();

    /** post: indica si no entra ninguna carga más, por cantidad o por peso. */
    boolean estaLleno();

    /** post: indica si el vagón admite cargas de ese tipo (independientemente de si hay lugar). */
    boolean admiteTipo(TipoDeCarga tipo);

    /** post: devuelve cuántas cargas hay a bordo. */
    int getCantidadDeCargas();

    /** post: devuelve cuántas cargas más entran (por cantidad, sin mirar el peso). */
    int getLugaresLibres();

    /** post: devuelve el peso [t] total de lo que hay a bordo. */
    double getPesoDeCarga();

    /** post: devuelve cuántas toneladas más entran. */
    double getPesoDisponible();
}

package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e03;

/**
 * TDA Mesa: una mesa del restaurant con número, capacidad y estado.
 *
 * <p>Diagrama de estados (SUS transiciones válidas, y ninguna otra):</p>
 * <pre>
 *   LIBRE --reservar()--&gt; RESERVADA --ocupar()--&gt; OCUPADA --liberar()--&gt; LIBRE
 * </pre>
 *
 * <p>Las transiciones validan el estado ACTUAL: reservar una mesa ocupada,
 * ocupar una libre o liberar una reservada son errores. Esa lógica vive acá
 * (delegación): el Restaurant nunca asigna el estado de una mesa a mano.</p>
 */
public class Mesa {

    private final int numero;
    private final int capacidad;
    private EstadoDeMesa estado;

    /**
     * pre: {@code numero} no es negativo y {@code capacidad} es mayor que 0.<br>
     * post: crea una mesa LIBRE con ese número y capacidad.
     *
     * @param numero número identificador de la mesa.
     * @param capacidad cantidad máxima de comensales.
     * @throws IllegalArgumentException si algún parámetro es inválido.
     */
    public Mesa(int numero, int capacidad) {
        if (numero < 0) {
            throw new IllegalArgumentException(
                    "El número de mesa no puede ser negativo: " + numero);
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que 0, se recibió: " + capacidad);
        }
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = EstadoDeMesa.LIBRE;
    }

    /**
     * Transición LIBRE -&gt; RESERVADA.
     *
     * <p>pre: la mesa está LIBRE.<br>
     * post: la mesa queda RESERVADA.</p>
     *
     * @throws IllegalStateException si la mesa no está libre.
     */
    public void reservar() {
        if (estado != EstadoDeMesa.LIBRE) {
            throw new IllegalStateException(
                    "Sólo se puede reservar una mesa LIBRE; la mesa " + numero + " está " + estado);
        }
        estado = EstadoDeMesa.RESERVADA;
    }

    /**
     * Transición RESERVADA -&gt; OCUPADA.
     *
     * <p>pre: la mesa está RESERVADA.<br>
     * post: la mesa queda OCUPADA.</p>
     *
     * @throws IllegalStateException si la mesa no está reservada.
     */
    public void ocupar() {
        if (estado != EstadoDeMesa.RESERVADA) {
            throw new IllegalStateException(
                    "Sólo se puede ocupar una mesa RESERVADA; la mesa " + numero + " está " + estado);
        }
        estado = EstadoDeMesa.OCUPADA;
    }

    /**
     * Transición OCUPADA -&gt; LIBRE.
     *
     * <p>pre: la mesa está OCUPADA.<br>
     * post: la mesa queda LIBRE.</p>
     *
     * @throws IllegalStateException si la mesa no está ocupada.
     */
    public void liberar() {
        if (estado != EstadoDeMesa.OCUPADA) {
            throw new IllegalStateException(
                    "Sólo se puede liberar una mesa OCUPADA; la mesa " + numero + " está " + estado);
        }
        estado = EstadoDeMesa.LIBRE;
    }

    /**
     * pre: -.<br>
     * post: devuelve el número de la mesa.
     *
     * @return el número identificador.
     */
    public int numero() {
        return numero;
    }

    /**
     * pre: -.<br>
     * post: devuelve la capacidad de comensales.
     *
     * @return la capacidad.
     */
    public int capacidad() {
        return capacidad;
    }

    /**
     * pre: -.<br>
     * post: devuelve el estado actual de la mesa.
     *
     * @return el estado (nunca null).
     */
    public EstadoDeMesa estado() {
        return estado;
    }

    /**
     * pre: -.<br>
     * post: devuelve número, capacidad y estado de la mesa.
     */
    @Override
    public String toString() {
        return "Mesa " + numero + " (capacidad " + capacidad + ", " + estado + ")";
    }
}

package ar.uba.fi.cb100.material.i13_diseno.tostadora;

/**
 * Una ranura de la tostadora: sabe si está libre u ocupada y cuántos panes
 * tostó. Es un TDA chico con responsabilidad propia: la {@link Tostadora} no
 * implementa lo que es de la ranura (checklist de TDA, punto 6), sólo la
 * busca por número y le pide que lo haga.
 */
public class Ranura {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int numero;
    private int panesTostados;
    private EstadoDeRanura estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code numero} es mayor que cero.
     * post: crea la ranura LIBRE, sin panes tostados.
     */
    public Ranura(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("el número de ranura debe ser positivo: " + numero);
        }
        this.numero = numero;
        this.panesTostados = 0;
        this.estado = EstadoDeRanura.LIBRE;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: la ranura está LIBRE.
     * post: la ranura queda OCUPADA. Si estaba ocupada, lanza
     *       {@link IllegalStateException} y no cambia nada.
     */
    public void iniciarTostado() {
        if (estado != EstadoDeRanura.LIBRE) {
            throw new IllegalStateException("la ranura " + numero + " ya está ocupada");
        }
        estado = EstadoDeRanura.OCUPADA;
    }

    /**
     * pre: la ranura está OCUPADA.
     * post: la ranura queda LIBRE y cuenta un pan tostado más. Si estaba
     *       libre, lanza {@link IllegalStateException} y no cambia nada.
     */
    public void finalizarTostado() {
        if (estado != EstadoDeRanura.OCUPADA) {
            throw new IllegalStateException("la ranura " + numero + " no está tostando");
        }
        estado = EstadoDeRanura.LIBRE;
        panesTostados++;
    }

    /** post: indica si la ranura está libre. */
    public boolean estaLibre() {
        return estado == EstadoDeRanura.LIBRE;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "ranura " + numero + " (" + estado + ", " + panesTostados + " panes)";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve el número que identifica a la ranura (desde 1). */
    public int getNumero() {
        return numero;
    }

    /** post: devuelve cuántos tostados se completaron en esta ranura. */
    public int getPanesTostados() {
        return panesTostados;
    }

    /** post: devuelve el estado actual. */
    public EstadoDeRanura getEstado() {
        return estado;
    }
}

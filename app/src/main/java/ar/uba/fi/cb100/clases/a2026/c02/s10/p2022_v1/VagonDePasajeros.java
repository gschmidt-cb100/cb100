package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

/**
 * Vagón que sólo admite {@link TipoDeCarga#PASAJERO}. El límite real es la
 * cantidad de asientos; el peso máximo se deriva de ellos (asientos por el
 * peso promedio), así que nunca es el que manda.
 */
public class VagonDePasajeros extends VagonConCargas {

    /**
     * pre: {@code longitud} y {@code asientos} son mayores que cero.
     * post: crea el vagón vacío con esa cantidad de asientos.
     */
    public VagonDePasajeros(double longitud, int asientos) {
        super(longitud, asientos, asientos * Pasajero.PESO_PROMEDIO);
    }

    @Override
    public boolean admiteTipo(TipoDeCarga tipo) {
        return tipo == TipoDeCarga.PASAJERO;
    }

    /** post: devuelve la cantidad de asientos. */
    public int getAsientos() {
        return getCantidadDeCargas() + getLugaresLibres();
    }
}

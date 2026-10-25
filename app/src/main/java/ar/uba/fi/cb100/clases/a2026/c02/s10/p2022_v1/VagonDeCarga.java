package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Vagón de carga: admite los tipos que se le indican al construirlo (un
 * vagón plataforma acepta CONTENEDOR; una tolva, GRANEL; un jaula, GANADO;
 * uno mixto, varios). El límite real es el peso; la cantidad de bultos es un
 * tope secundario.
 */
public class VagonDeCarga extends VagonConCargas {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final TipoDeCarga[] tiposAdmitidos;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code longitud}, {@code capacidadEnToneladas} y {@code bultosMaximos}
     *      son mayores que cero; hay al menos un tipo admitido y ninguno es PASAJERO.
     * post: crea el vagón vacío que admite exactamente esos tipos.
     */
    public VagonDeCarga(double longitud, double capacidadEnToneladas, int bultosMaximos,
                        TipoDeCarga... tiposAdmitidos) {
        super(longitud, bultosMaximos, capacidadEnToneladas);
        ValidacionesUtiles.validarDistintoDeNull(tiposAdmitidos, "tipos admitidos");
        ValidacionesUtiles.validarFalso(tiposAdmitidos.length == 0, "Un vagón de carga admite al menos un tipo de carga");
        for (TipoDeCarga tipo : tiposAdmitidos) {
            ValidacionesUtiles.validarDistintoDeNull(tipo, "tipo admitido");
            ValidacionesUtiles.validarFalso(tipo == TipoDeCarga.PASAJERO, "Los pasajeros viajan en VagonDePasajeros");
        }
        this.tiposAdmitidos = tiposAdmitidos.clone();   // copia defensiva: el arreglo de afuera no es nuestro
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    @Override
    public boolean admiteTipo(TipoDeCarga tipo) {
        for (TipoDeCarga admitido : tiposAdmitidos) {
            if (admitido == tipo) {
                return true;
            }
        }
        return false;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve una copia de los tipos admitidos. */
    public TipoDeCarga[] getTiposAdmitidos() {
        return tiposAdmitidos.clone();
    }
}

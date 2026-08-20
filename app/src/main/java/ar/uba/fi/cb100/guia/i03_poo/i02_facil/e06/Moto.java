package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e06;

/** Moto: redefine la descripcion del vehiculo. */
public class Moto extends Vehiculo {

    public Moto(String marca) {
        super(marca);
    }

    @Override
    public String descripcion() {
        return "Moto de marca " + getMarca();
    }
}

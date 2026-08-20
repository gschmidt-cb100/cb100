package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e06;

/** Auto: redefine la descripcion del vehiculo. */
public class Auto extends Vehiculo {

    public Auto(String marca) {
        super(marca);
    }

    @Override
    public String descripcion() {
        return "Auto de marca " + getMarca();
    }
}

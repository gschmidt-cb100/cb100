package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e05;

/** Implementacion formal del saludo. */
public class Formal implements Saludador {

    @Override
    public String saludar() {
        return "Buenos dias, es un placer saludarlo.";
    }
}

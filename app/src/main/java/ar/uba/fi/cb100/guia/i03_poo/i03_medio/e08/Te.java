package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e08;

/** Subclase concreta que completa los pasos abstractos para preparar un té. */
public class Te extends Bebida {

    @Override
    protected String agregarPrincipal() {
        return "Poner saquito de té en infusión";
    }

    @Override
    protected String servir() {
        return "Servir en taza con rodaja de limón";
    }

    public static void main(String[] args) {
        Bebida te = new Te();
        System.out.println(te.preparar());
    }
}

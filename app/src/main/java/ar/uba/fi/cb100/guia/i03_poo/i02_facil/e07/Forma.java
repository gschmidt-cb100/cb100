package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e07;

/**
 * e07: clase abstracta Forma con area() abstracto.
 * Una clase abstracta no se puede instanciar directamente y puede
 * dejar metodos sin implementar para que los definan las subclases.
 */
public abstract class Forma {

    /** Area de la forma; cada subclase la calcula a su modo. */
    public abstract double area();
}

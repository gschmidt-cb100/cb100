package ar.uba.fi.cb100.material.i03_poo;

/**
 * TDA Figura como <b>interfaz</b>: el CONTRATO (qué sabe hacer una figura), sin
 * decir cómo. Cualquier figura concreta deberá saber dar su área y su perímetro.
 */
public interface Figura {
    double area();
    double perimetro();
}

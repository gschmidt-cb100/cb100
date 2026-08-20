package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e04;

/**
 * Tipo abstracto de dato para una figura geométrica.
 * Toda figura debe saber calcular su área y su perímetro.
 */
public interface Figura {

    /** @return área de la figura */
    double area();

    /** @return perímetro de la figura */
    double perimetro();
}

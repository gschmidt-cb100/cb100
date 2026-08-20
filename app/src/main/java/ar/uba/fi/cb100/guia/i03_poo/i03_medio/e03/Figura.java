package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e03;

/**
 * TDA Figura: abstrae cualquier forma geométrica capaz de calcular su área.
 * Es una interfaz: define el "qué" (contrato) sin fijar el "cómo".
 */
public interface Figura {

    /** Área de la figura. */
    double area();

    /**
     * Suma las áreas de un arreglo de figuras aprovechando el polimorfismo:
     * cada elemento responde a area() según su tipo concreto.
     */
    static double sumaAreas(Figura[] figuras) {
        double total = 0.0;
        for (Figura f : figuras) {
            total += f.area();
        }
        return total;
    }
}

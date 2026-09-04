package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

/**
 * TDA <b>Oveja</b>. La más simple de las tres especies concretas: no agrega
 * estado propio, sólo resuelve lo que {@link Animal} dejó abstracto.
 */
public class Oveja extends Animal {

    private static final int DESGASTE_DIARIO = 18;

    public Oveja(String nombre,
                 double peso, double pesoOptimo) {
        super(nombre, peso, pesoOptimo);
    }

    @Override
    public String especie() {
        return "Oveja";
    }

    @Override
    public boolean esCarnivoro() {
        return false;
    }

    @Override
    protected int desgasteDiario() {
        return DESGASTE_DIARIO;
    }
}

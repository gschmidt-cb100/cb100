package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

/**
 * tda Gallina, se agrega un estado propio, que ni las ovejas ni las
 * jirafas tienen: es lo que usa el cuidador para saber cuántos huevos
 * recolectar en su rutina
 */
public class Gallina extends Animal {

    private static final int DESGASTE_DIARIO = 10;

    private static final int HUEVOS_POR_DIA_MINIMO = 0;
    private static final int HUEVOS_POR_DIA_MAXIMO = 2;

    private final int huevosPorDia;

    public Gallina(String nombre,
                   double peso, double pesoOptimo,
                   int huevosPorDia) {
        super(nombre, peso, pesoOptimo);
        if (huevosPorDia < HUEVOS_POR_DIA_MINIMO || huevosPorDia > HUEVOS_POR_DIA_MAXIMO) {
            throw new IllegalArgumentException(
                    "una gallina pone entre " + HUEVOS_POR_DIA_MINIMO + " y " + HUEVOS_POR_DIA_MAXIMO
                            + " huevos por día, y llegó " + huevosPorDia);
        }
        this.huevosPorDia = huevosPorDia;
    }

    @Override
    public String especie() {
        return "Gallina";
    }

    @Override
    public boolean esCarnivoro() {
        return false;
    }

    @Override
    protected int desgasteDiario() {
        return DESGASTE_DIARIO;
    }

    /** la usa el cuidador para saber cuántos huevos recolectar en la rutina */
    public int huevosPorDia() {
        return huevosPorDia;
    }
}

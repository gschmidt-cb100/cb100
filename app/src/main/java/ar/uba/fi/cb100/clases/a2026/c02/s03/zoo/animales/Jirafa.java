package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

/**
 * TDA <b>Jirafa</b>. Cuelga directamente de {@link Animal}: no hay una clase
 * intermedia {@code Herbivoro} porque, con dos herbívoros, todavía no se
 * gana nada. Se agrega cuando haya varios que compartan comportamiento, no
 * antes.
 *
 * <p>Compará con {@link Leon}, que sí tiene nivel intermedio: la jerarquía no
 * tiene por qué ser simétrica.
 */
public class Jirafa extends Animal {

    private static final int DESGASTE_DIARIO = 25;

    private final int alturaEnCm;

    public Jirafa(String nombre,
                  double peso, double pesoOptimo,
                  int alturaEnCm) {
        super(nombre, peso, pesoOptimo);
        if (alturaEnCm < 200 || alturaEnCm > 600) {
            throw new IllegalArgumentException(
                    "altura de jirafa fuera de rango: " + alturaEnCm + " cm");
        }
        this.alturaEnCm = alturaEnCm;
    }

    @Override
    public String especie() {
        return "Jirafa";
    }

    @Override
    public boolean esCarnivoro() {
        return false;
    }

    @Override
    protected int desgasteDiario() {
        return DESGASTE_DIARIO;
    }

    /** La usa el cuidador para saber a qué altura colgar el alimento. */
    public int alturaEnCm() {
        return alturaEnCm;
    }
}

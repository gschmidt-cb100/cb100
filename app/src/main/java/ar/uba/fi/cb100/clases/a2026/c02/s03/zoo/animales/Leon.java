package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

/**
 * TDA <b>León</b>. Tercer nivel de la jerarquía: {@code Leon} es un
 * {@link Felino}, que a su vez es un {@link Animal}.
 *
 * <p>Es una clase <b>concreta</b>: acá sí se puede hacer {@code new}. Sólo
 * tiene que resolver lo que quedó pendiente y no resolvió {@code Felino}.
 */
public class Leon extends Felino {

    private static final int DESGASTE_DIARIO = 35;

    public Leon(String nombre,
                double peso,
                double pesoOptimo,
                int nivelDeAgresividad) {
        super(nombre, peso, pesoOptimo, nivelDeAgresividad);
    }

    @Override
    public String especie() {
        return "León";
    }

    @Override
    protected int desgasteDiario() {
        return DESGASTE_DIARIO;
    }

    // esCarnivoro() no se escribe: lo hereda de Felino, que ya lo resolvió.
}

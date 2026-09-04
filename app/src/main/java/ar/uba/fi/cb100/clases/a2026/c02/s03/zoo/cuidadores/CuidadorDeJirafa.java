package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Jirafa;

import java.time.LocalDateTime;

/**
 * Cuidador especializado en jirafas. Dos comidas por día, servidas en alto:
 * la rutina se adapta a la altura de cada animal.
 */
public class CuidadorDeJirafa extends Cuidador {

    private static final int COMIDAS_POR_DIA = 2;
    /** Kilos que gana la jirafa con una ración. Valor provisorio, a ajustar. */
    private static final double PESO_DE_LA_RACION = 2.0;

    /** El comedero se cuelga un poco por debajo de la cabeza. */
    private static final int MARGEN_BAJO_LA_CABEZA_CM = 40;

    public CuidadorDeJirafa(String nombre, int legajo) {
        super(nombre, legajo);
    }

    @Override
    public boolean puedeAtender(Animal animal) {
        return animal instanceof Jirafa;
    }

    @Override
    protected int comidasPorDia() {
        return COMIDAS_POR_DIA;
    }

    @Override
    protected Racion prepararRacion(Animal animal) {
        Jirafa jirafa = (Jirafa) animal;   // seguro: puedeAtender ya lo garantizó
        int alturaDelComedero = jirafa.alturaEnCm() - MARGEN_BAJO_LA_CABEZA_CM;

        String[] pasos = {
                "Subir a la plataforma elevada",
                "Colgar el comedero a " + alturaDelComedero + " cm",
                "Cargar ramas de acacia y hojas frescas",
                "Bajar y verificar que alcance sin estirarse",
        };
        return new Racion(LocalDateTime.now(), pasos, PESO_DE_LA_RACION);
    }

    @Override
    public String especialidad() {
        return "jirafas";
    }
}

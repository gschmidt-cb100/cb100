package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Felino;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Leon;

import java.time.LocalDateTime;

/**
 * Cuidador especializado en leones. Una comida grande por día, con protocolo
 * de seguridad que depende de la agresividad del animal.
 *
 * <p>Fijate que {@link #prepararRacion} recibe un {@link Animal} pero necesita
 * un {@link Felino} para consultar la agresividad. El <i>cast</i> es seguro
 * porque el método plantilla ya verificó {@link #puedeAtender} antes de
 * llamar acá. Ese es el contrato entre la plantilla y sus huecos.
 */
public class CuidadorDeLeon extends Cuidador {

    private static final int COMIDAS_POR_DIA = 1;
    /** Kilos que gana el león con una ración. Valor provisorio, a ajustar. */
    private static final double PESO_DE_LA_RACION = 3.0;
    private static final int KILOS_DE_CARNE = 8;

    public CuidadorDeLeon(String nombre, int legajo) {
        super(nombre, legajo);
    }

    @Override
    public boolean puedeAtender(Animal animal) {
        return animal instanceof Leon;
    }

    @Override
    protected int comidasPorDia() {
        return COMIDAS_POR_DIA;
    }

    @Override
    protected Racion prepararRacion(Animal animal) {
        Leon leon = (Leon) animal;   // seguro: puedeAtender ya lo garantizó

        String[] pasos;
        if (leon.necesitaEncierroParaAlimentar()) {
            pasos = new String[]{
                    "Activar el cierre de la jaula interna",
                    "Esperar a que el león pase al sector cerrado",
                    "Depositar " + KILOS_DE_CARNE + " kg de carne en el sector abierto",
                    "Retirarse y liberar el cierre",
            };
        } else {
            pasos = new String[]{
                    "Verificar la reja",
                    "Lanzar " + KILOS_DE_CARNE + " kg de carne por la tolva",
                    "Observar desde afuera hasta que termine",
            };
        }
        return new Racion(LocalDateTime.now(), pasos, PESO_DE_LA_RACION);
    }

    @Override
    public String especialidad() {
        return "leones";
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Oveja;

import java.time.LocalDateTime;

/**
 * Cuidador especializado en ovejas. Tres comidas chicas por día, sin ningún
 * protocolo de seguridad: la rutina más simple de las tres.
 */
public class CuidadorDeOveja extends Cuidador {

    private static final int COMIDAS_POR_DIA = 3;
    /** Kilos que gana la oveja con una ración. Valor provisorio, a ajustar. */
    private static final double PESO_DE_LA_RACION = 0.5;

    public CuidadorDeOveja(String nombre, int legajo) {
        super(nombre, legajo);
    }

    @Override
    public boolean puedeAtender(Animal animal) {
        return animal instanceof Oveja;
    }

    @Override
    protected int comidasPorDia() {
        return COMIDAS_POR_DIA;
    }

    @Override
    protected Racion prepararRacion(Animal animal) {
        String[] pasos = {
                "Entrar al corral",
                "Llenar el comedero con pasto y balanceado",
                "Renovar el agua del bebedero",
        };
        return new Racion(LocalDateTime.now(), pasos, PESO_DE_LA_RACION);
    }

    @Override
    public String especialidad() {
        return "ovejas";
    }
}

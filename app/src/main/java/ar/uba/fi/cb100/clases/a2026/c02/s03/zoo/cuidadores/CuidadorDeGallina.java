package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Gallina;

import java.time.LocalDateTime;

/**
 * cuidador especializado en gallinas, cuatro comidas chicas por día, y la
 * rutina se adapta a cada animal: recolecta según lo que esa gallina ponga
 */
public class CuidadorDeGallina extends Cuidador {

    private static final int COMIDAS_POR_DIA = 4;
    /** kilos que gana la gallina con una ración */
    private static final double PESO_DE_LA_RACION = 0.05;

    public CuidadorDeGallina(String nombre, int legajo) {
        super(nombre, legajo);
    }

    @Override
    public boolean puedeAtender(Animal animal) {
        return animal instanceof Gallina;
    }

    @Override
    protected int comidasPorDia() {
        return COMIDAS_POR_DIA;
    }

    @Override
    protected Racion prepararRacion(Animal animal) {
        Gallina gallina = (Gallina) animal;

        String[] pasos = {
                "Entrar al gallinero",
                "Esparcir el grano y el balanceado en el comedero",
                "Recolectar hasta " + gallina.huevosPorDia() + " huevos del nidal",
                "Renovar el agua del bebedero",
        };
        return new Racion(LocalDateTime.now(), pasos, PESO_DE_LA_RACION);
    }

    @Override
    public String especialidad() {
        return "gallinas";
    }
}
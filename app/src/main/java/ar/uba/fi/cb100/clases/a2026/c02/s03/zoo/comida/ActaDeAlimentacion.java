package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.AdministradorDeZoologico;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.Cuidador;

/**
 * El registro de un intento de alimentación: quién, a quién, qué pasó, y
 * cómo quedó la energía. Es lo que el {@link Cuidador} devuelve y lo que el
 * {@link AdministradorDeZoologico} junta en su informe.
 *
 * <p>{@code pasos} está vacío cuando no se alimentó (salteado o fuera de
 * especialidad), y en ese caso {@code energiaAntes == energiaDespues}.
 */
public record ActaDeAlimentacion(String cuidador,
                                 String animal,
                                 String especie,
                                 ResultadoDeAlimentacion resultado,
                                 String[] pasos,
                                 double pesoAntes,
                                 double pesoDespues) {

    public ActaDeAlimentacion {
        if (pasos == null) {
            pasos = new String[0];
        }
    }

    public boolean seAlimento() {
        return resultado == ResultadoDeAlimentacion.ALIMENTADO;
    }

    public double pesoGanado() {
        return pesoDespues - pesoAntes;
    }
}

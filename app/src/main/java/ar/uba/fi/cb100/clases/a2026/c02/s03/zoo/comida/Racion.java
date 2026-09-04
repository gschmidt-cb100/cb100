package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida;

import java.time.LocalDateTime;

/**
 * Una ración lista para servir: los pasos que siguió el cuidador para
 * prepararla y cuánta energía le aporta al animal.
 *
 * <p>Es un <b>valor</b> (dos raciones iguales son intercambiables), por eso es
 * un {@code record}. Y es <b>inmutable</b>: una vez preparada no se modifica.
 *
 * <p>Fijate que los pasos son <i>datos</i>, no {@code System.out.println}. El
 * cuidador describe lo que hizo y otro (el {@code main}, un informe, un test)
 * decide qué hacer con eso. Separar el cálculo de la presentación es lo que
 * permite testear la rutina sin mirar la consola.
 */
public record Racion(
        LocalDateTime fecha,
        String[] pasos,
        double pesoQueAporta)
{

    public Racion {
        if (pasos == null || pasos.length == 0) {
            throw new IllegalArgumentException("una ración tiene al menos un paso");
        }
        if (pesoQueAporta <= 0) {
            throw new IllegalArgumentException(
                    "una ración aporta energía positiva, y llegó " + pesoQueAporta);
        }
    }
}

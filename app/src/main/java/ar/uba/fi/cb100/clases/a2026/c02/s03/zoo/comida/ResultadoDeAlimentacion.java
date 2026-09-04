package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida;

/**
 * Qué pasó cuando un cuidador intentó alimentar a un animal.
 *
 * <p>Ninguno de los tres es un error: los tres son situaciones normales del
 * zoológico que hay que <b>registrar</b>, no <b>interrumpir</b>. Por eso es un
 * {@code enum} devuelto en el acta, y no una excepción.
 */
public enum ResultadoDeAlimentacion {

    /** Se sirvió la ración y se actualizó la energía del animal. */
    ALIMENTADO,

    /** Regla R5: el animal ya recibió todas las comidas del día. */
    SALTEADO_POR_FRECUENCIA,

    /** Regla R6: el cuidador no atiende a esta especie. */
    FUERA_DE_ESPECIALIDAD
}

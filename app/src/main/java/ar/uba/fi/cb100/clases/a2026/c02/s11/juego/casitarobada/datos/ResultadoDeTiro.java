package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

/**
 * Lo que pasó con un tiro.
 */
public enum ResultadoDeTiro {
    /** La carta coincidió con cartas de la mesa: se levantaron todas. */
    LEVANTO,
    /** La carta coincidió con la de arriba de otra casita: se la robó entera. */
    ROBO,
    /** No coincidió con nada: quedó en la mesa. */
    DEJO_EN_LA_MESA;
}

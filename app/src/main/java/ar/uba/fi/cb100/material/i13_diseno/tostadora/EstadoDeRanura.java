package ar.uba.fi.cb100.material.i13_diseno.tostadora;

/**
 * Estados de una {@link Ranura}. Diagrama de estados (Unidad 13):
 * <pre>
 *   (inicio) -> LIBRE --iniciarTostado()--> OCUPADA
 *               LIBRE <--finalizarTostado()-- OCUPADA   / panesTostados++
 * </pre>
 * Las dos transiciones que faltan (iniciar en OCUPADA, finalizar en LIBRE)
 * son {@link IllegalStateException}.
 */
public enum EstadoDeRanura {
    LIBRE,
    OCUPADA
}

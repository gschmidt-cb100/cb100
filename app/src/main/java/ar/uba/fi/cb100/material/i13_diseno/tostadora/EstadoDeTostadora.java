package ar.uba.fi.cb100.material.i13_diseno.tostadora;

/**
 * Estados de la {@link Tostadora}. Diagrama de estados (Unidad 13):
 * <pre>
 *   (inicio) -> APAGADA --encender()--> ENCENDIDA
 *               APAGADA <--apagar()---- ENCENDIDA   [sin ranuras ocupadas]
 * </pre>
 * Iniciar un tostado con la tostadora APAGADA es una transición que no
 * existe: {@link IllegalStateException}.
 */
public enum EstadoDeTostadora {
    APAGADA,
    ENCENDIDA
}

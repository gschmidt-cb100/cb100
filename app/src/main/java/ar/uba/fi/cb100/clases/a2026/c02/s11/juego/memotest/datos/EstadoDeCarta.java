package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos;

/**
 * Estados de una carta del memotest.
 *
 * <pre>
 *   BOCA_ABAJO ──darVuelta──► BOCA_ARRIBA ──marcarEncontrada──► ENCONTRADA
 *        ▲                         │
 *        └─────────tapar───────────┘
 * </pre>
 */
public enum EstadoDeCarta {
    BOCA_ABAJO,
    BOCA_ARRIBA,
    ENCONTRADA;
}

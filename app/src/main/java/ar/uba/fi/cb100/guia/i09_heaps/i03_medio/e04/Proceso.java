package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e04;

/**
 * Un proceso listo para ejecutar.
 *
 * @param pid       identificador del proceso
 * @param prioridad prioridad de planificación (menor número = más urgente,
 *                  como el "nice" de Unix)
 * @param llegada   instante en que el proceso quedó listo (para desempatar)
 */
public record Proceso(int pid, int prioridad, long llegada) {
}

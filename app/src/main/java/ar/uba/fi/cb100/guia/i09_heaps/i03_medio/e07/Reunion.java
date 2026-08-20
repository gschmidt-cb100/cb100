package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e07;

/**
 * Una reunión con horario de inicio y de fin.
 *
 * <p>El intervalo es semiabierto [inicio, fin): una reunión que termina
 * a las 10 no se solapa con otra que empieza a las 10.</p>
 *
 * @param inicio instante de comienzo
 * @param fin    instante de finalización (mayor que inicio)
 */
public record Reunion(int inicio, int fin) {
}

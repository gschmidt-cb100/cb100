package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e07;

/**
 * Una actividad con nombre y un intervalo de tiempo [inicio, fin).
 * Dos actividades son compatibles si sus intervalos no se solapan:
 * una puede empezar exactamente cuando la otra termina.
 *
 * @param nombre identificador legible de la actividad
 * @param inicio instante en que empieza
 * @param fin    instante en que termina (fin &gt; inicio)
 */
public record Actividad(String nombre, int inicio, int fin) {

    public Actividad {
        if (fin <= inicio) {
            throw new IllegalArgumentException(
                    "el fin (" + fin + ") debe ser mayor que el inicio (" + inicio + ")");
        }
    }
}

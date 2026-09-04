package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

/**
 * TDA <b>Felino</b>. Nivel intermedio de la jerarquía: sigue siendo
 * <b>abstracto</b> (no existe "un felino" genérico en una jaula), pero ya
 * puede resolver lo que es común a todos los felinos.
 *
 * <p>Esto es lo que aporta una clase intermedia: {@link #esCarnivoro()} se
 * escribe <b>una sola vez</b> acá y la heredan todos los felinos. Si mañana
 * agregamos {@code Tigre} o {@code Puma}, no hay que volver a escribirlo.
 *
 * <p>Y agrega estado propio, {@code nivelDeAgresividad}, que los herbívoros no
 * tienen. Una subclase puede sumar atributos, no sólo métodos.
 */
public abstract class Felino extends Animal {

    /** Un felino tranquilo se puede alimentar sin cerrar la jaula. */
    public static final int AGRESIVIDAD_MAXIMA_SIN_ENCIERRO = 3;

    private final int nivelDeAgresividad;

    protected Felino(String nombre,
                     double peso,
                     double pesoOptimo,
                     int nivelDeAgresividad) {
        super(nombre, peso, pesoOptimo);
        if (nivelDeAgresividad < 1 || nivelDeAgresividad > 10) {
            throw new IllegalArgumentException(
                    "la agresividad va de 1 a 10, y llegó " + nivelDeAgresividad);
        }
        this.nivelDeAgresividad = nivelDeAgresividad;
    }

    /**
     * Todos los felinos son carnívoros: se resuelve acá, una vez, para toda la
     * rama. {@code final} para que ninguna subclase lo contradiga.
     */
    @Override
    public final boolean esCarnivoro() {
        return true;
    }

    public int nivelDeAgresividad() {
        return nivelDeAgresividad;
    }

    /** Regla R11: por encima del umbral, hay que encerrarlo para alimentarlo. */
    public boolean necesitaEncierroParaAlimentar() {
        return nivelDeAgresividad > AGRESIVIDAD_MAXIMA_SIN_ENCIERRO;
    }
}

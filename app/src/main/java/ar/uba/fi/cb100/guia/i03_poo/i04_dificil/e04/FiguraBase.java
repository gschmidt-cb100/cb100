package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e04;

/**
 * Clase base abstracta para las figuras. Aporta el nombre, el orden natural
 * (por área) y una representación textual común. Las subclases concretas
 * sólo deben implementar {@link #area()} y {@link #perimetro()}.
 */
public abstract class FiguraBase implements Figura, Comparable<Figura> {

    private final String nombre;

    protected FiguraBase(String nombre) {
        this.nombre = nombre;
    }

    public String nombre() {
        return nombre;
    }

    /** Orden natural de las figuras: por área ascendente. */
    @Override
    public int compareTo(Figura otra) {
        return Double.compare(this.area(), otra.area());
    }

    @Override
    public String toString() {
        return String.format("%s(area=%.2f, perimetro=%.2f)", nombre, area(), perimetro());
    }
}

package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e04;

/**
 * Caja MUTABLE: se puede cambiar su valor en el lugar con set().
 * Todas las referencias que apunten a esta caja ven el cambio.
 */
public class CajaMutable {
    private int valor;

    public CajaMutable(int valor) {
        this.valor = valor;
    }

    public int get() {
        return valor;
    }

    public void set(int valor) {
        this.valor = valor;
    }
}

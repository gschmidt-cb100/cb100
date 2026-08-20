package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e04;

/**
 * Caja INMUTABLE: su valor es final. Para "cambiarlo" se crea una caja nueva
 * con con(...); la instancia original nunca se modifica.
 */
public class CajaInmutable {
    private final int valor;

    public CajaInmutable(int valor) {
        this.valor = valor;
    }

    public int get() {
        return valor;
    }

    /** Devuelve una NUEVA caja con el valor dado; la actual queda intacta. */
    public CajaInmutable con(int nuevoValor) {
        return new CajaInmutable(nuevoValor);
    }

    public static void main(String[] args) {
        // Mutable: set cambia la misma instancia.
        CajaMutable m = new CajaMutable(1);
        m.set(2);
        System.out.println("Mutable tras set: " + m.get());

        // Inmutable: con(...) crea otra instancia; la original no cambia.
        CajaInmutable original = new CajaInmutable(1);
        CajaInmutable nueva = original.con(2);
        System.out.println("Inmutable original: " + original.get());
        System.out.println("Inmutable nueva:    " + nueva.get());
    }
}

package ar.uba.fi.cb100.material.i03_poo;

/**
 * <b>Genéricos</b>: una {@code Caja<T>} guarda un valor de un tipo {@code T} que
 * se decide al usarla, con seguridad de tipos en tiempo de compilación.
 */
public class Caja<T> {

    private T contenido;

    public Caja(T contenido) {
        this.contenido = contenido;
    }

    public T get() {
        return contenido;
    }

    public void set(T nuevo) {
        this.contenido = nuevo;
    }

    /** Método genérico: devuelve el primer elemento de cualquier arreglo. */
    public static <E> E primero(E[] arreglo) {
        return arreglo[0];
    }

    /** Genérico acotado: el máximo de elementos que se pueden comparar. */
    public static <E extends Comparable<E>> E maximo(E[] arreglo) {
        E m = arreglo[0];
        for (E e : arreglo) {
            if (e.compareTo(m) > 0) {
                m = e;
            }
        }
        return m;
    }

    public static void main(String[] args) {
        Caja<String> caja = new Caja<>("hola");
        System.out.println(caja.get());

        Integer[] numeros = {3, 9, 1};
        System.out.println("primero: " + primero(numeros) + "  máximo: " + maximo(numeros));
    }
}

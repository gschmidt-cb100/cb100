package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e08;

/**
 * e08: Caja generica con get/set.
 * El tipo T se decide al usar la clase, permitiendo reutilizar
 * el mismo codigo con distintos tipos de dato de forma segura.
 */
public class Caja<T> {

    private T contenido;

    public Caja(T contenido) {
        this.contenido = contenido;
    }

    public T get() {
        return contenido;
    }

    public void set(T contenido) {
        this.contenido = contenido;
    }

    public static void main(String[] args) {
        Caja<String> texto = new Caja<>("hola");
        Caja<Integer> numero = new Caja<>(42);
        System.out.println(texto.get());
        System.out.println(numero.get());
    }
}

package ar.uba.fi.cb100.material.i03_poo;

/**
 * <b>Herencia</b> y <b>polimorfismo</b> con una clase abstracta.
 * {@code Animal} define el comportamiento común y deja {@code sonido()} abstracto;
 * cada subclase lo implementa a su manera.
 */
public class DemoHerencia {

    abstract static class Animal {
        private final String nombre;

        Animal(String nombre) { this.nombre = nombre; }

        abstract String sonido();                 // método abstracto

        String presentarse() {                     // método concreto reutilizable
            return nombre + " dice " + sonido();
        }
    }

    static class Perro extends Animal {
        Perro(String nombre) { super(nombre); }
        @Override String sonido() { return "Guau"; }
    }

    static class Gato extends Animal {
        Gato(String nombre) { super(nombre); }
        @Override String sonido() { return "Miau"; }
    }

    public static void main(String[] args) {
        // La referencia es Animal, pero cada objeto ejecuta SU propio sonido().
        Animal[] animales = {new Perro("Fido"), new Gato("Milo")};
        for (Animal a : animales) {
            System.out.println(a.presentarse());
        }
    }
}

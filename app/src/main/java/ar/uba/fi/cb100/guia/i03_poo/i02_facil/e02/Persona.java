package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e02;

/**
 * e02: Persona con toString legible.
 * toString permite obtener una representacion textual del objeto,
 * util para depurar e imprimir por consola.
 */
public class Persona {

    private final String nombre;
    private final int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    /** Representacion legible: "Persona{nombre=Ana, edad=30}". */
    @Override
    public String toString() {
        return "Persona{nombre=" + nombre + ", edad=" + edad + "}";
    }

    public static void main(String[] args) {
        Persona p = new Persona("Ana", 30);
        System.out.println(p);
    }
}

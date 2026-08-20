package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e04;

/**
 * e04: Persona que implementa Comparable por edad.
 * Comparable define un orden natural entre objetos, que luego
 * usan utilidades como Arrays.sort.
 */
public class Persona implements Comparable<Persona> {

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

    /**
     * Compara por edad: negativo si esta persona es mas joven,
     * positivo si es mayor, cero si tienen la misma edad.
     */
    @Override
    public int compareTo(Persona otra) {
        return Integer.compare(this.edad, otra.edad);
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + ")";
    }

    public static void main(String[] args) {
        Persona joven = new Persona("Ana", 20);
        Persona mayor = new Persona("Beto", 40);
        System.out.println("compareTo: " + joven.compareTo(mayor));
    }
}

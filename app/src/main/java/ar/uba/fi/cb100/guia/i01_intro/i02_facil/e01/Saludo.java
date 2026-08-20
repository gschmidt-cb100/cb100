package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e01;

/**
 * Ejercicio 1 (facil): Saludo personal.
 * Devuelve un saludo formado a partir del nombre y el legajo del alumno.
 */
public class Saludo {

    // Constructor privado: esta clase solo agrupa metodos utilitarios estaticos.
    private Saludo() {
    }

    /**
     * Arma un saludo personalizado.
     *
     * @param nombre nombre del alumno
     * @param legajo legajo del alumno
     * @return texto de saludo
     */
    public static String saludar(String nombre, String legajo) {
        return "Hola " + nombre + ", tu legajo es " + legajo + ". Bienvenido/a a CB100!";
    }

    public static void main(String[] args) {
        // Demostracion de uso desde consola.
        System.out.println(saludar("Ada", "12345"));
        System.out.println(saludar("Alan", "67890"));
    }
}

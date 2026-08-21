package ar.uba.fi.cb100.material.i01_intro;

/**
 * Primer programa de la materia: la estructura mínima de una aplicación Java.
 * La ejecución siempre arranca en el método {@code main}.
 */
public class HolaCB100 {

    public static void main(String[] args) {
        String materia = "CB100 — Algoritmos y Estructuras de Datos";
        System.out.println("¡Hola! Bienvenidas y bienvenidos a " + materia);

        String bienvenida = """
                En esta materia vamos a:
                  • programar con Tipos de Datos Abstractos (TDA) y POO
                  • usar herramientas profesionales: IntelliJ, Gradle y Git
                  • apoyarnos en IA como asistente, con criterio
                """;
        System.out.println(bienvenida);
    }
}

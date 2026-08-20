package ar.uba.fi.cb100.material.i01_intro;

/**
 * El tipo {@code String} en profundidad: métodos, comparación, inmutabilidad,
 * {@code StringBuilder} y formateo de texto.
 */
public class CadenasDeTexto {

    public static void main(String[] args) {
        String saludo = "Hola, CB100";

        // --- Métodos más usados ---
        System.out.println("largo: " + saludo.length());
        System.out.println("mayúsculas: " + saludo.toUpperCase());
        System.out.println("¿empieza con Hola?: " + saludo.startsWith("Hola"));
        System.out.println("posición de la coma: " + saludo.indexOf(','));
        System.out.println("subcadena: " + saludo.substring(6));      // "CB100"
        System.out.println("reemplazo: " + saludo.replace("Hola", "Chau"));

        // --- Comparación: SIEMPRE con equals, NUNCA con == ---
        String a = "java";
        System.out.println("equals: " + a.equals("java"));
        System.out.println("equalsIgnoreCase: " + a.equalsIgnoreCase("JAVA"));

        // --- Inmutabilidad: los métodos NO cambian el original ---
        String original = "abc";
        original.toUpperCase();          // el resultado se descarta
        System.out.println("sigue igual: " + original);   // abc

        // --- StringBuilder: construir texto de a poco, de forma eficiente ---
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i).append(' ');
        }
        System.out.println("armado: " + sb.toString().trim());

        // --- Formateo ---
        System.out.println(String.format("%-8s | %5.2f", "total", 3.14159));
        System.out.printf("%d por ciento%n", 80);
    }
}

package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e02;

/**
 * Ejercicio 02: Contar vocales.
 * Cuenta las vocales (mayúsculas y minúsculas) de una cadena.
 */
public class ContarVocales {

    /**
     * Cuenta cuántas vocales tiene la cadena, sin distinguir may/min.
     *
     * @param s cadena a analizar (si es nula devuelve 0)
     * @return cantidad de vocales
     */
    public static int contarVocales(String s) {
        if (s == null) {
            return 0;
        }
        int cuenta = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                cuenta++;
            }
        }
        return cuenta;
    }

    public static void main(String[] args) {
        System.out.println("Vocales de 'Murcielago': " + contarVocales("Murcielago"));
        System.out.println("Vocales de 'AEIOU': " + contarVocales("AEIOU"));
        System.out.println("Vocales de 'xyz': " + contarVocales("xyz"));
    }
}

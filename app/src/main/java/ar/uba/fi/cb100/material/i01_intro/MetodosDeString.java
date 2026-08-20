package ar.uba.fi.cb100.material.i01_intro;

import java.util.Arrays;

/**
 * Recorrido por los métodos más útiles de {@link String}.
 * Recordá: el String es inmutable, así que cada método devuelve uno nuevo.
 */
public class MetodosDeString {

    public static void main(String[] args) {
        String s = "  Hola, Mundo CB100  ";
        String t = s.strip();                 // quita espacios de los extremos

        System.out.println("length: " + t.length());
        System.out.println("isBlank de '   ': " + "   ".isBlank());
        System.out.println("charAt(0): " + t.charAt(0));
        System.out.println("indexOf('o'): " + t.indexOf('o'));
        System.out.println("lastIndexOf('o'): " + t.lastIndexOf('o'));
        System.out.println("substring(0,4): " + t.substring(0, 4));
        System.out.println("contains('Mundo'): " + t.contains("Mundo"));
        System.out.println("startsWith('Hola'): " + t.startsWith("Hola"));
        System.out.println("endsWith('100'): " + t.endsWith("100"));
        System.out.println("toUpperCase: " + t.toUpperCase());
        System.out.println("toLowerCase: " + t.toLowerCase());
        System.out.println("replace: " + t.replace("Mundo", "Java"));
        System.out.println("split: " + Arrays.toString(t.split(" ")));
        System.out.println("join: " + String.join("-", "a", "b", "c"));
        System.out.println("repeat: " + "ab".repeat(3));
        System.out.println("compareTo('abc','abd'): " + "abc".compareTo("abd"));
        System.out.println("equalsIgnoreCase: " + "Java".equalsIgnoreCase("JAVA"));
        System.out.println("toCharArray().length: " + t.toCharArray().length);
        System.out.println("matches(\\d+) de '12345': " + "12345".matches("\\d+"));
        System.out.println("valueOf(42): " + String.valueOf(42));
        System.out.println("format %05d: " + String.format("%05d", 42));
    }
}

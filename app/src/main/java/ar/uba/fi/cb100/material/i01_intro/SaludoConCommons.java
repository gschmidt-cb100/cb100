package ar.uba.fi.cb100.material.i01_intro;

import org.apache.commons.lang3.StringUtils;

/**
 * Ejemplo de uso de una LIBRERÍA EXTERNA: Apache Commons Lang.
 * <p>
 * Para que esta clase compile hay que declarar la dependencia en
 * {@code build.gradle}:
 * <pre>
 *   implementation 'org.apache.commons:commons-lang3:3.17.0'
 * </pre>
 * StringUtils agrega utilidades de texto que el String estándar no trae.
 */
public class SaludoConCommons {

    public static void main(String[] args) {
        String nombre = "   ada lovelace   ";

        System.out.println(StringUtils.capitalize(StringUtils.strip(nombre))); // "Ada lovelace"
        System.out.println(StringUtils.reverse("Java"));                       // "avaJ"
        System.out.println("¿'12345' es numérico?: " + StringUtils.isNumeric("12345"));
        System.out.println(StringUtils.abbreviate("Algoritmos y Estructuras", 15)); // "Algoritmos y..."
    }
}

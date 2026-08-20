package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e03;

import java.util.Map;
import java.util.StringJoiner;

/**
 * e03: traducir una frase palabra por palabra usando un diccionario.
 * El {@link Map} funciona literalmente como un diccionario: buscamos
 * cada palabra y, si no está, la dejamos como vino.
 */
public final class Traductor {

    private Traductor() {
    }

    /**
     * Traduce {@code frase} palabra por palabra según {@code dic}.
     * Las palabras que no figuran en el diccionario quedan igual.
     *
     * @param frase frase con palabras separadas por espacios
     * @param dic   diccionario palabra → traducción
     * @return frase traducida
     */
    public static String traducir(String frase, Map<String, String> dic) {
        if (frase.isEmpty()) {
            return "";
        }
        StringJoiner resultado = new StringJoiner(" ");
        for (String palabra : frase.split(" ")) {
            // getOrDefault: si la palabra no está, devolvemos la misma palabra.
            resultado.add(dic.getOrDefault(palabra, palabra));
        }
        return resultado.toString();
    }

    public static void main(String[] args) {
        Map<String, String> dic = Map.of(
                "hola", "hello",
                "mundo", "world",
                "chau", "bye");
        System.out.println(traducir("hola mundo cruel", dic));
    }
}

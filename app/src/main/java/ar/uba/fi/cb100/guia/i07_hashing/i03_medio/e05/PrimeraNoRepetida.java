package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e05;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * e05: encontrar el primer carácter que aparece exactamente una vez.
 * Dos pasadas con un {@link LinkedHashMap}:
 * <ol>
 *   <li>primera pasada: contamos apariciones de cada carácter;</li>
 *   <li>segunda pasada: recorremos el mapa, que conserva el orden de
 *       inserción, y devolvemos la primera clave con conteo 1.</li>
 * </ol>
 * Con un HashMap común no alcanzaría: perderíamos el orden y no sabríamos
 * cuál de los no repetidos apareció primero.
 */
public final class PrimeraNoRepetida {

    private PrimeraNoRepetida() {
    }

    /**
     * Devuelve el primer carácter de {@code s} que no se repite,
     * o {@code null} si todos se repiten (o el texto está vacío).
     *
     * @param s texto a analizar
     * @return primer carácter con una sola aparición, o null si no hay
     */
    public static Character primeraNoRepetida(String s) {
        // LinkedHashMap conserva el orden en que se insertan las claves.
        Map<Character, Integer> conteo = new LinkedHashMap<>();
        for (char c : s.toCharArray()) {
            conteo.merge(c, 1, Integer::sum);
        }
        for (Map.Entry<Character, Integer> entrada : conteo.entrySet()) {
            if (entrada.getValue() == 1) {
                return entrada.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("'banana' → " + primeraNoRepetida("banana"));
        System.out.println("'aabbcc' → " + primeraNoRepetida("aabbcc"));
    }
}

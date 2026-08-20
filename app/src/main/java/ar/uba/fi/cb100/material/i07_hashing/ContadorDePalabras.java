package ar.uba.fi.cb100.material.i07_hashing;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Ejemplo integrador de la Unidad 7</b>: contar la frecuencia de cada palabra
 * de un texto con un diccionario — el uso más clásico del hashing. Con
 * {@link HashMap}, cada palabra se cuenta en O(1) promedio: contar n palabras
 * cuesta O(n) total (con una lista sería O(n²)).
 */
public class ContadorDePalabras {

    /** Devuelve un mapa palabra → cantidad de apariciones (en minúsculas). */
    public static Map<String, Integer> contar(String texto) {
        Map<String, Integer> frecuencia = new HashMap<>();
        for (String palabra : texto.toLowerCase().split("[^\\p{L}]+")) {
            if (palabra.isEmpty()) continue;
            frecuencia.merge(palabra, 1, Integer::sum);   // crea con 1 o suma 1
        }
        return frecuencia;
    }

    /** La palabra más frecuente (cualquiera de ellas si hay empate). */
    public static String masFrecuente(Map<String, Integer> frecuencia) {
        String mejor = null;
        int max = 0;
        for (Map.Entry<String, Integer> e : frecuencia.entrySet()) {
            if (e.getValue() > max) { max = e.getValue(); mejor = e.getKey(); }
        }
        return mejor;
    }

    public static void main(String[] args) {
        String texto = "el que lee mucho y anda mucho, ve mucho y sabe mucho";
        Map<String, Integer> f = contar(texto);
        System.out.println(f.get("mucho"));      // 4
        System.out.println(f.get("y"));          // 2
        System.out.println(f.get("lee"));        // 1
        System.out.println(masFrecuente(f));     // mucho
        System.out.println(f.size() + " palabras distintas");
    }
}

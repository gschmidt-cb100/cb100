package ar.uba.fi.cb100.material.i12_profesional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * La tabla de costos del curso, MEDIDA: buscar 10.000 palabras en una
 * colección de 100.000 — con {@code List.contains} (O(n) por búsqueda, U5)
 * y con {@code HashSet.contains} (O(1) promedio, U7).
 * <p>
 * Elegir bien la estructura no es una elegancia académica: acá se ve que es
 * la diferencia entre segundos y milisegundos. Este experimento es el
 * resumen ejecutivo de todo el curso.
 */
public final class ComparaEstructuras {

    private ComparaEstructuras() {}

    public static void main(String[] args) {
        final int tamano = 100_000;
        final int busquedas = 10_000;

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            lista.add("clave-" + i);
        }
        Set<String> conjunto = new HashSet<>(lista);     // los mismos datos, otra estructura

        // buscamos claves que NO están (el peor caso de la lista: recorrerla entera)
        long inicioLista = System.nanoTime();
        int encontradosLista = 0;
        for (int i = 0; i < busquedas; i++) {
            if (lista.contains("fantasma-" + i)) {
                encontradosLista++;
            }
        }
        long milisLista = (System.nanoTime() - inicioLista) / 1_000_000;

        long inicioConjunto = System.nanoTime();
        int encontradosConjunto = 0;
        for (int i = 0; i < busquedas; i++) {
            if (conjunto.contains("fantasma-" + i)) {
                encontradosConjunto++;
            }
        }
        long milisConjunto = (System.nanoTime() - inicioConjunto) / 1_000_000;

        System.out.println("Buscar " + busquedas + " claves entre " + tamano + ":");
        System.out.println("  List.contains    -> " + milisLista + " ms   (O(n) por búsqueda)");
        System.out.println("  HashSet.contains -> " + milisConjunto + " ms   (O(1) promedio)");
        // Valores típicos: lista ~2000-4000 ms, conjunto ~1-15 ms: CIENTOS de veces más rápido.
        System.out.println(encontradosLista == 0 && encontradosConjunto == 0
                ? "  (ninguna estaba, como corresponde)" : "  ¡ojo, algo raro!");
    }
}

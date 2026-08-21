package ar.uba.fi.cb100.material.i08_arboles;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * <b>Ejemplo integrador de la Unidad 8</b>: un autocompletador de palabras.
 * La clave es una consulta por RANGO: todas las palabras que empiezan con un
 * prefijo forman un rango contiguo en el orden alfabético — exactamente lo que
 * un árbol ordenado ({@link TreeSet}) responde en O(log n), y un
 * {@code HashSet} no puede responder de ninguna forma razonable.
 */
public class Autocompletar {

    private final NavigableSet<String> palabras = new TreeSet<>();

    public void agregar(String palabra) {
        palabras.add(palabra.toLowerCase());
    }

    /** Las primeras {@code limite} palabras que empiezan con el prefijo. */
    public List<String> sugerir(String prefijo, int limite) {
        String desde = prefijo.toLowerCase();
        String hasta = desde + Character.MAX_VALUE;   // cota superior del rango
        List<String> sugerencias = new ArrayList<>();
        for (String p : palabras.subSet(desde, true, hasta, false)) {
            if (sugerencias.size() == limite) {
                break;
            }
            sugerencias.add(p);
        }
        return sugerencias;
    }

    public static void main(String[] args) {
        Autocompletar ac = new Autocompletar();
        for (String p : new String[]{"arbol", "arboleda", "arbusto", "arco",
                                     "grafo", "grado", "lista", "listado"}) {
            ac.agregar(p);
        }
        System.out.println(ac.sugerir("arb", 10));   // [arbol, arboleda, arbusto]
        System.out.println(ac.sugerir("gra", 10));   // [grado, grafo]
        System.out.println(ac.sugerir("lis", 1));    // [lista]
        System.out.println(ac.sugerir("zzz", 10));   // []
    }
}

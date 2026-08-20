package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e05;

import java.util.Arrays;

/**
 * Alcanzabilidad en un grafo dirigido representado por matriz de adyacencia.
 * Hacemos DFS iterativo usando un arreglo como pila y un arreglo de visitados.
 * No usamos colecciones del JDK: solo arreglos.
 */
public class Alcanzabilidad {

    /**
     * Devuelve un arreglo booleano donde la posicion i es true si el vertice i
     * es alcanzable desde raiz. ady[u][v] == true significa arista u -> v.
     */
    public static boolean[] alcanzables(boolean[][] ady, int raiz) {
        int n = ady.length;
        boolean[] visitados = new boolean[n];

        // Pila sobre arreglo. Cada vertice se apila a lo sumo una vez
        // (lo marcamos al apilarlo), asi que con capacidad n alcanza.
        int[] pila = new int[n];
        int tope = 0;

        pila[tope] = raiz;
        tope++;
        visitados[raiz] = true;

        while (tope > 0) {
            tope--;
            int actual = pila[tope];
            for (int v = 0; v < n; v++) {
                if (ady[actual][v] && !visitados[v]) {
                    visitados[v] = true;
                    pila[tope] = v;
                    tope++;
                }
            }
        }
        return visitados;
    }

    /** Cuenta cuantos vertices quedaron sin marcar (no alcanzables). */
    public static int sinMarcar(boolean[] visitados) {
        int cuenta = 0;
        for (boolean b : visitados) {
            if (!b) {
                cuenta++;
            }
        }
        return cuenta;
    }

    public static void main(String[] args) {
        // 0 -> 1 -> 2 ; 3 aislado
        boolean[][] ady = {
                {false, true,  false, false},
                {false, false, true,  false},
                {false, false, false, false},
                {false, false, false, false},
        };
        boolean[] visitados = alcanzables(ady, 0);
        System.out.println("Alcanzables desde 0: " + Arrays.toString(visitados));
        System.out.println("Sin marcar: " + sinMarcar(visitados));
    }
}

package ar.uba.fi.cb100.material.i10_tecnicas;

import java.util.Arrays;

/**
 * <b>Backtracking</b>: el problema de las N reinas. Ubicar N reinas en un
 * tablero de N×N sin que se ataquen (misma fila, columna o diagonal).
 * <p>
 * La estrategia: colocar una reina por FILA, probando columna por columna.
 * Si una columna es válida, se AVANZA a la fila siguiente; si ninguna sirve,
 * se VUELVE ATRÁS (backtrack) y se mueve la reina de la fila anterior.
 * El arreglo {@code columnaDe[f]} guarda la columna de la reina de la fila f.
 */
public class NReinas {

    private final int n;
    private final int[] columnaDe;      // columnaDe[fila] = columna elegida
    private int soluciones;
    private int[] primeraSolucion;

    public NReinas(int n) {
        this.n = n;
        this.columnaDe = new int[n];
    }

    /** Cuenta todas las soluciones (y recuerda la primera). */
    public int contarSoluciones() {
        soluciones = 0;
        primeraSolucion = null;
        ubicar(0);
        return soluciones;
    }

    /** El esquema clásico de backtracking: probar, avanzar, deshacer. */
    private void ubicar(int fila) {
        if (fila == n) {                          // todas ubicadas: ¡solución!
            soluciones++;
            if (primeraSolucion == null) {
                primeraSolucion = Arrays.copyOf(columnaDe, n);
            }
            return;
        }
        for (int columna = 0; columna < n; columna++) {   // candidatos de esta fila
            if (esValida(fila, columna)) {
                columnaDe[fila] = columna;        // 1) ELEGIR
                ubicar(fila + 1);                 // 2) AVANZAR
                // 3) DESHACER: no hace falta borrar nada, la próxima vuelta pisa
            }
            // columna inválida: PODA — ni se intenta ese subárbol entero
        }
    }

    /** ¿La reina de (fila, columna) convive con las ya ubicadas? */
    private boolean esValida(int fila, int columna) {
        for (int f = 0; f < fila; f++) {
            int c = columnaDe[f];
            if (c == columna) return false;                      // misma columna
            if (Math.abs(c - columna) == fila - f) return false; // misma diagonal
        }
        return true;                              // (misma fila es imposible: una por fila)
    }

    public int[] primeraSolucion() { return primeraSolucion; }

    public static void main(String[] args) {
        NReinas cuatro = new NReinas(4);
        System.out.println(cuatro.contarSoluciones());              // 2
        System.out.println(Arrays.toString(cuatro.primeraSolucion())); // [1, 3, 0, 2]

        NReinas ocho = new NReinas(8);
        System.out.println(ocho.contarSoluciones());                // 92 (el clásico)
    }
}

package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e07;

import java.util.ArrayList;
import java.util.List;

/**
 * e07: Torres de Hanoi, un clasico ejemplo de recursividad.
 *
 * <p>Para mover {@code n} discos desde la varilla origen a la destino usando una
 * varilla auxiliar:</p>
 * <ol>
 *   <li>Mover los {@code n-1} discos de arriba de origen a auxiliar (subproblema).</li>
 *   <li>Mover el disco {@code n} (el mas grande) de origen a destino.</li>
 *   <li>Mover los {@code n-1} discos de auxiliar a destino (subproblema).</li>
 * </ol>
 *
 * <p>La cantidad de movimientos es exactamente {@code 2^n - 1}.</p>
 */
public final class TorresHanoi {

    private TorresHanoi() {
    }

    /**
     * Devuelve la lista ordenada de movimientos para resolver el juego con
     * {@code n} discos, moviendolos de la varilla A a la C.
     *
     * @param n cantidad de discos (no negativa)
     * @return lista de movimientos, cada uno como "mover disco X de A a C"
     * @throws IllegalArgumentException si {@code n} es negativo
     */
    public static List<String> resolver(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        List<String> movimientos = new ArrayList<>();
        resolver(n, 'A', 'C', 'B', movimientos);
        return movimientos;
    }

    /**
     * Auxiliar recursivo que acumula los movimientos.
     *
     * @param n           discos a mover
     * @param origen      varilla de la que salen
     * @param destino     varilla a la que van
     * @param auxiliar    varilla intermedia
     * @param movimientos acumulador de resultados
     */
    private static void resolver(int n, char origen, char destino, char auxiliar,
                                 List<String> movimientos) {
        if (n == 0) {
            return; // caso base: no hay discos que mover
        }
        resolver(n - 1, origen, auxiliar, destino, movimientos);
        movimientos.add("mover disco " + n + " de " + origen + " a " + destino);
        resolver(n - 1, auxiliar, destino, origen, movimientos);
    }

    public static void main(String[] args) {
        int n = 3;
        List<String> pasos = resolver(n);
        System.out.println("Solucion para " + n + " discos (" + pasos.size() + " movimientos):");
        for (String paso : pasos) {
            System.out.println("  " + paso);
        }
    }
}

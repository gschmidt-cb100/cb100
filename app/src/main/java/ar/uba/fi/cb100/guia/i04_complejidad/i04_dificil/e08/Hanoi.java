package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e08;

/**
 * Torres de Hanoi: cantidad mínima de movimientos para mover n discos.
 *
 * Recurrencia: T(n) = 2*T(n-1) + 1, con T(0) = 0.
 * Para mover n discos: se mueven n-1 al poste auxiliar (T(n-1)), se mueve el
 * disco grande (1 movimiento) y se mueven los n-1 sobre él (T(n-1)).
 * Solución cerrada: T(n) = 2^n - 1.
 *
 * Complejidad temporal (versión recursiva que lista movimientos): O(2^n),
 * porque hay 2^n - 1 movimientos. La versión que solo cuenta se implementa en
 * O(1) usando la fórmula cerrada.
 */
public final class Hanoi {

    private Hanoi() {
    }

    /**
     * Cantidad mínima de movimientos = 2^n - 1. Implementación O(1) con la
     * fórmula cerrada (equivalente a la recurrencia T(n)=2T(n-1)+1).
     * Se limita n para no desbordar long (2^62 - 1 entra en long).
     */
    public static long movimientos(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo");
        }
        if (n > 62) {
            throw new IllegalArgumentException("n demasiado grande para long: " + n);
        }
        return (1L << n) - 1L; // 2^n - 1
    }

    /**
     * Versión recursiva que sigue literalmente la recurrencia T(n)=2T(n-1)+1.
     * Útil para mostrar el árbol de recursión en clase. O(2^n).
     */
    public static long movimientosRecursivo(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo");
        }
        if (n == 0) {
            return 0;
        }
        return 2 * movimientosRecursivo(n - 1) + 1;
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 5; n++) {
            System.out.println("n=" + n + " -> " + movimientos(n)
                    + " (recursivo: " + movimientosRecursivo(n) + ")");
        }
    }
}

package ar.uba.fi.cb100.material.i06_estrategias;

/**
 * Las <b>Torres de Hanói</b>: el ejemplo clásico de recursión. Para mover {@code n}
 * discos de una torre a otra: mové {@code n−1} a la auxiliar, mové el disco grande
 * al destino, y mové los {@code n−1} de la auxiliar al destino.
 * <p>
 * Recurrencia: {@code T(n) = 2·T(n−1) + 1}, que da {@code 2ⁿ − 1} movimientos
 * (Unidad 4, forma por sustracción con a>1 → exponencial).
 */
public class TorresHanoi {

    /** Cantidad de movimientos para n discos: 2ⁿ − 1. */
    public static long movimientos(int n) {
        if (n == 0) {
            return 0;   // caso base
        }
        return 2 * movimientos(n - 1) + 1;          // recurrencia
    }

    /** Imprime la secuencia de movimientos para resolver n discos. */
    public static void resolver(int n, char origen, char destino, char auxiliar) {
        if (n == 0) {
            return;   // caso base
        }
        resolver(n - 1, origen, auxiliar, destino);            // 1) n−1 al auxiliar
        System.out.println("mover disco " + n + " de " + origen + " a " + destino);
        resolver(n - 1, auxiliar, destino, origen);            // 3) n−1 al destino
    }

    public static void main(String[] args) {
        System.out.println("movimientos(3) = " + movimientos(3));   // 7
        resolver(3, 'A', 'C', 'B');
    }
}

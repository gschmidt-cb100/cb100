package ar.uba.fi.cb100.material.i04_complejidad;

/**
 * Complejidad <b>O(log n)</b>: en cada paso descarta la mitad de los elementos.
 * <p>
 * PRECONDICIÓN: {@code a} está ordenado de menor a mayor (no se verifica: hacerlo
 * costaría O(n) y anularía la ventaja de la búsqueda binaria).
 */
public class BusquedaBinaria {

    public static int buscar(int[] a, int x) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {                 // el rango se divide por 2 cada vuelta -> O(log n)
            int mid = (lo + hi) >>> 1;
            if (a[mid] == x) return mid;
            if (a[mid] < x) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;                         // no está
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7, 9, 11};
        System.out.println("índice de 7: " + buscar(a, 7));   // 3
        System.out.println("índice de 8: " + buscar(a, 8));   // -1
    }
}

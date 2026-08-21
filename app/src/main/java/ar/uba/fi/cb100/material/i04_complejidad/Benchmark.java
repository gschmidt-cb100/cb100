package ar.uba.fi.cb100.material.i04_complejidad;

/**
 * Medición <b>empírica</b> del tiempo (con {@code System.nanoTime}), para
 * contrastarla con la predicción teórica. Como {@link SumaArreglo#sumar} es O(n),
 * al multiplicar n por 10 el tiempo debería multiplicarse por ~10.
 * <p>
 * Cuidado: la medición real está afectada por el compilador JIT, el sistema
 * operativo y la máquina; por eso la teoría es más confiable para comparar.
 */
public class Benchmark {

    public static void main(String[] args) {
        for (int n : new int[]{1_000_000, 10_000_000, 100_000_000}) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }

            long inicio = System.nanoTime();
            long suma = SumaArreglo.sumar(a);
            long fin = System.nanoTime();

            System.out.printf("n=%,d   tiempo=%.1f ms   (suma=%d)%n",
                    n, (fin - inicio) / 1e6, suma);
        }
    }
}

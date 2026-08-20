package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e08;

import java.util.Arrays;

/**
 * TECNICA: GREEDY con demostracion de correccion (y una PD de referencia).
 *
 * Problema (jump game): alcance[i] es el salto maximo desde la posicion i.
 * Partiendo de la posicion 0, calcular la MINIMA cantidad de saltos para
 * llegar a la ultima posicion (o -1 si es inalcanzable).
 *
 * GREEDY O(n) por "ventanas": el salto numero k puede aterrizar en un
 * rango contiguo de posiciones (la ventana k). La ventana 0 es {0}; la
 * ventana k+1 llega hasta el maximo de i + alcance[i] sobre todas las
 * posiciones i de las ventanas 0..k. Recorremos el arreglo una sola vez
 * manteniendo el fin de la ventana actual (finVentana) y el mejor borde
 * alcanzable (maxAlcance); al pisar finVentana "cerramos" la ventana:
 * un salto mas y la nueva ventana termina en maxAlcance.
 *
 * DEMOSTRACION de correccion (induccion sobre k): sea V(k) la posicion
 * mas lejana alcanzable con a lo sumo k saltos. Afirmamos que finVentana
 * tras k cierres es exactamente V(k).
 *  - Base: V(0) = 0 = finVentana inicial.
 *  - Paso: toda posicion alcanzable con k+1 saltos se alcanza saltando
 *    desde alguna posicion i alcanzable con <= k saltos, o sea i <= V(k);
 *    lo mas lejos que se llega asi es max_{i <= V(k)} (i + alcance[i]),
 *    que es justo el maxAlcance acumulado al momento de cerrar la
 *    ventana k. Luego el nuevo finVentana es V(k+1).
 * Como V(k) es creciente en k, el primer k con V(k) >= n-1 es el minimo
 * de saltos, y es exactamente lo que devuelve el algoritmo. Si al cerrar
 * una ventana maxAlcance no avanza (quedamos atascados), no hay camino.
 *
 * La version PD O(n^2) (dp[i] = minimo de saltos hasta i, mirando todos
 * los j anteriores que llegan a i) queda como referencia para contrastar
 * resultados y costos.
 */
public class SaltoMinimo {

    /** Greedy por ventanas, O(n) en tiempo y O(1) en espacio. Devuelve -1 si no se llega. */
    public int saltosMinimos(int[] alcance) {
        validar(alcance);
        int n = alcance.length;
        if (n == 1) {
            return 0; // Ya estamos parados en la ultima posicion.
        }
        int saltos = 0;
        int finVentana = 0;  // Ultima posicion alcanzable con "saltos" saltos.
        int maxAlcance = 0;  // Ultima posicion alcanzable con "saltos + 1" saltos.
        for (int i = 0; i < n - 1; i++) {
            maxAlcance = Math.max(maxAlcance, i + alcance[i]);
            if (i == finVentana) {
                if (maxAlcance <= i) {
                    return -1; // La ventana no avanza: posicion inalcanzable.
                }
                saltos++;
                finVentana = maxAlcance;
                if (finVentana >= n - 1) {
                    break; // Ya cubrimos la ultima posicion.
                }
            }
        }
        return saltos;
    }

    /** PD de referencia, O(n^2): dp[i] = minimo de saltos para llegar a i. */
    public int saltosMinimosDp(int[] alcance) {
        validar(alcance);
        int n = alcance.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int destino = 1; destino < n; destino++) {
            for (int origen = 0; origen < destino; origen++) {
                boolean origenAlcanzable = dp[origen] != Integer.MAX_VALUE;
                if (origenAlcanzable && origen + alcance[origen] >= destino) {
                    dp[destino] = Math.min(dp[destino], dp[origen] + 1);
                }
            }
        }
        return dp[n - 1] == Integer.MAX_VALUE ? -1 : dp[n - 1];
    }

    private void validar(int[] alcance) {
        if (alcance == null || alcance.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser vacio");
        }
        for (int valor : alcance) {
            if (valor < 0) {
                throw new IllegalArgumentException("Los alcances deben ser >= 0, vino " + valor);
            }
        }
    }

    /** Demostracion: greedy y PD coinciden. */
    public static void main(String[] args) {
        SaltoMinimo saltarina = new SaltoMinimo();
        int[][] casos = {{2, 3, 1, 1, 4}, {1, 1, 1, 1}, {3, 0, 0, 0}, {1, 0, 4}};
        for (int[] caso : casos) {
            System.out.println(Arrays.toString(caso)
                    + " -> greedy = " + saltarina.saltosMinimos(caso)
                    + ", dp = " + saltarina.saltosMinimosDp(caso));
        }
    }
}

package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TECNICA: PROGRAMACION DINAMICA (subset-sum maximizado) + RECONSTRUCCION.
 *
 * Problema: armar un lado de casete / playlist de duracion maxima que no
 * supere la capacidad, eligiendo canciones (cada una a lo sumo una vez).
 * Es la version "maximizar la suma sin pasarse" del subset-sum, o una
 * mochila 0/1 donde el valor de cada cancion es su propia duracion.
 *
 * Por que PD y no greedy: el greedy "agarrar la mas larga que entre"
 * falla (con [3,5,7,8] y capacidad 14 agarra 8, despues 5 y llega a 13
 * de casualidad, pero con [4,6,7] y capacidad 10 agarraria 7 y llegaria
 * a 7, cuando 4+6 = 10 es exacto). Hay que explorar combinaciones, y la
 * PD lo hace sin repetir subproblemas.
 *
 * Tabla: alcanzable[i][c] = true si con las primeras i canciones se
 * puede armar EXACTAMENTE la duracion c. Recurrencia:
 *   alcanzable[i][c] = alcanzable[i-1][c]                     (no la uso)
 *                   || alcanzable[i-1][c - duraciones[i-1]]   (la uso)
 * La respuesta es el mayor c <= capacidad con alcanzable[n][c].
 *
 * Reconstruccion: desde (n, mejorC) caminamos hacia atras. Si
 * alcanzable[i-1][c] es true, la cancion i-1 no hizo falta (preferimos
 * no usarla); si no, la unica explicacion es que se uso: la agregamos
 * y descontamos su duracion. Eso da una eleccion concreta y valida.
 *
 * Complejidad: O(n * capacidad) en tiempo y espacio (la tabla completa
 * se conserva justamente para poder reconstruir).
 */
public class EmpaquetadoDeCanciones {

    /** Mayor duracion total <= capacidad alcanzable con un subconjunto de canciones. */
    public int duracionMaxima(int[] duraciones, int capacidad) {
        boolean[][] alcanzable = tabla(duraciones, capacidad);
        return mejorSuma(alcanzable, duraciones.length, capacidad);
    }

    /**
     * Indices (en orden creciente) de un subconjunto de canciones cuya
     * duracion total es exactamente {@link #duracionMaxima}.
     */
    public List<Integer> cancionesElegidas(int[] duraciones, int capacidad) {
        boolean[][] alcanzable = tabla(duraciones, capacidad);
        int c = mejorSuma(alcanzable, duraciones.length, capacidad);
        List<Integer> elegidas = new ArrayList<>();
        for (int i = duraciones.length; i >= 1; i--) {
            if (alcanzable[i - 1][c]) {
                continue; // La suma c ya era alcanzable sin la cancion i-1.
            }
            elegidas.add(i - 1); // Sin la cancion i-1 no se llega a c: se uso seguro.
            c -= duraciones[i - 1];
        }
        Collections.reverse(elegidas);
        return elegidas;
    }

    private boolean[][] tabla(int[] duraciones, int capacidad) {
        if (duraciones == null || capacidad < 0) {
            throw new IllegalArgumentException("Duraciones no null y capacidad >= 0");
        }
        for (int duracion : duraciones) {
            if (duracion <= 0) {
                throw new IllegalArgumentException("Las duraciones deben ser > 0, vino " + duracion);
            }
        }
        int n = duraciones.length;
        boolean[][] alcanzable = new boolean[n + 1][capacidad + 1];
        alcanzable[0][0] = true; // Sin canciones solo se arma la duracion 0.
        for (int i = 1; i <= n; i++) {
            int duracion = duraciones[i - 1];
            for (int c = 0; c <= capacidad; c++) {
                alcanzable[i][c] = alcanzable[i - 1][c]
                        || (c >= duracion && alcanzable[i - 1][c - duracion]);
            }
        }
        return alcanzable;
    }

    private int mejorSuma(boolean[][] alcanzable, int n, int capacidad) {
        for (int c = capacidad; c >= 0; c--) {
            if (alcanzable[n][c]) {
                return c;
            }
        }
        return 0; // Inalcanzable en teoria: c = 0 siempre es true.
    }

    /** Demostracion: [3, 5, 7, 8] con capacidad 14 llena 13 minutos con {5, 8}. */
    public static void main(String[] args) {
        EmpaquetadoDeCanciones empaquetadora = new EmpaquetadoDeCanciones();
        int[] duraciones = {3, 5, 7, 8};
        int capacidad = 14;
        System.out.println("Duraciones: " + Arrays.toString(duraciones) + ", capacidad: " + capacidad);
        System.out.println("Duracion maxima: " + empaquetadora.duracionMaxima(duraciones, capacidad));
        System.out.println("Indices elegidos: " + empaquetadora.cancionesElegidas(duraciones, capacidad));
    }
}

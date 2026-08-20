package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e02;

import java.util.List;
import java.util.stream.Stream;

/**
 * TECNICA: STREAM INFINITO CON {@code Stream.iterate} + EVALUACION PEREZOSA.
 *
 * {@code Stream.iterate(semilla, f)} describe la sucesion infinita
 * semilla, f(semilla), f(f(semilla)), ... pero NO la calcula: un stream es
 * una receta, no una lista (U12). Recien cuando una operacion terminal pide
 * elementos, la fuente produce uno por vez, y {@code limit(n)} corta el
 * pedido en n. Por eso una fuente INFINITA no cuelga el programa: la
 * pereza garantiza que solo se computan los n primeros que alguien consume.
 *
 * El truco del estado: Fibonacci necesita DOS valores anteriores, y las
 * lambdas no pueden mutar variables locales. Solucion: la semilla es el par
 * {@code long[]{F(i), F(i+1)}} y cada paso avanza al par siguiente
 * {@code {F(i+1), F(i)+F(i+1)}}; el {@code map} final se queda con la
 * primera componente.
 */
public final class GeneradorDeFibonacci {

    /** Los primeros n numeros de Fibonacci: F(0)=0, F(1)=1, ... */
    public static List<Long> primeros(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        return Stream.iterate(new long[]{0, 1}, par -> new long[]{par[1], par[0] + par[1]})
                .map(par -> par[0])
                .limit(n)
                .toList();
    }

    /** F(n) puntual, reutilizando la misma fuente perezosa. */
    public static long fibonacci(int n) {
        return Stream.iterate(new long[]{0, 1}, par -> new long[]{par[1], par[0] + par[1]})
                .skip(n)
                .map(par -> par[0])
                .findFirst()
                .orElseThrow();
    }

    private GeneradorDeFibonacci() {
    }

    public static void main(String[] args) {
        System.out.println("Primeros 10: " + primeros(10));
        // [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]
        System.out.println("F(90) = " + fibonacci(90));
        // 2880067194370816120 (justo antes del limite de long: F(93) desborda)
    }
}

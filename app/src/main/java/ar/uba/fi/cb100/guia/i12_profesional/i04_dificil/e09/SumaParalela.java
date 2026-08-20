package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e09;

import java.util.stream.LongStream;

/**
 * TECNICA: STREAMS PARALELOS — CUANDO SI Y CUANDO NO.
 *
 * Cambiar {@code .sum()} por {@code .parallel().sum()} reparte el rango
 * entre los nucleos y reduce los parciales. Que el resultado sea IDENTICO
 * al secuencial no es suerte: la suma es asociativa y cada elemento se
 * procesa una sola vez, asi que el orden de combinacion no importa.
 *
 * Cuando conviene paralelizar (las tres a la vez):
 * <ul>
 *   <li>MUCHO dato: repartir trabajo entre hilos tiene un costo fijo; con
 *       pocos elementos el reparto sale mas caro que la cuenta;</li>
 *   <li>operacion PURA: cada paso depende solo de su entrada, sin efectos
 *       visibles afuera;</li>
 *   <li>SIN estado compartido: ningun hilo escribe algo que otro lea.</li>
 * </ul>
 *
 * El peligro clasico: una lambda "con estado" que muta una variable o
 * coleccion externa. En secuencial parece andar; en paralelo es la
 * condicion de carrera del e06 y da resultados distintos en cada corrida.
 * Regla practica: si la lambda toca algo declarado afuera del stream, no
 * la paralelices.
 *
 * Verificacion independiente: Gauss. 1+2+...+n = n*(n+1)/2, la formula
 * O(1) de la U4 que delata a cualquier suma mal hecha.
 */
public final class SumaParalela {

    /** Suma 1..n con un pipeline secuencial. */
    public static long sumaSecuencial(long n) {
        return LongStream.rangeClosed(1, n).sum();
    }

    /** Suma 1..n repartida entre los nucleos: mismo resultado, garantizado. */
    public static long sumaParalela(long n) {
        return LongStream.rangeClosed(1, n).parallel().sum();
    }

    /** La formula de Gauss: la verdad en O(1), sin recorrer nada. */
    public static long formulaDeGauss(long n) {
        return n * (n + 1) / 2;
    }

    private SumaParalela() {
    }

    public static void main(String[] args) {
        long n = 50_000_000;
        long secuencial = sumaSecuencial(n);
        long paralela = sumaParalela(n);
        long gauss = formulaDeGauss(n);
        System.out.println("Secuencial: " + secuencial);
        System.out.println("Paralela:   " + paralela);
        System.out.println("Gauss:      " + gauss);
        System.out.println("Coinciden:  " + (secuencial == paralela && paralela == gauss));
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e06;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TECNICA: POR QUE {@code HashMap} NO SIRVE BAJO CONCURRENCIA.
 *
 * El {@code HashMap} de la U7 promete O(1) esperado... para UN hilo. Su
 * contrato dice explicitamente que no esta sincronizado: un
 * {@code merge(clave, 1, suma)} es leer-calcular-escribir en tres pasos, y
 * si dos hilos leen el mismo valor "al mismo tiempo", los dos escriben
 * viejo+1 y UNA actualizacion se pierde (condicion de carrera). Peor aun:
 * durante un rehash concurrente la estructura interna puede quedar
 * corrupta. Nada de esto es un bug de Java: es usar la estructura fuera de
 * su contrato.
 *
 * {@code ConcurrentHashMap} si esta diseniado para esto: su {@code merge}
 * es ATOMICO (los tres pasos ocurren como uno solo, sin que otro hilo se
 * meta en el medio), asi que ninguna suma se pierde, y sin frenar todo el
 * mapa con un unico candado.
 *
 * Ojo con el test: la perdida del HashMap es PROBABLE pero no garantizada
 * (depende del scheduler), asi que seria un test flaky — solo se muestra
 * en el main. La version segura si es determinista: el total exacto,
 * siempre.
 */
public final class ContadorConcurrente {

    private static final String[] CLAVES = {"rojo", "verde", "azul"};

    /**
     * Varios hilos incrementando un {@code HashMap} compartido: suele
     * PERDER actualizaciones (y hasta podria corromper el mapa).
     */
    public static Map<String, Integer> mapaInseguro(int hilos, int incrementosPorHilo)
            throws InterruptedException {
        Map<String, Integer> contadores = new HashMap<>();
        ejecutar(hilos, incrementosPorHilo, contadores);
        return contadores;
    }

    /**
     * Lo mismo con {@code ConcurrentHashMap}: el merge atomico garantiza
     * que cada incremento cuenta. Total = hilos * incrementosPorHilo.
     */
    public static Map<String, Integer> mapaSeguro(int hilos, int incrementosPorHilo)
            throws InterruptedException {
        Map<String, Integer> contadores = new ConcurrentHashMap<>();
        ejecutar(hilos, incrementosPorHilo, contadores);
        return contadores;
    }

    /** Lanza los hilos, los espera con join y deja el mapa como quedo. */
    private static void ejecutar(int hilos, int incrementosPorHilo,
                                 Map<String, Integer> contadores) throws InterruptedException {
        Thread[] trabajadores = new Thread[hilos];
        for (int i = 0; i < hilos; i++) {
            trabajadores[i] = new Thread(() -> {
                for (int j = 0; j < incrementosPorHilo; j++) {
                    contadores.merge(CLAVES[j % CLAVES.length], 1, Integer::sum);
                }
            });
            trabajadores[i].start();
        }
        for (Thread trabajador : trabajadores) {
            trabajador.join();                     // esperar a todos antes de mirar el mapa
        }
    }

    /** Suma de todos los contadores (el total que deberia dar hilos*incrementos). */
    public static long total(Map<String, Integer> contadores) {
        return contadores.values().stream().mapToLong(Integer::longValue).sum();
    }

    private ContadorConcurrente() {
    }

    public static void main(String[] args) throws InterruptedException {
        int hilos = 8;
        int incrementos = 100_000;
        long esperado = (long) hilos * incrementos;

        long inseguro = total(mapaInseguro(hilos, incrementos));
        long seguro = total(mapaSeguro(hilos, incrementos));

        System.out.println("Esperado:            " + esperado);
        System.out.println("HashMap:             " + inseguro
                + (inseguro == esperado ? "  (hoy zafo... proba de nuevo)"
                                        : "  <- perdio " + (esperado - inseguro)));
        System.out.println("ConcurrentHashMap:   " + seguro + "  (exacto, siempre)");
    }
}

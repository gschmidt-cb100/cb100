package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Solo se testea la version SEGURA: es determinista (con join, el total es
 * exacto siempre). La perdida del HashMap depende del scheduler y por eso
 * no se asserta — verla es tarea del main.
 */
public class ContadorConcurrenteTest {

    @Test
    @DisplayName("ConcurrentHashMap nunca pierde: total exacto con 8 hilos")
    public void laVersionSeguraNoPierde() throws InterruptedException {
        int hilos = 8;
        int incrementos = 50_000;
        Map<String, Integer> contadores = ContadorConcurrente.mapaSeguro(hilos, incrementos);
        assertEquals((long) hilos * incrementos, ContadorConcurrente.total(contadores));
    }

    @Test
    @DisplayName("la version segura repite el total exacto en varias corridas")
    public void laVersionSeguraEsRepetible() throws InterruptedException {
        for (int corrida = 0; corrida < 3; corrida++) {
            Map<String, Integer> contadores = ContadorConcurrente.mapaSeguro(4, 25_000);
            assertEquals(100_000L, ContadorConcurrente.total(contadores),
                    "corrida " + corrida);
        }
    }

    @Test
    @DisplayName("con un solo hilo hasta el HashMap es exacto (sin carrera no hay perdida)")
    public void conUnSoloHiloNoHayCarrera() throws InterruptedException {
        Map<String, Integer> contadores = ContadorConcurrente.mapaInseguro(1, 30_000);
        assertEquals(30_000L, ContadorConcurrente.total(contadores));
    }
}

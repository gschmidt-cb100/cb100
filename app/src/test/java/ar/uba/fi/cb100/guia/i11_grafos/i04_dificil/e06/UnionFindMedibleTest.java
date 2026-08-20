package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UnionFindMedibleTest {

    @Test
    @DisplayName("Ambas versiones responden lo mismo sobre conectividad")
    void mismasRespuestas() {
        UnionFindMedible con = new UnionFindMedible(10, true);
        UnionFindMedible sin = new UnionFindMedible(10, false);
        int[][] uniones = {{0, 1}, {1, 2}, {5, 6}, {6, 7}, {2, 5}};
        for (int[] par : uniones) {
            assertEquals(sin.unir(par[0], par[1]), con.unir(par[0], par[1]));
        }
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                assertEquals(sin.estanConectados(a, b), con.estanConectados(a, b),
                        "difieren en (" + a + ", " + b + ")");
            }
        }
        assertEquals(sin.cantidadDeGrupos(), con.cantidadDeGrupos());
    }

    @Test
    @DisplayName("Unir dos del mismo grupo devuelve false y no cambia los grupos")
    void unionRedundante() {
        UnionFindMedible grupos = new UnionFindMedible(4, true);
        assertTrue(grupos.unir(0, 1));
        assertTrue(grupos.unir(1, 2));
        assertFalse(grupos.unir(0, 2));          // ya estaban juntos
        assertEquals(2, grupos.cantidadDeGrupos());
    }

    @Test
    @DisplayName("Con las mismas uniones y busquedas repetidas, la compresion sube MENOS escalones")
    void laCompresionAhorraEscalones() {
        int n = 4096;
        UnionFindMedible con = new UnionFindMedible(n, true);
        UnionFindMedible sin = new UnionFindMedible(n, false);

        // MISMAS operaciones para ambos, generadas una sola vez con semilla fija.
        // Uniones "en torneo" para que la union por rango arme arboles de
        // altura log n (una cadena 0-1, 1-2, 2-3... con rango queda plana).
        for (int salto = 1; salto < n; salto *= 2) {
            for (int elemento = 0; elemento + salto < n; elemento += 2 * salto) {
                con.unir(elemento, elemento + salto);
                sin.unir(elemento, elemento + salto);
            }
        }

        // Muchas busquedas repetidas sobre elementos al azar (semilla fija).
        Random azar = new Random(2024);
        for (int busqueda = 0; busqueda < 20_000; busqueda++) {
            int elemento = azar.nextInt(n);
            assertEquals(sin.encontrar(elemento), con.encontrar(elemento),
                    "los representantes pueden diferir en numero, pero aca las uniones "
                    + "fueron identicas, asi que deben coincidir");
        }

        // La comparacion central del ejercicio:
        assertTrue(con.escalonesSubidos() < sin.escalonesSubidos(),
                "con compresion=" + con.escalonesSubidos()
                        + " deberia ser menor que sin=" + sin.escalonesSubidos());
    }

    @Test
    @DisplayName("Tras comprimir, repetir la misma busqueda cuesta a lo sumo 1 escalon")
    void busquedaRepetidaBarata() {
        UnionFindMedible grupos = new UnionFindMedible(8, true);
        // Cadena a mano para lograr profundidad: torneo de 8.
        grupos.unir(0, 1);
        grupos.unir(2, 3);
        grupos.unir(4, 5);
        grupos.unir(6, 7);
        grupos.unir(0, 2);
        grupos.unir(4, 6);
        grupos.unir(0, 4);

        grupos.encontrar(7);                     // primera vez: paga y comprime
        long antes = grupos.escalonesSubidos();
        grupos.encontrar(7);                     // segunda vez: ya cuelga de la raiz
        assertTrue(grupos.escalonesSubidos() - antes <= 1,
                "la segunda busqueda deberia costar a lo sumo 1 escalon");
    }

    @Test
    @DisplayName("El contador arranca en cero y encontrar una raiz no suma escalones")
    void contadorInicial() {
        UnionFindMedible grupos = new UnionFindMedible(3, true);
        assertEquals(0, grupos.escalonesSubidos());
        assertEquals(1, grupos.encontrar(1));    // 1 es su propia raiz
        assertEquals(0, grupos.escalonesSubidos());
    }
}

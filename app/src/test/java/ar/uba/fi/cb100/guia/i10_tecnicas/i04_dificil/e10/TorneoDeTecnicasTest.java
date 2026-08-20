package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e10;

import ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e10.TorneoDeTecnicas.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TorneoDeTecnicasTest {

    private final TorneoDeTecnicas torneo = new TorneoDeTecnicas();

    @Test
    @DisplayName("Con {1,3,4} y monto 6 el greedy falla: da 3 cuando el optimo es 2")
    void greedyFalla() {
        Resultado resultado = torneo.comparar(new int[] {1, 3, 4}, 6);
        assertEquals(2, resultado.backtracking()); // 3 + 3
        assertEquals(2, resultado.dp());
        assertEquals(3, resultado.greedy());       // 4 + 1 + 1
    }

    @Test
    @DisplayName("Con {1,5,10,25} y monto 30 las tres tecnicas dan 2 (25 + 5)")
    void sistemaCanonicoTreinta() {
        Resultado resultado = torneo.comparar(new int[] {1, 5, 10, 25}, 30);
        assertEquals(2, resultado.greedy());
        assertEquals(2, resultado.backtracking());
        assertEquals(2, resultado.dp());
    }

    @Test
    @DisplayName("Con {1,5,10,25} y monto 63 las tres dan 6 (25+25+10+1+1+1)")
    void sistemaCanonicoSesentaYTres() {
        Resultado resultado = torneo.comparar(new int[] {1, 5, 10, 25}, 63);
        assertEquals(6, resultado.greedy());
        assertEquals(6, resultado.backtracking());
        assertEquals(6, resultado.dp());
    }

    @Test
    @DisplayName("Backtracking y PD coinciden siempre, y el greedy nunca gana (varios casos)")
    void exactosCoinciden() {
        int[][] sistemas = {{1, 3, 4}, {1, 5, 10, 25}, {1, 2, 5}, {1, 7, 10}, {2, 3, 7}};
        int[] montos = {6, 63, 11, 14, 12};
        for (int i = 0; i < sistemas.length; i++) {
            Resultado resultado = torneo.comparar(sistemas[i], montos[i]);
            assertEquals(resultado.dp(), resultado.backtracking(),
                    "Backtracking y PD difieren en " + Arrays.toString(sistemas[i]) + "/" + montos[i]);
            if (resultado.greedy() != -1) {
                assertTrue(resultado.greedy() >= resultado.dp(),
                        "El greedy no puede ganarle al optimo");
            }
        }
    }

    @Test
    @DisplayName("Monto 0: cero monedas para las tres tecnicas")
    void montoCero() {
        assertEquals(new Resultado(0, 0, 0), torneo.comparar(new int[] {1, 3, 4}, 0));
    }

    @Test
    @DisplayName("Monto inalcanzable sin moneda de 1: las tecnicas exactas devuelven -1")
    void montoInalcanzable() {
        // Con {5, 10} no se puede armar 7.
        Resultado resultado = torneo.comparar(new int[] {5, 10}, 7);
        assertEquals(-1, resultado.greedy());
        assertEquals(-1, resultado.backtracking());
        assertEquals(-1, resultado.dp());
    }
}

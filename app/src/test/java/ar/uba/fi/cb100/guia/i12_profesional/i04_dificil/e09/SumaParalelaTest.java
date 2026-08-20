package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumaParalelaTest {

    @Test
    @DisplayName("con 50 millones: secuencial = paralela = Gauss")
    public void cincuentaMillones() {
        long n = 50_000_000;
        long esperado = SumaParalela.formulaDeGauss(n);   // 1250000025000000
        assertEquals(1_250_000_025_000_000L, esperado);
        assertEquals(esperado, SumaParalela.sumaSecuencial(n));
        assertEquals(esperado, SumaParalela.sumaParalela(n));
    }

    @Test
    @DisplayName("casos chicos y borde: n=1 y n=10")
    public void casosChicos() {
        assertEquals(1L, SumaParalela.sumaSecuencial(1));
        assertEquals(1L, SumaParalela.sumaParalela(1));
        assertEquals(55L, SumaParalela.sumaSecuencial(10));
        assertEquals(55L, SumaParalela.sumaParalela(10));
        assertEquals(55L, SumaParalela.formulaDeGauss(10));
    }

    @Test
    @DisplayName("la paralela es determinista: tres corridas, mismo valor")
    public void paralelaDeterminista() {
        long n = 1_000_000;
        long esperado = SumaParalela.formulaDeGauss(n);
        for (int corrida = 0; corrida < 3; corrida++) {
            assertEquals(esperado, SumaParalela.sumaParalela(n), "corrida " + corrida);
        }
    }
}

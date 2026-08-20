package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 09 - Histograma de letras. */
class HistogramaTest {

    @Test
    @DisplayName("Cuenta correctamente la letra 'a' ignorando mayusculas")
    void cuentaLetraA() {
        int[] h = Histograma.histograma("Anita lava la tina");
        // 'a' aparece 6 veces: Anita(2), lava(2), la(1), tina(1)
        assertEquals(6, h[0]);
        // 'z' no aparece
        assertEquals(0, h[25]);
    }

    @Test
    @DisplayName("Cuenta bien los extremos 'a' y 'z'")
    void extremos() {
        int[] h = Histograma.histograma("azAZ");
        assertEquals(2, h[0]);
        assertEquals(2, h[25]);
    }

    @Test
    @DisplayName("Ignora numeros y simbolos")
    void ignoraOtrosCaracteres() {
        int[] h = Histograma.histograma("a1!b?");
        assertEquals(1, h[0]);
        assertEquals(1, h[1]);
    }

    @Test
    @DisplayName("Cadena null lanza excepcion")
    void cadenaNull() {
        assertThrows(IllegalArgumentException.class, () -> Histograma.histograma(null));
    }
}

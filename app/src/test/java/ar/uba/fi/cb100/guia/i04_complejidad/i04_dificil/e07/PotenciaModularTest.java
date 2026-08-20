package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PotenciaModularTest {

    @Test
    @DisplayName("Casos conocidos")
    void casosConocidos() {
        assertEquals(24, PotenciaModular.potModular(2, 10, 1000)); // 1024 mod 1000
        assertEquals(3, PotenciaModular.potModular(3, 13, 7));
        assertEquals(445, PotenciaModular.potModular(4, 13, 497));
    }

    @Test
    @DisplayName("Exponente cero da 1 (o 0 si mod es 1)")
    void exponenteCero() {
        assertEquals(1, PotenciaModular.potModular(123, 0, 1000));
        assertEquals(0, PotenciaModular.potModular(123, 0, 1));
    }

    @Test
    @DisplayName("Base cero y base negativa normalizada")
    void baseEspecial() {
        assertEquals(0, PotenciaModular.potModular(0, 5, 7));
        // (-2)^3 = -8 ; -8 mod 5 = 2
        assertEquals(2, PotenciaModular.potModular(-2, 3, 5));
    }

    @Test
    @DisplayName("Coincide con cálculo directo para exponentes chicos")
    void coincideConDirecto() {
        long mod = 1_000;
        for (int base = 0; base < 10; base++) {
            long directo = 1;
            for (int e = 0; e <= 6; e++) {
                assertEquals(directo % mod, PotenciaModular.potModular(base, e, mod));
                directo = (directo * base) % mod;
            }
        }
    }

    @Test
    @DisplayName("Módulo no positivo o exponente negativo lanzan excepción")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> PotenciaModular.potModular(2, 3, 0));
        assertThrows(IllegalArgumentException.class, () -> PotenciaModular.potModular(2, 3, -5));
        assertThrows(IllegalArgumentException.class, () -> PotenciaModular.potModular(2, -1, 5));
    }
}

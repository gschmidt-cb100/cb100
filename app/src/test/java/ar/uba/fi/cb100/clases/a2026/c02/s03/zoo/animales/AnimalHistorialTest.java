package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;

/**
 * El historial de raciones de {@link Animal}: capacidad fija, ordenado de la
 * más vieja a la más nueva, y cuando se llena descarta la más vieja.
 */
class AnimalHistorialTest {

    private static final int CAPACIDAD = 10;

    /** Una ración identificable por su número, para verificar el orden. */
    private static Racion racion(int numero) {
        return new Racion(LocalDateTime.now(), new String[]{"racion " + numero}, 1.5);
    }

    private static int numeroDe(Racion racion) {
        return Integer.parseInt(racion.pasos()[0].substring("racion ".length()));
    }

    private static Animal unaOveja() {
        return new Oveja("Dolly", 40.0, 50.0);
    }

    @Test
    @DisplayName("registrar una ración sube el peso en lo que la ración aporta")
    void registrarSubeElPeso() {
        Animal dolly = unaOveja();

        dolly.registrarRacion(racion(1));

        assertEquals(41.5, dolly.getPeso(), 0.0001);
        assertEquals(1, dolly.getCantidadDeRaciones());
    }

    @Test
    @DisplayName("el historial guarda las raciones en el orden en que llegaron")
    void elHistorialConservaElOrden() {
        Animal dolly = unaOveja();
        dolly.registrarRacion(racion(1));
        dolly.registrarRacion(racion(2));
        dolly.registrarRacion(racion(3));

        Racion[] historial = dolly.getHistorialDeRaciones();

        assertEquals(3, historial.length, "sólo las posiciones usadas, sin nulls");
        assertEquals(1, numeroDe(historial[0]));
        assertEquals(2, numeroDe(historial[1]));
        assertEquals(3, numeroDe(historial[2]));
    }

    @Test
    @DisplayName("al llenarse, descarta la más vieja y la nueva queda última")
    void alLlenarseDescartaLaMasVieja() {
        Animal dolly = unaOveja();
        for (int i = 1; i <= CAPACIDAD; i++) {
            dolly.registrarRacion(racion(i));
        }
        assertEquals(CAPACIDAD, dolly.getCantidadDeRaciones(), "lleno, todavía sin descartar");

        dolly.registrarRacion(racion(CAPACIDAD + 1));          // la que no entra

        Racion[] historial = dolly.getHistorialDeRaciones();
        assertEquals(CAPACIDAD, historial.length, "sigue lleno, no crece");
        assertEquals(2, numeroDe(historial[0]), "la 1 se fue; la 2 pasó a ser la más vieja");
        assertEquals(CAPACIDAD + 1, numeroDe(historial[CAPACIDAD - 1]), "la nueva entró al final");
    }

    @Test
    @DisplayName("después de muchas raciones sigue ordenado y con las últimas 10")
    void conMuchasRacionesQuedanLasUltimasDiezOrdenadas() {
        Animal dolly = unaOveja();
        int total = 25;
        for (int i = 1; i <= total; i++) {
            dolly.registrarRacion(racion(i));
        }

        Racion[] historial = dolly.getHistorialDeRaciones();

        assertEquals(CAPACIDAD, historial.length);
        for (int i = 0; i < CAPACIDAD; i++) {
            int esperado = total - CAPACIDAD + 1 + i;   // 16, 17, ..., 25
            assertEquals(esperado, numeroDe(historial[i]), "posición " + i);
        }
        assertEquals(40.0 + total * 1.5, dolly.getPeso(), 0.0001,
                "el peso acumula TODAS las raciones, aunque el historial olvide las viejas");
    }

    @Test
    @DisplayName("getHistorialDeRaciones devuelve una copia: modificarla no afecta al animal")
    void elHistorialQueSeDevuelveEsUnaCopia() {
        Animal dolly = unaOveja();
        dolly.registrarRacion(racion(1));
        dolly.registrarRacion(racion(2));

        Racion[] copia = dolly.getHistorialDeRaciones();
        copia[0] = null;                                   // rompo la copia

        assertEquals(1, numeroDe(dolly.getHistorialDeRaciones()[0]), "el original sigue intacto");
    }

    @Test
    @DisplayName("getUltimaRacion es null sin raciones, y después la más reciente")
    void laUltimaRacion() {
        Animal dolly = unaOveja();
        assertNull(dolly.getUltimaRacion());

        Racion primera = racion(1);
        Racion segunda = racion(2);
        dolly.registrarRacion(primera);
        dolly.registrarRacion(segunda);

        assertSame(segunda, dolly.getUltimaRacion());
    }

    @Test
    @DisplayName("una ración nula se rechaza")
    void laRacionNulaSeRechaza() {
        Animal dolly = unaOveja();

        assertThrows(IllegalArgumentException.class, () -> dolly.registrarRacion(null));
        assertEquals(0, dolly.getCantidadDeRaciones(), "no quedó nada registrado");
    }
}

package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EmpaquetadoDeCancionesTest {

    private final EmpaquetadoDeCanciones empaquetadora = new EmpaquetadoDeCanciones();

    @Test
    @DisplayName("Duraciones [3,5,7,8] con capacidad 14: el maximo es 13")
    void ejemploVerificadoAMano() {
        // Verificacion a mano de todos los subconjuntos <= 14:
        // 3, 5, 7, 8, 3+5=8, 3+7=10, 3+8=11, 5+7=12, 5+8=13, 7+... (7+8=15 no),
        // 3+5+... (3+5+7=15 no). El mejor es 5+8 = 13.
        assertEquals(13, empaquetadora.duracionMaxima(new int[] {3, 5, 7, 8}, 14));
    }

    @Test
    @DisplayName("Duraciones [3,5,7,8] con capacidad 14: se eligen los indices [1, 3] (5 y 8)")
    void reconstruccion() {
        assertEquals(List.of(1, 3), empaquetadora.cancionesElegidas(new int[] {3, 5, 7, 8}, 14));
    }

    @Test
    @DisplayName("Las canciones elegidas suman exactamente la duracion maxima y respetan la capacidad")
    void eleccionConsistente() {
        int[] duraciones = {4, 6, 7, 9, 2};
        int capacidad = 16;
        int maximo = empaquetadora.duracionMaxima(duraciones, capacidad);
        List<Integer> elegidas = empaquetadora.cancionesElegidas(duraciones, capacidad);
        int suma = elegidas.stream().mapToInt(indice -> duraciones[indice]).sum();
        assertEquals(maximo, suma);
        assertTrue(suma <= capacidad);
        assertEquals(elegidas.size(), elegidas.stream().distinct().count(),
                "No se puede elegir dos veces la misma cancion");
        // Verificacion a mano: 4+6+2 = 12, 7+9 = 16 exacto -> el maximo es 16.
        assertEquals(16, maximo);
    }

    @Test
    @DisplayName("Donde el greedy 'mas larga primero' falla, la PD encuentra la suma exacta")
    void mejorQueGreedy() {
        // Greedy "mas larga primero" con capacidad 10: toma 7; despues ni 6
        // (7+6=13) ni 4 (7+4=11) entran, y se queda en 7.
        // La PD encuentra 4 + 6 = 10 exacto.
        assertEquals(10, empaquetadora.duracionMaxima(new int[] {4, 6, 7}, 10));
        assertEquals(List.of(0, 1), empaquetadora.cancionesElegidas(new int[] {4, 6, 7}, 10));
    }

    @Test
    @DisplayName("Si ninguna cancion entra, la duracion es 0 y la lista queda vacia")
    void ningunaEntra() {
        assertEquals(0, empaquetadora.duracionMaxima(new int[] {10, 12}, 5));
        assertTrue(empaquetadora.cancionesElegidas(new int[] {10, 12}, 5).isEmpty());
    }

    @Test
    @DisplayName("Duraciones no positivas lanzan IllegalArgumentException")
    void duracionInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> empaquetadora.duracionMaxima(new int[] {3, 0}, 10));
    }
}

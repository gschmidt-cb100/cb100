package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedianaIncrementalTest {

    @Test
    @DisplayName("Secuencia 5, 15, 1, 3: medianas 5.0, 10.0, 5.0, 4.0")
    void secuenciaClasica() {
        MedianaIncremental m = new MedianaIncremental();
        m.agregar(5);
        assertEquals(5.0, m.mediana());
        m.agregar(15);
        assertEquals(10.0, m.mediana());
        m.agregar(1);
        assertEquals(5.0, m.mediana());
        m.agregar(3);
        assertEquals(4.0, m.mediana());
    }

    @Test
    @DisplayName("Con valores en orden creciente la mediana acompaña al centro")
    void ordenCreciente() {
        MedianaIncremental m = new MedianaIncremental();
        for (int i = 1; i <= 5; i++) {
            m.agregar(i * 10);   // 10, 20, 30, 40, 50
        }
        assertEquals(30.0, m.mediana());
        assertEquals(5, m.cantidad());
    }

    @Test
    @DisplayName("Con cantidad par promedia los dos valores centrales")
    void cantidadPar() {
        MedianaIncremental m = new MedianaIncremental();
        m.agregar(7);
        m.agregar(9);
        // Centrales: 7 y 9 → (7 + 9) / 2 = 8.0
        assertEquals(8.0, m.mediana());
    }

    @Test
    @DisplayName("Valores repetidos no rompen el balanceo")
    void valoresRepetidos() {
        MedianaIncremental m = new MedianaIncremental();
        for (int i = 0; i < 4; i++) {
            m.agregar(4);
        }
        assertEquals(4.0, m.mediana());
    }

    @Test
    @DisplayName("Pedir la mediana sin valores lanza IllegalStateException")
    void sinValores() {
        MedianaIncremental m = new MedianaIncremental();
        assertThrows(IllegalStateException.class, m::mediana);
    }
}

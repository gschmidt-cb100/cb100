package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CorteDeVarillaTest {

    private final CorteDeVarilla cortadora = new CorteDeVarilla();

    @Test
    @DisplayName("Precios [1,5,8,9] con largo 4: el mejor precio es 10")
    void ejemploClasicoPrecio() {
        assertEquals(10, cortadora.mejorPrecio(new int[] {1, 5, 8, 9}, 4));
    }

    @Test
    @DisplayName("Precios [1,5,8,9] con largo 4: los cortes optimos son [2, 2]")
    void ejemploClasicoCortes() {
        assertEquals(List.of(2, 2), cortadora.cortesOptimos(new int[] {1, 5, 8, 9}, 4));
    }

    @Test
    @DisplayName("Los cortes reconstruidos suman el largo y valen el mejor precio")
    void cortesConsistentes() {
        int[] precios = {1, 5, 8, 9, 10, 17, 17, 20};
        for (int largo = 1; largo <= 8; largo++) {
            List<Integer> cortes = cortadora.cortesOptimos(precios, largo);
            int sumaLargos = cortes.stream().mapToInt(Integer::intValue).sum();
            int precioTotal = cortes.stream().mapToInt(corte -> precios[corte - 1]).sum();
            assertEquals(largo, sumaLargos, "largo " + largo);
            assertEquals(cortadora.mejorPrecio(precios, largo), precioTotal, "largo " + largo);
        }
    }

    @Test
    @DisplayName("Si no conviene cortar, devuelve la varilla entera")
    void sinCortar() {
        // El largo 3 paga 50, mucho mas que cualquier combinacion de cortes.
        assertEquals(50, cortadora.mejorPrecio(new int[] {1, 2, 50}, 3));
        assertEquals(List.of(3), cortadora.cortesOptimos(new int[] {1, 2, 50}, 3));
    }

    @Test
    @DisplayName("Largo 0 vale 0 y no tiene cortes")
    void largoCero() {
        assertEquals(0, cortadora.mejorPrecio(new int[] {1, 5, 8}, 0));
        assertTrue(cortadora.cortesOptimos(new int[] {1, 5, 8}, 0).isEmpty());
    }

    @Test
    @DisplayName("Largo mayor que la tabla: combina trozos que si tienen precio")
    void largoMayorQueLaTabla() {
        // Tabla hasta largo 2, varilla de 5: 2+2+1 = 5+5+1 = 11.
        assertEquals(11, cortadora.mejorPrecio(new int[] {1, 5}, 5));
    }
}

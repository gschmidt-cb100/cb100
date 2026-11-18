package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listas;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parcial 12/11 - filtrarRuido")
class DiseñadorDeSonidoTest {

    private static final double EPSILON = 1e-9;

    private final DiseñadorDeSonido diseñador = new DiseñadorDeSonido();

    private static Melodia melodiaCon(double... frecuencias) {
        Melodia melodia = new Melodia("Prueba");
        for (double frecuencia : frecuencias) {
            melodia.agregarNota(new Nota(frecuencia, 500, 1));
        }
        return melodia;
    }

    private static double[] frecuenciasDe(Melodia melodia) {
        return melodia.getNotas().stream().mapToDouble(Nota::getFrecuencia).toArray();
    }

    @Test
    @DisplayName("una melodía sin ruido no cambia")
    void sinRuido() {
        Melodia melodia = melodiaCon(440, 445, 450, 442, 448);
        diseñador.filtrarRuido(melodia, 10, 3);
        assertArrayEquals(new double[] {440, 445, 450, 442, 448}, frecuenciasDe(melodia), EPSILON);
    }

    @Test
    @DisplayName("una frecuencia anormal se reemplaza por el promedio de la ventana")
    void corrigeUnaNota() {
        Melodia melodia = melodiaCon(100, 100, 100, 200, 100);
        diseñador.filtrarRuido(melodia, 10, 3);
        assertArrayEquals(new double[] {100, 100, 100, 100, 100}, frecuenciasDe(melodia), EPSILON);
    }

    @Test
    @DisplayName("las primeras 'ventana' notas no se modifican aunque sean ruido")
    void primerasNotasIntactas() {
        Melodia melodia = melodiaCon(100, 900, 100, 100);
        diseñador.filtrarRuido(melodia, 10, 3);
        // promedio de la ventana = 1100 / 3 = 366,67: la cuarta nota (100) es anormal
        assertArrayEquals(new double[] {100, 900, 100, 1100.0 / 3}, frecuenciasDe(melodia), EPSILON);
    }

    @Test
    @DisplayName("la nota corregida entra a la ventana con su frecuencia nueva")
    void laCorregidaEntraALaVentana() {
        Melodia melodia = melodiaCon(100, 100, 300, 110);
        diseñador.filtrarRuido(melodia, 20, 2);
        // 300 contra promedio 100: se corrige a 100.
        // 110 contra promedio (100 + 100) / 2 = 100: está dentro del 20 %, queda.
        // Si la ventana guardara el 300 original, el promedio sería 200 y el 110 se corregiría.
        assertArrayEquals(new double[] {100, 100, 100, 110}, frecuenciasDe(melodia), EPSILON);
    }

    @Test
    @DisplayName("la ventana se desliza: sólo cuentan las últimas notas")
    void laVentanaSeDesliza() {
        Melodia melodia = melodiaCon(100, 100, 115, 130, 150, 100);
        diseñador.filtrarRuido(melodia, 20, 2);
        // 115 vs 100 ok; 130 vs 107,5 se corrige a 107,5; 150 vs 111,25 se corrige a 111,25;
        // 100 vs (107,5 + 111,25) / 2 = 109,375 ok.
        assertArrayEquals(new double[] {100, 100, 115, 107.5, 111.25, 100}, frecuenciasDe(melodia), EPSILON);
    }

    @Test
    @DisplayName("el borde exacto del porcentaje no es anormal")
    void bordeDelPorcentaje() {
        Melodia melodia = melodiaCon(100, 100, 120, 80);
        diseñador.filtrarRuido(melodia, 20, 2);
        // 120 está justo al 20 % de 100: queda. 80 contra promedio 110 (27 %): se corrige a 110.
        assertArrayEquals(new double[] {100, 100, 120, 110}, frecuenciasDe(melodia), EPSILON);
    }

    @Test
    @DisplayName("se modifican las notas existentes: la lista y sus objetos son los mismos")
    void modificaEnElLugar() {
        Melodia melodia = melodiaCon(100, 100, 500);
        List<Nota> lista = melodia.getNotas();
        Nota tercera = lista.get(2);
        diseñador.filtrarRuido(melodia, 10, 2);
        assertSame(lista, melodia.getNotas());
        assertSame(tercera, melodia.getNotas().get(2));
        assertEquals(100, tercera.getFrecuencia(), EPSILON);
        assertEquals(500, tercera.getDuracion());
    }

    @Test
    @DisplayName("una melodía vacía o más corta que la ventana no cambia")
    void melodiaCorta() {
        Melodia vacia = melodiaCon();
        diseñador.filtrarRuido(vacia, 10, 3);
        assertTrue(vacia.getNotas().isEmpty());

        Melodia corta = melodiaCon(100, 900, 50);
        diseñador.filtrarRuido(corta, 10, 3);
        assertArrayEquals(new double[] {100, 900, 50}, frecuenciasDe(corta), EPSILON);
    }

    @Test
    @DisplayName("valida los parámetros")
    void validaciones() {
        Melodia melodia = melodiaCon(100, 100);
        assertThrows(IllegalArgumentException.class, () -> diseñador.filtrarRuido(null, 10, 2));
        assertThrows(IllegalArgumentException.class, () -> diseñador.filtrarRuido(melodia, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> diseñador.filtrarRuido(melodia, 10, 0));
    }
}

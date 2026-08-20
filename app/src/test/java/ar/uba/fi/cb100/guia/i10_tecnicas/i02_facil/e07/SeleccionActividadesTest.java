package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeleccionActividadesTest {

    @Test
    @DisplayName("el ejemplo del apunte: de A..F se seleccionan A, C y F")
    void ejemploDelApunte() {
        Actividad a = new Actividad("A", 1, 3);
        Actividad b = new Actividad("B", 2, 5);
        Actividad c = new Actividad("C", 4, 7);
        Actividad d = new Actividad("D", 1, 8);
        Actividad e = new Actividad("E", 5, 9);
        Actividad f = new Actividad("F", 8, 10);

        assertEquals(List.of(a, c, f),
                SeleccionActividades.seleccionar(List.of(a, b, c, d, e, f)));
    }

    @Test
    @DisplayName("una actividad puede empezar justo cuando termina la anterior")
    void bordesQueSeTocanSonCompatibles() {
        Actividad m = new Actividad("mañana", 8, 12);
        Actividad t = new Actividad("tarde", 12, 18);
        assertEquals(List.of(m, t), SeleccionActividades.seleccionar(List.of(t, m)));
    }

    @Test
    @DisplayName("si todas se solapan, queda solo la que termina primero")
    void todasSolapadas() {
        Actividad corta = new Actividad("corta", 3, 4);
        Actividad media = new Actividad("media", 1, 6);
        Actividad larga = new Actividad("larga", 0, 10);
        assertEquals(List.of(corta),
                SeleccionActividades.seleccionar(List.of(larga, media, corta)));
    }

    @Test
    @DisplayName("la lista vacía devuelve una selección vacía")
    void listaVacia() {
        assertTrue(SeleccionActividades.seleccionar(List.of()).isEmpty());
    }

    @Test
    @DisplayName("no modifica la lista de entrada")
    void noModificaLaEntrada() {
        Actividad x = new Actividad("X", 5, 6);
        Actividad y = new Actividad("Y", 1, 2);
        List<Actividad> entrada = new java.util.ArrayList<>(List.of(x, y));
        SeleccionActividades.seleccionar(entrada);
        assertEquals(List.of(x, y), entrada);
    }
}

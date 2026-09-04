package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrenTest {

    @Test
    @DisplayName("el vagón valida su propia carga y calcula su lugar libre")
    void vagonValidaSuCarga() {
        Vagon vagon = new Vagon(100);
        assertEquals(100.0, vagon.lugarLibre());

        vagon.cargar(60);
        assertEquals(60.0, vagon.cargaActual());
        assertEquals(40.0, vagon.lugarLibre());

        assertThrows(IllegalStateException.class, () -> vagon.cargar(50),
                "no entran 50 kg en 40 kg de lugar");
        assertThrows(IllegalArgumentException.class, () -> vagon.cargar(0));
        assertThrows(IllegalArgumentException.class, () -> vagon.cargar(-10));
        assertThrows(IllegalArgumentException.class, () -> new Vagon(0));
    }

    @Test
    @DisplayName("cargar reparte al PRIMER vagón con lugar suficiente")
    void repartoAlPrimerVagonConLugar() {
        Tren tren = new Tren(3);
        Vagon primero = new Vagon(1000);
        Vagon segundo = new Vagon(2000);
        tren.engancharVagon(primero);
        tren.engancharVagon(segundo);

        tren.cargar(800);
        assertEquals(800.0, primero.cargaActual(), "el primer cargamento va al primer vagón");
        assertEquals(0.0, segundo.cargaActual());

        tren.cargar(500);
        assertEquals(800.0, primero.cargaActual(), "al primero le quedan 200: no entra");
        assertEquals(500.0, segundo.cargaActual(), "el segundo cargamento va al segundo vagón");

        tren.cargar(200);
        assertEquals(1000.0, primero.cargaActual(), "200 sí entran en el primero");
    }

    @Test
    @DisplayName("cargar lanza si el cargamento no entra entero en ningún vagón")
    void cargaQueNoEntra() {
        Tren tren = new Tren(2);
        tren.engancharVagon(new Vagon(100));
        tren.engancharVagon(new Vagon(100));

        assertThrows(IllegalStateException.class, () -> tren.cargar(150),
                "no se parte el cargamento entre vagones");
        assertThrows(IllegalArgumentException.class, () -> tren.cargar(0));

        Tren sinVagones = new Tren(1);
        assertThrows(IllegalStateException.class, () -> sinVagones.cargar(10));
    }

    @Test
    @DisplayName("cargaTotal se calcula y vagonMasCargado devuelve null sin vagones")
    void cargaTotalYMasCargado() {
        Tren tren = new Tren(3);
        assertEquals(0.0, tren.cargaTotal());
        assertNull(tren.vagonMasCargado());

        Vagon liviano = new Vagon(500);
        Vagon pesado = new Vagon(500);
        tren.engancharVagon(liviano);
        tren.engancharVagon(pesado);
        tren.cargar(100);   // al primero
        tren.cargar(300);   // entra en el primero (quedan 400)... 100+300=400 en el primero

        assertEquals(400.0, tren.cargaTotal());
        assertSame(liviano, tren.vagonMasCargado());
    }

    @Test
    @DisplayName("enganchar y desenganchar respetan los límites")
    void engancharYDesenganchar() {
        Tren tren = new Tren(2);
        assertThrows(IllegalStateException.class, tren::desengancharUltimo,
                "sin vagones no hay nada que desenganchar");
        assertThrows(IllegalArgumentException.class, () -> tren.engancharVagon(null));

        Vagon primero = new Vagon(100);
        Vagon segundo = new Vagon(200);
        tren.engancharVagon(primero);
        tren.engancharVagon(segundo);
        assertEquals(2, tren.cantidadDeVagones());
        assertThrows(IllegalStateException.class, () -> tren.engancharVagon(new Vagon(50)),
                "la formación está completa");

        assertSame(segundo, tren.desengancharUltimo());
        assertEquals(1, tren.cantidadDeVagones());
        assertSame(primero, tren.desengancharUltimo());
        assertThrows(IllegalStateException.class, tren::desengancharUltimo);
    }

    @Test
    @DisplayName("el constructor del tren valida el máximo de vagones")
    void constructorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Tren(0));
        assertThrows(IllegalArgumentException.class, () -> new Tren(-1));
    }
}

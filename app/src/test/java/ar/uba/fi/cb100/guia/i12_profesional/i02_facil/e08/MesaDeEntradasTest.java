package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MesaDeEntradasTest {

    private MesaDeEntradas mesa;

    @BeforeEach
    void preparar() {
        mesa = new MesaDeEntradas();
    }

    @Test
    @DisplayName("los numeros de tramite son crecientes desde 1")
    void numerosCrecientes() {
        assertEquals(1, mesa.ingresar("A", false));
        assertEquals(2, mesa.ingresar("B", true));
        assertEquals(3, mesa.ingresar("C", false));
    }

    @Test
    @DisplayName("sin urgentes se atiende por orden de llegada")
    void fifoSinUrgentes() {
        mesa.ingresar("A", false);
        mesa.ingresar("B", false);
        assertEquals("A", mesa.atender().descripcion());
        assertEquals("B", mesa.atender().descripcion());
    }

    @Test
    @DisplayName("un urgente pasa antes que los normales que llegaron primero")
    void urgentePrimero() {
        mesa.ingresar("Normal 1", false);
        mesa.ingresar("Normal 2", false);
        mesa.ingresar("Urgente", true);
        assertEquals("Urgente", mesa.atender().descripcion());
        assertEquals("Normal 1", mesa.atender().descripcion());
    }

    @Test
    @DisplayName("entre urgentes tambien vale el orden de llegada")
    void urgentesEntreSi() {
        mesa.ingresar("Normal", false);
        mesa.ingresar("Urgente 1", true);
        mesa.ingresar("Urgente 2", true);
        assertEquals("Urgente 1", mesa.atender().descripcion());
        assertEquals("Urgente 2", mesa.atender().descripcion());
        assertEquals("Normal", mesa.atender().descripcion());
    }

    @Test
    @DisplayName("atender sin nadie esperando devuelve null")
    void mesaVacia() {
        assertNull(mesa.atender());
        assertEquals(0, mesa.enEspera());
    }

    @Test
    @DisplayName("enEspera cuenta urgentes y normales juntos")
    void enEspera() {
        mesa.ingresar("A", false);
        mesa.ingresar("B", true);
        assertEquals(2, mesa.enEspera());
        mesa.atender();
        assertEquals(1, mesa.enEspera());
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    private Agenda agendaDeEjemplo() {
        Agenda agenda = new Agenda();
        agenda.agregar("Marta", "11-5555-1234");
        agenda.agregar("Miguel", "11-4444-9876");
        agenda.agregar("Ana", "11-3333-0000");
        return agenda;
    }

    @Test
    @DisplayName("agregar y consultar devuelve el telefono guardado")
    void agregarYConsultar() {
        assertEquals("11-5555-1234", agendaDeEjemplo().telefonoDe("Marta"));
    }

    @Test
    @DisplayName("un contacto inexistente devuelve null")
    void contactoInexistente() {
        assertNull(agendaDeEjemplo().telefonoDe("Zulema"));
    }

    @Test
    @DisplayName("quitar elimina el contacto de la agenda")
    void quitarContacto() {
        Agenda agenda = agendaDeEjemplo();
        agenda.quitar("Ana");
        assertNull(agenda.telefonoDe("Ana"));
    }

    @Test
    @DisplayName("conInicial('M') devuelve solo a Marta y Miguel, en orden")
    void contactosConInicial() {
        SortedMap<String, String> conM = agendaDeEjemplo().conInicial('M');
        assertEquals(2, conM.size());
        assertEquals("Marta", conM.firstKey());
        assertEquals("Miguel", conM.lastKey());
    }

    @Test
    @DisplayName("conInicial de una letra sin contactos devuelve un mapa vacio")
    void inicialSinContactos() {
        assertTrue(agendaDeEjemplo().conInicial('Z').isEmpty());
    }
}

package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    @Test
    @DisplayName("agregar y consultar un telefono")
    void agregarYConsultar() {
        Agenda agenda = new Agenda();
        agenda.agregar("Ana", "11-5555-0001");
        assertEquals("11-5555-0001", agenda.telefonoDe("Ana"));
    }

    @Test
    @DisplayName("un contacto inexistente devuelve null")
    void contactoInexistente() {
        assertNull(new Agenda().telefonoDe("Zoe"));
    }

    @Test
    @DisplayName("agregar dos veces el mismo nombre reemplaza el telefono")
    void agregarReemplaza() {
        Agenda agenda = new Agenda();
        agenda.agregar("Bruno", "11-1111-1111");
        agenda.agregar("Bruno", "11-2222-2222");
        assertEquals("11-2222-2222", agenda.telefonoDe("Bruno"));
    }

    @Test
    @DisplayName("quitar elimina el contacto")
    void quitarElimina() {
        Agenda agenda = new Agenda();
        agenda.agregar("Carla", "11-3333-3333");
        agenda.quitar("Carla");
        assertNull(agenda.telefonoDe("Carla"));
    }

    @Test
    @DisplayName("duenioDe encuentra por telefono y devuelve null si nadie lo tiene")
    void busquedaInversa() {
        Agenda agenda = new Agenda();
        agenda.agregar("Ana", "11-5555-0001");
        agenda.agregar("Bruno", "11-5555-0002");
        assertEquals("Bruno", agenda.duenioDe("11-5555-0002"));
        assertNull(agenda.duenioDe("11-9999-9999"));
    }
}

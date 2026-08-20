package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltroDeMailsTest {

    private static final List<String> CRUDOS = List.of(
            "Ana@fi.uba.ar", "sin-arroba", "beto@GMAIL.com",
            "ana@fi.uba.ar", "Carla@fi.uba.ar");

    @Test
    @DisplayName("la version declarativa filtra, normaliza, deduplica y ordena")
    void declarativa() {
        assertEquals(List.of("ana@fi.uba.ar", "beto@gmail.com", "carla@fi.uba.ar"),
                FiltroDeMails.mailsValidosDeclarativo(CRUDOS));
    }

    @Test
    @DisplayName("ambas versiones coinciden con datos mezclados")
    void coincidenConDatosMezclados() {
        assertEquals(FiltroDeMails.mailsValidosImperativo(CRUDOS),
                FiltroDeMails.mailsValidosDeclarativo(CRUDOS));
    }

    @Test
    @DisplayName("ambas versiones coinciden con lista vacia")
    void coincidenConVacia() {
        assertEquals(FiltroDeMails.mailsValidosImperativo(List.of()),
                FiltroDeMails.mailsValidosDeclarativo(List.of()));
        assertTrue(FiltroDeMails.mailsValidosDeclarativo(List.of()).isEmpty());
    }

    @Test
    @DisplayName("ambas versiones coinciden cuando ningun mail es valido")
    void coincidenSinValidos() {
        List<String> invalidos = List.of("hola", "chau", "arroba.no");
        assertEquals(FiltroDeMails.mailsValidosImperativo(invalidos),
                FiltroDeMails.mailsValidosDeclarativo(invalidos));
        assertTrue(FiltroDeMails.mailsValidosDeclarativo(invalidos).isEmpty());
    }

    @Test
    @DisplayName("duplicados que solo difieren en mayusculas quedan una vez")
    void duplicadosPorMayusculas() {
        List<String> lista = List.of("A@b.com", "a@B.COM", "a@b.com");
        assertEquals(List.of("a@b.com"), FiltroDeMails.mailsValidosDeclarativo(lista));
        assertEquals(FiltroDeMails.mailsValidosImperativo(lista),
                FiltroDeMails.mailsValidosDeclarativo(lista));
    }
}

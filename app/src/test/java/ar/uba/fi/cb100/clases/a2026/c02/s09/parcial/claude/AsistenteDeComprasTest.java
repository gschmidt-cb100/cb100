package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.claude;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link AsistenteDeCompras#comprarAtuendos(List, List)} y las reglas que
 * delega en {@link Atuendo} y {@link Comprador}.
 */
class AsistenteDeComprasTest {

    private final AsistenteDeCompras asistente = new AsistenteDeCompras();

    // ---- Atuendo ------------------------------------------------------------------------------

    @Test
    @DisplayName("dos atuendos combinan si tienen el mismo color y distinto tipo")
    void combinanConMismoColorYDistintoTipo() {
        Atuendo remeraRoja = new Atuendo("remera", "rojo", 100);
        Atuendo pantalonRojo = new Atuendo("pantalón", "rojo", 200);
        Atuendo pantalonAzul = new Atuendo("pantalón", "azul", 200);
        Atuendo otraRemeraRoja = new Atuendo("remera", "rojo", 50);

        assertTrue(remeraRoja.combinaCon(pantalonRojo));
        assertTrue(pantalonRojo.combinaCon(remeraRoja), "es simétrico");
        assertFalse(remeraRoja.combinaCon(pantalonAzul), "distinto color");
        assertFalse(remeraRoja.combinaCon(otraRemeraRoja), "mismo tipo");
    }

    @Test
    @DisplayName("el color se compara exactamente, sin normalizar")
    void elColorSeComparaExacto() {
        Atuendo rojo = new Atuendo("remera", "rojo", 100);
        Atuendo rojoMayuscula = new Atuendo("pantalón", "Rojo", 100);

        assertFalse(rojo.combinaCon(rojoMayuscula));
    }

    @Test
    @DisplayName("un atuendo sin tipo, sin color o con precio negativo se rechaza")
    void atuendoInvalidoSeRechaza() {
        assertThrows(IllegalArgumentException.class, () -> new Atuendo(null, "rojo", 1));
        assertThrows(IllegalArgumentException.class, () -> new Atuendo("remera", "  ", 1));
        assertThrows(IllegalArgumentException.class, () -> new Atuendo("remera", "rojo", -1));
    }

    // ---- Comprador ----------------------------------------------------------------------------

    @Test
    @DisplayName("puede comprar si combinan y la suma de precios no supera el presupuesto")
    void puedeComprarSegunPresupuesto() {
        Atuendo remera = new Atuendo("remera", "rojo", 100);
        Atuendo pantalon = new Atuendo("pantalón", "rojo", 200);

        assertTrue(new Comprador("justo", 300).puedeComprar(remera, pantalon), "el límite incluye");
        assertFalse(new Comprador("corto", 299.99).puedeComprar(remera, pantalon));
        assertFalse(new Comprador("rico", 1000).puedeComprar(remera, new Atuendo("remera", "rojo", 1)),
                "con plata pero sin combinar, no");
    }

    @Test
    @DisplayName("un comprador sin nombre o con presupuesto negativo se rechaza")
    void compradorInvalidoSeRechaza() {
        assertThrows(IllegalArgumentException.class, () -> new Comprador("", 10));
        assertThrows(IllegalArgumentException.class, () -> new Comprador("Ana", -1));
    }

    // ---- AsistenteDeCompras -------------------------------------------------------------------

    @Test
    @DisplayName("un comprador se lleva el primer par que le sirve y queda el resto")
    void unCompradorSeLlevaElPrimerParQueLeSirve() {
        Atuendo remeraRoja = new Atuendo("remera", "rojo", 100);
        Atuendo pantalonAzul = new Atuendo("pantalón", "azul", 300);
        Atuendo pantalonRojo = new Atuendo("pantalón", "rojo", 250);
        Atuendo remeraAzul = new Atuendo("remera", "azul", 120);
        List<Atuendo> stock = List.of(remeraRoja, pantalonAzul, pantalonRojo, remeraAzul);

        List<Atuendo> noComprados = asistente.comprarAtuendos(stock, List.of(new Comprador("Ana", 350)));

        assertEquals(List.of(pantalonAzul, remeraAzul), noComprados);
    }

    @Test
    @DisplayName("cada atuendo se compra una sola vez: el segundo comprador no ve lo que se llevó el primero")
    void unAtuendoSeCompraUnaSolaVez() {
        Atuendo remeraRoja = new Atuendo("remera", "rojo", 100);
        Atuendo pantalonRojo = new Atuendo("pantalón", "rojo", 100);
        Atuendo camperaRoja = new Atuendo("campera", "rojo", 100);
        List<Atuendo> stock = List.of(remeraRoja, pantalonRojo, camperaRoja);
        List<Comprador> compradores = List.of(new Comprador("Ana", 200), new Comprador("Beto", 200));

        List<Atuendo> noComprados = asistente.comprarAtuendos(stock, compradores);

        assertEquals(List.of(camperaRoja), noComprados,
                "Ana se llevó remera+pantalón; a Beto le quedó sólo la campera, sin par");
    }

    @Test
    @DisplayName("si a un comprador no le alcanza, no compra y el siguiente sí puede")
    void siNoLeAlcanzaPasaAlSiguiente() {
        Atuendo remeraRoja = new Atuendo("remera", "rojo", 100);
        Atuendo pantalonRojo = new Atuendo("pantalón", "rojo", 250);
        List<Atuendo> stock = List.of(remeraRoja, pantalonRojo);
        List<Comprador> compradores = List.of(new Comprador("pobre", 150), new Comprador("rico", 350));

        List<Atuendo> noComprados = asistente.comprarAtuendos(stock, compradores);

        assertTrue(noComprados.isEmpty());
    }

    @Test
    @DisplayName("con menos de dos atuendos nadie compra")
    void conMenosDeDosAtuendosNadieCompra() {
        Atuendo unica = new Atuendo("remera", "rojo", 1);
        List<Comprador> compradores = List.of(new Comprador("Ana", 1000));

        assertEquals(List.of(unica), asistente.comprarAtuendos(List.of(unica), compradores));
        assertEquals(List.of(), asistente.comprarAtuendos(List.of(), compradores));
    }

    @Test
    @DisplayName("sin compradores, vuelve todo el stock")
    void sinCompradoresVuelveTodo() {
        List<Atuendo> stock = List.of(new Atuendo("remera", "rojo", 1), new Atuendo("pantalón", "rojo", 1));

        assertEquals(stock, asistente.comprarAtuendos(stock, List.of()));
    }

    @Test
    @DisplayName("el par puede estar en cualquier posición del stock, no sólo contiguo")
    void elParNoTieneQueSerContiguo() {
        Atuendo remeraRoja = new Atuendo("remera", "rojo", 100);
        Atuendo remeraVerde = new Atuendo("remera", "verde", 100);
        Atuendo pantalonVerde = new Atuendo("pantalón", "verde", 100);
        Atuendo pantalonRojo = new Atuendo("pantalón", "rojo", 100);
        List<Atuendo> stock = List.of(remeraRoja, remeraVerde, pantalonVerde, pantalonRojo);

        List<Atuendo> noComprados = asistente.comprarAtuendos(stock, List.of(new Comprador("Ana", 200)));

        assertEquals(List.of(remeraVerde, pantalonVerde), noComprados,
                "remera roja (pos 0) + pantalón rojo (pos 3) es el primer par que sirve");
    }

    @Test
    @DisplayName("la lista de stock que se pasa no se modifica")
    void noModificaElStockRecibido() {
        List<Atuendo> stock = new ArrayList<>(List.of(
                new Atuendo("remera", "rojo", 100), new Atuendo("pantalón", "rojo", 100)));
        List<Atuendo> copia = new ArrayList<>(stock);

        asistente.comprarAtuendos(stock, List.of(new Comprador("Ana", 1000)));

        assertEquals(copia, stock);
    }

    @Test
    @DisplayName("listas nulas o con nulos se rechazan")
    void listasNulasSeRechazan() {
        List<Atuendo> stock = List.of(new Atuendo("remera", "rojo", 1));
        List<Comprador> compradores = List.of(new Comprador("Ana", 1));
        List<Atuendo> stockConNulo = new ArrayList<>();
        stockConNulo.add(null);

        assertThrows(IllegalArgumentException.class, () -> asistente.comprarAtuendos(null, compradores));
        assertThrows(IllegalArgumentException.class, () -> asistente.comprarAtuendos(stock, null));
        assertThrows(IllegalArgumentException.class, () -> asistente.comprarAtuendos(stockConNulo, compradores));
    }
}

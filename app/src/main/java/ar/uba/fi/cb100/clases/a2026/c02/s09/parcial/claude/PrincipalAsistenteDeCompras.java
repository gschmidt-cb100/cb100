package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.claude;

import java.util.List;

/**
 * Programa de ejemplo: arma un stock y una fila de compradores, los atiende
 * y muestra qué quedó sin vender.
 */
public class PrincipalAsistenteDeCompras {

    public static void main(String[] args) {
        List<Atuendo> stock = List.of(
                new Atuendo("remera", "rojo", 100),
                new Atuendo("pantalón", "azul", 300),
                new Atuendo("pantalón", "rojo", 250),
                new Atuendo("remera", "azul", 120),
                new Atuendo("campera", "rojo", 900),
                new Atuendo("remera", "rojo", 80));

        List<Comprador> compradores = List.of(
                new Comprador("Ana", 350),     // se lleva remera roja $100 + pantalón rojo $250
                new Comprador("Bruno", 150),   // no le alcanza para ningún par
                new Comprador("Carla", 500));  // se lleva pantalón azul $300 + remera azul $120

        AsistenteDeCompras asistente = new AsistenteDeCompras();
        List<Atuendo> noComprados = asistente.comprarAtuendos(stock, compradores);

        System.out.println("Stock inicial:   " + stock);
        System.out.println("Compradores:     " + compradores);
        System.out.println("Sin comprar:     " + noComprados);
    }
}

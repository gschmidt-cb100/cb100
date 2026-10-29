package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.tda.datos.Bicicleta;

public class PrincipalEstacionDeBicicletas {

    public static void main(String[] args) {
        EstacionDeBicicletas estacion = new EstacionDeBicicletas(3);
        System.out.println("Más usada (vacía): " + estacion.getBicicletaMasUsada());        // null

        Bicicleta b1 = new Bicicleta("BK101", 120);
        Bicicleta b2 = new Bicicleta("BK102", 40);
        Bicicleta b3 = new Bicicleta("BK103");

        estacion.devolver(b1);                 // anclaje 1
        estacion.devolver(b2);                 // anclaje 2
        System.out.println("Ocupados: " + estacion.getCantidadDeAnclajesOcupados());     // 2
        System.out.println("Libres: " + estacion.getCantidadDeAnclajesLibres());         // 1
        System.out.println("Ocupación: " + estacion.getPorcentajeDeOcupacion() + "%");  // 66.6

        estacion.retirar(1);                   // b1: 1 uso
        b1.agregarKilometros(15);
        estacion.devolver(b3);                 // anclaje 1 (primer libre)
        estacion.devolver(b1);                 // anclaje 3
        estacion.retirar(3);                   // b1: 2 usos
        estacion.retirar(2);                   // b2: 1 uso
        estacion.devolver(b1);                 // anclaje 2

        System.out.println(estacion);
        System.out.println("Más usada: " + estacion.getBicicletaMasUsada());            // BK101
        System.out.println("Más km: " + estacion.getBicicletaConMasKilometros());       // BK101 (135 km)

        b3.enviarAReparacion();
        try {
            estacion.retirar(1);
        } catch (IllegalStateException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }

        estacion.devolver(b2);
        try {
            estacion.devolver(new Bicicleta("BK104"));
        } catch (IllegalStateException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }

        try {
            new Bicicleta("BK-105");
        } catch (IllegalArgumentException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }
    }
}

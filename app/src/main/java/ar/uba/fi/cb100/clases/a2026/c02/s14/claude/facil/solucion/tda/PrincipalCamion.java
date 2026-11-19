package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.tda;

/**
 * Caso de uso del TDA Camion, contado de punta a punta: sirve para verificar
 * que no falta ni sobra ninguna operación.
 */
public class PrincipalCamion {

    public static void main(String[] args) {
        Camion camion = new Camion(4, 100);
        System.out.println("Más pesado (vacío): " + camion.getPaqueteMasPesado());          // null

        Paquete heladera = new Paquete("P-001", "Rosario", 60, false);
        Paquete vajilla = new Paquete("P-002", "Córdoba", 15, true);
        Paquete libros = new Paquete("P-003", "Rosario", 20, false);
        Paquete televisor = new Paquete("P-004", "Mendoza", 12, true);

        System.out.println("Lugar de la heladera: " + camion.cargar(heladera));             // 1
        System.out.println("Lugar de la vajilla: " + camion.cargar(vajilla));               // 2
        System.out.println("Lugar de los libros: " + camion.cargar(libros));                // 3

        System.out.println("Lugares libres: " + camion.getCantidadDeLugaresLibres());       // 1
        System.out.println("Peso total: " + camion.getPesoTotal() + " kg");                 // 95.0
        System.out.println("Ocupación: " + camion.getPorcentajeDeOcupacion() + " %");       // 95.0
        System.out.println("Más pesado: " + camion.getPaqueteMasPesado());                  // P-001
        System.out.println("Frágiles: " + camion.getCantidadDePaquetesFragiles());          // 1
        System.out.println("A Rosario: " + camion.buscarPaquetes("Rosario").length);        // 2

        try {
            camion.cargar(televisor);                                                       // 95 + 12 > 100
        } catch (IllegalStateException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }

        camion.descargar("P-002");                                                          // libera el lugar 2
        System.out.println("Lugar del televisor: " + camion.cargar(televisor));             // 2 (primer libre)
        System.out.println(camion);

        Camion otro = new Camion(2, 500);
        otro.cargar(new Paquete("P-100", "Salta", 200, false));
        System.out.println("¿Destino en común?: " + camion.tieneDestinoEnComun(otro));      // false
        otro.cargar(new Paquete("P-101", "mendoza", 30, false));
        System.out.println("¿Destino en común?: " + camion.tieneDestinoEnComun(otro));      // true

        try {
            otro.cargar(new Paquete("P-102", "Salta", 1, false));                           // lleno
        } catch (IllegalStateException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }

        try {
            camion.descargar("P-999");
        } catch (IllegalArgumentException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }
    }
}

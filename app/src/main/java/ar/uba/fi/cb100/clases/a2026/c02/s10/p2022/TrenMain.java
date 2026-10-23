package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022;

/**
 * Prueba de todos los métodos del tren y de los vagones, caso por caso: los
 * válidos muestran el estado resultante, los inválidos muestran la excepción
 * que el TDA tiene que lanzar. Cada "intentar" dice qué se esperaba, así se
 * ve de un vistazo si el TDA se comportó bien.
 */
public class TrenMain {

    public static void main(String[] args) {
        Tren tren = new Tren(4);
        System.out.println("nuevo:         " + tren + ", lleno: " + tren.estaLleno());

        VagonDePasajeros primero = new VagonDePasajeros(20, 60);
        tren.agregarVagon(primero);
        tren.agregarVagon(new VagonDePasajeros(20, 40));
        tren.agregarVagon(new VagonDeCarga(15, 30));
        System.out.println("3 vagones:     " + tren);

        for (int i = 1; i <= tren.getCantidadDeVagones(); i++) {
            System.out.println("   vagón " + i + ": " + tren.getVagon(i));
        }
        intentar("agregar null", "IllegalArgumentException", () -> tren.agregarVagon(null));
        intentar("agregar el primero otra vez", "IllegalArgumentException", () -> tren.agregarVagon(primero));
        intentar("pedir el vagón 0", "IllegalArgumentException", () -> tren.getVagon(0));
        intentar("pedir el vagón 4", "IllegalArgumentException", () -> tren.getVagon(4));
        tren.agregarVagon(new VagonDeCarga(15, 20));
        System.out.println("4 vagones:     " + tren + ", lleno: " + tren.estaLleno());
        intentar("agregar un quinto", "IllegalStateException", () -> tren.agregarVagon(new VagonDeCarga(15, 20)));

        titulo("3. Capacidades totales y restantes");
        System.out.println("longitud total:         " + tren.getLongitudTotal() + " m (20 + 20 + 15 + 15)");
        System.out.println("pasajeros total/rest.:  " + tren.getCapacidadDePasajeros() + " / "
                + tren.getCapacidadRestanteDePasajeros() + " (60 + 40)");
        System.out.println("carga total/restante:   " + tren.getCapacidadDeCarga() + " / "
                + tren.getCapacidadRestanteDeCarga() + " t (30 + 20)");

        titulo("4. Subir y bajar pasajeros");
        tren.subirPasajeros(75);
        System.out.println("suben 75:      " + tren.getVagon(1) + " | " + tren.getVagon(2)
                + "  (esperado 60 y 15)");
        System.out.println("               a bordo " + tren.getPasajeros() + ", restante " + tren.getCapacidadRestanteDePasajeros());
        intentar("subir 26 con 25 lugares", "IllegalArgumentException", () -> tren.subirPasajeros(26));
        intentar("subir -3", "IllegalArgumentException", () -> tren.subirPasajeros(-3));
        tren.bajarPasajeros(20);
        System.out.println("bajan 20:      " + tren.getVagon(1) + " | " + tren.getVagon(2)
                + "  (esperado 55 y 0: se vacía de atrás hacia adelante)");
        intentar("bajar 56 con 55 a bordo", "IllegalArgumentException", () -> tren.bajarPasajeros(56));

        titulo("5. Cargar y descargar");
        tren.cargar(42.5);
        System.out.println("cargan 42.5 t: " + tren.getVagon(3) + " | " + tren.getVagon(4)
                + "  (esperado 30 y 12.5)");
        System.out.println("               a bordo " + tren.getCarga() + " t, restante " + tren.getCapacidadRestanteDeCarga() + " t");
        intentar("cargar 8 t con 7.5 de lugar", "IllegalArgumentException", () -> tren.cargar(8));
        tren.descargar(20);
        System.out.println("descargan 20:  " + tren.getVagon(3) + " | " + tren.getVagon(4)
                + "  (esperado 22.5 y 0: se vacía de atrás hacia adelante)");
        intentar("descargar 23 con 22.5 a bordo", "IllegalArgumentException", () -> tren.descargar(23));
        System.out.println("estado:        " + tren);

        titulo("6. Retirar vagones de pasajeros");
        intentar("retirar con el último de pasajeros ocupado (55 a bordo en el 1, el 2 vacío)",
                "devuelve el vagón 2, que está vacío", () -> System.out.println("   retirado: " + tren.retirarVagonDePasajeros()));
        System.out.println("estado:        " + tren);
        intentar("retirar otro: el que queda lleva 55 pasajeros", "RuntimeException",
                () -> tren.retirarVagonDePasajeros());
        tren.bajarPasajeros(55);
        intentar("retirar con el vagón ya vacío", "devuelve el vagón 1",
                () -> System.out.println("   retirado: " + tren.retirarVagonDePasajeros()));
        System.out.println("estado:        " + tren);
        intentar("retirar sin vagones de pasajeros", "RuntimeException", () -> tren.retirarVagonDePasajeros());
        intentar("subir pasajeros a un tren sin vagones de pasajeros", "IllegalArgumentException",
                () -> tren.subirPasajeros(1));
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    /** Ejecuta la acción e informa si hizo lo esperado: terminó bien o lanzó la excepción esperada. */
    private static void intentar(String descripcion, String esperado, Runnable accion) {
        System.out.print("intentar " + descripcion + " [esperado: " + esperado + "] -> ");
        try {
            accion.run();
            System.out.println("terminó sin excepción");
        } catch (RuntimeException e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}

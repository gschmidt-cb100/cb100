package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

/**
 * Prueba de todo el tren versión 1. Lo importante para mirar: el tren carga
 * pasajeros, contenedores y granel con el MISMO método {@code cargar(Carga)},
 * y cada carga termina en el vagón que la admite sin que el tren pregunte de
 * qué tipo es.
 */
public class TrenMain {

    public static void main(String[] args) {
        titulo("1. Las cargas");
        Carga ana = new Pasajero("Ana");
        Carga bruno = new Pasajero("Bruno");
        Carga contenedorRojo = new Contenedor("MSCU-1234", 12.5);
        Carga contenedorAzul = new Contenedor("MSCU-5678", 20);
        Carga trigo = new Granel("trigo", 18);
        for (Carga carga : new Carga[]{ana, contenedorRojo, trigo}) {
            System.out.println("   " + carga.getDescripcion() + ": tipo " + carga.getTipo() + ", " + carga.getPeso() + " t");
        }
        intentar("pasajero sin nombre", "IllegalArgumentException", () -> new Pasajero(" "));
        intentar("contenedor de -1 t", "IllegalArgumentException", () -> new Contenedor("X", -1));

        titulo("2. Los vagones y qué admite cada uno");
        VagonDePasajeros coche = new VagonDePasajeros(20, 2);
        VagonDeCarga plataforma = new VagonDeCarga(15, 30, 3, TipoDeCarga.CONTENEDOR);
        VagonDeCarga tolva = new VagonDeCarga(12, 40, 1, TipoDeCarga.GRANEL);
        VagonDeCarga mixto = new VagonDeCarga(18, 25, 4, TipoDeCarga.CONTENEDOR, TipoDeCarga.GRANEL, TipoDeCarga.GANADO);
        System.out.println("   coche admite PASAJERO: " + coche.admiteTipo(TipoDeCarga.PASAJERO)
                + ", CONTENEDOR: " + coche.admiteTipo(TipoDeCarga.CONTENEDOR));
        System.out.println("   plataforma acepta el contenedor rojo: " + plataforma.aceptaCarga(contenedorRojo)
                + ", el trigo: " + plataforma.aceptaCarga(trigo) + ", a Ana: " + plataforma.aceptaCarga(ana));
        System.out.println("   mixto acepta el trigo: " + mixto.aceptaCarga(trigo) + " y el contenedor azul de 20 t: "
                + mixto.aceptaCarga(contenedorAzul));
        intentar("vagón de carga sin tipos", "IllegalArgumentException", () -> new VagonDeCarga(15, 30, 3));
        intentar("vagón de carga que admite PASAJERO", "IllegalArgumentException",
                () -> new VagonDeCarga(15, 30, 3, TipoDeCarga.PASAJERO));
        intentar("subir el trigo a la plataforma a la fuerza", "IllegalArgumentException",
                () -> plataforma.agregarCarga(trigo));

        titulo("3. Armar el tren");
        Tren tren = new Tren(5);
        tren.agregarVagon(coche);
        tren.agregarVagon(new VagonComedor(22));
        tren.agregarVagon(plataforma);
        tren.agregarVagon(tolva);
        tren.agregarVagon(mixto);
        System.out.println("   " + tren);
        intentar("agregar el coche otra vez", "IllegalArgumentException", () -> tren.agregarVagon(coche));
        intentar("agregar un sexto", "IllegalStateException", () -> tren.agregarVagon(new VagonComedor(10)));
        System.out.println("   lugares para PASAJERO: " + tren.getLugaresLibresPara(TipoDeCarga.PASAJERO)
                + " | toneladas para CONTENEDOR: " + tren.getPesoDisponiblePara(TipoDeCarga.CONTENEDOR)
                + " | para GRANEL: " + tren.getPesoDisponiblePara(TipoDeCarga.GRANEL)
                + " | para GANADO: " + tren.getPesoDisponiblePara(TipoDeCarga.GANADO));

        titulo("4. Cargar: el mismo método para todo");
        for (Carga carga : new Carga[]{ana, contenedorRojo, trigo, bruno, contenedorAzul}) {
            System.out.println("   " + carga.getDescripcion() + " -> vagón " + tren.cargar(carga));
        }
        System.out.println("   " + tren);
        for (int i = 1; i <= tren.getCantidadDeVagones(); i++) {
            System.out.println("      vagón " + i + ": " + tren.getVagon(i));
        }
        intentar("un tercer pasajero con 2 asientos", "IllegalStateException", () -> tren.cargar(new Pasajero("Carla")));
        intentar("ganado de 30 t: el mixto admite GANADO pero ya no tiene peso", "IllegalStateException",
                () -> tren.cargar(new Granel("maíz", 30)));
        System.out.println("   lugares para PASAJERO: " + tren.getLugaresLibresPara(TipoDeCarga.PASAJERO)
                + " | toneladas para CONTENEDOR: " + tren.getPesoDisponiblePara(TipoDeCarga.CONTENEDOR));

        titulo("5. Descargar y retirar");
        System.out.println("   contiene a Ana: " + tren.contiene(ana) + ", contiene a Carla: " + tren.contiene(new Pasajero("Carla")));
        System.out.println("   baja Ana del vagón " + tren.descargar(ana) + "; baja el contenedor azul del vagón " + tren.descargar(contenedorAzul));
        intentar("bajar a Ana otra vez", "IllegalArgumentException", () -> tren.descargar(ana));
        intentar("retirar la plataforma con el contenedor rojo a bordo", "IllegalStateException", () -> tren.retirarVagon(3));
        System.out.println("   retirado el comedor: " + tren.retirarVagon(2));
        System.out.println("   " + tren);
        for (int i = 1; i <= tren.getCantidadDeVagones(); i++) {
            System.out.println("      vagón " + i + ": " + tren.getVagon(i));
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    private static void intentar(String descripcion, String esperado, Runnable accion) {
        System.out.print("   intentar " + descripcion + " [esperado: " + esperado + "] -> ");
        try {
            accion.run();
            System.out.println("terminó sin excepción");
        } catch (RuntimeException e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}

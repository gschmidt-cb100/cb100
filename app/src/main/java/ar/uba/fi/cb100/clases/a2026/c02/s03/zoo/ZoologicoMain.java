package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Jirafa;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Leon;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Oveja;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.ActaDeAlimentacion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.CuidadorDeJirafa;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.CuidadorDeLeon;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.CuidadorDeOveja;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura.Jaula;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura.Zoologico;

/**
 * Programa de prueba del zoológico. Arma un zoológico chico, simula las rondas
 * de alimentación de un día y muestra por consola lo que va pasando.
 *
 * <p>Toda la <b>presentación</b> está acá. Ninguna clase del modelo imprime
 * nada: devuelven datos ({@link ActaDeAlimentacion}) y este {@code main}
 * decide cómo mostrarlos. Eso es lo que permite testear el modelo sin mirar la
 * consola.
 *
 * <p>El "día" ya no se simula: la cuota de comidas (regla R5) se controla
 * contra el historial de raciones, que registra la fecha <b>real</b> de cada
 * una. Por eso este programa muestra un solo día — el de hoy — con varias
 * rondas.
 *
 * <p>Las reglas de negocio están documentadas en {@code reglas-de-negocio.md}.
 */
public class ZoologicoMain {

    public static void main(String[] args) {

        AdministradorDeZoologico administradorDeZoologico = new AdministradorDeZoologico(new Zoologico("CABA", 4));

        //Guardo los leones
        administradorDeZoologico.getZoologico().getJaula(1).alojar(
                new Leon("Simba", 158.0, 190.0, 7),    // flaco y agresivo: rutina con encierro
                new Leon("Nala", 182.0, 185.0, 2)
        );
        administradorDeZoologico.getZoologico().getJaula(1).asignarCuidador( new CuidadorDeLeon("Marta", 101));

        //Guardo la jirafa
        administradorDeZoologico.getZoologico().getJaula(2).asignarCuidador( new CuidadorDeJirafa("Pedro", 102));
        administradorDeZoologico.getZoologico().getJaula(2).alojar(
                new Jirafa("Melman", 758.0, 800.0, 520)
        );

        //Guardo las ovejas
        administradorDeZoologico.getZoologico().getJaula(3).asignarCuidador( new CuidadorDeOveja("Lucía", 103));
        administradorDeZoologico.getZoologico().getJaula(3).alojar(
                new Oveja("Dolly", 68.0, 70.0),
                new Oveja("Shaun", 65.8, 70.0),       // apenas por debajo del umbral
                new Oveja("Perdida", 60.0, 70.0)
        );

        titulo(administradorDeZoologico.getZoologico().toString());
        mostrarEstado(administradorDeZoologico.getZoologico());

        // ---------------------------------------------------------------
        //  El polimorfismo, a la vista
        // ---------------------------------------------------------------
        titulo("HOY — tres rondas de alimentación");
        for (int ronda = 1; ronda <= 3; ronda++) {
            subtitulo("Ronda " + ronda);
            mostrarActas(administradorDeZoologico.hacerRondaDeAlimentacion());
        }

        mostrarEstado(administradorDeZoologico.getZoologico());

        subtitulo("Con peso bajo al final del día");
        for (Animal animal : administradorDeZoologico.animalesConPesoBajo()) {
            System.out.println("   ! " + animal);
        }
    }

    // -------------------------------------------------------------------
    //  Presentación
    // -------------------------------------------------------------------

    private static void mostrarActas(ActaDeAlimentacion[] actas) {
        for (ActaDeAlimentacion acta : actas) {
            String linea = String.format("   %-6s -> %-8s %-9s %-25s peso %6.2f -> %6.2f",
                    acta.cuidador(), acta.animal(), "(" + acta.especie() + ")",
                    acta.resultado(), acta.pesoAntes(), acta.pesoDespues());
            System.out.println(linea);
            if (acta.seAlimento()) {
                for (String paso : acta.pasos()) {
                    System.out.println("            . " + paso);
                }
            }
        }
    }

    private static void mostrarEstado(Zoologico zoo) {
        System.out.println();
        for (int j = 1; j <= zoo.getCantidadDeJaulas(); j++) {
            Jaula jaula = zoo.getJaula(j);
            System.out.println("   " + jaula);
            for (Animal animal : jaula.getAnimales()) {
                System.out.println("        " + animal);
            }
        }
        System.out.println();
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=".repeat(72));
        System.out.println("  " + texto);
        System.out.println("=".repeat(72));
    }

    private static void subtitulo(String texto) {
        System.out.println();
        System.out.println("--- " + texto + " " + "-".repeat(Math.max(0, 66 - texto.length())));
    }

    public static void test1() {
        Animal animal;

        animal = new Leon("Simba", 158.0, 190.0, 7);
        Leon leon = (Leon) animal;

        if (animal instanceof Leon) {
            leon = (Leon) animal;
        }

        animal = new Jirafa("Tigre", 158.0, 190.0, 7);
        leon = (Leon) animal; //Error: ClassCastException

        if (animal instanceof Jirafa) {
            Jirafa jirafa = (Jirafa) animal;
        }
        if (animal instanceof Jirafa jirafa) {
            // Usar jirafa
            System.out.println(jirafa);
        }

    }
}

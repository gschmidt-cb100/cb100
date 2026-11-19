package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.tda;

/**
 * Caso de uso del TDA Agenda, contado de punta a punta: sirve para verificar
 * que no falta ni sobra ninguna operación.
 * Los horarios están en minutos desde las 0:00 (540 = 9:00, 1080 = 18:00).
 */
public class PrincipalAgenda {

    private static final int NUEVE = 9 * 60;
    private static final int DIECIOCHO = 18 * 60;

    public static void main(String[] args) {
        Agenda agenda = new Agenda(NUEVE, DIECIOCHO);
        System.out.println("Mayor intervalo libre (vacía): " + agenda.obtenerMayorIntervaloLibre());   // 540

        // Se reservan desordenados: la agenda los deja ordenados por inicio.
        agenda.reservar(new Turno("T-3", "Fisica", 840, 960));            // 14:00 a 16:00
        agenda.reservar(new Turno("T-1", "Clima", 540, 600));             //  9:00 a 10:00
        agenda.reservar(new Turno("T-2", "Genoma", 660, 780));            // 11:00 a 13:00
        System.out.println(agenda);

        try {
            agenda.reservar(new Turno("T-4", "Quimica", 720, 800));       // pisa a T-2
        } catch (IllegalStateException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }
        try {
            agenda.reservar(new Turno("T-5", "Quimica", 480, 560));       // empieza antes de la apertura
        } catch (IllegalArgumentException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }

        System.out.println("¿Libre de 10 a 11?: " + agenda.estaLibre(600, 660));                    // true
        System.out.println("¿Libre de 10 a 11:30?: " + agenda.estaLibre(600, 690));                 // false
        System.out.println("Primer inicio para 60 min: " + agenda.obtenerPrimerInicioLibre(60));    // 600
        System.out.println("Primer inicio para 90 min: " + agenda.obtenerPrimerInicioLibre(90));    // 960
        System.out.println("Primer inicio para 200 min: " + agenda.obtenerPrimerInicioLibre(200));  // null
        System.out.println("Mayor intervalo libre: " + agenda.obtenerMayorIntervaloLibre());        // 120
        System.out.println("Ocupación: " + agenda.obtenerPorcentajeDeOcupacion() + " %");           // 55.55

        Agenda mantenimiento = new Agenda(NUEVE, DIECIOCHO);
        mantenimiento.reservar(new Turno("M-1", "Backup", 590, 670));     // choca con T-1 y con T-2
        mantenimiento.reservar(new Turno("M-2", "Limpieza", 1000, 1030)); // no choca con nadie

        Turno[] enConflicto = agenda.obtenerTurnosEnConflicto(mantenimiento);
        System.out.println("En conflicto con mantenimiento: " + enConflicto.length);                // 2
        for (Turno turno : enConflicto) {
            System.out.println("  " + turno);                                                       // T-1, T-2
        }

        try {
            agenda.fusionar(mantenimiento);
        } catch (IllegalStateException e) {
            System.out.println("OK, no se puede: " + e.getMessage());
        }

        mantenimiento.cancelar("M-1");
        Agenda fusion = agenda.fusionar(mantenimiento);
        System.out.println(fusion);                                       // T-1, T-2, T-3, M-2
    }
}

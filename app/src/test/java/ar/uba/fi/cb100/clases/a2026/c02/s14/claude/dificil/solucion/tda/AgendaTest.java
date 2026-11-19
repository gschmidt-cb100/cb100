package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.tda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ejemplo de parcial 26/11 (difícil) - TDA Agenda")
class AgendaTest {

    private static final double EPSILON = 1e-9;

    private static final int APERTURA = 540;    //  9:00
    private static final int CIERRE = 1080;     // 18:00

    private static final Turno CLIMA = new Turno("T-1", "Clima", 540, 600);      //  9:00 a 10:00
    private static final Turno GENOMA = new Turno("T-2", "Genoma", 660, 780);    // 11:00 a 13:00
    private static final Turno FISICA = new Turno("T-3", "Fisica", 840, 960);    // 14:00 a 16:00

    /**
     * Horario 540..1080 con Clima [540,600), Genoma [660,780) y Fisica [840,960).
     * Huecos: [600,660) de 60, [780,840) de 60 y [960,1080) de 120. Reservados: 300 de 540.
     */
    private static Agenda agendaDeEjemplo() {
        Agenda agenda = new Agenda(APERTURA, CIERRE);
        agenda.reservar(FISICA);
        agenda.reservar(CLIMA);
        agenda.reservar(GENOMA);
        return agenda;
    }

    @Test
    @DisplayName("un turno valida sus datos")
    void turnoValida() {
        assertThrows(IllegalArgumentException.class, () -> new Turno(null, "Clima", 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno(" ", "Clima", 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno("T", null, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno("T", "", 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno("T", "Clima", -1, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno("T", "Clima", 10, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno("T", "Clima", 20, 10));
        assertThrows(IllegalArgumentException.class, () -> new Turno("T", "Clima", 10, Turno.MINUTOS_POR_DIA + 1));
    }

    @Test
    @DisplayName("dos turnos son iguales por código")
    void igualdadDeTurnos() {
        assertEquals(CLIMA, new Turno("T-1", "Otro", 0, 5));
        assertEquals(CLIMA.hashCode(), new Turno("T-1", "Otro", 0, 5).hashCode());
        assertNotEquals(CLIMA, new Turno("T-9", "Clima", 540, 600));
    }

    @Test
    @DisplayName("dos turnos están en conflicto si comparten algún minuto; pegados no")
    void conflictoEntreTurnos() {
        Turno base = new Turno("A", "x", 100, 200);
        assertTrue(base.estaEnConflictoCon(new Turno("B", "x", 150, 250)));   // se pisan al final
        assertTrue(base.estaEnConflictoCon(new Turno("B", "x", 50, 101)));    // se pisan al principio
        assertTrue(base.estaEnConflictoCon(new Turno("B", "x", 120, 130)));   // uno adentro del otro
        assertTrue(base.estaEnConflictoCon(new Turno("B", "x", 50, 300)));    // uno contiene al otro
        assertFalse(base.estaEnConflictoCon(new Turno("B", "x", 200, 300)));  // pegado después
        assertFalse(base.estaEnConflictoCon(new Turno("B", "x", 0, 100)));    // pegado antes
        assertEquals(100, base.getDuracion());
    }

    @Test
    @DisplayName("una agenda valida su horario")
    void agendaValida() {
        assertThrows(IllegalArgumentException.class, () -> new Agenda(-1, 100));
        assertThrows(IllegalArgumentException.class, () -> new Agenda(100, 100));
        assertThrows(IllegalArgumentException.class, () -> new Agenda(200, 100));
        assertThrows(IllegalArgumentException.class, () -> new Agenda(0, Turno.MINUTOS_POR_DIA + 1));
    }

    @Test
    @DisplayName("una agenda nace vacía: todo el horario libre")
    void agendaVacia() {
        Agenda agenda = new Agenda(APERTURA, CIERRE);
        assertTrue(agenda.estaVacia());
        assertEquals(0, agenda.getCantidadDeTurnos());
        assertEquals(0, agenda.getTurnos().length);
        assertTrue(agenda.estaLibre(APERTURA, CIERRE));
        assertEquals(APERTURA, agenda.obtenerPrimerInicioLibre(1));
        assertEquals(APERTURA, agenda.obtenerPrimerInicioLibre(CIERRE - APERTURA));
        assertNull(agenda.obtenerPrimerInicioLibre(CIERRE - APERTURA + 1));
        assertEquals(CIERRE - APERTURA, agenda.obtenerMayorIntervaloLibre());
        assertEquals(0, agenda.obtenerPorcentajeDeOcupacion(), EPSILON);
    }

    @Test
    @DisplayName("reservar deja los turnos ordenados por inicio, se reserven en el orden que sea")
    void reservarOrdena() {
        Agenda agenda = agendaDeEjemplo();
        assertEquals(3, agenda.getCantidadDeTurnos());
        assertArrayEquals(new Turno[] {CLIMA, GENOMA, FISICA}, agenda.getTurnos());
        assertSame(CLIMA, agenda.getTurno(1));
        assertSame(FISICA, agenda.getTurno(3));
    }

    @Test
    @DisplayName("se puede reservar un turno pegado a otro y uno que ocupa justo un hueco")
    void reservarPegado() {
        Agenda agenda = agendaDeEjemplo();
        Turno enElHueco = new Turno("T-4", "Quimica", 600, 660);
        agenda.reservar(enElHueco);
        assertArrayEquals(new Turno[] {CLIMA, enElHueco, GENOMA, FISICA}, agenda.getTurnos());
    }

    @Test
    @DisplayName("reservar rechaza un turno en conflicto con cualquiera de los existentes")
    void reservarEnConflicto() {
        Agenda agenda = agendaDeEjemplo();
        assertThrows(IllegalStateException.class, () -> agenda.reservar(new Turno("X", "x", 590, 620)));   // pisa el final de Clima
        assertThrows(IllegalStateException.class, () -> agenda.reservar(new Turno("X", "x", 640, 661)));   // pisa el inicio de Genoma
        assertThrows(IllegalStateException.class, () -> agenda.reservar(new Turno("X", "x", 700, 720)));   // adentro de Genoma
        assertThrows(IllegalStateException.class, () -> agenda.reservar(new Turno("X", "x", 600, 900)));   // contiene a Genoma
        assertThrows(IllegalStateException.class, () -> agenda.reservar(new Turno("X", "x", 840, 960)));   // igual a Fisica
        assertEquals(3, agenda.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("reservar rechaza null, un turno fuera del horario y un código repetido")
    void reservarInvalido() {
        Agenda agenda = agendaDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> agenda.reservar(null));
        assertThrows(IllegalArgumentException.class, () -> agenda.reservar(new Turno("X", "x", 500, 540)));    // antes de abrir
        assertThrows(IllegalArgumentException.class, () -> agenda.reservar(new Turno("X", "x", 1000, 1081)));  // después de cerrar
        assertThrows(IllegalArgumentException.class, () -> agenda.reservar(new Turno("T-1", "x", 600, 610)));  // código repetido
        assertEquals(3, agenda.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("la agenda no tiene máximo de turnos: el vector crece cuando se llena")
    void creceSinLimite() {
        Agenda agenda = new Agenda(0, Turno.MINUTOS_POR_DIA);
        int cantidad = 100;
        // Se reservan de atrás para adelante: cada uno entra en la primera posición.
        for (int i = cantidad - 1; i >= 0; i--) {
            agenda.reservar(new Turno("T-" + i, "x", i * 10, i * 10 + 5));
        }
        assertEquals(cantidad, agenda.getCantidadDeTurnos());
        for (int i = 0; i < cantidad; i++) {
            assertEquals(i * 10, agenda.getTurno(i + 1).getInicio());
        }
    }

    @Test
    @DisplayName("cancelar quita el turno, lo devuelve y libera su intervalo")
    void cancelar() {
        Agenda agenda = agendaDeEjemplo();
        assertSame(GENOMA, agenda.cancelar("T-2"));
        assertArrayEquals(new Turno[] {CLIMA, FISICA}, agenda.getTurnos());
        assertNull(agenda.buscarTurno("T-2"));
        assertTrue(agenda.estaLibre(600, 840));
        agenda.reservar(GENOMA);                                             // se puede volver a reservar
        assertArrayEquals(new Turno[] {CLIMA, GENOMA, FISICA}, agenda.getTurnos());
    }

    @Test
    @DisplayName("cancelar el primero y el último")
    void cancelarLosExtremos() {
        Agenda agenda = agendaDeEjemplo();
        agenda.cancelar("T-1");
        agenda.cancelar("T-3");
        assertArrayEquals(new Turno[] {GENOMA}, agenda.getTurnos());
        agenda.cancelar("T-2");
        assertTrue(agenda.estaVacia());
    }

    @Test
    @DisplayName("cancelar rechaza null y un código que no está")
    void cancelarInvalido() {
        Agenda agenda = agendaDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> agenda.cancelar(null));
        assertThrows(IllegalArgumentException.class, () -> agenda.cancelar("T-9"));
        assertEquals(3, agenda.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("estaLibre: huecos, bordes pegados y superposiciones")
    void estaLibre() {
        Agenda agenda = agendaDeEjemplo();
        assertTrue(agenda.estaLibre(600, 660));       // el hueco exacto
        assertTrue(agenda.estaLibre(610, 650));       // adentro del hueco
        assertTrue(agenda.estaLibre(960, 1080));      // después del último, hasta el cierre
        assertFalse(agenda.estaLibre(599, 660));      // pisa un minuto de Clima
        assertFalse(agenda.estaLibre(600, 661));      // pisa un minuto de Genoma
        assertFalse(agenda.estaLibre(540, 541));      // adentro de Clima
        assertFalse(agenda.estaLibre(600, 1080));     // contiene a Genoma y a Fisica
        assertFalse(agenda.estaLibre(540, 1080));     // todo el horario
    }

    @Test
    @DisplayName("estaLibre rechaza un intervalo vacío, invertido o fuera del horario")
    void estaLibreInvalido() {
        Agenda agenda = agendaDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> agenda.estaLibre(600, 600));
        assertThrows(IllegalArgumentException.class, () -> agenda.estaLibre(650, 600));
        assertThrows(IllegalArgumentException.class, () -> agenda.estaLibre(500, 600));
        assertThrows(IllegalArgumentException.class, () -> agenda.estaLibre(1000, 1100));
    }

    @Test
    @DisplayName("el primer inicio libre es el del primer hueco donde entra la duración")
    void primerInicioLibre() {
        Agenda agenda = agendaDeEjemplo();
        assertEquals(600, agenda.obtenerPrimerInicioLibre(1));
        assertEquals(600, agenda.obtenerPrimerInicioLibre(60));       // entra justo
        assertEquals(960, agenda.obtenerPrimerInicioLibre(61));       // sólo entra en el último hueco
        assertEquals(960, agenda.obtenerPrimerInicioLibre(120));
        assertNull(agenda.obtenerPrimerInicioLibre(121));             // no entra en ninguno
        assertThrows(IllegalArgumentException.class, () -> agenda.obtenerPrimerInicioLibre(0));
    }

    @Test
    @DisplayName("si hay lugar antes del primer turno, el primer inicio libre es la apertura")
    void primerInicioLibreEnLaApertura() {
        Agenda agenda = new Agenda(APERTURA, CIERRE);
        agenda.reservar(GENOMA);
        assertEquals(APERTURA, agenda.obtenerPrimerInicioLibre(120));
        assertEquals(780, agenda.obtenerPrimerInicioLibre(121));
    }

    @Test
    @DisplayName("el mayor intervalo libre mira también antes del primero y después del último")
    void mayorIntervaloLibre() {
        assertEquals(120, agendaDeEjemplo().obtenerMayorIntervaloLibre());   // [960, 1080)

        Agenda alFinal = new Agenda(APERTURA, CIERRE);
        alFinal.reservar(new Turno("Z", "x", 1000, 1080));
        assertEquals(460, alFinal.obtenerMayorIntervaloLibre());             // [540, 1000)

        Agenda completa = new Agenda(APERTURA, CIERRE);
        completa.reservar(new Turno("A", "x", 540, 800));
        completa.reservar(new Turno("B", "x", 800, 1080));
        assertEquals(0, completa.obtenerMayorIntervaloLibre());
        assertNull(completa.obtenerPrimerInicioLibre(1));
        assertEquals(100, completa.obtenerPorcentajeDeOcupacion(), EPSILON);
    }

    @Test
    @DisplayName("el porcentaje de ocupación es minutos reservados sobre minutos del horario")
    void porcentajeDeOcupacion() {
        Agenda agenda = agendaDeEjemplo();
        assertEquals(300, agenda.getMinutosReservados());
        assertEquals(300 * 100.0 / 540, agenda.obtenerPorcentajeDeOcupacion(), EPSILON);
    }

    @Test
    @DisplayName("turnos en conflicto: devuelve los de esta agenda, una vez cada uno y en orden")
    void turnosEnConflicto() {
        Agenda agenda = agendaDeEjemplo();
        Agenda otra = new Agenda(APERTURA, CIERRE);
        otra.reservar(new Turno("M-1", "Backup", 590, 670));       // choca con Clima y con Genoma
        otra.reservar(new Turno("M-2", "Antivirus", 700, 710));    // choca con Genoma (otra vez)
        otra.reservar(new Turno("M-3", "Limpieza", 1000, 1030));   // no choca con nadie

        assertArrayEquals(new Turno[] {CLIMA, GENOMA}, agenda.obtenerTurnosEnConflicto(otra));

        // No es simétrico: desde la otra agenda se devuelven SUS turnos.
        Turno[] deLaOtra = otra.obtenerTurnosEnConflicto(agenda);
        assertEquals(2, deLaOtra.length);
        assertEquals("M-1", deLaOtra[0].getCodigo());
        assertEquals("M-2", deLaOtra[1].getCodigo());
    }

    @Test
    @DisplayName("turnos en conflicto: sin choques, con agendas vacías y con turnos pegados")
    void turnosEnConflictoSinChoques() {
        Agenda agenda = agendaDeEjemplo();
        Agenda vacia = new Agenda(APERTURA, CIERRE);
        assertEquals(0, agenda.obtenerTurnosEnConflicto(vacia).length);
        assertEquals(0, vacia.obtenerTurnosEnConflicto(agenda).length);

        Agenda pegada = new Agenda(APERTURA, CIERRE);
        pegada.reservar(new Turno("P-1", "x", 600, 660));          // justo entre Clima y Genoma
        pegada.reservar(new Turno("P-2", "x", 960, 1080));         // justo después de Fisica
        assertEquals(0, agenda.obtenerTurnosEnConflicto(pegada).length);

        assertThrows(IllegalArgumentException.class, () -> agenda.obtenerTurnosEnConflicto(null));
    }

    @Test
    @DisplayName("turnos en conflicto: un turno largo de la otra agenda choca con todos")
    void turnosEnConflictoConUnTurnoLargo() {
        Agenda agenda = agendaDeEjemplo();
        Agenda otra = new Agenda(APERTURA, CIERRE);
        otra.reservar(new Turno("L", "Auditoria", 540, 1080));
        assertArrayEquals(new Turno[] {CLIMA, GENOMA, FISICA}, agenda.obtenerTurnosEnConflicto(otra));
        assertEquals(1, otra.obtenerTurnosEnConflicto(agenda).length);
    }

    @Test
    @DisplayName("fusionar intercala los turnos de las dos agendas en una nueva")
    void fusionar() {
        Agenda agenda = agendaDeEjemplo();
        Agenda otra = new Agenda(480, 1020);
        Turno temprano = new Turno("M-0", "Arranque", 480, 540);
        Turno intermedio = new Turno("M-1", "Backup", 600, 660);
        Turno tardio = new Turno("M-2", "Limpieza", 960, 1020);
        otra.reservar(tardio);
        otra.reservar(temprano);
        otra.reservar(intermedio);

        Agenda fusion = agenda.fusionar(otra);

        assertArrayEquals(new Turno[] {temprano, CLIMA, intermedio, GENOMA, FISICA, tardio}, fusion.getTurnos());
        assertEquals(480, fusion.getApertura());        // abre con la que abre antes
        assertEquals(1080, fusion.getCierre());         // cierra con la que cierra después
        assertEquals(3, agenda.getCantidadDeTurnos());  // las originales no cambian
        assertEquals(3, otra.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("la agenda fusionada es independiente y sigue funcionando")
    void laFusionEsIndependiente() {
        Agenda agenda = agendaDeEjemplo();
        Agenda fusion = agenda.fusionar(new Agenda(APERTURA, CIERRE));
        assertArrayEquals(agenda.getTurnos(), fusion.getTurnos());

        fusion.reservar(new Turno("N-1", "Nuevo", 600, 660));
        fusion.reservar(new Turno("N-2", "Nuevo", 780, 840));
        fusion.cancelar("T-1");
        assertEquals(4, fusion.getCantidadDeTurnos());
        assertEquals(3, agenda.getCantidadDeTurnos());
        assertFalse(fusion.estaLibre(600, 660));
        assertTrue(agenda.estaLibre(600, 660));
    }

    @Test
    @DisplayName("fusionar dos agendas vacías da una agenda vacía que acepta turnos")
    void fusionarVacias() {
        Agenda fusion = new Agenda(APERTURA, CIERRE).fusionar(new Agenda(APERTURA, CIERRE));
        assertTrue(fusion.estaVacia());
        fusion.reservar(CLIMA);
        assertEquals(1, fusion.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("fusionar rechaza agendas con turnos en conflicto y no modifica ninguna")
    void fusionarConConflicto() {
        Agenda agenda = agendaDeEjemplo();
        Agenda otra = new Agenda(APERTURA, CIERRE);
        otra.reservar(new Turno("M-1", "Backup", 600, 661));       // pisa un minuto de Genoma
        assertThrows(IllegalStateException.class, () -> agenda.fusionar(otra));
        assertThrows(IllegalStateException.class, () -> otra.fusionar(agenda));

        Agenda mismoInicio = new Agenda(APERTURA, CIERRE);
        mismoInicio.reservar(new Turno("M-2", "Backup", 840, 850)); // empieza junto con Fisica
        assertThrows(IllegalStateException.class, () -> agenda.fusionar(mismoInicio));

        assertThrows(IllegalArgumentException.class, () -> agenda.fusionar(null));
        assertEquals(3, agenda.getCantidadDeTurnos());
        assertEquals(1, otra.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("getTurnos devuelve una copia: modificarla no afecta a la agenda")
    void getTurnosDevuelveUnaCopia() {
        Agenda agenda = agendaDeEjemplo();
        Turno[] copia = agenda.getTurnos();
        copia[0] = null;
        assertSame(CLIMA, agenda.getTurno(1));
        assertEquals(3, agenda.getCantidadDeTurnos());
    }

    @Test
    @DisplayName("getTurno valida la posición y buscarTurno devuelve null si no está")
    void consultasPorPosicionYCodigo() {
        Agenda agenda = agendaDeEjemplo();
        assertThrows(IllegalArgumentException.class, () -> agenda.getTurno(0));
        assertThrows(IllegalArgumentException.class, () -> agenda.getTurno(4));
        assertSame(GENOMA, agenda.buscarTurno("T-2"));
        assertNull(agenda.buscarTurno("T-9"));
        assertThrows(IllegalArgumentException.class, () -> agenda.buscarTurno(null));
    }

    @Test
    @DisplayName("el toString muestra todos los turnos")
    void texto() {
        String texto = agendaDeEjemplo().toString();
        assertTrue(texto.contains("T-1"));
        assertTrue(texto.contains("T-2"));
        assertTrue(texto.contains("T-3"));
    }
}

package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e02;

import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

/**
 * e02: próximo turno de una agenda de horarios. {@code ceilingEntry(h)}
 * devuelve la entrada de menor clave que sea mayor o igual a {@code h},
 * exactamente lo que necesitamos para "el próximo turno desde tal hora".
 */
public final class ProximoTurno {

    private ProximoTurno() {
    }

    /**
     * Devuelve el nombre del paciente del próximo turno a partir de {@code hora}
     * (inclusive), o {@code null} si no queda ningún turno de ahí en adelante.
     *
     * @param turnos mapa horario → nombre del paciente
     * @param hora   hora desde la que se busca
     * @return nombre del próximo paciente o {@code null}
     */
    public static String proximoDesde(TreeMap<LocalTime, String> turnos, LocalTime hora) {
        Map.Entry<LocalTime, String> entrada = turnos.ceilingEntry(hora);
        return entrada == null ? null : entrada.getValue();
    }

    public static void main(String[] args) {
        TreeMap<LocalTime, String> turnos = new TreeMap<>();
        turnos.put(LocalTime.of(9, 0), "García");
        turnos.put(LocalTime.of(10, 30), "López");
        turnos.put(LocalTime.of(14, 0), "Suárez");
        System.out.println("Turnos: " + turnos);
        System.out.println("Próximo desde las 10:00: " + proximoDesde(turnos, LocalTime.of(10, 0)));
        System.out.println("Próximo desde las 15:00: " + proximoDesde(turnos, LocalTime.of(15, 0)));
    }
}

package ar.uba.fi.cb100.material.i01_intro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Manejo de fechas y horas con la API moderna {@code java.time} (Java 8+).
 * Sus objetos son inmutables: cada operación devuelve uno nuevo.
 */
public class Fechas {

    public static void main(String[] args) {
        LocalDate hoy = LocalDate.of(2026, 3, 15);        // fijo para el ejemplo
        LocalDate finCuatri = LocalDate.of(2026, 7, 4);

        System.out.println("hoy: " + hoy);
        System.out.println("mañana: " + hoy.plusDays(1));
        System.out.println("día de semana: " + hoy.getDayOfWeek());

        long dias = ChronoUnit.DAYS.between(hoy, finCuatri);
        System.out.println("faltan " + dias + " días para fin de cuatrimestre");

        Period p = Period.between(hoy, finCuatri);
        System.out.println("o sea " + p.getMonths() + " meses y "
                + p.getDays() + " días");

        LocalDateTime ahora = LocalDateTime.of(2026, 3, 15, 18, 30);
        var fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("formateado: " + ahora.format(fmt));
    }
}

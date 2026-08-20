package ar.uba.fi.cb100.material.i01_intro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * EJEMPLO INTEGRADOR de la Unidad 1.
 * <p>
 * Combina muchas de las cosas vistas: un {@code record} con validaciones,
 * arreglos, ciclos, {@code Math}, formateo con {@code String.format},
 * {@code StringBuilder}, escritura de archivos y manejo de excepciones.
 * Genera el boletín de un curso y lo guarda en un archivo.
 */
public class BoletinDeNotas {

    /** Un alumno con su nombre y sus notas. Valida los datos al construirse. */
    record Alumno(String nombre, int[] notas) {
        Alumno {
            Objects.requireNonNull(nombre, "el nombre no puede ser null");
            if (notas.length == 0) {
                throw new IllegalArgumentException("el alumno " + nombre + " no tiene notas");
            }
            for (int n : notas) {
                if (n < 0 || n > 10) {
                    throw new IllegalArgumentException("nota inválida (" + n + ") de " + nombre);
                }
            }
        }

        double promedio() {
            int suma = 0;
            for (int n : notas) {
                suma += n;
            }
            return (double) suma / notas.length;
        }

        String condicion() {
            double p = promedio();
            return p >= 8 ? "Promociona" : p >= 4 ? "Aprueba" : "Recursa";
        }
    }

    public static void main(String[] args) throws IOException {
        Alumno[] curso = {
                new Alumno("Ada Lovelace", new int[]{10, 9, 8}),
                new Alumno("Alan Turing",  new int[]{7, 6, 5}),
                new Alumno("Grace Hopper", new int[]{4, 3, 5}),
        };

        // Armamos el reporte de a poco con un StringBuilder.
        StringBuilder reporte = new StringBuilder();
        reporte.append(String.format("%-16s | %-8s | %s%n", "Alumno", "Promedio", "Condición"));
        reporte.append("-".repeat(42)).append('\n');

        for (Alumno a : curso) {
            double prom = Math.round(a.promedio() * 100.0) / 100.0;   // redondeo a 2 decimales
            reporte.append(String.format("%-16s | %8.2f | %s%n", a.nombre(), prom, a.condicion()));
        }

        System.out.print(reporte);

        // Guardamos el boletín en un archivo.
        Path salida = Files.createTempFile("boletin", ".txt");
        Files.writeString(salida, reporte.toString());
        System.out.println("Boletín guardado en: " + salida);
        Files.deleteIfExists(salida);
    }
}

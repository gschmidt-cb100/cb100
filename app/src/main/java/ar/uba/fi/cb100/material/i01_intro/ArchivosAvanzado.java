package ar.uba.fi.cb100.material.i01_intro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Operaciones más completas con archivos y directorios usando {@link Files}:
 * crear, escribir, anexar, leer, tamaño, copiar, listar y borrar.
 */
public class ArchivosAvanzado {

    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("cb100dir");
        Path archivo = dir.resolve("notas.txt");

        Files.writeString(archivo, "10\n8\n7\n");
        System.out.println("existe: " + Files.exists(archivo));
        System.out.println("tamaño (bytes): " + Files.size(archivo));

        // Anexar al final (append) sin borrar lo anterior.
        Files.writeString(archivo, "9\n", StandardOpenOption.APPEND);
        List<String> lineas = Files.readAllLines(archivo);
        System.out.println("líneas: " + lineas);

        // Copiar el archivo.
        Path copia = dir.resolve("copia.txt");
        Files.copy(archivo, copia, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("existe la copia: " + Files.exists(copia));

        // Listar el contenido del directorio (Files.list devuelve un Stream).
        System.out.println("contenido del directorio:");
        try (var stream = Files.list(dir)) {
            stream.forEach(p -> System.out.println("  " + p.getFileName()));
        }

        // Limpiar todo.
        Files.deleteIfExists(archivo);
        Files.deleteIfExists(copia);
        Files.deleteIfExists(dir);
    }
}

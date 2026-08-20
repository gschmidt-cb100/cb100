package ar.uba.fi.cb100.material.i01_intro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Lectura y escritura de archivos de texto con la API {@code java.nio.file.Files}.
 */
public class ArchivosDemo {

    public static void main(String[] args) throws IOException {
        Path archivo = Files.createTempFile("cb100", ".txt");

        // Escribir todo el contenido de una vez.
        Files.writeString(archivo, "línea 1\nlínea 2\nlínea 3\n");

        // Leer todo el contenido como un solo String.
        String contenido = Files.readString(archivo);
        System.out.println("Contenido:\n" + contenido);

        // Leer línea por línea.
        List<String> lineas = Files.readAllLines(archivo);
        System.out.println("Cantidad de líneas: " + lineas.size());

        Files.deleteIfExists(archivo);   // limpiar el archivo temporal
    }
}

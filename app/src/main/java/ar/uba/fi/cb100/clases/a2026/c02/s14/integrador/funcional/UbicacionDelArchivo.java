package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Dónde está el Excel de ejemplo. El programa se puede ejecutar parado en la
 * raíz del proyecto (IntelliJ) o dentro del módulo {@code app} (Gradle): se
 * prueban las dos ubicaciones.
 */
public final class UbicacionDelArchivo {

    public static final String NOMBRE = "personas.xlsx";

    private static final String CARPETA =
            "src/main/java/ar/uba/fi/cb100/clases/a2026/c02/s14/integrador/archivos";

    private UbicacionDelArchivo() {
    }

    /** post: devuelve la ruta del archivo de ejemplo, exista o no todavía. */
    public static Path getArchivoDeEjemplo() {
        Path desdeLaRaiz = Path.of("app", CARPETA);
        Path carpeta = Files.isDirectory(Path.of("app")) ? desdeLaRaiz : Path.of(CARPETA);
        return carpeta.resolve(NOMBRE);
    }
}

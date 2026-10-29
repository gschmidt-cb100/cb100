package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Encuentra carpetas de imágenes y sonidos que están junto a los fuentes.
 *
 * <p>Según desde dónde se corra el programa (IntelliJ, gradle run, la carpeta
 * app), la carpeta de trabajo cambia. Se prueba desde la raíz del proyecto y
 * desde app/, igual que EjemploDeJuegoVisual de la librería.
 */
public class Recursos {

    private static final String[] PREFIJOS = {
            "app/src/main/java/",
            "src/main/java/",
            "",
    };

    private Recursos() {
        // sólo métodos de clase
    }

    /**
     * @param rutaDesdeJava ruta de la carpeta desde src/main/java, por ejemplo
     *                      "ar/uba/fi/cb100/clases/a2026/c02/s11/juego/tateti/imagenes"
     * @param archivoQueDebeTener un archivo que confirma que es la carpeta correcta
     * @return la carpeta encontrada, o null si no está
     */
    public static Path buscarCarpeta(String rutaDesdeJava, String archivoQueDebeTener) {
        for (String prefijo : PREFIJOS) {
            Path carpeta = Path.of(prefijo + rutaDesdeJava);
            if (Files.isRegularFile(carpeta.resolve(archivoQueDebeTener))) {
                return carpeta;
            }
        }
        return null;
    }
}

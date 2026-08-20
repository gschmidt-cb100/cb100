package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Arma un mini-repo en un directorio temporal con violaciones conocidas y
 * verifica que el robot las detecta todas (y solo esas).
 */
public class VerificadorDeRepoTest {

    // La marca partida en dos para que este archivo no viole su propia regla.
    private static final String MARCA = "TO" + "DO";

    private Path raizMain;
    private Path raizTest;

    @BeforeEach
    public void armarMiniRepo() throws IOException {
        Path repo = Files.createTempDirectory("mini-repo");
        raizMain = repo.resolve("main");
        raizTest = repo.resolve("test");

        // Clase con test y sin pendientes: no debe generar violaciones.
        escribir(raizMain.resolve("app/Sana.java"), "public class Sana {}");
        escribir(raizTest.resolve("app/SanaTest.java"), "public class SanaTest {}");

        // Violacion 1: clase sin su test espejo.
        escribir(raizMain.resolve("app/Huerfana.java"), "public class Huerfana {}");

        // Violacion 2: fuente con pendiente comiteado.
        escribir(raizMain.resolve("app/Pendiente.java"),
                "public class Pendiente {} // " + MARCA + ": terminar");
        escribir(raizTest.resolve("app/PendienteTest.java"), "public class PendienteTest {}");

        // Violacion 3: directorio de paquete con mayusculas.
        escribir(raizMain.resolve("MiPaquete/Otra.java"), "public class Otra {}");
        escribir(raizTest.resolve("MiPaquete/OtraTest.java"), "public class OtraTest {}");

        // Excluida: bajo i01_teorico no se exige test.
        escribir(raizMain.resolve("i01_teorico/Apunte.java"), "public class Apunte {}");
    }

    private static void escribir(Path archivo, String contenido) throws IOException {
        Files.createDirectories(archivo.getParent());
        Files.writeString(archivo, contenido);
    }

    @Test
    @DisplayName("detecta exactamente las tres violaciones plantadas")
    public void detectaLasViolacionesPlantadas() {
        List<String> violaciones = new VerificadorDeRepo(raizMain, raizTest).verificar();

        assertEquals(3, violaciones.size(), "violaciones: " + violaciones);
        assertTrue(violaciones.stream().anyMatch(v ->
                v.startsWith("SIN TEST") && v.contains("Huerfana.java")));
        assertTrue(violaciones.stream().anyMatch(v ->
                v.startsWith("PENDIENTE") && v.contains("Pendiente.java")));
        assertTrue(violaciones.stream().anyMatch(v ->
                v.startsWith("PAQUETE CON MAYUSCULAS") && v.contains("MiPaquete")));
    }

    @Test
    @DisplayName("no acusa a la clase sana ni exige test bajo i01_teorico")
    public void noHayFalsosPositivos() {
        List<String> violaciones = new VerificadorDeRepo(raizMain, raizTest).verificar();

        assertTrue(violaciones.stream().noneMatch(v -> v.contains("Sana.java")));
        assertTrue(violaciones.stream().noneMatch(v -> v.contains("Apunte.java")));
    }

    @Test
    @DisplayName("un repo sin problemas devuelve lista vacia")
    public void repoSano() throws IOException {
        Path repo = Files.createTempDirectory("repo-sano");
        Path main = repo.resolve("main");
        Path test = repo.resolve("test");
        escribir(main.resolve("app/Unica.java"), "public class Unica {}");
        escribir(test.resolve("app/UnicaTest.java"), "public class UnicaTest {}");

        assertTrue(new VerificadorDeRepo(main, test).verificar().isEmpty());
    }
}

package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e05;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * TECNICA: {@code Files.walk} + STREAMS COMO ROBOT DE INTEGRACION CONTINUA.
 *
 * En un equipo real nadie revisa a mano que cada clase tenga su test o que
 * no queden pendientes olvidados: lo hace un robot de CI en cada push. Este
 * verificador es ese robot en miniatura: {@code Files.walk} convierte el
 * arbol de archivos en un stream de rutas (el recorrido en profundidad de
 * la U8, servido como stream perezoso de la U12) y cada regla es un
 * pipeline filter/map que junta violaciones:
 * <ol>
 *   <li>todo {@code *.java} bajo la raiz main (salvo los paquetes
 *       {@code i01_teorico}, que son apuntes sin logica testeable) tiene su
 *       {@code *Test.java} en la MISMA ruta relativa bajo la raiz test;</li>
 *   <li>ningun fuente contiene la marca de pendiente ("TO" + "DO"): un
 *       pendiente comiteado es un olvido garantizado;</li>
 *   <li>los directorios de paquete van en minusculas (convencion de Java
 *       que ademas evita choques entre sistemas de archivos).</li>
 * </ol>
 *
 * Las raices main/test son parametros: el mismo robot sirve para el repo
 * real y para el mini-repo del test (un directorio temporal con violaciones
 * plantadas a proposito — asi se testea a un verificador).
 */
public final class VerificadorDeRepo {

    /** La marca, partida para que este propio archivo no se delate solo. */
    private static final String MARCA_PENDIENTE = "TO" + "DO";
    private static final String PAQUETE_EXCLUIDO = "i01_teorico";

    private final Path raizMain;
    private final Path raizTest;

    public VerificadorDeRepo(Path raizMain, Path raizTest) {
        this.raizMain = raizMain;
        this.raizTest = raizTest;
    }

    /** Corre las tres reglas y devuelve la lista de violaciones (vacia = repo sano). */
    public List<String> verificar() {
        List<String> violaciones = new ArrayList<>();
        violaciones.addAll(clasesSinTest());
        violaciones.addAll(fuentesConPendientes());
        violaciones.addAll(directoriosConMayusculas());
        return violaciones;
    }

    /** Regla 1: cada Foo.java de main tiene un FooTest.java espejo en test. */
    private List<String> clasesSinTest() {
        try (Stream<Path> archivos = Files.walk(raizMain)) {
            return archivos
                    .filter(VerificadorDeRepo::esFuenteJava)
                    .filter(fuente -> !estaExcluido(raizMain.relativize(fuente)))
                    .filter(fuente -> {
                        Path relativa = raizMain.relativize(fuente);
                        String nombreTest = relativa.getFileName().toString()
                                .replace(".java", "Test.java");
                        Path esperado = raizTest.resolve(relativa).resolveSibling(nombreTest);
                        return !Files.exists(esperado);
                    })
                    .map(fuente -> "SIN TEST: " + raizMain.relativize(fuente))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Regla 2: ningun fuente de main contiene la marca de pendiente. */
    private List<String> fuentesConPendientes() {
        try (Stream<Path> archivos = Files.walk(raizMain)) {
            return archivos
                    .filter(VerificadorDeRepo::esFuenteJava)
                    .filter(fuente -> {
                        try {
                            return Files.readString(fuente).contains(MARCA_PENDIENTE);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .map(fuente -> "PENDIENTE (" + MARCA_PENDIENTE + "): "
                            + raizMain.relativize(fuente))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Regla 3: los nombres de directorio de paquete van en minusculas. */
    private List<String> directoriosConMayusculas() {
        try (Stream<Path> rutas = Files.walk(raizMain)) {
            return rutas
                    .filter(Files::isDirectory)
                    .filter(directorio -> !directorio.equals(raizMain))
                    .filter(directorio -> {
                        String nombre = directorio.getFileName().toString();
                        return !nombre.equals(nombre.toLowerCase());
                    })
                    .map(directorio -> "PAQUETE CON MAYUSCULAS: " + raizMain.relativize(directorio))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static boolean esFuenteJava(Path ruta) {
        return Files.isRegularFile(ruta) && ruta.getFileName().toString().endsWith(".java");
    }

    private static boolean estaExcluido(Path relativa) {
        for (Path segmento : relativa) {
            if (segmento.toString().equals(PAQUETE_EXCLUIDO)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Corrida real sobre esta unidad del repo (desde la raiz del proyecto).
        Path base = Path.of("app/src");
        if (!Files.isDirectory(base)) {
            base = Path.of("cb100-codigo/app/src");        // por si se corre un nivel arriba
        }
        Path sufijo = Path.of("java/ar/uba/fi/cb100/guia/i12_profesional");
        VerificadorDeRepo robot = new VerificadorDeRepo(
                base.resolve("main").resolve(sufijo),
                base.resolve("test").resolve(sufijo));
        List<String> violaciones = robot.verificar();
        System.out.println("Violaciones en guia/i12_profesional: " + violaciones.size());
        violaciones.forEach(v -> System.out.println("  - " + v));
        if (violaciones.isEmpty()) {
            System.out.println("  (repo sano: el robot no encontro nada)");
        }
    }
}

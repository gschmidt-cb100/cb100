package ar.uba.fi.cb100.librerias.sonido;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * Cómo se usa {@link ReproductorDeSonido}. Usa los MP3 de prueba de la
 * carpeta {@code sonidos/} (paso, agua, tesoro y una música en bucle). Si no
 * la encuentra, fabrica dos tonos como WAV y sigue igual: el ejemplo corre en
 * cualquier máquina.
 *
 * <p>Se puede pasar por parámetro otro MP3 para usar de música:
 * <pre>
 *   ./gradlew run -PmainClass=...EjemploDeSonido --args="mi-tema.mp3"
 * </pre>
 *
 * En el juego real los efectos se cargan una vez al iniciar y se disparan
 * desde el modelo de eventos: cada vez que el héroe se mueve, "paso"; cuando
 * entra al agua, "agua"; cuando levanta algo, "tesoro".
 */
public class EjemploDeSonido {

    /** Dónde están los sonidos de prueba, relativo a la raíz del proyecto y al módulo app. */
    private static final String[] CARPETAS_DE_SONIDOS = {
            "app/src/main/java/ar/uba/fi/cb100/librerias/sonido/sonidos",
            "src/main/java/ar/uba/fi/cb100/librerias/sonido/sonidos",
    };

    public static void main(String[] args) throws Exception {
        Path carpeta = buscarCarpetaDeSonidos();
        Path paso, agua, tesoro, musica;
        if (carpeta != null) {
            System.out.println("Usando los MP3 de " + carpeta);
            paso = carpeta.resolve("paso.mp3");
            agua = carpeta.resolve("agua.mp3");
            tesoro = carpeta.resolve("tesoro.mp3");
            musica = carpeta.resolve("musica.mp3");
        } else {
            System.out.println("No encontré la carpeta de sonidos: fabrico tonos WAV de prueba.");
            Path temporal = Files.createTempDirectory("cb100-sonido");
            paso = generarTono(temporal.resolve("paso.wav"), 660, 0.12);
            agua = generarTono(temporal.resolve("agua.wav"), 220, 0.30);
            tesoro = generarTono(temporal.resolve("tesoro.wav"), 1320, 0.20);
            musica = null;
        }
        if (args.length > 0) {
            musica = Path.of(args[0]);
        }

        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            System.out.println("¿Hay audio en esta máquina? " + sonido.hayAudioDisponible());

            // 1. Cargar los efectos UNA vez, al iniciar el juego.
            sonido.cargarEfecto("paso", paso);
            sonido.cargarEfecto("agua", agua);
            sonido.cargarEfecto("tesoro", tesoro);

            // 2. Música de fondo, en bucle, más baja que los efectos.
            if (musica != null) {
                sonido.reproducirMusica(musica);
                sonido.setVolumenDeMusica(35);
                System.out.println("Música en bucle: " + musica.getFileName() + " al 35%");
            }

            // 3. Disparar efectos mientras el juego avanza.
            System.out.println("Cuatro pasos por piso...");
            for (int i = 0; i < 4; i++) {
                sonido.reproducirEfecto("paso", 300);
            }
            System.out.println("...dos por agua...");
            for (int i = 0; i < 2; i++) {
                sonido.reproducirEfecto("agua", 500);
            }
            System.out.println("...y levanta un tesoro.");
            sonido.reproducirEfecto("tesoro", 700);

            // 4. Volumen y silencio.
            System.out.println("Efectos al 25%: un paso apenas se escucha.");
            sonido.setVolumenDeEfectos(25);
            sonido.reproducirEfecto("paso", 400);

            System.out.println("Silenciado 1,5 s: la música y un tesoro, mudos.");
            sonido.silenciar();
            sonido.reproducirEfecto("tesoro", 1500);
            sonido.activarSonido();
            sonido.setVolumenDeEfectos(ReproductorDeSonido.VOLUMEN_INICIAL);

            if (sonido.hayMusicaSonando()) {
                System.out.println("Vuelve el sonido: la música sigue 4 segundos más...");
                Thread.sleep(4000);
            }
        }   // close() detiene la música y libera todo
        System.out.println("Listo.");
    }

    /** Busca la carpeta de sonidos desde la raíz del proyecto o desde app/. */
    private static Path buscarCarpetaDeSonidos() {
        for (String candidata : CARPETAS_DE_SONIDOS) {
            Path ruta = Path.of(candidata);
            if (Files.isDirectory(ruta) && Files.exists(ruta.resolve("paso.mp3"))) {
                return ruta;
            }
        }
        return null;
    }

    /**
     * Genera un WAV con un tono puro. Sirve para probar sin archivos. En el
     * juego real los sonidos se graban o se bajan; esto es sólo para el
     * ejemplo y los tests.
     */
    static Path generarTono(Path destino, double frecuenciaHz, double segundos) throws IOException {
        float tasa = 44100f;
        int muestras = (int) (tasa * segundos);
        byte[] datos = new byte[muestras * 2];           // 16 bits, mono
        for (int i = 0; i < muestras; i++) {
            double envolvente = 1.0 - (double) i / muestras;   // se apaga suave
            short valor = (short) (Math.sin(2 * Math.PI * frecuenciaHz * i / tasa) * 12000 * envolvente);
            datos[2 * i] = (byte) (valor & 0xff);
            datos[2 * i + 1] = (byte) ((valor >> 8) & 0xff);
        }
        AudioFormat formato = new AudioFormat(tasa, 16, 1, true, false);
        try (AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(datos), formato, muestras)) {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, destino.toFile());
        }
        return destino;
    }
}

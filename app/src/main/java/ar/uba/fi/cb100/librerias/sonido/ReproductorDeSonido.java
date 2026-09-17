package ar.uba.fi.cb100.librerias.sonido;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Reproduce efectos de sonido cortos y una música de fondo, con control de
 * volumen. Pensado para integrarse en un juego en tres líneas:
 *
 * <pre>{@code
 * ReproductorDeSonido sonido = new ReproductorDeSonido();
 * sonido.cargarEfecto("paso", Path.of("sonidos/paso.mp3"));
 * sonido.reproducirMusica(Path.of("sonidos/mazmorra.mp3"));
 * ...
 * sonido.reproducirEfecto("paso");        // cada vez que el héroe se mueve
 * sonido.setVolumenDeMusica(40);          // 0 a 100
 * ...
 * sonido.cerrar();                        // al salir del juego
 * }</pre>
 *
 * <h2>Efectos y música</h2>
 *
 * Los <b>efectos</b> (un paso, levantar algo, entrar al agua) se cargan una vez
 * con {@link #cargarEfecto} y quedan decodificados en memoria: reproducirlos
 * después es instantáneo, sin leer el disco. Se identifican por un nombre que
 * eligen ustedes.
 *
 * <p>La <b>música</b> suena en bucle hasta que se la detiene o se pone otra.
 * Hay una sola música a la vez.
 *
 * <p>Los archivos pueden ser <b>MP3</b> o <b>WAV</b>. El MP3 pesa unas diez
 * veces menos, por eso es el formato recomendado para el juego. Java no lo
 * decodifica solo: hace falta la dependencia {@code mp3spi} declarada en
 * {@code build.gradle}.
 *
 * <h2>Volumen</h2>
 *
 * Va de 0 (mudo) a 100 (máximo), separado para efectos y para música.
 * Internamente la placa de sonido trabaja en decibeles, que no son lineales:
 * la conversión está en {@link #decibelesPara(int)}, y es lo único con
 * matemática de esta clase.
 *
 * <h2>Si la máquina no tiene audio</h2>
 *
 * Una máquina virtual o un servidor pueden no tener placa de sonido. En ese
 * caso el reproductor avisa una vez por consola y después <b>no hace nada</b>:
 * el juego sigue funcionando en silencio, sin excepciones. Se puede consultar
 * con {@link #hayAudioDisponible()}.
 *
 * <p>Esta clase usa {@code java.util.HashMap}: es una librería de la cátedra,
 * una capa de borde, no parte del modelo del juego.
 */
public final class ReproductorDeSonido implements AutoCloseable {

    /** Volumen máximo. El mínimo es 0. */
    public static final int VOLUMEN_MAXIMO = 100;

    /** Volumen con el que arrancan los efectos y la música. */
    public static final int VOLUMEN_INICIAL = 80;

    /** Decibeles que se aplican con volumen 0: en la práctica, silencio. */
    static final float DECIBELES_DE_SILENCIO = -80.0f;

    private final Map<String, Clip> efectos = new HashMap<>();
    private final boolean hayAudio;
    private Clip musica;
    private int volumenDeEfectos = VOLUMEN_INICIAL;
    private int volumenDeMusica = VOLUMEN_INICIAL;
    private boolean silenciado = false;

    /**
     * Crea el reproductor y detecta si la máquina tiene audio. Si no lo tiene,
     * avisa una vez y el reproductor queda en modo silencioso.
     */
    public ReproductorDeSonido() {
        boolean disponible;
        try {
            Clip prueba = AudioSystem.getClip();
            prueba.close();
            disponible = true;
        } catch (LineUnavailableException | IllegalArgumentException | SecurityException e) {
            disponible = false;
            System.err.println("[ReproductorDeSonido] No hay dispositivo de audio: el juego sigue sin sonido.");
        }
        this.hayAudio = disponible;
    }

    // ==================================================================
    //  Efectos
    // ==================================================================

    /**
     * Carga un efecto y lo deja listo en memoria. Se hace una sola vez, al
     * iniciar el juego, no en cada reproducción.
     *
     * @param nombre  cómo lo van a llamar después: "paso", "agua", "tesoro"
     * @param archivo el {@code .mp3} o {@code .wav}
     * @throws IllegalArgumentException si el archivo no existe o el formato no
     *                                  se puede decodificar
     */
    public void cargarEfecto(String nombre, Path archivo) {
        validarNombre(nombre);
        Clip anterior = efectos.put(nombre, cargarClip(archivo));
        if (anterior != null) {
            anterior.close();                     // se reemplazó: liberar el viejo
        }
    }

    /**
     * Reproduce un efecto ya cargado. No bloquea: el juego sigue mientras
     * suena. Si el mismo efecto todavía estaba sonando, vuelve a empezar.
     *
     * @throws IllegalArgumentException si el nombre no fue cargado
     */
    public void reproducirEfecto(String nombre) {
        validarNombre(nombre);
        if (!efectos.containsKey(nombre)) {
            throw new IllegalArgumentException("No hay ningún efecto llamado \"" + nombre
                    + "\". Los cargados son: " + efectos.keySet());
        }
        Clip clip = efectos.get(nombre);
        if (clip == null) {
            return;                               // sin audio en la máquina
        }
        clip.stop();
        clip.setFramePosition(0);
        aplicarVolumen(clip, volumenDeEfectos);
        clip.start();
    }

    public void reproducirEfecto(String nombre, long milisegundos) {
        reproducirEfecto(nombre);
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
    /** @return true si ese nombre ya fue cargado. */
    public boolean tieneEfecto(String nombre) {
        return efectos.containsKey(nombre);
    }

    // ==================================================================
    //  Música
    // ==================================================================

    /**
     * Pone una música de fondo en bucle. Si había otra sonando, la reemplaza.
     *
     * <p>La música entera queda decodificada en memoria: unos 10 MB por minuto
     * de audio estéreo. Para un juego, un tema de 1 a 3 minutos en bucle es lo
     * razonable.
     *
     * @throws IllegalArgumentException si el archivo no existe o no se puede decodificar
     */
    public void reproducirMusica(Path archivo) {
        detenerMusica();
        musica = cargarClip(archivo);
        if (musica == null) {
            return;                               // sin audio en la máquina
        }
        aplicarVolumen(musica, volumenDeMusica);
        musica.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** Detiene la música y libera su memoria. No pasa nada si no había. */
    public void detenerMusica() {
        if (musica != null) {
            musica.stop();
            musica.close();
            musica = null;
        }
    }

    /**
     * @return true si hay una música puesta. Como suena en bucle, sólo deja
     *         de sonar cuando se la detiene o se la reemplaza.
     *
     * <p>No se consulta {@code Clip.isRunning()}: la placa arranca en otro
     * hilo y durante unos milisegundos después de {@code loop()} todavía
     * contesta {@code false}. El estado que importa es el nuestro.
     */
    public boolean hayMusicaSonando() {
        return musica != null;
    }

    // ==================================================================
    //  Volumen
    // ==================================================================

    /** @param volumen de 0 (mudo) a 100 (máximo) */
    public void setVolumenDeEfectos(int volumen) {
        validarVolumen(volumen);
        this.volumenDeEfectos = volumen;
        // Se aplica en la próxima reproducción: un efecto dura décimas de segundo.
    }

    /** @param volumen de 0 (mudo) a 100 (máximo). Se aplica de inmediato. */
    public void setVolumenDeMusica(int volumen) {
        validarVolumen(volumen);
        this.volumenDeMusica = volumen;
        if (musica != null) {
            aplicarVolumen(musica, volumen);
        }
    }

    public int getVolumenDeEfectos() {
        return volumenDeEfectos;
    }

    public int getVolumenDeMusica() {
        return volumenDeMusica;
    }

    /** Silencia todo sin perder los volúmenes elegidos. */
    public void silenciar() {
        silenciado = true;
        if (musica != null) {
            aplicarVolumen(musica, 0);
        }
    }

    /** Vuelve a los volúmenes que había antes de silenciar. */
    public void activarSonido() {
        silenciado = false;
        if (musica != null) {
            aplicarVolumen(musica, volumenDeMusica);
        }
    }

    public boolean estaSilenciado() {
        return silenciado;
    }

    /** @return false si la máquina no tiene placa de sonido: todo es no-op. */
    public boolean hayAudioDisponible() {
        return hayAudio;
    }

    // ==================================================================
    //  Cierre
    // ==================================================================

    /** Detiene todo y libera los recursos de audio. Llamarlo al salir del juego. */
    public void cerrar() {
        detenerMusica();
        for (Clip clip : efectos.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        efectos.clear();
    }

    /** Permite usar el reproductor en un {@code try (...)}. Equivale a {@link #cerrar()}. */
    @Override
    public void close() {
        cerrar();
    }

    // ==================================================================
    //  Auxiliares
    // ==================================================================

    /**
     * Convierte un volumen de 0 a 100 en la ganancia en decibeles que entiende
     * la placa. El oído es logarítmico: bajar 6 dB es escuchar "la mitad".
     * Con volumen 100 la ganancia es 0 dB (el sonido tal cual está grabado);
     * con 50 es -6 dB; con 0 es silencio.
     */
    static float decibelesPara(int volumen) {
        validarVolumen(volumen);
        if (volumen == 0) {
            return DECIBELES_DE_SILENCIO;
        }
        return (float) (20.0 * Math.log10(volumen / (double) VOLUMEN_MAXIMO));
    }

    private void aplicarVolumen(Clip clip, int volumen) {
        int efectivo = silenciado ? 0 : volumen;
        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;                               // algún driver raro: se queda como está
        }
        FloatControl ganancia = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float decibeles = decibelesPara(efectivo);
        // Cada placa admite un rango distinto: no salirse de él.
        decibeles = Math.max(ganancia.getMinimum(), Math.min(ganancia.getMaximum(), decibeles));
        ganancia.setValue(decibeles);
    }

    /**
     * Abre el archivo, lo decodifica a PCM si hace falta (caso MP3) y lo
     * carga en un {@link Clip}. Devuelve null si la máquina no tiene audio,
     * pero igual valida que el archivo exista, así el error se ve en todas
     * las máquinas.
     */
    private Clip cargarClip(Path archivo) {
        Objects.requireNonNull(archivo, "el archivo no puede ser null");
        if (!Files.isRegularFile(archivo)) {
            throw new IllegalArgumentException("No existe el archivo de sonido: " + archivo.toAbsolutePath());
        }
        if (!hayAudio) {
            return null;
        }
        try (AudioInputStream original = AudioSystem.getAudioInputStream(archivo.toFile())) {
            AudioInputStream pcm = aPcm(original);
            Clip clip = AudioSystem.getClip();
            clip.open(pcm);
            return clip;
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("No se puede decodificar " + archivo.getFileName()
                    + ". Si es un MP3, falta la dependencia mp3spi en build.gradle. "
                    + "Formatos soportados sin ella: WAV, AIFF, AU.", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo leer " + archivo.getFileName() + ": " + e.getMessage(), e);
        } catch (LineUnavailableException e) {
            throw new IllegalStateException("El dispositivo de audio está ocupado o no disponible", e);
        }
    }

    /**
     * Un MP3 viene comprimido; la placa sólo entiende PCM (muestras crudas).
     * Si el stream ya es PCM (caso WAV), se devuelve tal cual.
     */
    private static AudioInputStream aPcm(AudioInputStream original) {
        AudioFormat formato = original.getFormat();
        if (formato.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
            return original;
        }
        AudioFormat pcm = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                formato.getSampleRate(),
                16,
                formato.getChannels(),
                formato.getChannels() * 2,
                formato.getSampleRate(),
                false);
        return AudioSystem.getAudioInputStream(pcm, original);
    }

    private static void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el nombre del efecto no puede estar vacío");
        }
    }

    private static void validarVolumen(int volumen) {
        if (volumen < 0 || volumen > VOLUMEN_MAXIMO) {
            throw new IllegalArgumentException("el volumen va de 0 a " + VOLUMEN_MAXIMO + ", y llegó " + volumen);
        }
    }
}

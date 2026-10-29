package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.nio.file.Path;

import ar.uba.fi.cb100.librerias.sonido.ReproductorDeSonido;

/**
 * Implementación de {@link Sonido} con {@link ReproductorDeSonido} y los sonidos
 * de la cátedra (librerias/sonido/sonidos). Traduce cada evento a un archivo:
 * jugada → paso, error → agua, victoria → tesoro, y música de fondo baja.
 * Usa los WAV, que no necesitan decodificador (los MP3 dependen de mp3spi).
 *
 * <p>Para crearla se usa {@link #crear()}, que si no hay audio o no encuentra
 * los archivos devuelve un {@link SinSonido}: el juego funciona igual.
 */
public class SonidoDeLaCatedra implements Sonido {

    private static final String CARPETA_DE_SONIDOS = "ar/uba/fi/cb100/librerias/sonido/sonidos";
    private static final int VOLUMEN_DE_MUSICA = 20;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final ReproductorDeSonido reproductor;
    private final Path carpeta;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    private SonidoDeLaCatedra(ReproductorDeSonido reproductor, Path carpeta) {
        this.reproductor = reproductor;
        this.carpeta = carpeta;
        this.reproductor.cargarEfecto("jugada", carpeta.resolve("paso.wav"));
        this.reproductor.cargarEfecto("error", carpeta.resolve("agua.wav"));
        this.reproductor.cargarEfecto("victoria", carpeta.resolve("tesoro.wav"));
    }

    /**
     * @return el sonido de la cátedra, o un {@link SinSonido} si no se puede reproducir audio
     */
    public static Sonido crear() {
        Path carpeta = Recursos.buscarCarpeta(CARPETA_DE_SONIDOS, "paso.wav");
        if (carpeta == null) {
            return new SinSonido();
        }
        ReproductorDeSonido reproductor = new ReproductorDeSonido();
        if (!reproductor.hayAudioDisponible()) {
            reproductor.close();
            return new SinSonido();
        }
        try {
            return new SonidoDeLaCatedra(reproductor, carpeta);
        } catch (IllegalArgumentException e) {      // falta un archivo o no se pudo decodificar
            reproductor.close();
            System.err.println("[Sonido] " + e.getMessage() + ": el juego sigue sin sonido.");
            return new SinSonido();
        }
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    @Override
    public void reproducirJugada() {
        this.reproductor.reproducirEfecto("jugada");
    }

    @Override
    public void reproducirError() {
        this.reproductor.reproducirEfecto("error");
    }

    @Override
    public void reproducirVictoria() {
        this.reproductor.reproducirEfecto("victoria");
    }

    @Override
    public void reproducirEmpate() {
        this.reproductor.reproducirEfecto("error");
    }

    @Override
    public void iniciarMusica() {
        try {
            this.reproductor.reproducirMusica(this.carpeta.resolve("musica.wav"));
            this.reproductor.setVolumenDeMusica(VOLUMEN_DE_MUSICA);
        } catch (IllegalArgumentException e) {      // sin música, el juego sigue igual
            System.err.println("[Sonido] " + e.getMessage());
        }
    }

    @Override
    public void close() {
        this.reproductor.close();
    }
}

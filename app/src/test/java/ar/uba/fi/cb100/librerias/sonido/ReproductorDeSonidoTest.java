package ar.uba.fi.cb100.librerias.sonido;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Los tests no pueden "escuchar", así que verifican lo que sí se puede medir:
 * la conversión de volumen, las validaciones, y que cargar y reproducir un
 * WAV sintetizado no lance excepciones, con o sin placa de sonido.
 */
class ReproductorDeSonidoTest {

    @TempDir
    Path carpeta;

    private Path unTono() throws Exception {
        return EjemploDeSonido.generarTono(carpeta.resolve("tono.wav"), 440, 0.05);
    }

    // ------------------------------------------------------------ volumen

    @Test
    @DisplayName("volumen 100 es 0 dB: el sonido tal cual está grabado")
    void volumenMaximoEsCeroDecibeles() {
        assertEquals(0.0f, ReproductorDeSonido.decibelesPara(100), 0.001f);
    }

    @Test
    @DisplayName("volumen 50 es -6 dB: la mitad para el oído")
    void volumenMedioEsMenosSeisDecibeles() {
        assertEquals(-6.02f, ReproductorDeSonido.decibelesPara(50), 0.01f);
    }

    @Test
    @DisplayName("volumen 0 es silencio, no -infinito")
    void volumenCeroEsSilencio() {
        assertEquals(ReproductorDeSonido.DECIBELES_DE_SILENCIO, ReproductorDeSonido.decibelesPara(0), 0.001f);
    }

    @Test
    @DisplayName("la escala es monótona: más volumen, más decibeles")
    void laEscalaEsMonotona() {
        float anterior = ReproductorDeSonido.decibelesPara(1);
        for (int v = 2; v <= 100; v++) {
            float actual = ReproductorDeSonido.decibelesPara(v);
            assertTrue(actual > anterior, "en " + v);
            anterior = actual;
        }
    }

    @Test
    @DisplayName("un volumen fuera de 0..100 se rechaza")
    void volumenFueraDeRango() {
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            assertThrows(IllegalArgumentException.class, () -> sonido.setVolumenDeEfectos(-1));
            assertThrows(IllegalArgumentException.class, () -> sonido.setVolumenDeMusica(101));
            assertEquals(ReproductorDeSonido.VOLUMEN_INICIAL, sonido.getVolumenDeEfectos(), "no cambió");
        }
    }

    @Test
    @DisplayName("los volúmenes se guardan por separado y silenciar no los pierde")
    void losVolumenesSeGuardan() {
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            sonido.setVolumenDeEfectos(30);
            sonido.setVolumenDeMusica(70);
            sonido.silenciar();

            assertTrue(sonido.estaSilenciado());
            assertEquals(30, sonido.getVolumenDeEfectos());
            assertEquals(70, sonido.getVolumenDeMusica());

            sonido.activarSonido();
            assertFalse(sonido.estaSilenciado());
        }
    }

    // ------------------------------------------------------------ efectos

    @Test
    @DisplayName("cargar y reproducir un WAV no lanza excepciones, haya o no placa de sonido")
    void cargarYReproducirUnWav() throws Exception {
        Path tono = unTono();
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            assertDoesNotThrow(() -> sonido.cargarEfecto("paso", tono));
            assertTrue(sonido.tieneEfecto("paso"));
            assertDoesNotThrow(() -> sonido.reproducirEfecto("paso"));
            assertDoesNotThrow(() -> sonido.reproducirEfecto("paso"), "repetirlo lo reinicia");
        }
    }

    @Test
    @DisplayName("un efecto que no se cargó avisa cuáles sí están")
    void efectoDesconocido() throws Exception {
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            sonido.cargarEfecto("paso", unTono());

            IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                    () -> sonido.reproducirEfecto("explosion"));

            assertTrue(error.getMessage().contains("paso"), error.getMessage());
        }
    }

    @Test
    @DisplayName("un archivo inexistente se rechaza con la ruta en el mensaje")
    void archivoInexistente() {
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                    () -> sonido.cargarEfecto("x", carpeta.resolve("no-existe.mp3")));

            assertTrue(error.getMessage().contains("no-existe.mp3"), error.getMessage());
            assertFalse(sonido.tieneEfecto("x"));
        }
    }

    @Test
    @DisplayName("un archivo que no es audio se rechaza explicando el formato")
    void archivoQueNoEsAudio() throws Exception {
        Path falso = carpeta.resolve("falso.mp3");
        Files.writeString(falso, "esto no es un mp3");
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            if (!sonido.hayAudioDisponible()) {
                return;      // sin placa no se decodifica: no hay nada que verificar
            }
            assertThrows(IllegalArgumentException.class, () -> sonido.cargarEfecto("x", falso));
        }
    }

    @Test
    @DisplayName("el nombre del efecto no puede estar vacío")
    void nombreVacio() throws Exception {
        Path tono = unTono();
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            assertThrows(IllegalArgumentException.class, () -> sonido.cargarEfecto("", tono));
            assertThrows(IllegalArgumentException.class, () -> sonido.cargarEfecto(null, tono));
        }
    }

    // ------------------------------------------------------------ música

    @Test
    @DisplayName("la música se reproduce en bucle y se detiene")
    void musicaEnBucle() throws Exception {
        Path tono = unTono();
        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            assertFalse(sonido.hayMusicaSonando());

            sonido.reproducirMusica(tono);
            if (sonido.hayAudioDisponible()) {
                assertTrue(sonido.hayMusicaSonando(), "en bucle, no termina sola");
            }
            assertDoesNotThrow(() -> sonido.setVolumenDeMusica(10), "se aplica en caliente");

            sonido.detenerMusica();
            assertFalse(sonido.hayMusicaSonando());
            assertDoesNotThrow(sonido::detenerMusica, "detener dos veces no molesta");
        }
    }

    // ------------------------------------------------------------ mp3 de verdad

    /**
     * {@code assumeTrue} es distinto de {@code assertTrue}: si la condición no
     * se cumple, el test se marca como <b>salteado</b>, no como fallido. Es
     * para precondiciones del entorno, como "hay placa de sonido", que no son
     * culpa del código.
     */
    @Test
    @DisplayName("los MP3 de prueba de la cátedra se decodifican con mp3spi")
    void losMp3DeLaCatedraSeDecodifican() {
        Path carpeta = Path.of("src/main/java/ar/uba/fi/cb100/librerias/sonido/sonidos");
        assumeTrue(Files.exists(carpeta.resolve("paso.mp3")), "no están los MP3 de prueba");

        try (ReproductorDeSonido sonido = new ReproductorDeSonido()) {
            assumeTrue(sonido.hayAudioDisponible(), "sin placa de sonido no se decodifica");

            for (String nombre : new String[]{"paso", "agua", "tesoro"}) {
                assertDoesNotThrow(() -> sonido.cargarEfecto(nombre, carpeta.resolve(nombre + ".mp3")), nombre);
                assertTrue(sonido.tieneEfecto(nombre));
                assertDoesNotThrow(() -> sonido.reproducirEfecto(nombre), nombre);
            }
            assertDoesNotThrow(() -> sonido.reproducirMusica(carpeta.resolve("musica.mp3")));
            assertTrue(sonido.hayMusicaSonando());

            // El mismo sonido en WAV se carga igual: el formato es un detalle del archivo,
            // no de quien lo usa.
            assertDoesNotThrow(() -> sonido.cargarEfecto("paso-wav", carpeta.resolve("paso.wav")));
            assertDoesNotThrow(() -> sonido.reproducirEfecto("paso-wav"));
        }
    }

    // ------------------------------------------------------------ cierre

    @Test
    @DisplayName("cerrar libera todo y se puede llamar dos veces")
    void cerrarEsIdempotente() throws Exception {
        ReproductorDeSonido sonido = new ReproductorDeSonido();
        sonido.cargarEfecto("paso", unTono());
        sonido.reproducirMusica(unTono());

        sonido.cerrar();

        assertFalse(sonido.tieneEfecto("paso"));
        assertFalse(sonido.hayMusicaSonando());
        assertDoesNotThrow(sonido::cerrar);
    }
}

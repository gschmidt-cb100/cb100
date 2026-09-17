package ar.uba.fi.cb100.librerias.imagen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * La fachada se prueba <b>sin mostrar</b> la ventana: se construye, se arma el
 * tablero y se consulta, pero nunca se llama a {@code mostrar()}. Así los
 * tests no hacen aparecer ventanas. En una máquina sin entorno gráfico se
 * saltean con {@code assumeFalse}.
 */
class JuegoVisualTest {

    @TempDir
    Path carpeta;

    private static final String[] NIVEL = {
            "#####",
            "#..~#",
            "#####",
    };

    private static void necesitaPantalla() {
        assumeFalse(GraphicsEnvironment.isHeadless(), "sin entorno gráfico no se puede crear la ventana");
    }

    @Test
    @DisplayName("Tecla: las flechas y WASD son lo mismo, y todo lo demás es OTRA")
    void teclas() {
        assertEquals(Tecla.ARRIBA, Tecla.desdeCodigo(KeyEvent.VK_UP));
        assertEquals(Tecla.ARRIBA, Tecla.desdeCodigo(KeyEvent.VK_W));
        assertEquals(Tecla.IZQUIERDA, Tecla.desdeCodigo(KeyEvent.VK_A));
        assertEquals(Tecla.ESCAPE, Tecla.desdeCodigo(KeyEvent.VK_ESCAPE));
        assertEquals(Tecla.OTRA, Tecla.desdeCodigo(KeyEvent.VK_F5));

        assertTrue(Tecla.DERECHA.esDireccion());
        assertFalse(Tecla.ENTER.esDireccion());
        assertEquals(-1, Tecla.ARRIBA.deltaFila());
        assertEquals(1, Tecla.DERECHA.deltaColumna());
        assertEquals(0, Tecla.ESPACIO.deltaFila() + Tecla.ESPACIO.deltaColumna());
    }

    @Test
    @DisplayName("se construye desde una grilla y expone las celdas de 1 a n")
    void construirDesdeGrilla() {
        necesitaPantalla();
        try (JuegoVisual juego = new JuegoVisual("test", NIVEL, 16)) {
            assertEquals(3, juego.getFilas());
            assertEquals(5, juego.getColumnas());
            assertEquals('~', juego.getCelda(2, 4));
            assertTrue(juego.estaDentro(1, 1));
            assertFalse(juego.estaDentro(0, 1));
            assertFalse(juego.estaDentro(3, 6));
            assertFalse(juego.estaAbierta(), "hasta mostrar(), no está abierta");
        }
    }

    @Test
    @DisplayName("los personajes se agregan, se mueven y se consultan")
    void personajes() {
        necesitaPantalla();
        try (JuegoVisual juego = new JuegoVisual("test", NIVEL, 16)) {
            juego.agregarPersonaje("heroe", Color.BLUE, 2, 2);
            juego.moverPersonaje("heroe", 2, 3);

            assertTrue(juego.tienePersonaje("heroe"));
            assertEquals(2, juego.getFilaDe("heroe"));
            assertEquals(3, juego.getColumnaDe("heroe"));
            assertThrows(IllegalArgumentException.class, () -> juego.moverPersonaje("heroe", 9, 9));
            assertThrows(IllegalArgumentException.class, () -> juego.getFilaDe("nadie"));
        }
    }

    @Test
    @DisplayName("sin teclas apretadas, leerTecla es null; cerrada, esperarTecla devuelve CERRAR")
    void teclado() {
        necesitaPantalla();
        JuegoVisual juego = new JuegoVisual("test", NIVEL, 16);
        assertNull(juego.leerTecla());

        juego.cerrar();

        assertFalse(juego.estaAbierta());
        assertEquals(Tecla.CERRAR, juego.esperarTecla(), "no se queda esperando para siempre");
    }

    @Test
    @DisplayName("el mensaje se guarda y se puede consultar")
    void mensaje() {
        necesitaPantalla();
        try (JuegoVisual juego = new JuegoVisual("test", 2, 2)) {
            juego.mostrarMensaje("Energía 90");
            assertEquals("Energía 90", juego.getMensaje());
        }
    }

    @Test
    @DisplayName("una imagen inexistente o que no es imagen se rechaza con un mensaje claro")
    void imagenesInvalidas() throws Exception {
        necesitaPantalla();
        Path falsa = carpeta.resolve("falsa.png");
        Files.writeString(falsa, "esto no es un png");
        try (JuegoVisual juego = new JuegoVisual("test", 2, 2)) {
            IllegalArgumentException noExiste = assertThrows(IllegalArgumentException.class,
                    () -> juego.definirCelda('#', carpeta.resolve("no-existe.png")));
            assertTrue(noExiste.getMessage().contains("no-existe.png"));

            IllegalArgumentException noEsImagen = assertThrows(IllegalArgumentException.class,
                    () -> juego.agregarPersonaje("h", falsa, 1, 1));
            assertTrue(noEsImagen.getMessage().contains("falsa.png"));
            assertFalse(juego.tienePersonaje("h"), "no quedó a medio agregar");
        }
    }

    @Test
    @DisplayName("los sprites de la cátedra se cargan como imagen de celda y de personaje")
    void spritesDeLaCatedra() {
        necesitaPantalla();
        Path imagenes = Path.of("src/main/java/ar/uba/fi/cb100/librerias/imagen/imagenes");
        assumeFalse(!Files.exists(imagenes.resolve("heroe.png")), "no están los sprites de prueba");
        try (JuegoVisual juego = new JuegoVisual("test", NIVEL, 16)) {
            juego.definirCelda('#', imagenes.resolve("pared.png"));
            juego.agregarPersonaje("heroe", imagenes.resolve("heroe.png"), 2, 2);
            assertTrue(juego.tienePersonaje("heroe"));
        }
    }

    @Test
    @DisplayName("una grilla vacía o una celda demasiado chica se rechazan")
    void construccionInvalida() {
        necesitaPantalla();
        assertThrows(IllegalArgumentException.class, () -> new JuegoVisual("test", new String[0]));
        assertThrows(IllegalArgumentException.class, () -> new JuegoVisual("test", 3, 3, 4));
    }
}

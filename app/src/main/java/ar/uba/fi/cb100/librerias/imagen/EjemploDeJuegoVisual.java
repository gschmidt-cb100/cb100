package ar.uba.fi.cb100.librerias.imagen;

import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;

import ar.uba.fi.cb100.librerias.sonido.ReproductorDeSonido;

/**
 * Un juego mínimo con {@link JuegoVisual}: el nivel de ejemplo del TP 2, el
 * héroe se mueve con las flechas (o W A S D), las paredes frenan, el agua
 * cuesta energía, la llave abre la puerta, el tesoro suma puntos y la salida
 * gana. Con sonidos, si están los MP3 de la cátedra.
 *
 * <p>Todo el ciclo del juego está en {@link #main}: es la plantilla para el
 * TP. Fijate que <b>no hay ni un {@code KeyListener} ni un {@code repaint()}</b>:
 * eso vive adentro de la librería.
 */
public class EjemploDeJuegoVisual {

    private static final String[] NIVEL = {
            "##########",
            "#H..~~..K#",
            "#.##.#.#.#",
            "#.#M..T..#",
            "#.#.###.##",
            "#...#..P.S",
            "##########",
    };

    private static final String[] CARPETAS_DE_IMAGENES = {
            "app/src/main/java/ar/uba/fi/cb100/librerias/imagen/imagenes",
            "src/main/java/ar/uba/fi/cb100/librerias/imagen/imagenes",
    };
    private static final String[] CARPETAS_DE_SONIDOS = {
            "app/src/main/java/ar/uba/fi/cb100/librerias/sonido/sonidos",
            "src/main/java/ar/uba/fi/cb100/librerias/sonido/sonidos",
    };

    public static void main(String[] args) {
        Path imagenes = buscar(CARPETAS_DE_IMAGENES, "heroe.png");
        Path sonidos = buscar(CARPETAS_DE_SONIDOS, "paso.mp3");

        try (JuegoVisual juego = new JuegoVisual("La fuga de la mazmorra", NIVEL, 56);
             ReproductorDeSonido sonido = new ReproductorDeSonido()) {

            // ---- Apariencia: imágenes si están, colores si no ----------------
            int filaHeroe = 2, columnaHeroe = 2;
            juego.setCelda(filaHeroe, columnaHeroe, '.');          // la H del texto es sólo la posición inicial
            if (imagenes != null) {
                juego.definirCelda('#', imagenes.resolve("pared.png"));
                juego.definirCelda('.', imagenes.resolve("piso.png"));
                juego.definirCelda('~', imagenes.resolve("agua.png"));
                juego.definirCelda('T', imagenes.resolve("tesoro.png"));
                juego.definirCelda('K', imagenes.resolve("llave.png"));
                juego.definirCelda('P', imagenes.resolve("puerta.png"));
                juego.definirCelda('S', imagenes.resolve("salida.png"));
                juego.agregarPersonaje("heroe", imagenes.resolve("heroe.png"), filaHeroe, columnaHeroe);
                juego.agregarPersonaje("monstruo", imagenes.resolve("monstruo.png"), 4, 4);
            } else {
                juego.agregarPersonaje("heroe", new Color(47, 111, 209), filaHeroe, columnaHeroe);
                juego.agregarPersonaje("monstruo", new Color(198, 40, 40), 4, 4);
            }
            juego.setCelda(4, 4, '.');                              // ídem con la M

            if (sonidos != null && sonido.hayAudioDisponible()) {
                sonido.cargarEfecto("paso", sonidos.resolve("paso.mp3"));
                sonido.cargarEfecto("agua", sonidos.resolve("agua.mp3"));
                sonido.cargarEfecto("tesoro", sonidos.resolve("tesoro.mp3"));
                sonido.reproducirMusica(sonidos.resolve("musica.mp3"));
                sonido.setVolumenDeMusica(30);
            }

            // ---- El ciclo del juego ------------------------------------------
            int energia = 100, puntos = 0, turno = 0;
            boolean tieneLlave = false;
            juego.mostrar();
            juego.mostrarMensaje("Flechas para moverte, ESC para salir.   Energía " + energia);

            while (juego.estaAbierta()) {
                Tecla tecla = juego.esperarTecla();
                if (tecla == Tecla.ESCAPE || tecla == Tecla.CERRAR) {
                    break;
                }
                if (!tecla.esDireccion()) {
                    continue;
                }

                int fila = filaHeroe + tecla.deltaFila();
                int columna = columnaHeroe + tecla.deltaColumna();
                if (!juego.estaDentro(fila, columna)) {
                    continue;
                }
                char destino = juego.getCelda(fila, columna);

                if (destino == '#') {
                    juego.mostrarMensaje("Pared.   Energía " + energia + "   Puntos " + puntos);
                    continue;
                }
                if (destino == 'P' && !tieneLlave) {
                    juego.mostrarMensaje("La puerta está cerrada: te falta la llave.");
                    continue;
                }

                // Se mueve: primero el costo del terreno...
                turno++;
                String aviso;
                if (destino == '~') {
                    energia -= 3;
                    aviso = "Agua: -3";
                    reproducir(sonido, "agua");
                } else {
                    energia -= 1;
                    aviso = "";
                    reproducir(sonido, "paso");
                }
                // ...después lo que había en la celda.
                if (destino == 'K') {
                    tieneLlave = true;
                    aviso = "¡Llave!";
                    juego.setCelda(fila, columna, '.');
                    reproducir(sonido, "tesoro");
                } else if (destino == 'T') {
                    puntos += 100;
                    aviso = "¡Tesoro! +100";
                    juego.setCelda(fila, columna, '.');
                    reproducir(sonido, "tesoro");
                } else if (destino == 'P') {
                    aviso = "La puerta se abre.";
                    juego.setCelda(fila, columna, '.');
                }

                filaHeroe = fila;
                columnaHeroe = columna;
                juego.moverPersonaje("heroe", filaHeroe, columnaHeroe);

                if (destino == 'S') {
                    int puntaje = puntos + energia * 2 - turno;
                    juego.mostrarMensaje("¡Escapaste! Puntaje: " + puntos + " + " + energia + "x2 - " + turno
                            + " = " + puntaje + ".   ESC para salir.");
                    esperarSalida(juego);
                    break;
                }
                if (energia <= 0) {
                    juego.mostrarMensaje("Te quedaste sin energía en el turno " + turno + ".   ESC para salir.");
                    esperarSalida(juego);
                    break;
                }
                juego.mostrarMensaje((aviso.isEmpty() ? "" : aviso + "   ")
                        + "Energía " + energia + "   Puntos " + puntos + "   Turno " + turno
                        + (tieneLlave ? "   [llave]" : ""));
            }
        }
    }

    private static void esperarSalida(JuegoVisual juego) {
        Tecla tecla;
        do {
            tecla = juego.esperarTecla();
        } while (tecla != Tecla.ESCAPE && tecla != Tecla.CERRAR && tecla != Tecla.ENTER);
    }

    private static void reproducir(ReproductorDeSonido sonido, String efecto) {
        if (sonido.tieneEfecto(efecto)) {
            sonido.reproducirEfecto(efecto);
        }
    }

    /** Busca una carpeta que contenga cierto archivo, desde la raíz del proyecto o desde app/. */
    private static Path buscar(String[] candidatas, String archivoQueDebeTener) {
        for (String candidata : candidatas) {
            Path ruta = Path.of(candidata);
            if (Files.exists(ruta.resolve(archivoQueDebeTener))) {
                return ruta;
            }
        }
        return null;
    }
}

package ar.uba.fi.cb100.librerias.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Lo que hay en el tablero: qué símbolo tiene cada celda, con qué se dibuja
 * cada símbolo, dónde está cada personaje y el mensaje de abajo.
 *
 * <p>No sabe nada de Swing: es puro estado, y por eso se puede testear sin
 * abrir una ventana. {@link PanelDelTablero} lo lee para dibujar y
 * {@link JuegoVisual} lo modifica. Los dos lo hacen adentro de
 * {@code synchronized (estado)}, porque el que dibuja corre en el hilo de
 * Swing y el que modifica en el hilo del juego.
 *
 * <p>Filas y columnas van <b>de 1 a n</b>, como en el resto de la materia.
 */
final class EstadoDelTablero {

    /** Con qué se dibuja un símbolo: un color liso, o una imagen. */
    record Apariencia(Color color, BufferedImage imagen) {
        static Apariencia de(Color color) {
            return new Apariencia(Objects.requireNonNull(color), null);
        }

        static Apariencia de(BufferedImage imagen) {
            return new Apariencia(null, Objects.requireNonNull(imagen));
        }
    }

    /** Un sprite con nombre, parado en una celda. */
    static final class Personaje {
        final String nombre;
        final Apariencia apariencia;
        int fila;
        int columna;

        Personaje(String nombre, Apariencia apariencia, int fila, int columna) {
            this.nombre = nombre;
            this.apariencia = apariencia;
            this.fila = fila;
            this.columna = columna;
        }
    }

    private final int filas;
    private final int columnas;
    private final char[][] celdas;
    private final Map<Character, Apariencia> apariencias = new HashMap<>();
    private final Map<String, Personaje> personajes = new LinkedHashMap<>();   // conserva el orden de dibujo
    private String mensaje = "";

    EstadoDelTablero(int filas, int columnas) {
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("el tablero necesita al menos 1 fila y 1 columna, y llegó "
                    + filas + "x" + columnas);
        }
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new char[filas][columnas];
        for (char[] fila : celdas) {
            java.util.Arrays.fill(fila, ' ');
        }
        aparienciasPorDefecto();
    }

    /** Colores razonables para los símbolos habituales, para arrancar sin configurar nada. */
    private void aparienciasPorDefecto() {
        apariencias.put(' ', Apariencia.de(Color.WHITE));
        apariencias.put('#', Apariencia.de(new Color(74, 74, 74)));       // pared
        apariencias.put('.', Apariencia.de(new Color(243, 234, 215)));    // piso
        apariencias.put('~', Apariencia.de(new Color(158, 208, 240)));    // agua
        apariencias.put('^', Apariencia.de(new Color(240, 138, 93)));     // lava
        apariencias.put('S', Apariencia.de(new Color(102, 187, 106)));    // salida
        apariencias.put('T', Apariencia.de(new Color(242, 193, 78)));     // tesoro
        apariencias.put('K', Apariencia.de(new Color(255, 241, 118)));    // llave
        apariencias.put('P', Apariencia.de(new Color(161, 136, 127)));    // puerta
        apariencias.put('X', Apariencia.de(new Color(198, 40, 40)));      // trampa
    }

    // ------------------------------------------------------------ celdas

    /** Carga la grilla entera desde textos, una fila por elemento. Tiene que ser rectangular y del tamaño del tablero. */
    void cargar(String[] grilla) {
        Objects.requireNonNull(grilla, "la grilla no puede ser null");
        if (grilla.length != filas) {
            throw new IllegalArgumentException("la grilla tiene " + grilla.length + " filas y el tablero " + filas);
        }
        for (int f = 0; f < filas; f++) {
            if (grilla[f] == null || grilla[f].length() != columnas) {
                throw new IllegalArgumentException("la fila " + (f + 1) + " tiene "
                        + (grilla[f] == null ? "null" : grilla[f].length() + " columnas")
                        + " y el tablero tiene " + columnas);
            }
        }
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                celdas[f][c] = grilla[f].charAt(c);
            }
        }
    }

    void setCelda(int fila, int columna, char simbolo) {
        validarPosicion(fila, columna);
        celdas[fila - 1][columna - 1] = simbolo;
    }

    char getCelda(int fila, int columna) {
        validarPosicion(fila, columna);
        return celdas[fila - 1][columna - 1];
    }

    int getFilas() {
        return filas;
    }

    int getColumnas() {
        return columnas;
    }

    // ------------------------------------------------------------ apariencias

    void definirApariencia(char simbolo, Apariencia apariencia) {
        apariencias.put(simbolo, Objects.requireNonNull(apariencia));
    }

    /** @return la apariencia del símbolo, o null si nadie la definió (se dibuja como "desconocido"). */
    Apariencia aparienciaDe(char simbolo) {
        return apariencias.get(simbolo);
    }

    // ------------------------------------------------------------ personajes

    void agregarPersonaje(String nombre, Apariencia apariencia, int fila, int columna) {
        validarNombre(nombre);
        if (personajes.containsKey(nombre)) {
            throw new IllegalArgumentException("ya hay un personaje llamado \"" + nombre + "\"");
        }
        validarPosicion(fila, columna);
        personajes.put(nombre, new Personaje(nombre, Objects.requireNonNull(apariencia), fila, columna));
    }

    void moverPersonaje(String nombre, int fila, int columna) {
        validarPosicion(fila, columna);
        Personaje personaje = personaje(nombre);
        personaje.fila = fila;
        personaje.columna = columna;
    }

    void quitarPersonaje(String nombre) {
        personaje(nombre);                        // valida que exista
        personajes.remove(nombre);
    }

    boolean tienePersonaje(String nombre) {
        return nombre != null && personajes.containsKey(nombre);
    }

    int filaDe(String nombre) {
        return personaje(nombre).fila;
    }

    int columnaDe(String nombre) {
        return personaje(nombre).columna;
    }

    Iterable<Personaje> personajes() {
        return personajes.values();
    }

    private Personaje personaje(String nombre) {
        validarNombre(nombre);
        Personaje personaje = personajes.get(nombre);
        if (personaje == null) {
            throw new IllegalArgumentException("no hay ningún personaje llamado \"" + nombre
                    + "\". Los que hay: " + personajes.keySet());
        }
        return personaje;
    }

    // ------------------------------------------------------------ mensaje

    void setMensaje(String mensaje) {
        this.mensaje = (mensaje == null) ? "" : mensaje;
    }

    String getMensaje() {
        return mensaje;
    }

    // ------------------------------------------------------------ validaciones

    private void validarPosicion(int fila, int columna) {
        if (fila < 1 || fila > filas || columna < 1 || columna > columnas) {
            throw new IllegalArgumentException("la posición (" + fila + ", " + columna
                    + ") está fuera del tablero de " + filas + "x" + columnas);
        }
    }

    private static void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el nombre del personaje no puede estar vacío");
        }
    }
}

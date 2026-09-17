package ar.uba.fi.cb100.librerias.imagen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Una ventana con un tablero de celdas, personajes que se mueven por él y el
 * teclado listo para leer. Pensada para que un juego de grilla por turnos se
 * escriba como un ciclo común, sin saber nada de Swing:
 *
 * <pre>{@code
 * String[] nivel = {
 *         "##########",
 *         "#H..~~..K#",
 *         "#........S",
 *         "##########",
 * };
 * try (JuegoVisual juego = new JuegoVisual("Mazmorra", nivel)) {
 *     juego.definirCelda('#', Path.of("imagenes/pared.png"));     // opcional: sin esto, colores
 *     juego.agregarPersonaje("heroe", Path.of("imagenes/heroe.png"), 2, 2);
 *     juego.mostrar();
 *
 *     int fila = 2, columna = 2;
 *     while (juego.estaAbierta()) {
 *         Tecla tecla = juego.esperarTecla();              // se queda esperando
 *         if (tecla == Tecla.ESCAPE || tecla == Tecla.CERRAR) {
 *             break;
 *         }
 *         if (tecla.esDireccion() && juego.getCelda(fila + tecla.deltaFila(), columna + tecla.deltaColumna()) != '#') {
 *             fila += tecla.deltaFila();
 *             columna += tecla.deltaColumna();
 *             juego.moverPersonaje("heroe", fila, columna);
 *         }
 *     }
 * }
 * }</pre>
 *
 * <h2>Lo que hay que saber</h2>
 * <ul>
 *   <li>Filas y columnas van <b>de 1 a n</b>, como en toda la materia.</li>
 *   <li>Cada celda tiene un <b>símbolo</b> (un {@code char}): {@code '#'} pared,
 *       {@code '.'} piso, {@code '~'} agua, y los que ustedes quieran. Los
 *       habituales ya tienen un color; con {@link #definirCelda} se les pone
 *       una imagen o se agregan símbolos nuevos. Un símbolo sin definir se
 *       dibuja en <b>magenta con un signo</b>, para que se note.</li>
 *   <li>Un <b>personaje</b> es un sprite con nombre que se para en una celda y
 *       se mueve con {@link #moverPersonaje}. La celda de abajo no cambia.</li>
 *   <li>{@link #esperarTecla()} <b>bloquea</b> hasta que el usuario apriete
 *       algo. Devuelve {@link Tecla#CERRAR} si cerró la ventana.</li>
 *   <li>Todo lo que cambia el tablero se redibuja solo.</li>
 * </ul>
 *
 * <p>Las imágenes son PNG (con transparencia) o JPG. Se agrandan al tamaño de
 * la celda sin suavizar, así el pixel-art queda nítido.
 *
 * <p>Esta clase usa {@code java.util}: es una librería de la cátedra, capa de
 * borde, no parte del modelo del juego.
 */
public final class JuegoVisual implements AutoCloseable {

    /** Lado de cada celda en píxeles, si no se indica otro. */
    public static final int TAMANIO_DE_CELDA_POR_DEFECTO = 48;

    private final EstadoDelTablero estado;
    private final BlockingQueue<Tecla> teclas = new LinkedBlockingQueue<>();
    private JFrame ventana;
    private PanelDelTablero panel;
    private JLabel barraDeMensaje;
    private volatile boolean abierta = false;

    // ==================================================================
    //  Construcción
    // ==================================================================

    /** Un tablero vacío (todo espacio en blanco) de filas × columnas, con celdas de tamaño por defecto. */
    public JuegoVisual(String titulo, int filas, int columnas) {
        this(titulo, filas, columnas, TAMANIO_DE_CELDA_POR_DEFECTO);
    }

    /** Un tablero vacío de filas × columnas, con celdas del tamaño indicado en píxeles. */
    public JuegoVisual(String titulo, int filas, int columnas, int tamanioDeCelda) {
        if (tamanioDeCelda < 8) {
            throw new IllegalArgumentException("la celda tiene que medir al menos 8 píxeles");
        }
        this.estado = new EstadoDelTablero(filas, columnas);
        construirVentana(titulo == null ? "Juego" : titulo, tamanioDeCelda);
    }

    /** Un tablero cargado desde textos, una fila por elemento, con celdas de tamaño por defecto. */
    public JuegoVisual(String titulo, String[] grilla) {
        this(titulo, grilla, TAMANIO_DE_CELDA_POR_DEFECTO);
    }

    /** Un tablero cargado desde textos, una fila por elemento. */
    public JuegoVisual(String titulo, String[] grilla, int tamanioDeCelda) {
        this(titulo, filasDe(grilla), columnasDe(grilla), tamanioDeCelda);
        cargar(grilla);
    }

    private static int filasDe(String[] grilla) {
        if (grilla == null || grilla.length == 0) {
            throw new IllegalArgumentException("la grilla no puede estar vacía");
        }
        return grilla.length;
    }

    private static int columnasDe(String[] grilla) {
        if (grilla == null || grilla.length == 0 || grilla[0] == null) {
            throw new IllegalArgumentException("la grilla no puede estar vacía");
        }
        return grilla[0].length();
    }

    private void construirVentana(String titulo, int tamanioDeCelda) {
        try {
            enElHiloDeSwing(() -> {
                panel = new PanelDelTablero(estado, tamanioDeCelda);
                barraDeMensaje = new JLabel(" ");
                barraDeMensaje.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
                barraDeMensaje.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

                ventana = new JFrame(titulo);
                ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventana.setLayout(new BorderLayout());
                ventana.add(panel, BorderLayout.CENTER);
                ventana.add(barraDeMensaje, BorderLayout.SOUTH);
                ventana.setResizable(false);
                ventana.pack();
                ventana.setLocationRelativeTo(null);          // centrada en la pantalla

                panel.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent evento) {
                        teclas.offer(Tecla.desdeCodigo(evento.getKeyCode()));
                    }
                });
                ventana.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent evento) {
                        abierta = false;
                        teclas.offer(Tecla.CERRAR);           // despierta a quien esté esperando
                    }

                    @Override
                    public void windowActivated(WindowEvent evento) {
                        panel.requestFocusInWindow();         // que las teclas lleguen al panel
                    }
                });
            });
        } catch (HeadlessException e) {
            throw new IllegalStateException("Esta máquina no tiene entorno gráfico: no se puede abrir una ventana", e);
        }
    }

    // ==================================================================
    //  Ciclo de vida
    // ==================================================================

    /** Muestra la ventana. Hasta acá no se ve nada: así se puede armar el tablero primero. */
    public void mostrar() {
        abierta = true;
        enElHiloDeSwing(() -> {
            ventana.setVisible(true);
            panel.requestFocusInWindow();
        });
    }

    /** @return false si todavía no se mostró, o si el usuario la cerró. */
    public boolean estaAbierta() {
        return abierta;
    }

    /** Cierra la ventana. Quien esté en {@link #esperarTecla()} recibe {@link Tecla#CERRAR}. */
    public void cerrar() {
        if (ventana != null) {
            enElHiloDeSwing(ventana::dispose);              // dispara windowClosed
        }
        abierta = false;
    }

    /** Permite usar el juego en un {@code try (...)}. Equivale a {@link #cerrar()}. */
    @Override
    public void close() {
        cerrar();
    }

    // ==================================================================
    //  Teclado
    // ==================================================================

    /**
     * Se queda esperando hasta que el usuario apriete una tecla, y la devuelve.
     * Si la ventana se cerró, devuelve {@link Tecla#CERRAR} de inmediato.
     */
    public Tecla esperarTecla() {
        if (!abierta && teclas.isEmpty()) {
            return Tecla.CERRAR;
        }
        try {
            return teclas.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Tecla.CERRAR;
        }
    }

    /** Devuelve la próxima tecla apretada, o {@code null} si no hay ninguna. No espera. */
    public Tecla leerTecla() {
        return teclas.poll();
    }

    /** Descarta las teclas que se apretaron y todavía no se leyeron. */
    public void vaciarTeclas() {
        teclas.clear();
    }

    // ==================================================================
    //  Celdas
    // ==================================================================

    /** Reemplaza la grilla entera. Tiene que tener el tamaño del tablero. */
    public void cargar(String[] grilla) {
        synchronized (estado) {
            estado.cargar(grilla);
        }
        redibujar();
    }

    /** Con qué color se dibuja un símbolo. */
    public void definirCelda(char simbolo, Color color) {
        synchronized (estado) {
            estado.definirApariencia(simbolo, EstadoDelTablero.Apariencia.de(color));
        }
        redibujar();
    }

    /** Con qué imagen se dibuja un símbolo. Se agranda al tamaño de la celda. */
    public void definirCelda(char simbolo, Path imagen) {
        BufferedImage cargada = cargarImagen(imagen);
        synchronized (estado) {
            estado.definirApariencia(simbolo, EstadoDelTablero.Apariencia.de(cargada));
        }
        redibujar();
    }

    public void setCelda(int fila, int columna, char simbolo) {
        synchronized (estado) {
            estado.setCelda(fila, columna, simbolo);
        }
        redibujar();
    }

    public char getCelda(int fila, int columna) {
        synchronized (estado) {
            return estado.getCelda(fila, columna);
        }
    }

    /** @return true si la posición existe en el tablero. Útil antes de moverse. */
    public boolean estaDentro(int fila, int columna) {
        return fila >= 1 && fila <= getFilas() && columna >= 1 && columna <= getColumnas();
    }

    public int getFilas() {
        return estado.getFilas();
    }

    public int getColumnas() {
        return estado.getColumnas();
    }

    // ==================================================================
    //  Personajes
    // ==================================================================

    /** Agrega un personaje dibujado con una imagen, parado en (fila, columna). */
    public void agregarPersonaje(String nombre, Path imagen, int fila, int columna) {
        BufferedImage cargada = cargarImagen(imagen);
        synchronized (estado) {
            estado.agregarPersonaje(nombre, EstadoDelTablero.Apariencia.de(cargada), fila, columna);
        }
        redibujar();
    }

    /** Agrega un personaje dibujado como un círculo de ese color con su inicial. Sirve para probar sin imágenes. */
    public void agregarPersonaje(String nombre, Color color, int fila, int columna) {
        synchronized (estado) {
            estado.agregarPersonaje(nombre, EstadoDelTablero.Apariencia.de(color), fila, columna);
        }
        redibujar();
    }

    public void moverPersonaje(String nombre, int fila, int columna) {
        synchronized (estado) {
            estado.moverPersonaje(nombre, fila, columna);
        }
        redibujar();
    }

    public void quitarPersonaje(String nombre) {
        synchronized (estado) {
            estado.quitarPersonaje(nombre);
        }
        redibujar();
    }

    public boolean tienePersonaje(String nombre) {
        synchronized (estado) {
            return estado.tienePersonaje(nombre);
        }
    }

    public int getFilaDe(String nombre) {
        synchronized (estado) {
            return estado.filaDe(nombre);
        }
    }

    public int getColumnaDe(String nombre) {
        synchronized (estado) {
            return estado.columnaDe(nombre);
        }
    }

    // ==================================================================
    //  Mensaje
    // ==================================================================

    /** Texto en la barra de abajo: energía, turno, avisos. */
    public void mostrarMensaje(String texto) {
        String mensaje = (texto == null || texto.isBlank()) ? " " : texto;
        synchronized (estado) {
            estado.setMensaje(mensaje);
        }
        if (barraDeMensaje != null) {
            SwingUtilities.invokeLater(() -> barraDeMensaje.setText(mensaje));
        }
    }

    public String getMensaje() {
        synchronized (estado) {
            return estado.getMensaje().trim();
        }
    }

    // ==================================================================
    //  Auxiliares
    // ==================================================================

    private void redibujar() {
        if (panel != null) {
            panel.repaint();                                 // repaint() se puede llamar desde cualquier hilo
        }
    }

    /** Ejecuta algo en el hilo de Swing y espera a que termine. Swing exige que su UI se toque sólo desde ahí. */
    private static void enElHiloDeSwing(Runnable accion) {
        if (SwingUtilities.isEventDispatchThread()) {
            accion.run();
            return;
        }
        try {
            SwingUtilities.invokeAndWait(accion);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof HeadlessException headless) {
                throw headless;
            }
            throw new IllegalStateException("falló la interfaz gráfica", e.getCause());
        }
    }

    static BufferedImage cargarImagen(Path archivo) {
        if (archivo == null || !Files.isRegularFile(archivo)) {
            throw new IllegalArgumentException("No existe la imagen: " + (archivo == null ? "null" : archivo.toAbsolutePath()));
        }
        try {
            BufferedImage imagen = ImageIO.read(archivo.toFile());
            if (imagen == null) {
                throw new IllegalArgumentException("El archivo no es una imagen que Java entienda (PNG, JPG, GIF, BMP): " + archivo.getFileName());
            }
            return imagen;
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo leer la imagen " + archivo.getFileName() + ": " + e.getMessage(), e);
        }
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.librerias.imagen.JuegoVisual;
import ar.uba.fi.cb100.librerias.imagen.Tecla;

/**
 * Base de las interfaces gráficas, hecha con {@link JuegoVisual} de la librería
 * de la cátedra. Es la <b>única</b> clase que conoce la librería: si se cambia
 * la librería, se cambia esta clase y nada más.
 *
 * <p>Resuelve lo que es igual en todos los juegos: la ventana, el teclado, la
 * barra de mensajes, y <b>con qué se dibuja cada celda</b>. No sabe qué es
 * una celda para cada juego: eso lo deciden las subclases.
 * <pre>
 *   InterfazGrafica
 *   ├── InterfazGraficaDeCasitaRobada        mesa, casitas, mazo y mano en una grilla
 *   └── InterfazGraficaDeTablero&lt;T&gt;           el tablero, celda por celda
 *       ├── InterfazGraficaDeMemotest
 *       └── InterfazGraficaConFicha&lt;F&gt;
 * </pre>
 *
 * <h2>Apariencias</h2>
 * Cada celda se dibuja con una <b>apariencia</b>, identificada por un nombre
 * ("x", "dorso", "7-oros"...). Para cada apariencia hay dos imágenes: la
 * normal y la "con cursor" (con un borde amarillo): para "x.png" se espera
 * también "x-cursor.png". Si la carpeta es null o falta una imagen, se usa
 * el color de respaldo, así el juego anda igual.
 */
public abstract class InterfazGrafica implements InterfazDeJuego {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    /** JuegoVisual dibuja según un símbolo (char). Cada apariencia usa dos: normal y con cursor. */
    private static final char PRIMER_SIMBOLO = 'Ā';

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final JuegoVisual ventana;
    private final Path carpetaDeImagenes;
    private final List<String> apariencias;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea la ventana, todavía oculta.
     * @param titulo título de la ventana
     * @param cantidadDeFilas filas de la grilla, mayor que cero
     * @param cantidadDeColumnas columnas de la grilla, mayor que cero
     * @param tamanioDeCelda lado de cada celda en píxeles
     * @param carpetaDeImagenes carpeta con las imágenes, o null para usar sólo colores
     */
    protected InterfazGrafica(String titulo, int cantidadDeFilas, int cantidadDeColumnas, int tamanioDeCelda,
                              Path carpetaDeImagenes) {
        ValidacionesUtiles.esMayorQueCero(cantidadDeFilas, "cantidadDeFilas");
        ValidacionesUtiles.esMayorQueCero(cantidadDeColumnas, "cantidadDeColumnas");
        this.ventana = new JuegoVisual(titulo, cantidadDeFilas, cantidadDeColumnas, tamanioDeCelda);
        this.carpetaDeImagenes = carpetaDeImagenes;
        this.apariencias = new ArrayList<>();
    }

//METODOS PARA LAS SUBCLASES ------------------------------------------------------------------------------

    /**
     * Define con qué se dibuja una apariencia.
     * @param apariencia nombre de la apariencia, no null ni repetido
     * @param imagen nombre de la imagen (por ejemplo "x.png")
     * @param colorDeRespaldo color si no está la imagen
     * @throws IllegalArgumentException si el nombre es null o ya estaba definido
     */
    protected void definirApariencia(String apariencia, String imagen, Color colorDeRespaldo) {
        ValidacionesUtiles.validarDistintoDeNull(apariencia, "apariencia");
        ValidacionesUtiles.validarDistintoDeNull(colorDeRespaldo, "colorDeRespaldo");
        ValidacionesUtiles.validarFalso(this.apariencias.contains(apariencia), "La apariencia " + apariencia + " ya está definida.");
        int indice = this.apariencias.size();
        this.apariencias.add(apariencia);

        Path archivo = buscarImagen(imagen);
        Path archivoConCursor = buscarImagen(nombreConCursor(imagen));
        if (archivo != null && archivoConCursor != null) {
            this.ventana.definirCelda(simbolo(indice, false), archivo);
            this.ventana.definirCelda(simbolo(indice, true), archivoConCursor);
        } else {
            this.ventana.definirCelda(simbolo(indice, false), colorDeRespaldo);
            this.ventana.definirCelda(simbolo(indice, true), colorDeRespaldo.darker());
        }
    }

    /**
     * Dibuja una celda de la grilla con una apariencia.
     * @param celda posición de la celda, de 1 a n
     * @param apariencia nombre de una apariencia definida
     * @param conCursor true si el cursor está en esa celda
     * @throws IllegalStateException si la apariencia no está definida
     */
    protected void dibujar(Posicion celda, String apariencia, boolean conCursor) {
        int indice = this.apariencias.indexOf(apariencia);
        ValidacionesUtiles.validarEstadoFalso(indice == -1,
                "La apariencia " + apariencia + " no está definida: falta llamar a definirApariencia.");
        this.ventana.setCelda(celda.getFila(), celda.getColumna(), simbolo(indice, conCursor));
    }

//METODOS DE InterfazDeJuego ------------------------------------------------------------------------------

    @Override
    public void abrir() {
        this.ventana.mostrar();
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        this.ventana.mostrarMensaje(mensaje);
    }

    @Override
    public Accion esperarAccion() {
        Tecla tecla = this.ventana.esperarTecla();
        switch (tecla) {
            case ARRIBA:
                return Accion.MOVER_ARRIBA;
            case ABAJO:
                return Accion.MOVER_ABAJO;
            case IZQUIERDA:
                return Accion.MOVER_IZQUIERDA;
            case DERECHA:
                return Accion.MOVER_DERECHA;
            case ENTER:
            case ESPACIO:
                return Accion.JUGAR;
            case RETROCESO:
                return Accion.NUEVA_PARTIDA;
            case ESCAPE:
            case CERRAR:
                return Accion.SALIR;
            default:
                return Accion.NINGUNA;
        }
    }

    @Override
    public void close() {
        this.ventana.close();
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * El símbolo de JuegoVisual de una apariencia: dos por apariencia, normal y con cursor.
     */
    private static char simbolo(int indiceDeApariencia, boolean conCursor) {
        return (char) (PRIMER_SIMBOLO + 2 * indiceDeApariencia + (conCursor ? 1 : 0));
    }

    /**
     * @return el archivo de la imagen, o null si no hay carpeta o no existe
     */
    private Path buscarImagen(String imagen) {
        if (this.carpetaDeImagenes == null || imagen == null) {
            return null;
        }
        Path archivo = this.carpetaDeImagenes.resolve(imagen);
        return Files.isRegularFile(archivo) ? archivo : null;
    }

    /**
     * "x.png" a "x-cursor.png"
     */
    private static String nombreConCursor(String imagen) {
        if (imagen == null) {
            return null;
        }
        int punto = imagen.lastIndexOf('.');
        if (punto == -1) {
            return imagen + "-cursor";
        }
        return imagen.substring(0, punto) + "-cursor" + imagen.substring(punto);
    }
}

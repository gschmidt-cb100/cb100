package ar.uba.fi.cb100.librerias.imagen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * El panel de Swing que dibuja un {@link EstadoDelTablero}. Es la única clase
 * de la librería que pinta. Swing llama a {@link #paintComponent} cuando hace
 * falta; nosotros sólo le avisamos con {@code repaint()} que algo cambió.
 */
final class PanelDelTablero extends JPanel {

    private static final Color LINEA_DE_GRILLA = new Color(0, 0, 0, 28);
    private static final Color DESCONOCIDO = Color.MAGENTA;

    private final EstadoDelTablero estado;
    private final int tamanio;

    PanelDelTablero(EstadoDelTablero estado, int tamanioDeCelda) {
        this.estado = estado;
        this.tamanio = tamanioDeCelda;
        setPreferredSize(new Dimension(estado.getColumnas() * tamanio, estado.getFilas() * tamanio));
        setBackground(Color.WHITE);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics graficos) {
        super.paintComponent(graficos);
        Graphics2D g = (Graphics2D) graficos;
        // Pixel-art: al agrandar una imagen chica, no suavizar (quedaría borrosa).
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        synchronized (estado) {
            dibujarCeldas(g);
            dibujarPersonajes(g);
        }
    }

    private void dibujarCeldas(Graphics2D g) {
        for (int f = 1; f <= estado.getFilas(); f++) {
            for (int c = 1; c <= estado.getColumnas(); c++) {
                int x = (c - 1) * tamanio;
                int y = (f - 1) * tamanio;
                char simbolo = estado.getCelda(f, c);
                EstadoDelTablero.Apariencia apariencia = estado.aparienciaDe(simbolo);
                if (apariencia == null) {
                    // Símbolo sin apariencia definida: que se note, en vez de fallar en silencio.
                    g.setColor(DESCONOCIDO);
                    g.fillRect(x, y, tamanio, tamanio);
                    dibujarLetra(g, String.valueOf(simbolo), x, y, Color.BLACK);
                } else if (apariencia.imagen() != null) {
                    g.drawImage(apariencia.imagen(), x, y, tamanio, tamanio, null);
                } else {
                    g.setColor(apariencia.color());
                    g.fillRect(x, y, tamanio, tamanio);
                }
                g.setColor(LINEA_DE_GRILLA);
                g.drawRect(x, y, tamanio - 1, tamanio - 1);
            }
        }
    }

    private void dibujarPersonajes(Graphics2D g) {
        int margen = Math.max(2, tamanio / 8);
        int lado = tamanio - 2 * margen;
        for (EstadoDelTablero.Personaje personaje : estado.personajes()) {
            int x = (personaje.columna - 1) * tamanio + margen;
            int y = (personaje.fila - 1) * tamanio + margen;
            if (personaje.apariencia.imagen() != null) {
                g.drawImage(personaje.apariencia.imagen(), x, y, lado, lado, null);
            } else {
                g.setColor(personaje.apariencia.color());
                g.fillOval(x, y, lado, lado);
                g.setColor(Color.BLACK);
                g.drawOval(x, y, lado, lado);
                dibujarLetra(g, personaje.nombre.substring(0, 1).toUpperCase(),
                        x - margen, y - margen, Color.WHITE);
            }
        }
    }

    /** Una letra centrada en la celda cuya esquina es (x, y). */
    private void dibujarLetra(Graphics2D g, String letra, int x, int y, Color color) {
        g.setColor(color);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Math.max(10, tamanio / 2)));
        FontMetrics metricas = g.getFontMetrics();
        int ancho = metricas.stringWidth(letra);
        int alto = metricas.getAscent();
        g.drawString(letra, x + (tamanio - ancho) / 2, y + (tamanio + alto) / 2 - 2);
    }
}

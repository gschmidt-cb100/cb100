package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JPanel;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;

/**
 * Punto 5: dibuja el grafo de la familia de una persona. Cada persona es un
 * nodo, ubicado en la fila de su generación, y hay una flecha de cada padre
 * o madre a cada hijo. El número sobre cada nodo es su lugar en el orden
 * topológico: todo padre tiene un número menor que sus hijos.
 */
class PanelDeGrafo extends JPanel {

    private static final int ANCHO_DE_NODO = 118;
    private static final int ALTO_DE_NODO = 40;
    private static final int SEPARACION_VERTICAL = 110;
    private static final int MARGEN = 30;
    private static final Color AZUL = new Color(0x1D4E89);
    private static final Color NARANJA = new Color(0xC77D1A);
    private static final Color GRIS = new Color(0x9FB0C3);

    private final GrafoFamiliar grafo;
    private final long dniCentral;
    private final List<Long> ordenTopologico;
    private final Map<Long, Integer> nivelDe = new HashMap<>();
    private final Map<Integer, List<Long>> porNivel = new TreeMap<>();

    PanelDeGrafo(GrafoFamiliar grafo, long dniCentral, Set<Long> familia) {
        this.grafo = grafo;
        this.dniCentral = dniCentral;
        this.ordenTopologico = grafo.ordenarTopologicamente(familia);
        calcularNiveles(familia);
        int masAncho = porNivel.values().stream().mapToInt(List::size).max().orElse(1);
        setPreferredSize(new Dimension(Math.max(900, masAncho * (ANCHO_DE_NODO + 16) + 2 * MARGEN),
                porNivel.size() * SEPARACION_VERTICAL + 2 * MARGEN));
        setBackground(Color.WHITE);
    }

    List<Long> getOrdenTopologico() {
        return List.copyOf(ordenTopologico);
    }

    /** El nivel de cada persona es uno más que el mayor nivel de sus padres: se calcula en orden topológico. */
    private void calcularNiveles(Set<Long> familia) {
        for (long dni : ordenTopologico) {
            int nivel = 0;
            for (long padre : grafo.getPadresDe(dni)) {
                if (familia.contains(padre)) {
                    nivel = Math.max(nivel, nivelDe.get(padre) + 1);
                }
            }
            nivelDe.put(dni, nivel);
            porNivel.computeIfAbsent(nivel, n -> new ArrayList<>()).add(dni);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D lienzo = (Graphics2D) g;
        lienzo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Map<Long, int[]> centros = ubicar();

        lienzo.setStroke(new BasicStroke(1.4f));
        for (long dni : ordenTopologico) {
            for (long hijo : grafo.getHijosDe(dni)) {
                if (centros.containsKey(hijo)) {
                    dibujarFlecha(lienzo, centros.get(dni), centros.get(hijo));
                }
            }
        }
        for (int i = 0; i < ordenTopologico.size(); i++) {
            long dni = ordenTopologico.get(i);
            dibujarNodo(lienzo, grafo.getPersona(dni), centros.get(dni), i + 1);
        }
    }

    private Map<Long, int[]> ubicar() {
        Map<Long, int[]> centros = new HashMap<>();
        for (Map.Entry<Integer, List<Long>> nivel : porNivel.entrySet()) {
            List<Long> personas = nivel.getValue();
            int paso = (getWidth() - 2 * MARGEN) / personas.size();
            for (int i = 0; i < personas.size(); i++) {
                centros.put(personas.get(i), new int[]{MARGEN + paso * i + paso / 2,
                        MARGEN + ALTO_DE_NODO + nivel.getKey() * SEPARACION_VERTICAL});
            }
        }
        return centros;
    }

    private void dibujarNodo(Graphics2D lienzo, Persona persona, int[] centro, int numeroDeOrden) {
        int x = centro[0] - ANCHO_DE_NODO / 2;
        int y = centro[1] - ALTO_DE_NODO / 2;
        boolean esLaCentral = persona.dni() == dniCentral;
        lienzo.setColor(esLaCentral ? new Color(0xFBEFDD) : new Color(0xEAF0F7));
        lienzo.fillRoundRect(x, y, ANCHO_DE_NODO, ALTO_DE_NODO, 14, 14);
        lienzo.setColor(esLaCentral ? NARANJA : AZUL);
        lienzo.setStroke(new BasicStroke(esLaCentral ? 2.6f : 1.6f));
        lienzo.drawRoundRect(x, y, ANCHO_DE_NODO, ALTO_DE_NODO, 14, 14);

        lienzo.setColor(Color.DARK_GRAY);
        lienzo.setFont(getFont().deriveFont(Font.BOLD, 11f));
        centrar(lienzo, persona.nombre() + " " + persona.apellido(), centro[0], centro[1] - 2);
        lienzo.setFont(getFont().deriveFont(10f));
        centrar(lienzo, persona.edad() + " años", centro[0], centro[1] + 12);
        lienzo.setColor(NARANJA);
        lienzo.setFont(getFont().deriveFont(Font.BOLD, 10f));
        lienzo.drawString("#" + numeroDeOrden, x + 4, y - 3);
    }

    private static void centrar(Graphics2D lienzo, String texto, int x, int y) {
        FontMetrics medidas = lienzo.getFontMetrics();
        String visible = texto;
        while (medidas.stringWidth(visible) > ANCHO_DE_NODO - 8 && visible.length() > 4) {
            visible = visible.substring(0, visible.length() - 2);
        }
        lienzo.drawString(visible, x - medidas.stringWidth(visible) / 2, y);
    }

    private static void dibujarFlecha(Graphics2D lienzo, int[] desde, int[] hasta) {
        int x1 = desde[0];
        int y1 = desde[1] + ALTO_DE_NODO / 2;
        int x2 = hasta[0];
        int y2 = hasta[1] - ALTO_DE_NODO / 2 - 12;
        lienzo.setColor(GRIS);
        lienzo.drawLine(x1, y1, x2, y2);
        double angulo = Math.atan2(y2 - y1, x2 - x1);
        int largo = 8;
        lienzo.fillPolygon(
                new int[]{x2, (int) (x2 - largo * Math.cos(angulo - 0.4)), (int) (x2 - largo * Math.cos(angulo + 0.4))},
                new int[]{y2, (int) (y2 - largo * Math.sin(angulo - 0.4)), (int) (y2 - largo * Math.sin(angulo + 0.4))}, 3);
    }
}

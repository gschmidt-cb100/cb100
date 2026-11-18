package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;

/**
 * Punto 5 completo: el dibujo del grafo familiar y, debajo, los tres
 * recorridos con el orden de visita de los nodos.
 */
class VentanaDeGrafo extends JDialog {

    /** Hasta cuántas generaciones hacia arriba y hacia abajo se dibujan, y cuántas personas como máximo. */
    static final int NIVELES = 2;
    static final int MAXIMO_DE_PERSONAS = 24;

    VentanaDeGrafo(Frame duenio, Persona persona, GrafoFamiliar grafo) {
        super(duenio, "Grafo familiar de " + persona.getNombreCompleto(), true);
        Set<Long> familia = grafo.getFamiliaDe(persona.dni(), NIVELES, MAXIMO_DE_PERSONAS);
        PanelDeGrafo dibujo = new PanelDeGrafo(grafo, persona.dni(), familia);

        JTextArea recorridos = new JTextArea();
        recorridos.setEditable(false);
        recorridos.setLineWrap(true);
        recorridos.setWrapStyleWord(true);
        recorridos.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        recorridos.setText(
                "5.1 En PROFUNDIDAD desde " + persona.nombre() + " (baja por los hijos, agota una rama antes de pasar a la otra):\n   "
                + nombres(grafo, grafo.recorrerEnProfundidad(persona.dni()))
                + "\n\n5.1 En ANCHURA desde " + persona.nombre() + " (primero todos los hijos, después todos los nietos):\n   "
                + nombres(grafo, grafo.recorrerEnAnchura(persona.dni()))
                + "\n\n5.2 TOPOLÓGICO de la familia dibujada (todo padre antes que sus hijos; es el número # de cada nodo):\n   "
                + nombres(grafo, dibujo.getOrdenTopologico()));

        JScrollPane panelDeRecorridos = new JScrollPane(recorridos);
        panelDeRecorridos.setPreferredSize(new Dimension(900, 190));
        add(new JScrollPane(dibujo), BorderLayout.CENTER);
        add(panelDeRecorridos, BorderLayout.SOUTH);
        setSize(1000, 700);
        setLocationRelativeTo(duenio);
    }

    private static String nombres(GrafoFamiliar grafo, List<Long> dnis) {
        return dnis.stream()
                .map(dni -> grafo.getPersona(dni).nombre() + " " + grafo.getPersona(dni).apellido())
                .collect(Collectors.joining("  →  "));
    }
}

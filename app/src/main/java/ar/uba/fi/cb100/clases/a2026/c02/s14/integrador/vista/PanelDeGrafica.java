package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.function.ToDoubleFunction;

import javax.swing.JPanel;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.Medicion;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.TipoDeIndice;

/**
 * Punto 7: la gráfica que muestra todo. Tres gráficos de barras, uno por
 * magnitud (memoria, tiempo de armado, tiempo de búsqueda); en cada uno, un
 * grupo de barras por campo y una barra por estructura.
 */
class PanelDeGrafica extends JPanel {

    private static final Color[] COLORES = {new Color(0x1D4E89), new Color(0xC77D1A), new Color(0x1F7A54)};
    private static final int MARGEN = 46;
    private static final int ALTO_DEL_TITULO = 34;

    private final List<Medicion> mediciones;

    PanelDeGrafica(List<Medicion> mediciones) {
        this.mediciones = List.copyOf(mediciones);
        setPreferredSize(new Dimension(1080, 430));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D lienzo = (Graphics2D) g;
        lienzo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ancho = getWidth() / 3;
        dibujar(lienzo, 0, ancho, "Memoria del índice (KB)", Medicion::getKilobytes, "%.0f");
        dibujar(lienzo, ancho, ancho, "Tiempo de armado (ms)", Medicion::getMilisegundosDeConstruccion, "%.1f");
        dibujar(lienzo, 2 * ancho, ancho, "Tiempo por búsqueda (µs)", Medicion::getMicrosegundosPorBusqueda, "%.1f");
        dibujarLeyenda(lienzo);
    }

    private void dibujar(Graphics2D lienzo, int x0, int ancho, String titulo,
                         ToDoubleFunction<Medicion> valorDe, String formato) {
        double maximo = mediciones.stream().mapToDouble(valorDe).max().orElse(1);
        int base = getHeight() - MARGEN - 24;
        int alto = base - ALTO_DEL_TITULO - 30;

        lienzo.setColor(Color.DARK_GRAY);
        lienzo.setFont(getFont().deriveFont(Font.BOLD, 13f));
        centrar(lienzo, titulo, x0 + ancho / 2, ALTO_DEL_TITULO);
        lienzo.setColor(Color.LIGHT_GRAY);
        lienzo.drawLine(x0 + MARGEN / 2, base, x0 + ancho - MARGEN / 2, base);

        CampoDeBusqueda[] campos = CampoDeBusqueda.values();
        TipoDeIndice[] tipos = TipoDeIndice.values();
        int anchoDelGrupo = (ancho - MARGEN) / campos.length;
        int anchoDeBarra = (anchoDelGrupo - 14) / tipos.length;
        for (int c = 0; c < campos.length; c++) {
            int xGrupo = x0 + MARGEN / 2 + c * anchoDelGrupo + 7;
            for (int t = 0; t < tipos.length; t++) {
                double valor = valorDe.applyAsDouble(buscar(tipos[t], campos[c]));
                int altoDeBarra = (int) Math.max(1, Math.round(alto * valor / maximo));
                int x = xGrupo + t * anchoDeBarra;
                lienzo.setColor(COLORES[t]);
                lienzo.fillRect(x, base - altoDeBarra, anchoDeBarra - 3, altoDeBarra);
                lienzo.setColor(Color.DARK_GRAY);
                lienzo.setFont(getFont().deriveFont(9f));
                centrar(lienzo, String.format(formato, valor), x + (anchoDeBarra - 3) / 2, base - altoDeBarra - 4);
            }
            lienzo.setFont(getFont().deriveFont(Font.BOLD, 11f));
            centrar(lienzo, campos[c].getTitulo(), xGrupo + (anchoDelGrupo - 14) / 2, base + 16);
        }
    }

    private void dibujarLeyenda(Graphics2D lienzo) {
        TipoDeIndice[] tipos = TipoDeIndice.values();
        int x = MARGEN;
        int y = getHeight() - 14;
        lienzo.setFont(getFont().deriveFont(11f));
        for (int t = 0; t < tipos.length; t++) {
            lienzo.setColor(COLORES[t]);
            lienzo.fillRect(x, y - 10, 12, 12);
            lienzo.setColor(Color.DARK_GRAY);
            String texto = tipos[t].getTitulo() + ": " + tipos[t].getDescripcion();
            lienzo.drawString(texto, x + 17, y);
            x += lienzo.getFontMetrics().stringWidth(texto) + 44;
        }
    }

    private Medicion buscar(TipoDeIndice tipo, CampoDeBusqueda campo) {
        return mediciones.stream()
                .filter(medicion -> medicion.tipo() == tipo && medicion.campo() == campo)
                .findFirst().orElseThrow();
    }

    private static void centrar(Graphics2D lienzo, String texto, int x, int y) {
        FontMetrics medidas = lienzo.getFontMetrics();
        lienzo.drawString(texto, x - medidas.stringWidth(texto) / 2, y);
    }
}

package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.CatalogoDeIndices;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.EnlaceDeMapas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.FiltroPorFuerzaBruta;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ResultadoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.TipoDeIndice;

/**
 * La pantalla del sistema. No tiene lógica propia: cada botón le pide el
 * trabajo a una clase funcional (el catálogo de índices, el filtro por
 * fuerza bruta, el grafo, el enlace de mapas) y muestra lo que le devuelven.
 * Por eso el mismo sistema corre también desde {@code PrincipalDeConsola}.
 */
public class VentanaPrincipal extends JFrame {

    private final List<Persona> personas;
    private final CatalogoDeIndices catalogo;
    private final GrafoFamiliar grafo;

    private final ModeloDeGrilla modelo = new ModeloDeGrilla();
    private final JTable grilla = new JTable(modelo);
    private final JTextArea tiempos = new JTextArea(6, 60);
    private final JComboBox<CampoDeBusqueda> campo = new JComboBox<>(CampoDeBusqueda.values());
    private final JComboBox<TipoDeIndice> tipo = new JComboBox<>(TipoDeIndice.values());
    private final JTextField textoDelIndice = new JTextField(14);
    private final JComboBox<FiltroPorFuerzaBruta.Campo> campoDeFuerzaBruta =
            new JComboBox<>(FiltroPorFuerzaBruta.Campo.values());
    private final JTextField textoDeFuerzaBruta = new JTextField(14);

    public VentanaPrincipal(List<Persona> personas, CatalogoDeIndices catalogo, GrafoFamiliar grafo) {
        super("CB100 - Integrador: " + personas.size() + " personas");
        this.personas = List.copyOf(personas);
        this.catalogo = catalogo;
        this.grafo = grafo;

        add(armarFiltros(), BorderLayout.NORTH);
        add(new JScrollPane(grilla), BorderLayout.CENTER);
        add(armarPie(), BorderLayout.SOUTH);

        grilla.setAutoCreateRowSorter(true);
        grilla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    abrirFicha();
                }
            }
        });
        mostrar(new ResultadoDeBusqueda("todas las personas", personas, 0), List.of());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationRelativeTo(null);
    }

    // ---------------------------------------------------------------- armado

    private JPanel armarFiltros() {
        JPanel conIndice = new JPanel(new FlowLayout(FlowLayout.LEFT));
        conIndice.setBorder(BorderFactory.createTitledBorder("Filtro con índice (el texto es el comienzo del campo)"));
        conIndice.add(new JLabel("Campo:"));
        conIndice.add(campo);
        conIndice.add(new JLabel("Índice:"));
        conIndice.add(tipo);
        conIndice.add(textoDelIndice);
        conIndice.add(boton("Buscar", this::buscarConIndice));
        textoDelIndice.addActionListener(evento -> buscarConIndice());

        JPanel fuerzaBruta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fuerzaBruta.setBorder(BorderFactory.createTitledBorder("Filtro por fuerza bruta (recorre todo; el texto puede estar en cualquier parte)"));
        fuerzaBruta.add(new JLabel("Campo:"));
        fuerzaBruta.add(campoDeFuerzaBruta);
        fuerzaBruta.add(textoDeFuerzaBruta);
        fuerzaBruta.add(boton("Filtrar", this::filtrarPorFuerzaBruta));
        fuerzaBruta.add(boton("Ver todas", () -> mostrar(new ResultadoDeBusqueda("todas las personas", personas, 0), List.of())));
        textoDeFuerzaBruta.addActionListener(evento -> filtrarPorFuerzaBruta());

        JPanel filtros = new JPanel(new GridLayout(2, 1));
        filtros.add(conIndice);
        filtros.add(fuerzaBruta);
        return filtros;
    }

    private JPanel armarPie() {
        tiempos.setEditable(false);
        tiempos.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(boton("Ficha", this::abrirFicha));
        botones.add(boton("Grafo familiar", this::abrirGrafo));
        botones.add(boton("Gráfica de índices", this::abrirGrafica));
        botones.add(boton("Google Maps", this::abrirMapa));
        botones.add(new JLabel("   (doble clic en una fila abre la ficha; Ctrl o Shift para elegir varias)"));

        JPanel pie = new JPanel(new BorderLayout());
        pie.add(botones, BorderLayout.NORTH);
        pie.add(new JScrollPane(tiempos), BorderLayout.CENTER);
        return pie;
    }

    private static JButton boton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(evento -> accion.run());
        return boton;
    }

    // ---------------------------------------------------------------- acciones

    /** Punto 3: busca con el índice elegido, y muestra los tiempos de los tres para comparar. */
    private void buscarConIndice() {
        CampoDeBusqueda campoElegido = (CampoDeBusqueda) campo.getSelectedItem();
        TipoDeIndice tipoElegido = (TipoDeIndice) tipo.getSelectedItem();
        String prefijo = textoDelIndice.getText();
        List<ResultadoDeBusqueda> losTres = catalogo.buscarConTodos(campoElegido, prefijo);
        mostrar(losTres.get(tipoElegido.ordinal()), losTres);
    }

    /** Punto 3: filtra recorriendo todas las personas. */
    private void filtrarPorFuerzaBruta() {
        ResultadoDeBusqueda resultado = FiltroPorFuerzaBruta.filtrar(personas,
                (FiltroPorFuerzaBruta.Campo) campoDeFuerzaBruta.getSelectedItem(), textoDeFuerzaBruta.getText());
        mostrar(resultado, List.of());
    }

    /** Punto 2: vuelca el resultado en la grilla y los tiempos debajo. */
    private void mostrar(ResultadoDeBusqueda resultado, List<ResultadoDeBusqueda> comparacion) {
        modelo.mostrar(resultado.personas());
        StringBuilder texto = new StringBuilder(String.format("%d coincidencias con %s en %.3f ms%n",
                resultado.getCantidad(), resultado.metodo(), resultado.getMilisegundos()));
        for (ResultadoDeBusqueda otro : comparacion) {
            texto.append(String.format("   %-36s %10.3f ms%n", otro.metodo(), otro.getMilisegundos()));
        }
        tiempos.setText(texto.toString());
    }

    /** Punto 4. */
    private void abrirFicha() {
        List<Persona> elegidas = getSeleccionadas();
        if (hayUnaSola(elegidas)) {
            new FichaDePersona(this, elegidas.get(0), grafo).setVisible(true);
        }
    }

    /** Punto 5. */
    private void abrirGrafo() {
        List<Persona> elegidas = getSeleccionadas();
        if (hayUnaSola(elegidas)) {
            new VentanaDeGrafo(this, elegidas.get(0), grafo).setVisible(true);
        }
    }

    /** Punto 7. */
    private void abrirGrafica() {
        JDialog dialogo = new JDialog(this, "Lo que cuesta cada índice (" + personas.size() + " personas)", true);
        dialogo.add(new PanelDeGrafica(catalogo.getMediciones()));
        dialogo.pack();
        dialogo.setMinimumSize(new Dimension(900, 400));
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    /** Punto 8. */
    private void abrirMapa() {
        List<Persona> elegidas = getSeleccionadas();
        if (elegidas.isEmpty() || elegidas.size() > EnlaceDeMapas.MAXIMO_DE_PUNTOS) {
            avisar("Elegí entre 1 y " + EnlaceDeMapas.MAXIMO_DE_PUNTOS + " personas de la grilla.");
            return;
        }
        String enlace = EnlaceDeMapas.crear(elegidas);
        tiempos.setText("Google Maps: " + enlace);
        try {
            Desktop.getDesktop().browse(URI.create(enlace));
        } catch (Exception e) {
            avisar("No se pudo abrir el navegador. El enlace quedó escrito abajo para copiarlo.");
        }
    }

    private List<Persona> getSeleccionadas() {
        List<Persona> elegidas = new ArrayList<>();
        for (int fila : grilla.getSelectedRows()) {
            elegidas.add(modelo.getPersona(grilla.convertRowIndexToModel(fila)));
        }
        return elegidas;
    }

    private boolean hayUnaSola(List<Persona> elegidas) {
        if (elegidas.size() != 1) {
            avisar("Elegí una persona de la grilla.");
        }
        return elegidas.size() == 1;
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}

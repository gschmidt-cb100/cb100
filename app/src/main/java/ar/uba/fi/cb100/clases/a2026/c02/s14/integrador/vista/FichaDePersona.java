package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;

/** Punto 4: la ficha con todos los datos de una persona, incluidos los nombres de sus padres e hijos. */
class FichaDePersona extends JDialog {

    FichaDePersona(Frame duenio, Persona persona, GrafoFamiliar grafo) {
        super(duenio, "Ficha de " + persona.getNombreCompleto(), true);
        JPanel datos = new JPanel(new GridLayout(0, 2, 12, 6));
        datos.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        agregar(datos, "DNI", String.valueOf(persona.dni()));
        agregar(datos, "Nombre", persona.nombre());
        agregar(datos, "Apellido", persona.apellido());
        agregar(datos, "Edad", persona.edad() + " años");
        agregar(datos, "Ciudad", persona.ciudad());
        agregar(datos, "Provincia", persona.provincia());
        agregar(datos, "País", persona.pais());
        agregar(datos, "Teléfono", persona.telefono());
        agregar(datos, "Email", persona.email());
        agregar(datos, "Dirección", persona.direccion());
        agregar(datos, "Código postal", persona.codigoPostal());
        agregar(datos, "Geocodificación", persona.geocodificacion().aTexto());
        agregar(datos, "Padre", describir(persona.dniDelPadre(), grafo));
        agregar(datos, "Madre", describir(persona.dniDeLaMadre(), grafo));
        agregar(datos, "Hijos", nombres(grafo.getHijosDe(persona.dni()), grafo));

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(evento -> dispose());
        JPanel pie = new JPanel();
        pie.add(cerrar);

        add(datos, BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(duenio);
    }

    private static void agregar(JPanel panel, String titulo, String valor) {
        JLabel etiqueta = new JLabel(titulo + ":");
        etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD));
        panel.add(etiqueta);
        panel.add(new JLabel(valor));
    }

    private static String describir(Long dni, GrafoFamiliar grafo) {
        if (dni == null) {
            return "(desconocido)";
        }
        return grafo.contiene(dni) ? grafo.getPersona(dni).getNombreCompleto() + " (" + dni + ")"
                                   : dni + " (no está en el archivo)";
    }

    private static String nombres(List<Long> dnis, GrafoFamiliar grafo) {
        return dnis.isEmpty() ? "(ninguno)"
                : dnis.stream().map(dni -> grafo.getPersona(dni).nombre()).collect(Collectors.joining(", "));
    }
}

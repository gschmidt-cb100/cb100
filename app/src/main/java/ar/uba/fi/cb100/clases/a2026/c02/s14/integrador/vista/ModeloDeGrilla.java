package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/** Los datos de la grilla: una fila por persona, con DNI, nombre y apellido. */
class ModeloDeGrilla extends AbstractTableModel {

    private static final String[] COLUMNAS = {"DNI", "Nombre", "Apellido"};

    private List<Persona> personas = List.of();

    /** post: la grilla pasa a mostrar esas personas. */
    void mostrar(List<Persona> nuevas) {
        personas = List.copyOf(nuevas);
        fireTableDataChanged();
    }

    Persona getPersona(int fila) {
        return personas.get(fila);
    }

    @Override
    public int getRowCount() {
        return personas.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int columna) {
        return COLUMNAS[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Persona persona = personas.get(fila);
        return switch (columna) {
            case 0 -> persona.dni();
            case 1 -> persona.nombre();
            default -> persona.apellido();
        };
    }
}

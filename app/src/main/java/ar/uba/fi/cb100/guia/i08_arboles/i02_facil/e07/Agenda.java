package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e07;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * e07: agenda de contactos siempre en orden alfabético. Como el
 * {@link TreeMap} mantiene las claves ordenadas, listar los contactos
 * que empiezan con una letra es un simple {@code subMap} entre esa
 * letra y la siguiente.
 */
public class Agenda {

    private final TreeMap<String, String> contactos = new TreeMap<>();

    /**
     * Agrega un contacto (o actualiza su teléfono si ya existía).
     *
     * @param nombre   nombre del contacto
     * @param telefono número de teléfono
     */
    public void agregar(String nombre, String telefono) {
        contactos.put(nombre, telefono);
    }

    /**
     * Quita un contacto de la agenda. Si no estaba, no hace nada.
     *
     * @param nombre nombre del contacto a quitar
     */
    public void quitar(String nombre) {
        contactos.remove(nombre);
    }

    /**
     * Devuelve el teléfono del contacto, o {@code null} si no está en la agenda.
     *
     * @param nombre nombre del contacto
     * @return teléfono o {@code null}
     */
    public String telefonoDe(String nombre) {
        return contactos.get(nombre);
    }

    /**
     * Devuelve la vista de los contactos cuyo nombre empieza con {@code letra},
     * en orden alfabético.
     *
     * @param letra inicial buscada
     * @return submapa con los contactos que empiezan con esa letra
     */
    public SortedMap<String, String> conInicial(char letra) {
        // Todo nombre que empieza con 'M' está entre "M" (inclusive) y "N" (exclusive).
        String desde = String.valueOf(letra);
        String hasta = String.valueOf((char) (letra + 1));
        return contactos.subMap(desde, hasta);
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.agregar("Marta", "11-5555-1234");
        agenda.agregar("Miguel", "11-4444-9876");
        agenda.agregar("Ana", "11-3333-0000");
        System.out.println("Teléfono de Marta: " + agenda.telefonoDe("Marta"));
        System.out.println("Contactos con M: " + agenda.conInicial('M'));
        agenda.quitar("Miguel");
        System.out.println("Contactos con M tras quitar a Miguel: " + agenda.conInicial('M'));
    }
}

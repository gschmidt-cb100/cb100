package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e07;

import java.util.HashMap;
import java.util.Map;

/**
 * e07: agenda de contactos (nombre → teléfono) encapsulando un {@link Map}.
 * La búsqueda por nombre es O(1) promedio; la búsqueda inversa por teléfono
 * obliga a recorrer todas las entradas (O(n)): el mapa es rápido en un solo sentido.
 */
public class Agenda {

    /** Estado interno: nombre → teléfono. */
    private final Map<String, String> contactos = new HashMap<>();

    /**
     * Agrega un contacto. Si el nombre ya existía, reemplaza su teléfono.
     *
     * @param nombre   nombre del contacto
     * @param telefono teléfono asociado
     */
    public void agregar(String nombre, String telefono) {
        contactos.put(nombre, telefono);
    }

    /**
     * Quita el contacto con ese nombre. Si no existe, no hace nada.
     *
     * @param nombre nombre del contacto a quitar
     */
    public void quitar(String nombre) {
        contactos.remove(nombre);
    }

    /**
     * Devuelve el teléfono de {@code nombre}, o {@code null} si no está en la agenda.
     *
     * @param nombre nombre del contacto
     * @return teléfono, o {@code null}
     */
    public String telefonoDe(String nombre) {
        return contactos.get(nombre);
    }

    /**
     * Búsqueda inversa: devuelve el nombre de quien tiene ese teléfono,
     * o {@code null} si nadie lo tiene. Cuesta O(n) porque hay que
     * recorrer todas las entradas.
     *
     * @param telefono teléfono a buscar
     * @return nombre del dueño, o {@code null}
     */
    public String duenioDe(String telefono) {
        for (Map.Entry<String, String> entrada : contactos.entrySet()) {
            if (entrada.getValue().equals(telefono)) {
                return entrada.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.agregar("Ana", "11-5555-0001");
        agenda.agregar("Bruno", "11-5555-0002");
        System.out.println("Telefono de Ana: " + agenda.telefonoDe("Ana"));
        System.out.println("Duenio de 11-5555-0002: " + agenda.duenioDe("11-5555-0002"));
        agenda.quitar("Ana");
        System.out.println("Telefono de Ana tras quitarla: " + agenda.telefonoDe("Ana"));
    }
}

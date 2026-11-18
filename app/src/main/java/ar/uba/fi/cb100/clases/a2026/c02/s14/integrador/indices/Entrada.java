package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/** Un renglón de índice: la clave ya normalizada y la persona a la que lleva. */
record Entrada(String clave, Persona persona) implements Comparable<Entrada> {

    @Override
    public int compareTo(Entrada otra) {
        int porClave = clave.compareTo(otra.clave);
        return porClave != 0 ? porClave : Long.compare(persona.dni(), otra.persona.dni());
    }
}

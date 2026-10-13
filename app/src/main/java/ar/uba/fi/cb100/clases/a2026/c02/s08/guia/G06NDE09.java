package ar.uba.fi.cb100.clases.a2026.c02.s08.guia;

import ar.uba.fi.cb100.clases.a2026.c02.s07.p01.Programador;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class G06NDE09 {

    public static void main(String[] args) {


        HashMap<String, List<Programador>> programadoresPorLenguaje = new HashMap<>();

        programadoresPorLenguaje.put("Java", new LinkedList<>());
        programadoresPorLenguaje.put("Pascal", new LinkedList<>());
        programadoresPorLenguaje.put("C++", new LinkedList<>());


        programadoresPorLenguaje.get("Java").add(new Programador("Juan"));
        programadoresPorLenguaje.get("Java").add(new Programador("Gustavo"));
        programadoresPorLenguaje.get("Java").add(new Programador("Jose"));


        programadoresPorLenguaje.get("Pascal").add(new Programador("Julian"));
        programadoresPorLenguaje.get("Pascal").add(new Programador("Maria"));

        programadoresPorLenguaje.get("C++").add(new Programador("Ana"));
        programadoresPorLenguaje.get("C++").add(new Programador("Ricardo"));
    }
}

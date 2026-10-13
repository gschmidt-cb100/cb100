package ar.uba.fi.cb100.clases.a2026.c02.s08;

import ar.uba.fi.cb100.clases.a2026.c02.s08.tda.EntradaDeCableDeTelevisor;
import ar.uba.fi.cb100.clases.a2026.c02.s08.tda.EntradaDeTelevisor;
import ar.uba.fi.cb100.clases.a2026.c02.s08.tda.EntradaHDMIDeTelevisor;

import java.util.TreeSet;

public class Principal08 {

    public static void main(String[] args) {
        {
            TreeSet<EntradaDeTelevisor> arbol = new TreeSet<>();
            arbol.add(new EntradaDeCableDeTelevisor(140));
            arbol.add(new EntradaHDMIDeTelevisor(1));
        }
        {
            TreeSet<Object> arbol = new TreeSet<>();
        }
    }
}

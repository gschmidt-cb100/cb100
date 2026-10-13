package ar.uba.fi.cb100.clases.a2026.c02.s08.guia;

import java.util.HashMap;

public class G06E02 {

    public static int primerDuplicado(int[] a) {
        HashMap<Integer, Boolean> vistos = new HashMap<>();
        for(int i = 0; i<a.length; i++) {
            if(vistos.containsKey(a[i])) {
                return a[i];
            }
            vistos.put(a[i], true);
        }
        return -1; // Retorna -1 si no hay duplicados
    }
}

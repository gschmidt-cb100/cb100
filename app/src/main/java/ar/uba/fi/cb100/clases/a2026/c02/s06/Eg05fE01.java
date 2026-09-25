package ar.uba.fi.cb100.clases.a2026.c02.s06;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Eg05fE01 {

    public static void main() {

        List<String> nombres = List.of("Juan", "María", "Pedro", "Ana", "Luis");

        List<String> invertido = new LinkedList<>();

        //Invierto la lista de nombres usando un bucle for-each y agregando cada elemento al principio de la nueva lista
        for(String nombre : nombres) {
            invertido.addFirst(nombre);

//            invertido.addLast(nombre);
//            invertido.add(nombre);
        }

        System.out.println("Lista original: " + Arrays.toString(nombres.toArray()));
        System.out.println("Lista invertida: " + Arrays.toString(invertido.toArray()));

        System.out.println("Lista invertida: " + nombres.reversed());
    }
}

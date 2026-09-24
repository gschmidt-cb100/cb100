package ar.uba.fi.cb100.clases.a2026.c02.s04.tarea.Piano;
import java.util.Vector;

public class PianoMain {
    public static void main (String[] args){

        //teclas del piano
        Tecla teclaDo = new Tecla("Do"); //Tecla(nota) quedo definido en el constructor
        Tecla teclaRe = new Tecla("Re");
        Tecla teclaMi = new Tecla("Mi");

        Vector<Tecla> teclas = new Vector<Tecla>();
        teclas.add(teclaDo);
        teclas.add(teclaRe);
        teclas.add(teclaMi);

        Piano piano = new Piano(teclas);

        //ahí medio que creamos el miano con 3 teclas

        System.out.println("Presiones totales al crear el piano: " + piano.obtenerCantTotalDePresiones()); // 0

        System.out.println();
        System.out.println();
        System.out.println("Tocamos: Do, Re, Do, Mi, Do");
        piano.presionar(teclaDo);
        piano.presionar(teclaRe);
        piano.presionar(teclaDo);
        piano.presionar(teclaMi);
        piano.presionar(teclaDo);

        System.out.println();
        System.out.println("Melodía interpretada: " + piano.obtenerMelodiaInterpretada());
        // esperamos [Do, Re, Do, Mi, Do]

        System.out.println("Tecla más presionada: " + piano.obtenerMasPresionada());
        // esperamos Do


        System.out.println("Cantidad total de presiones: "
                + piano.obtenerCantTotalDePresiones());
        // esperado: 5

        // Probamos también el caso de error: presionar una tecla que no es del piano
        System.out.println();
        System.out.println("Probamos presionar una tecla que no pertenece al piano:");

        Tecla teclaFa = new Tecla("Fa");

        try {
            piano.presionar(teclaFa);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }



    }
}

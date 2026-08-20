package ar.uba.fi.cb100.material.i01_intro;

/**
 * Recorrido por los métodos de {@link StringBuilder}, que a diferencia de
 * String es <b>mutable</b>: modifica el mismo objeto.
 */
public class MetodosDeStringBuilder {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Hola");
        sb.append(" Mundo");             // "Hola Mundo"
        sb.insert(0, ">> ");             // ">> Hola Mundo"
        sb.replace(3, 7, "Chau");        // ">> Chau Mundo"
        sb.deleteCharAt(0);              // "> Chau Mundo"
        sb.delete(0, 2);                 // "Chau Mundo"

        System.out.println("length: " + sb.length());
        System.out.println("charAt(0): " + sb.charAt(0));
        sb.setCharAt(0, 'X');            // "Xhau Mundo"
        System.out.println("indexOf('Mundo'): " + sb.indexOf("Mundo"));

        sb.reverse();
        System.out.println("reverse: " + sb);
        sb.reverse();                    // lo dejamos como estaba
        System.out.println("final: " + sb);
    }
}

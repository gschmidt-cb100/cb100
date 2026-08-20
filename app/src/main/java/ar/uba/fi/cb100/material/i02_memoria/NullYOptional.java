package ar.uba.fi.cb100.material.i02_memoria;

import java.util.Optional;

/**
 * {@code null} es una referencia que no apunta a ningún objeto. Usarla lanza
 * {@link NullPointerException}. {@link Optional} permite expresar "puede no
 * haber valor" de forma explícita, sin null sueltos.
 */
public class NullYOptional {

    static Optional<String> buscarNombre(int id) {
        return id == 1 ? Optional.of("Ada") : Optional.empty();
    }

    public static void main(String[] args) {
        String s = null;
        try {
            s.length();                       // dereferenciar null
        } catch (NullPointerException e) {
            System.out.println("NullPointerException atrapada");
        }

        // Optional evita el null y obliga a pensar el caso "no hay valor".
        String nombre = buscarNombre(1).orElse("desconocido");
        String otro   = buscarNombre(2).orElse("desconocido");
        System.out.println(nombre + " / " + otro);     // Ada / desconocido

        buscarNombre(1).ifPresent(x -> System.out.println("encontrado: " + x));
    }
}

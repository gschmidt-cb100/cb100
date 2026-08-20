package ar.uba.fi.cb100.material.i12_profesional;

import java.util.List;
import java.util.Optional;

/**
 * Los streams son <b>perezosos</b>: las operaciones intermedias no ejecutan
 * NADA hasta que llega la terminal — y entonces los elementos fluyen DE A
 * UNO por todo el pipeline, no etapa por etapa.
 * <p>
 * Este demo lo hace visible con prints: buscamos el primer número par mayor
 * que 10. Si el pipeline procesara "toda la lista en cada etapa", veríamos
 * 8 filtrados y 8 mapeos; como es perezoso y {@code findFirst} corta, sólo
 * se procesan los elementos necesarios.
 */
public final class PipelinePerezoso {

    private PipelinePerezoso() {}

    public static void main(String[] args) {
        List<Integer> numeros = List.of(3, 7, 12, 5, 18, 4, 21, 30);

        System.out.println("-- sin terminal: no pasa NADA --");
        numeros.stream()
                .filter(n -> {
                    System.out.println("  ¿es par? " + n);
                    return n % 2 == 0;
                });                                    // sin terminal: cero prints

        System.out.println("-- con findFirst: fluye de a uno y CORTA --");
        Optional<Integer> primero = numeros.stream()
                .filter(n -> {
                    System.out.println("  ¿es par? " + n);
                    return n % 2 == 0;                 // 3 no, 7 no, 12 sí...
                })
                .map(n -> {
                    System.out.println("  duplico " + n);
                    return n * 2;                      // ...12 se duplica...
                })
                .findFirst();                          // ...y el pipeline CORTA
        System.out.println("resultado: " + primero.orElse(-1));   // 24
        // Sólo se evaluaron 3, 7 y 12: los otros cinco números ni se miraron.
    }
}

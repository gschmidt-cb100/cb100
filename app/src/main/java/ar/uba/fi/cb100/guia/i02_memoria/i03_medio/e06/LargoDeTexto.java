package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e06;

import java.util.Optional;

/**
 * Ejercicio 06 (OptionalLargo).
 *
 * Optional representa "un valor que puede o no estar". Con map/orElse
 * evitamos condicionales manuales sobre null: si hay texto devolvemos su
 * largo, si esta vacio devolvemos cero.
 */
public final class LargoDeTexto {

    private LargoDeTexto() {
    }

    public static int largoOrCero(Optional<String> o) {
        return o.map(String::length).orElse(0);
    }

    public static void main(String[] args) {
        System.out.println("Presente: " + largoOrCero(Optional.of("hola"))); // 4
        System.out.println("Vacio:    " + largoOrCero(Optional.empty()));    // 0
    }
}

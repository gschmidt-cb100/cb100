package ar.uba.fi.cb100.material.i02_memoria;

import java.util.Arrays;

/**
 * Un objeto es <b>inmutable</b> si su estado no cambia luego de creado
 * ({@code String}, wrappers, {@code record}). Es <b>mutable</b> si sí
 * ({@code arreglos}, clases con setters). "Cambiar" un inmutable = crear otro.
 */
public class MutableInmutable {

    record Punto(int x, int y) {
        Punto conX(int nuevoX) {      // no modifica: devuelve un Punto nuevo
            return new Punto(nuevoX, y);
        }
    }

    public static void main(String[] args) {
        // Inmutable: String
        String s = "abc";
        s.toUpperCase();                  // el resultado se descarta
        System.out.println(s);            // abc (no cambió)

        // Inmutable: record -> se crea uno nuevo
        Punto p = new Punto(1, 2);
        Punto p2 = p.conX(9);
        System.out.println(p + " / " + p2);   // Punto[x=1, y=2] / Punto[x=9, y=2]

        // Mutable: arreglo
        int[] arr = {1, 2, 3};
        arr[0] = 99;                      // sí cambia
        System.out.println(Arrays.toString(arr));   // [99, 2, 3]
    }
}

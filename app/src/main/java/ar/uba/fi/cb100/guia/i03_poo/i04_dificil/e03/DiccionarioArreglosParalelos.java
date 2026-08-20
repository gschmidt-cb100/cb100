package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e03;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Diccionario (mapa clave-valor) implementado sobre dos arreglos paralelos:
 * uno para las claves y otro para los valores, alineados por índice.
 * La igualdad de claves se determina con {@code equals}.
 * No usa colecciones del JDK.
 *
 * @param <K> tipo de las claves
 * @param <V> tipo de los valores
 */
public class DiccionarioArreglosParalelos<K, V> {

    private Object[] claves;
    private Object[] valores;
    private int cantidad;

    public DiccionarioArreglosParalelos() {
        this.claves = new Object[4];
        this.valores = new Object[4];
        this.cantidad = 0;
    }

    /** Inserta o actualiza el valor asociado a la clave. */
    public void poner(K clave, V valor) {
        int i = indiceDe(clave);
        if (i >= 0) {
            valores[i] = valor; // actualiza
            return;
        }
        if (cantidad == claves.length) {
            claves = Arrays.copyOf(claves, claves.length * 2);
            valores = Arrays.copyOf(valores, valores.length * 2);
        }
        claves[cantidad] = clave;
        valores[cantidad] = valor;
        cantidad++;
    }

    /**
     * Devuelve el valor asociado a la clave envuelto en un {@link Optional},
     * o {@code Optional.empty()} si la clave no está presente.
     */
    @SuppressWarnings("unchecked")
    public Optional<V> obtener(K clave) {
        int i = indiceDe(clave);
        if (i < 0) {
            return Optional.empty();
        }
        return Optional.ofNullable((V) valores[i]);
    }

    public boolean contieneClave(K clave) {
        return indiceDe(clave) >= 0;
    }

    public int tamanio() {
        return cantidad;
    }

    private int indiceDe(K clave) {
        for (int i = 0; i < cantidad; i++) {
            if (Objects.equals(claves[i], clave)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        DiccionarioArreglosParalelos<String, Integer> edades = new DiccionarioArreglosParalelos<>();
        edades.poner("Ana", 30);
        edades.poner("Beto", 25);
        edades.poner("Ana", 31); // actualiza
        System.out.println("Tamaño: " + edades.tamanio());
        System.out.println("Ana: " + edades.obtener("Ana").orElse(-1));
        System.out.println("¿Contiene Beto? " + edades.contieneClave("Beto"));
        System.out.println("Carla: " + edades.obtener("Carla"));
    }
}

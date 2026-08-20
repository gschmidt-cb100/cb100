package ar.uba.fi.cb100.material.i03_poo;

import java.util.Objects;

/**
 * Utilidades para validar <b>precondiciones</b> de forma uniforme y con mensajes
 * consistentes. Regla de diseño: validá lo que es barato y protege un invariante
 * (no nulo, rango, positivo); NO valides lo que es parte del contrato de quien
 * llama y costaría caro comprobar (p. ej. que un arreglo ya venga ordenado).
 */
public final class Preconditions {

    private Preconditions() { }   // clase de utilidades: no se instancia

    /** Falla si {@code valor} es null. */
    public static <T> T noNulo(T valor, String nombre) {
        return Objects.requireNonNull(valor, nombre + " no puede ser null");
    }

    /** Falla si {@code valor} no está en [min, max]. */
    public static int enRango(int valor, int min, int max, String nombre) {
        if (valor < min || valor > max) {
            throw new IllegalArgumentException(
                    nombre + " fuera de rango [" + min + ".." + max + "]: " + valor);
        }
        return valor;
    }

    /** Falla si {@code valor} no es estrictamente positivo. */
    public static double positivo(double valor, String nombre) {
        if (valor <= 0) {
            throw new IllegalArgumentException(nombre + " debe ser positivo: " + valor);
        }
        return valor;
    }

    public static void main(String[] args) {
        System.out.println("edad válida: " + enRango(30, 0, 150, "edad"));
        try {
            enRango(200, 0, 150, "edad");
        } catch (IllegalArgumentException e) {
            System.out.println("rechazado: " + e.getMessage());
        }
    }
}

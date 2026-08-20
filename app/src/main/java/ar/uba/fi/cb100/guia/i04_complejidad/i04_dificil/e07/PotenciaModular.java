package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e07;

/**
 * Exponenciación modular rápida (binaria): calcula (base^exp) mod mod.
 *
 * Idea: se recorre el exponente en binario. En cada bit se eleva la base al
 * cuadrado; si el bit está en 1, se multiplica el resultado por la base actual.
 * Todo se hace módulo 'mod' para evitar desbordes.
 *
 * Complejidad temporal: O(log exp), un paso por cada bit del exponente.
 * Complejidad espacial: O(1).
 *
 * Se usa aritmética con long y reducción modular en cada multiplicación.
 * Nota: para 'mod' cercano a Long.MAX_VALUE el producto puede desbordar; aquí
 * se asume mod que quepa holgadamente (típico en la práctica de la materia).
 */
public final class PotenciaModular {

    private PotenciaModular() {
    }

    /** Devuelve (base^exp) mod mod. Requiere exp >= 0 y mod > 0. */
    public static long potModular(long base, long exp, long mod) {
        if (mod <= 0) {
            throw new IllegalArgumentException("El módulo debe ser positivo");
        }
        if (exp < 0) {
            throw new IllegalArgumentException("El exponente no puede ser negativo");
        }
        if (mod == 1) {
            return 0; // todo es congruente a 0 módulo 1
        }
        long resultado = 1 % mod;
        long b = ((base % mod) + mod) % mod; // normaliza bases negativas
        long e = exp;
        while (e > 0) {
            if ((e & 1L) == 1L) {
                resultado = (resultado * b) % mod;
            }
            b = (b * b) % mod;
            e >>= 1;
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("2^10 mod 1000 = " + potModular(2, 10, 1000)); // 24
        System.out.println("3^13 mod 7 = " + potModular(3, 13, 7));       // 3
    }
}

package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e04;

/**
 * e04: exponenciacion rapida (por cuadrados), un algoritmo de division y conquista.
 *
 * <p>Idea: en lugar de multiplicar {@code base} por si misma {@code exp} veces
 * (O(exp)), se usa la identidad:</p>
 * <ul>
 *   <li>si {@code exp} es par: {@code base^exp = (base^(exp/2))^2}</li>
 *   <li>si {@code exp} es impar: {@code base^exp = base * base^(exp-1)}</li>
 * </ul>
 *
 * <p>Cada paso reduce el exponente a la mitad, por lo que la complejidad es
 * O(log exp).</p>
 */
public final class PotenciaRapida {

    private PotenciaRapida() {
    }

    /**
     * Calcula {@code base} elevado a {@code exp} en O(log exp).
     *
     * @param base base de la potencia
     * @param exp  exponente no negativo
     * @return {@code base^exp}
     * @throws IllegalArgumentException si {@code exp} es negativo
     */
    public static long potencia(long base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("el exponente no puede ser negativo: " + exp);
        }
        if (exp == 0) {
            return 1L; // caso base: cualquier base^0 = 1
        }
        long mitad = potencia(base, exp / 2); // resolvemos un subproblema de tamano mitad
        long cuadrado = mitad * mitad;
        if (exp % 2 == 0) {
            return cuadrado;          // exponente par
        }
        return base * cuadrado;       // exponente impar
    }

    public static void main(String[] args) {
        System.out.println("2^0  = " + potencia(2, 0));
        System.out.println("2^10 = " + potencia(2, 10));
        System.out.println("3^5  = " + potencia(3, 5));
        System.out.println("2^62 = " + potencia(2, 62));
    }
}

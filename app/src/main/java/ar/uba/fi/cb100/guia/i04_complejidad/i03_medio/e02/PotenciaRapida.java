package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e02;

/**
 * Exponenciacion rapida (por cuadrados / "exponentiation by squaring").
 *
 * Idea: base^exp se calcula elevando al cuadrado y aprovechando la paridad
 * del exponente:
 *   base^exp = (base^2)^(exp/2)          si exp es par
 *   base^exp = base * (base^2)^(exp/2)   si exp es impar
 *
 * Complejidad temporal: O(log n), donde n = exp.
 * En cada iteracion el exponente se divide por 2, por lo que hay log2(exp) pasos.
 * Complejidad espacial: O(1) (version iterativa).
 */
public final class PotenciaRapida {

    private PotenciaRapida() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Calcula {@code base} elevado a {@code exp}.
     *
     * @param base base de la potencia.
     * @param exp  exponente no negativo.
     * @return base^exp.
     * @throws IllegalArgumentException si {@code exp} es negativo.
     */
    public static long potencia(long base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("El exponente no puede ser negativo: " + exp);
        }
        long resultado = 1;
        long b = base;
        int e = exp;
        while (e > 0) {
            if ((e & 1) == 1) {   // e es impar
                resultado *= b;
            }
            b *= b;               // se eleva la base al cuadrado
            e >>= 1;              // se divide el exponente por 2
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("2^10 = " + potencia(2, 10));  // 1024
        System.out.println("3^0  = " + potencia(3, 0));   // 1
        System.out.println("5^1  = " + potencia(5, 1));   // 5
        System.out.println("7^5  = " + potencia(7, 5));   // 16807
    }
}

package ar.uba.fi.cb100.material.i04_complejidad;

/**
 * Exponenciación rápida: <b>O(log n)</b>. En vez de multiplicar n veces (O(n)),
 * eleva al cuadrado y divide el exponente por 2 en cada paso.
 */
public class PotenciaRapida {

    public static long potencia(long base, int exp) {
        long resultado = 1;
        while (exp > 0) {                 // el exponente se divide por 2 -> O(log n)
            if ((exp & 1) == 1) {
                resultado *= base;   // bit menos significativo
            }
            base *= base;
            exp >>= 1;
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("2^10 = " + potencia(2, 10));   // 1024
        System.out.println("3^5  = " + potencia(3, 5));    // 243
    }
}

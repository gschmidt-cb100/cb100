package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e10;

/**
 * e10 - Cuenta las vueltas del bucle {@code for (int i = 1; i < n; i *= 2)}.
 *
 * Complejidad: O(log n).
 * Justificacion: la variable i arranca en 1 y se duplica en cada vuelta,
 * por lo que toma los valores 1, 2, 4, 8, ... La cantidad de vueltas hasta
 * alcanzar o superar n es floor(log_2(n)) cuando n >= 1, es decir O(log n).
 */
public final class ConteoIteraciones {

    private ConteoIteraciones() {
    }

    /**
     * Devuelve cuantas veces se ejecuta el cuerpo del bucle
     * {@code for (int i = 1; i < n; i *= 2)}.
     *
     * @param n cota superior del bucle
     * @return cantidad de iteraciones (0 si n &lt;= 1)
     */
    public static int iteraciones(int n) {
        int contador = 0;
        for (int i = 1; i < n; i *= 2) { // se duplica -> O(log n) vueltas
            contador++;
        }
        return contador;
    }

    public static void main(String[] args) {
        System.out.println("iteraciones(1) = " + iteraciones(1));   // 0
        System.out.println("iteraciones(8) = " + iteraciones(8));   // 3 (i=1,2,4)
        System.out.println("iteraciones(16) = " + iteraciones(16)); // 4 (i=1,2,4,8)
    }
}

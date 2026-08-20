package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e04;

/**
 * e04: elegir elementos de un arreglo maximizando la suma, con la regla
 * de que no se pueden tomar dos posiciones adyacentes (el clásico
 * "ladrón de casas": no se pueden robar dos casas vecinas).
 *
 * <p><b>Técnica: programación dinámica 1D.</b> Para cada posición hay una
 * decisión binaria (incluirla o no), pero probar las 2^n combinaciones es
 * inviable. La observación de DP: el mejor resultado hasta la posición i
 * sólo depende de dos subproblemas, "mejor incluyendo a[i]" (= excluir
 * hasta i-1 + a[i]) y "mejor excluyendo a[i]" (= máximo hasta i-1). Con
 * dos variables que van rodando resolvemos en O(n) tiempo y O(1) espacio.</p>
 */
public final class SumaSinAdyacentes {

    private SumaSinAdyacentes() {
    }

    /**
     * Máxima suma alcanzable eligiendo elementos no adyacentes de
     * {@code a}. No elegir nada vale 0, así que el resultado nunca es
     * negativo.
     *
     * @param a arreglo de enteros (no se modifica; puede ser vacío)
     * @return la mayor suma posible sin tomar dos posiciones vecinas
     */
    public static int maximo(int[] a) {
        // incluir = mejor suma que SÍ usa el elemento actual;
        // excluir = mejor suma que NO lo usa.
        int incluir = 0;
        int excluir = 0;
        for (int x : a) {
            // Si incluimos x, el anterior tuvo que quedar excluido.
            int nuevoIncluir = excluir + x;
            // Si excluimos x, nos quedamos con lo mejor de antes.
            int nuevoExcluir = Math.max(incluir, excluir);
            incluir = nuevoIncluir;
            excluir = nuevoExcluir;
        }
        return Math.max(incluir, excluir);
    }

    public static void main(String[] args) {
        int[] casas = {2, 7, 9, 3, 1};
        System.out.println("Botines por casa: [2, 7, 9, 3, 1]");
        System.out.println("Maximo sin robar casas vecinas: " + maximo(casas)
                + " (2 + 9 + 1)");
    }
}

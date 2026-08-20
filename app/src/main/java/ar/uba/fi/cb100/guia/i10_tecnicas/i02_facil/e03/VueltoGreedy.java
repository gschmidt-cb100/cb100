package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e03;

import java.util.Arrays;

/**
 * e03: dar el vuelto con la menor cantidad de monedas... o al menos
 * intentarlo.
 *
 * <p><b>Técnica: greedy (algoritmo goloso).</b> En cada paso tomamos la
 * decisión localmente mejor: la moneda más grande que todavía "entra" en
 * lo que falta pagar, sin reconsiderar nunca esa elección. Con sistemas
 * canónicos como el nuestro ({100, 50, 20, 10, 1}) el greedy da el óptimo,
 * pero <b>no siempre</b>: con el sistema {1, 3, 4} y monto 6 el greedy usa
 * 3 monedas (4+1+1) cuando el óptimo real es 2 (3+3). Es el ejemplo de
 * manual de que greedy es rápido y simple, pero hay que <i>demostrar</i>
 * que es óptimo antes de confiar en él; si no, hace falta DP.</p>
 */
public final class VueltoGreedy {

    private VueltoGreedy() {
    }

    /**
     * Cantidad de monedas que usa la estrategia golosa para pagar
     * {@code monto}: siempre elige la mayor moneda que no se pasa.
     *
     * @param sistema denominaciones disponibles (todas positivas,
     *                cantidad ilimitada de cada una)
     * @param monto   monto a pagar (monto &gt;= 0)
     * @return cantidad de monedas usadas por el greedy, o -1 si con esa
     *         estrategia no se llega exactamente a 0
     */
    public static int monedasGreedy(int[] sistema, int monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("el monto debe ser >= 0, vino " + monto);
        }
        // Ordenamos una copia de mayor a menor para recorrer las monedas
        // grandes primero (no tocamos el arreglo del llamador).
        int[] deMayorAMenor = sistema.clone();
        Arrays.sort(deMayorAMenor);
        int restante = monto;
        int monedas = 0;
        for (int i = deMayorAMenor.length - 1; i >= 0; i--) {
            int moneda = deMayorAMenor[i];
            while (moneda <= restante) {
                // Decisión golosa: usamos esta moneda y no la reconsideramos.
                restante -= moneda;
                monedas++;
            }
        }
        return restante == 0 ? monedas : -1;
    }

    public static void main(String[] args) {
        int[] pesos = {100, 50, 20, 10, 1};
        System.out.println("Vuelto de 180 con {100,50,20,10,1}: "
                + monedasGreedy(pesos, 180) + " monedas (100+50+20+10)");
        int[] raro = {1, 3, 4};
        System.out.println("Vuelto de 6 con {1,3,4}: "
                + monedasGreedy(raro, 6) + " monedas (greedy: 4+1+1, pero el optimo es 3+3)");
    }
}

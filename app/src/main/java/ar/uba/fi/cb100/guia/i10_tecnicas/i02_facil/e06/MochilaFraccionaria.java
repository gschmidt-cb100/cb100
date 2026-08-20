package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e06;

import java.util.Arrays;
import java.util.Comparator;

/**
 * e06: la mochila fraccionaria. Tenemos objetos con peso y valor, una
 * mochila con capacidad limitada, y podemos llevar <i>fracciones</i> de
 * cada objeto (como si fueran harina o arena).
 *
 * <p><b>Técnica: greedy (algoritmo goloso), y acá SÍ es óptimo.</b>
 * Ordenamos los objetos por densidad (valor/peso) de mayor a menor y
 * vamos llenando la mochila; del primero que no entra entero llevamos la
 * fracción que quepa. El argumento de optimalidad es de intercambio: si
 * una solución lleva material de densidad baja habiendo lugar ocupable
 * con material de densidad más alta, cambiar uno por otro sólo puede
 * mejorar. Poder fraccionar es la clave: en la mochila 0/1 (objetos
 * indivisibles) este mismo greedy falla y hace falta DP.</p>
 */
public final class MochilaFraccionaria {

    private MochilaFraccionaria() {
    }

    /**
     * Máximo valor que se puede cargar en la mochila pudiendo fraccionar
     * los objetos.
     *
     * @param pesos     peso de cada objeto (todos &gt; 0)
     * @param valores   valor de cada objeto (mismo largo que {@code pesos})
     * @param capacidad capacidad de la mochila (&gt;= 0)
     * @return el valor máximo transportable
     */
    public static double valorMaximo(double[] pesos, double[] valores, double capacidad) {
        if (pesos.length != valores.length) {
            throw new IllegalArgumentException("pesos y valores deben tener el mismo largo");
        }
        // Índices 0..n-1 ordenados por densidad valor/peso, de mayor a menor.
        Integer[] indices = new Integer[pesos.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, Comparator.comparingDouble(i -> -valores[i] / pesos[i]));

        double lugar = capacidad;
        double total = 0;
        for (int i : indices) {
            if (lugar <= 0) {
                break;
            }
            if (pesos[i] <= lugar) {
                // Entra entero: lo llevamos completo.
                total += valores[i];
                lugar -= pesos[i];
            } else {
                // No entra entero: llevamos la fracción que quepa y listo.
                total += valores[i] * (lugar / pesos[i]);
                lugar = 0;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        double[] pesos = {10, 20, 30};
        double[] valores = {60, 100, 120};
        System.out.println("Pesos {10,20,30}, valores {60,100,120}, capacidad 50");
        System.out.println("Valor maximo: " + valorMaximo(pesos, valores, 50)
                + " (objetos 1 y 2 enteros + 2/3 del tercero)");
    }
}

package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e01;

import java.util.HashMap;
import java.util.Map;

/**
 * e01: ¿de cuántas formas se puede subir una escalera de n escalones,
 * dando pasos de 1 o de 2 escalones por vez?
 *
 * <p><b>Técnica: programación dinámica.</b> El problema tiene la
 * recurrencia formas(n) = formas(n-1) + formas(n-2) (el último paso fue
 * de 1 o de 2), con muchísimos subproblemas repetidos: la recursión
 * directa recalcula formas(k) una y otra vez y cuesta O(2^n). Acá lo
 * resolvemos de las dos maneras clásicas de DP: <i>memoización</i>
 * (recursión + cache, "de arriba hacia abajo") y <i>tabulación</i>
 * (llenar una tabla desde los casos base, "de abajo hacia arriba").
 * Ambas cuestan O(n).</p>
 */
public final class Escaleras {

    private Escaleras() {
    }

    /**
     * Cantidad de formas de subir {@code n} escalones con pasos de 1 o 2,
     * usando recursión con memoización (top-down).
     *
     * @param n cantidad de escalones (n &gt;= 0)
     * @return cantidad de formas distintas de subir
     */
    public static long formas(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n debe ser >= 0, vino " + n);
        }
        return formasMemo(n, new HashMap<>());
    }

    private static long formasMemo(int n, Map<Integer, Long> memo) {
        if (n <= 1) {
            // 0 escalones: una sola forma (no moverse). 1 escalón: un paso de 1.
            return 1;
        }
        Long guardado = memo.get(n);
        if (guardado != null) {
            // Ya lo calculamos antes: lo devolvemos sin recursar de nuevo.
            return guardado;
        }
        long resultado = formasMemo(n - 1, memo) + formasMemo(n - 2, memo);
        memo.put(n, resultado);
        return resultado;
    }

    /**
     * Misma cuenta pero por tabulación (bottom-up): llenamos la tabla
     * desde los casos base, sin recursión.
     *
     * @param n cantidad de escalones (n &gt;= 0)
     * @return cantidad de formas distintas de subir
     */
    public static long formasTabulada(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n debe ser >= 0, vino " + n);
        }
        if (n <= 1) {
            return 1;
        }
        long[] tabla = new long[n + 1];
        tabla[0] = 1;
        tabla[1] = 1;
        for (int i = 2; i <= n; i++) {
            tabla[i] = tabla[i - 1] + tabla[i - 2];
        }
        return tabla[n];
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 6; n++) {
            System.out.println("formas(" + n + ") = " + formas(n)
                    + " | tabulada = " + formasTabulada(n));
        }
    }
}

package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e02;

import java.util.Objects;

/**
 * e02 - Cuenta la cantidad de pares (i, j) con i &lt; j tales que a[i] == a[j].
 *
 * Complejidad: O(n^2).
 * Justificacion: se usan dos bucles anidados. El bucle externo ejecuta n veces
 * y, para cada i, el interno recorre los elementos restantes. El total de
 * comparaciones es n(n-1)/2, que crece de forma cuadratica -> O(n^2).
 */
public final class ParesIguales {

    private ParesIguales() {
    }

    /**
     * Cuenta cuantos pares de posiciones distintas tienen el mismo valor.
     * Por ejemplo, tres 3 forman C(3,2) = 3 pares.
     *
     * @param a arreglo de enteros (no nulo)
     * @return cantidad de pares iguales
     */
    public static int paresIguales(int[] a) {
        Objects.requireNonNull(a, "el arreglo no puede ser nulo");
        int contador = 0;
        for (int i = 0; i < a.length; i++) {          // O(n)
            for (int j = i + 1; j < a.length; j++) {  // O(n) por cada i -> O(n^2)
                if (a[i] == a[j]) {                    // O(1)
                    contador++;
                }
            }
        }
        return contador;
    }

    public static void main(String[] args) {
        int[] datos = {1, 2, 2, 3, 3, 3};
        // 1 par de doses + 3 pares de treses = 4
        System.out.println("Pares iguales de {1,2,2,3,3,3} = " + paresIguales(datos));
    }
}

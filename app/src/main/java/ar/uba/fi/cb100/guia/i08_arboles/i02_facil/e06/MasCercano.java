package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e06;

import java.util.TreeSet;

/**
 * e06: el valor más cercano a un número dado. {@code floor(x)} devuelve el
 * mayor elemento ≤ x y {@code ceiling(x)} el menor elemento ≥ x; el más
 * cercano tiene que ser uno de esos dos.
 */
public final class MasCercano {

    private MasCercano() {
    }

    /**
     * Devuelve el elemento del conjunto más cercano a {@code x}.
     * Si hay empate (misma distancia por abajo y por arriba) devuelve el menor.
     *
     * @param valores conjunto ordenado de enteros
     * @param x       valor de referencia
     * @return el elemento más cercano a {@code x}, o {@code null} si el conjunto está vacío
     */
    public static Integer masCercano(TreeSet<Integer> valores, int x) {
        Integer porAbajo = valores.floor(x);    // mayor elemento <= x
        Integer porArriba = valores.ceiling(x); // menor elemento >= x
        if (porAbajo == null) {
            return porArriba; // puede ser null si el conjunto está vacío
        }
        if (porArriba == null) {
            return porAbajo;
        }
        // En el empate gana el menor, por eso usamos <= (porAbajo siempre es el menor).
        return (x - porAbajo <= porArriba - x) ? porAbajo : porArriba;
    }

    public static void main(String[] args) {
        TreeSet<Integer> paradas = new TreeSet<>();
        paradas.add(100);
        paradas.add(250);
        paradas.add(400);
        System.out.println("Paradas en los km: " + paradas);
        System.out.println("Más cercana al km 300: " + masCercano(paradas, 300));
        System.out.println("Más cercana al km 175 (empate): " + masCercano(paradas, 175));
    }
}

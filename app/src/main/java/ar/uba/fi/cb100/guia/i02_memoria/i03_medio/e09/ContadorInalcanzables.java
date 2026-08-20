package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e09;

/**
 * Ejercicio 09 (ContarInalcanzables).
 *
 * Metafora simple del recolector de basura: dado un arreglo que marca que
 * objetos son "alcanzables" (true), cuenta cuantos son inalcanzables
 * (false), es decir los recolectables.
 */
public final class ContadorInalcanzables {

    private ContadorInalcanzables() {
    }

    public static int recolectables(boolean[] alcanzable) {
        int cuenta = 0;
        for (boolean marca : alcanzable) {
            if (!marca) {
                cuenta++;
            }
        }
        return cuenta;
    }

    public static void main(String[] args) {
        boolean[] alcanzable = {true, false, true, false, false};
        System.out.println("Recolectables: " + recolectables(alcanzable)); // 3
    }
}

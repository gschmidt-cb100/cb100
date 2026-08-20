package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e08;

/**
 * Detector de colisiones: dado un lote de claves y una capacidad m, cuenta
 * cuantas claves caen en un indice que ya habia usado otra clave.
 *
 * El indice de cada clave es Math.floorMod(clave.hashCode(), m), el mismo
 * calculo que hace una tabla de hash real. Si n claves ocupan k indices
 * distintos, entonces hubo n - k colisiones: la primera clave de cada
 * indice no colisiona, todas las demas si.
 *
 * Sirve para evaluar que tan buena es una funcion de hash (o una capacidad)
 * para un conjunto de claves dado: menos colisiones, mejor distribucion.
 *
 * Complejidad: O(n + m) en tiempo, O(m) en espacio.
 */
public final class DetectorDeColisiones {

    private DetectorDeColisiones() {
        // Clase utilitaria: no se instancia.
    }

    /**
     * Cantidad de claves que caen en un indice ya usado por otra clave,
     * es decir n - indicesDistintos.
     *
     * @param claves lote de claves a distribuir
     * @param m      capacidad de la tabla (cantidad de indices posibles)
     */
    public static int colisiones(String[] claves, int m) {
        if (m <= 0) {
            throw new IllegalArgumentException("m debe ser positivo: " + m);
        }
        boolean[] usado = new boolean[m];
        int indicesDistintos = 0;
        for (String clave : claves) {
            int indice = Math.floorMod(clave.hashCode(), m);
            if (!usado[indice]) {
                usado[indice] = true;
                indicesDistintos++;
            }
        }
        return claves.length - indicesDistintos;
    }

    /** Demostracion: un lote que colisiona mucho y otro que no colisiona. */
    public static void main(String[] args) {
        String[] chocan = { "juan", "eva", "sol" };  // Las tres dan indice 0 mod 8.
        String[] noChocan = { "ana", "mia", "leo" }; // Indices 4, 5 y 6 mod 8.
        System.out.println("colisiones(juan/eva/sol, 8) = " + colisiones(chocan, 8));
        System.out.println("colisiones(ana/mia/leo, 8)  = " + colisiones(noChocan, 8));
        // La misma capacidad puede ser buena para un lote y mala para otro.
        System.out.println("colisiones(juan/eva/sol, 7) = " + colisiones(chocan, 7));
    }
}

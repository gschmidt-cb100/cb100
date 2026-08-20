package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e07;

/**
 * e07: busqueda de un camino en un laberinto mediante backtracking recursivo.
 *
 * <p>El laberinto es una grilla {@code libre[fila][columna]}: {@code true}
 * significa que la celda es transitable. Partiendo de {@code (0,0)} se busca
 * llegar a {@code (n-1, m-1)} moviendose por celdas libres en las 4 direcciones
 * (arriba, abajo, izquierda, derecha), sin repetir celdas ya visitadas.</p>
 */
public final class Laberinto {

    private Laberinto() {
    }

    /**
     * Indica si existe un camino desde la esquina superior izquierda hasta la
     * inferior derecha.
     *
     * @param libre grilla rectangular de celdas transitables
     * @return {@code true} si hay camino; {@code false} en caso contrario
     * @throws IllegalArgumentException si la grilla es nula o esta vacia
     */
    public static boolean hayCamino(boolean[][] libre) {
        if (libre == null || libre.length == 0 || libre[0].length == 0) {
            throw new IllegalArgumentException("el laberinto no puede ser vacio");
        }
        int filas = libre.length;
        int columnas = libre[0].length;
        // Si la salida o la entrada estan bloqueadas, no hay camino posible.
        if (!libre[0][0] || !libre[filas - 1][columnas - 1]) {
            return false;
        }
        boolean[][] visitada = new boolean[filas][columnas];
        return explorar(libre, visitada, 0, 0);
    }

    /**
     * Intenta avanzar recursivamente desde {@code (fila, columna)} hacia el
     * destino, marcando y desmarcando celdas visitadas (backtracking).
     */
    private static boolean explorar(boolean[][] libre, boolean[][] visitada,
                                    int fila, int columna) {
        int filas = libre.length;
        int columnas = libre[0].length;
        // Fuera de la grilla, pared o ya visitada: callejon sin salida.
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false;
        }
        if (!libre[fila][columna] || visitada[fila][columna]) {
            return false;
        }
        // Llegamos al destino.
        if (fila == filas - 1 && columna == columnas - 1) {
            return true;
        }
        visitada[fila][columna] = true;
        // Probamos las 4 direcciones.
        boolean encontrado = explorar(libre, visitada, fila - 1, columna)
                || explorar(libre, visitada, fila + 1, columna)
                || explorar(libre, visitada, fila, columna - 1)
                || explorar(libre, visitada, fila, columna + 1);
        if (!encontrado) {
            visitada[fila][columna] = false; // deshacemos para permitir otros caminos
        }
        return encontrado;
    }

    public static void main(String[] args) {
        boolean[][] conSalida = {
                {true, true, false},
                {false, true, false},
                {false, true, true}
        };
        boolean[][] sinSalida = {
                {true, false},
                {false, true}
        };
        System.out.println("conSalida -> " + hayCamino(conSalida));
        System.out.println("sinSalida -> " + hayCamino(sinSalida));
    }
}

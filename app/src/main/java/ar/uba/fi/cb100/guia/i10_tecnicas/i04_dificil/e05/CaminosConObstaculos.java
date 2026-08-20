package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e05;

/**
 * TECNICA: PROGRAMACION DINAMICA sobre una grilla (conteo de caminos).
 *
 * Problema: en una grilla donde 0 es celda libre y 1 es obstaculo,
 * contar cuantos caminos van de la esquina superior izquierda a la
 * inferior derecha moviendose SOLO a la derecha o hacia abajo.
 *
 * Por que programacion dinamica: todo camino que llega a la celda (f, c)
 * paso por (f-1, c) o por (f, c-1), y esos dos conjuntos de caminos son
 * disjuntos. Entonces:
 *   caminos(f, c) = caminos(f-1, c) + caminos(f, c-1)
 * con caminos(0, 0) = 1 si la salida esta libre, y caminos(f, c) = 0 si
 * la celda es un obstaculo (nadie puede pisarla). La recursion directa
 * repite subproblemas (la misma celda se alcanza por muchos caminos);
 * la tabla los resuelve una unica vez recorriendo por filas.
 *
 * Usamos long porque el conteo crece combinatoriamente: sin obstaculos
 * en una grilla de m x n hay C(m+n-2, m-1) caminos, que desborda int
 * enseguida (por ejemplo 18x18 ya supera los 2 mil millones).
 *
 * Complejidad: O(m * n) en tiempo y espacio.
 */
public class CaminosConObstaculos {

    /**
     * Cantidad de caminos de (0,0) a (m-1,n-1) con movimientos derecha/abajo,
     * esquivando los obstaculos (celdas con 1). Si la salida o la llegada
     * estan bloqueadas, devuelve 0.
     */
    public long caminos(int[][] grilla) {
        if (grilla == null || grilla.length == 0 || grilla[0].length == 0) {
            throw new IllegalArgumentException("La grilla no puede ser vacia");
        }
        int filas = grilla.length;
        int columnas = grilla[0].length;
        long[][] dp = new long[filas][columnas];

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (grilla[f][c] == 1) {
                    dp[f][c] = 0; // Un obstaculo no lo pisa ningun camino.
                } else if (f == 0 && c == 0) {
                    dp[f][c] = 1; // Caso base: estar parado en la salida.
                } else {
                    long desdeArriba = f > 0 ? dp[f - 1][c] : 0;
                    long desdeIzquierda = c > 0 ? dp[f][c - 1] : 0;
                    dp[f][c] = desdeArriba + desdeIzquierda;
                }
            }
        }
        return dp[filas - 1][columnas - 1];
    }

    /** Demostracion: la misma grilla 3x3 con y sin obstaculo central. */
    public static void main(String[] args) {
        CaminosConObstaculos contador = new CaminosConObstaculos();
        int[][] libre = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}};
        int[][] conObstaculo = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}};
        System.out.println("3x3 libre: " + contador.caminos(libre) + " caminos");
        System.out.println("3x3 con obstaculo central: " + contador.caminos(conObstaculo) + " caminos");
    }
}

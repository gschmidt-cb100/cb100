package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e02;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * TECNICA: BFS SOBRE UN GRAFO IMPLICITO.
 *
 * Un laberinto ES un grafo: cada celda libre es un vertice y hay una arista
 * entre celdas vecinas (arriba/abajo/izquierda/derecha) si ninguna es pared.
 * Pero NO hace falta construir el objeto Grafo: los vecinos de una celda se
 * calculan al vuelo mirando la grilla. A eso se lo llama grafo IMPLICITO,
 * y es la forma habitual de resolver mapas, puzzles y juegos, donde
 * materializar todas las aristas gastaria memoria sin necesidad.
 *
 * Como todas las "aristas" pesan lo mismo (un paso), el camino mas corto
 * lo da BFS (U11): explora por niveles, y el primer nivel que toca la
 * salida es la distancia minima. Para reconstruir el camino guardamos,
 * por cada celda, DESDE DONDE la descubrimos (el arreglo "anterior" de
 * siempre, aca en version bidimensional).
 *
 * Costo: cada celda entra a la cola a lo sumo una vez -&gt; O(filas x columnas).
 *
 * Convencion ante laberinto sin solucion: {@link #resolver()} devuelve una
 * {@link Solucion} con {@code pasos() == -1} y la grilla sin marcas.
 * Elegimos -1 (y no una excepcion) porque "no hay camino" es una respuesta
 * valida y esperable, no un error de programacion: es la misma convencion
 * que usa {@code Recorridos.distanciasDesde} para los inalcanzables.
 */
public class Laberinto {

    public static final char PARED = '#';
    public static final char LIBRE = '.';
    public static final char ENTRADA = 'E';
    public static final char SALIDA = 'S';
    public static final char MARCA = '*';

    /** Movimientos posibles: arriba, abajo, izquierda, derecha. */
    private static final int[] DESPLAZAMIENTO_FILA = {-1, 1, 0, 0};
    private static final int[] DESPLAZAMIENTO_COLUMNA = {0, 0, -1, 1};

    private final char[][] grilla;
    private final int filas;
    private final int columnas;

    /**
     * El resultado: cantidad de pasos del camino mas corto E-&gt;S
     * (-1 si no existe) y la grilla con el camino marcado con '*'
     * (la entrada y la salida conservan sus letras).
     */
    public record Solucion(int pasos, char[][] marcado) {

        public String dibujo() {
            StringBuilder texto = new StringBuilder();
            for (char[] fila : marcado) {
                texto.append(new String(fila)).append('\n');
            }
            return texto.toString();
        }
    }

    public Laberinto(char[][] grilla) {
        this.filas = grilla.length;
        this.columnas = grilla[0].length;
        this.grilla = copiar(grilla);
    }

    /** Comodidad para escribir laberintos como lista de renglones. */
    public Laberinto(String... renglones) {
        this(aGrilla(renglones));
    }

    /** BFS desde la entrada; devuelve pasos minimos y camino marcado. */
    public Solucion resolver() {
        int[] entrada = buscar(ENTRADA);
        int[] salida = buscar(SALIDA);

        // anterior[f][c] = celda desde la que descubrimos (f, c), codificada
        // como f * columnas + c; -1 = todavia no descubierta.
        int[][] anterior = new int[filas][columnas];
        for (int[] fila : anterior) {
            java.util.Arrays.fill(fila, -1);
        }

        Deque<int[]> cola = new ArrayDeque<>();
        anterior[entrada[0]][entrada[1]] = entrada[0] * columnas + entrada[1]; // se descubre a si misma
        cola.addLast(entrada);

        while (!cola.isEmpty()) {
            int[] celda = cola.removeFirst();
            if (celda[0] == salida[0] && celda[1] == salida[1]) {
                break;                                   // BFS: la primera vez es la mejor
            }
            // Los "vecinos" del grafo implicito: se calculan aca, al vuelo.
            for (int movimiento = 0; movimiento < 4; movimiento++) {
                int fila = celda[0] + DESPLAZAMIENTO_FILA[movimiento];
                int columna = celda[1] + DESPLAZAMIENTO_COLUMNA[movimiento];
                if (esTransitable(fila, columna) && anterior[fila][columna] == -1) {
                    anterior[fila][columna] = celda[0] * columnas + celda[1];
                    cola.addLast(new int[] {fila, columna});
                }
            }
        }

        if (anterior[salida[0]][salida[1]] == -1) {
            return new Solucion(-1, copiar(grilla));     // sin salida: -1 y grilla intacta
        }
        return new Solucion(contarYMarcar(anterior, entrada, salida),
                            marcado(anterior, entrada, salida));
    }

    // ------------------------------------------------------------ auxiliares

    private boolean esTransitable(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas
                && grilla[fila][columna] != PARED;
    }

    /** Cantidad de pasos del camino: aristas entre E y S, retrocediendo por "anterior". */
    private int contarYMarcar(int[][] anterior, int[] entrada, int[] salida) {
        int pasos = 0;
        int fila = salida[0];
        int columna = salida[1];
        while (fila != entrada[0] || columna != entrada[1]) {
            int codigo = anterior[fila][columna];
            fila = codigo / columnas;
            columna = codigo % columnas;
            pasos++;
        }
        return pasos;
    }

    /** Copia de la grilla con las celdas intermedias del camino marcadas con '*'. */
    private char[][] marcado(int[][] anterior, int[] entrada, int[] salida) {
        char[][] resultado = copiar(grilla);
        int fila = salida[0];
        int columna = salida[1];
        while (fila != entrada[0] || columna != entrada[1]) {
            if (resultado[fila][columna] == LIBRE) {
                resultado[fila][columna] = MARCA;        // E y S conservan su letra
            }
            int codigo = anterior[fila][columna];
            fila = codigo / columnas;
            columna = codigo % columnas;
        }
        return resultado;
    }

    private int[] buscar(char simbolo) {
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                if (grilla[fila][columna] == simbolo) {
                    return new int[] {fila, columna};
                }
            }
        }
        throw new IllegalArgumentException("el laberinto no tiene '" + simbolo + "'");
    }

    private static char[][] copiar(char[][] original) {
        char[][] copia = new char[original.length][];
        for (int fila = 0; fila < original.length; fila++) {
            copia[fila] = original[fila].clone();
        }
        return copia;
    }

    private static char[][] aGrilla(String[] renglones) {
        char[][] grilla = new char[renglones.length][];
        for (int fila = 0; fila < renglones.length; fila++) {
            grilla[fila] = renglones[fila].toCharArray();
        }
        return grilla;
    }

    public static void main(String[] args) {
        Laberinto laberinto = new Laberinto(
                "E..#.",
                ".#.#.",
                ".#...",
                ".###.",
                "....S");
        Solucion solucion = laberinto.resolver();
        System.out.println("Pasos: " + solucion.pasos());
        System.out.print(solucion.dibujo());

        Laberinto imposible = new Laberinto("E#S");
        System.out.println("Sin salida: " + imposible.resolver().pasos());   // -1
    }
}

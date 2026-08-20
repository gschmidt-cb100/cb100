package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e05;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * e05: costo de una codificación de Huffman.
 *
 * <p>Huffman construye el código de compresión óptimo juntando siempre los
 * dos símbolos (o grupos) MENOS frecuentes: por eso el min-heap es la
 * estructura natural. Cada vez que fusionamos dos grupos, todos sus
 * caracteres "bajan" un nivel en el árbol, es decir, ganan un bit más de
 * código; por eso acumular la suma de cada fusión equivale a calcular
 * sum(frecuencia × profundidad) = cantidad total de bits del texto
 * comprimido.</p>
 *
 * <p>Acá no armamos el árbol: solo calculamos el costo, que es la parte
 * donde el heap hace su magia. Costo del algoritmo: O(n log n).</p>
 */
public final class Huffman {

    private Huffman() {
    }

    /**
     * Calcula el costo total (en bits) de codificar con Huffman un texto
     * cuyas frecuencias de caracteres son las dadas.
     *
     * @param frecuencias mapa carácter → cantidad de apariciones (positivas)
     * @return suma de frecuencia × profundidad en el árbol de Huffman;
     *         0 si hay menos de dos símbolos distintos
     */
    public static int costoHuffman(Map<Character, Integer> frecuencias) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(frecuencias.values());
        // Con 0 o 1 símbolos no hay nada que fusionar: costo 0.
        int costo = 0;
        while (heap.size() > 1) {
            // Sacamos los DOS grupos menos frecuentes y los fusionamos.
            int menor = heap.poll();
            int segundo = heap.poll();
            int suma = menor + segundo;
            // Todos los caracteres fusionados ganan un bit: se paga "suma".
            costo += suma;
            heap.offer(suma);
        }
        return costo;
    }

    public static void main(String[] args) {
        Map<Character, Integer> frecuencias = Map.of(
                'a', 5, 'b', 9, 'c', 12, 'd', 13, 'e', 16, 'f', 45);
        System.out.println("Frecuencias: " + frecuencias);
        System.out.println("Costo Huffman: " + costoHuffman(frecuencias) + " bits");
    }
}

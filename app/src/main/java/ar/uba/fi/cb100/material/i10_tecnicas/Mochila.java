package ar.uba.fi.cb100.material.i10_tecnicas;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Programación dinámica bidimensional</b>: la mochila 0/1. Con capacidad
 * limitada y objetos con peso y valor (cada uno se lleva entero o no se
 * lleva), maximizar el valor total.
 * <p>
 * La tabla: {@code mejor[i][c]} = máximo valor usando los primeros i objetos
 * con capacidad c. Para cada objeto, la decisión es binaria: NO llevarlo
 * (queda {@code mejor[i-1][c]}) o SÍ llevarlo (su valor + lo mejor del resto
 * de la capacidad: {@code mejor[i-1][c-peso]}). O(n × capacidad).
 */
public final class Mochila {

    private Mochila() {}

    public record Objeto(String nombre, int peso, int valor) {}

    public static int valorMaximo(List<Objeto> objetos, int capacidad) {
        int n = objetos.size();
        int[][] mejor = new int[n + 1][capacidad + 1];   // fila 0: sin objetos = 0

        for (int i = 1; i <= n; i++) {
            Objeto objeto = objetos.get(i - 1);
            for (int c = 0; c <= capacidad; c++) {
                mejor[i][c] = mejor[i - 1][c];                    // opción NO llevarlo
                if (objeto.peso() <= c) {                          // ¿entra?
                    int llevandolo = objeto.valor() + mejor[i - 1][c - objeto.peso()];
                    mejor[i][c] = Math.max(mejor[i][c], llevandolo);   // la mejor de las dos
                }
            }
        }
        return mejor[n][capacidad];
    }

    /** Reconstrucción: qué objetos forman la solución óptima. */
    public static List<String> objetosElegidos(List<Objeto> objetos, int capacidad) {
        int n = objetos.size();
        int[][] mejor = new int[n + 1][capacidad + 1];
        for (int i = 1; i <= n; i++) {
            Objeto objeto = objetos.get(i - 1);
            for (int c = 0; c <= capacidad; c++) {
                mejor[i][c] = mejor[i - 1][c];
                if (objeto.peso() <= c) {
                    mejor[i][c] = Math.max(mejor[i][c],
                            objeto.valor() + mejor[i - 1][c - objeto.peso()]);
                }
            }
        }
        List<String> elegidos = new ArrayList<>();
        int c = capacidad;
        for (int i = n; i > 0; i--) {                     // deshacer las decisiones
            if (mejor[i][c] != mejor[i - 1][c]) {         // el valor cambió: lo llevó
                Objeto objeto = objetos.get(i - 1);
                elegidos.add(objeto.nombre());
                c -= objeto.peso();
            }
        }
        return elegidos.reversed();
    }

    public static void main(String[] args) {
        List<Objeto> objetos = List.of(
                new Objeto("A", 2, 3),
                new Objeto("B", 3, 4),
                new Objeto("C", 4, 5));
        System.out.println(valorMaximo(objetos, 5));      // 7  (A + B: peso 5, valor 7)
        System.out.println(objetosElegidos(objetos, 5));  // [A, B]
        System.out.println(valorMaximo(objetos, 4));      // 5  (C sola... ¿o A? A=3, C=5 → C)
    }
}

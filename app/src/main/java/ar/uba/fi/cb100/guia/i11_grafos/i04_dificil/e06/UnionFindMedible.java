package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e06;

/**
 * TECNICA: UNION-FIND INSTRUMENTADO — medir el efecto de la compresion de caminos.
 *
 * Es el mismo Union-Find del material (arbolitos de padres, union por
 * rango), reimplementado desde cero con dos agregados:
 * <ul>
 *   <li>un CONTADOR de "escalones subidos": cada vez que {@code encontrar}
 *       pasa de un elemento a su padre, suma 1. Es el costo real de la
 *       operacion, medido en la unidad que importa (saltos de puntero),
 *       sin depender del reloj ni de la maquina;</li>
 *   <li>un interruptor para APAGAR la compresion de caminos y comparar.</li>
 * </ul>
 *
 * ¿Que esperamos ver? Ambas versiones usan union por rango, asi que los
 * arboles nunca superan altura O(log n). La diferencia esta en las
 * busquedas REPETIDAS: sin compresion, cada encontrar sobre un elemento
 * profundo vuelve a pagar el mismo camino; con compresion, la primera
 * busqueda cuelga a todos los visitados directamente de la raiz y las
 * siguientes cuestan 1 escalon. Por eso, con la misma secuencia de
 * operaciones, el total de escalones con compresion es MENOR — es la
 * evidencia experimental del O(1) amortizado que promete la teoria.
 *
 * {@code encontrar} esta escrito en forma ITERATIVA en dos pasadas
 * (subir hasta la raiz contando, y recien despues recolgar), para que el
 * conteo de escalones sea explicito y no haya truco escondido en la recursion.
 */
public class UnionFindMedible {

    private final int[] padre;
    private final int[] rango;
    private final boolean conCompresion;
    private long escalonesSubidos;
    private int cantidadDeGrupos;

    public UnionFindMedible(int cantidadDeElementos, boolean conCompresion) {
        this.padre = new int[cantidadDeElementos];
        this.rango = new int[cantidadDeElementos];
        for (int elemento = 0; elemento < cantidadDeElementos; elemento++) {
            padre[elemento] = elemento;
        }
        this.conCompresion = conCompresion;
        this.cantidadDeGrupos = cantidadDeElementos;
    }

    /**
     * El representante del grupo del elemento. Cada salto elemento->padre
     * suma un escalon al contador.
     */
    public int encontrar(int elemento) {
        // Primera pasada: subir hasta la raiz, contando escalones.
        int raiz = elemento;
        while (padre[raiz] != raiz) {
            raiz = padre[raiz];
            escalonesSubidos++;                  // un salto de puntero real
        }
        // Segunda pasada (solo con compresion): recolgar todo el camino
        // directamente de la raiz, para abaratar las proximas busquedas.
        if (conCompresion) {
            int actual = elemento;
            while (padre[actual] != raiz) {
                int siguiente = padre[actual];
                padre[actual] = raiz;
                actual = siguiente;
            }
        }
        return raiz;
    }

    /**
     * Fusiona los grupos de ambos elementos (union por rango, identica en
     * las dos versiones). Devuelve true si estaban en grupos distintos.
     */
    public boolean unir(int unElemento, int otroElemento) {
        int unaRaiz = encontrar(unElemento);
        int otraRaiz = encontrar(otroElemento);
        if (unaRaiz == otraRaiz) {
            return false;
        }
        if (rango[unaRaiz] < rango[otraRaiz]) {
            padre[unaRaiz] = otraRaiz;
        } else if (rango[unaRaiz] > rango[otraRaiz]) {
            padre[otraRaiz] = unaRaiz;
        } else {
            padre[otraRaiz] = unaRaiz;
            rango[unaRaiz]++;
        }
        cantidadDeGrupos--;
        return true;
    }

    public boolean estanConectados(int unElemento, int otroElemento) {
        return encontrar(unElemento) == encontrar(otroElemento);
    }

    public long escalonesSubidos() {
        return escalonesSubidos;
    }

    public int cantidadDeGrupos() {
        return cantidadDeGrupos;
    }

    public static void main(String[] args) {
        int n = 1 << 14;                          // 16384 elementos

        UnionFindMedible con = new UnionFindMedible(n, true);
        UnionFindMedible sin = new UnionFindMedible(n, false);

        // Uniones "en torneo" (1 con 2, 3 con 4, ...; luego los ganadores):
        // con union por rango arman arboles de altura log n.
        for (int salto = 1; salto < n; salto *= 2) {
            for (int elemento = 0; elemento + salto < n; elemento += 2 * salto) {
                con.unir(elemento, elemento + salto);
                sin.unir(elemento, elemento + salto);
            }
        }
        // Busquedas repetidas sobre todos los elementos.
        for (int repeticion = 0; repeticion < 5; repeticion++) {
            for (int elemento = 0; elemento < n; elemento++) {
                con.encontrar(elemento);
                sin.encontrar(elemento);
            }
        }
        System.out.println("Escalones CON compresion: " + con.escalonesSubidos());
        System.out.println("Escalones SIN compresion: " + sin.escalonesSubidos());
    }
}

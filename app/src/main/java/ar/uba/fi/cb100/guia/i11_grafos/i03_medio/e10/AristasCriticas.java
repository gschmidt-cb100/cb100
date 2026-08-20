package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e10;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;

import java.util.ArrayList;
import java.util.List;

/**
 * e10: aristas críticas del MST — fuerza bruta con recálculo.
 *
 * <p><b>Modelo:</b> una arista del árbol de tendido mínimo es CRÍTICA si al
 * sacarla del grafo el nuevo MST cuesta más (o directamente el grafo queda
 * desconectado): es un tramo que no tiene reemplazo gratis. Le dice al que
 * opera la red qué cables duelen de verdad si se cortan.</p>
 *
 * <p><b>Técnica (fuerza bruta):</b> calcular el MST original; por cada uno de
 * sus n−1 tramos, armar una copia del grafo SIN esa arista, recalcular el MST
 * y comparar costos. Costo: O(n · m log m); para grafos chicos alcanza de
 * sobra (los algoritmos finos usan la propiedad de corte).</p>
 *
 * <p><b>Resultado en la red de fibra del apunte:</b> las CINCO aristas del
 * MST {B-C 1, A-B 2, E-F 3, B-D 4, D-E 5} (costo 15) son críticas. Tiene
 * sentido: una arista del MST deja de ser crítica sólo si en el corte que
 * ella cruza existe OTRA arista del mismo peso que la reemplace gratis.
 * Acá cada reemplazo posible es estrictamente más caro: sin B-C 1 entra
 * A-C 3 (MST 17), sin A-B 2 entra A-C 3 (16), sin E-F 3 la única forma de
 * llegar a F es D-F 7 (19), sin B-D 4 entra C-E 6 (17) y sin D-E 5 entra
 * C-E 6 (16).</p>
 */
public final class AristasCriticas {

    private AristasCriticas() {
    }

    /**
     * Las aristas críticas del MST del grafo, en el orden en que Kruskal
     * las eligió.
     */
    public static List<Tramo> encontrar(Grafo grafo) {
        List<Tramo> arbol = Kruskal.arbolDeTendidoMinimo(grafo);
        int costoOriginal = Tramo.costoTotal(arbol);

        List<Tramo> criticas = new ArrayList<>();
        for (Tramo tramo : arbol) {
            if (esCritica(grafo, tramo, costoOriginal)) {
                criticas.add(tramo);
            }
        }
        return criticas;
    }

    /** Saca la arista, recalcula el MST y compara: ¿empeoró? */
    private static boolean esCritica(Grafo grafo, Tramo sacada, int costoOriginal) {
        Grafo recortado = copiarSin(grafo, sacada);
        try {
            int nuevoCosto = Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(recortado));
            return nuevoCosto > costoOriginal;      // hubo que pagar un reemplazo más caro
        } catch (IllegalStateException quedoDesconectado) {
            return true;                            // sin esa arista no hay red posible
        }
    }

    /** Copia del grafo sin UNA aparición de la arista dada. */
    private static Grafo copiarSin(Grafo grafo, Tramo sacada) {
        Grafo copia = new Grafo(grafo.cantidadDeVertices(), false);
        boolean salteada = false;
        for (int vertice = 0; vertice < grafo.cantidadDeVertices(); vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (vertice < arista.destino()) {   // cada arista una sola vez
                    boolean esLaSacada = !salteada
                            && vertice == Math.min(sacada.origen(), sacada.destino())
                            && arista.destino() == Math.max(sacada.origen(), sacada.destino())
                            && arista.peso() == sacada.peso();
                    if (esLaSacada) {
                        salteada = true;            // sólo una aparición (por si hay paralelas)
                    } else {
                        copia.agregarArista(vertice, arista.destino(), arista.peso());
                    }
                }
            }
        }
        return copia;
    }

    public static void main(String[] args) {
        // La red de fibra del apunte: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7

        List<Tramo> criticas = encontrar(red);
        System.out.println(criticas.size() + " críticas: " + criticas);
        // 5 críticas: [Tramo[origen=1, destino=2, peso=1], Tramo[origen=0, destino=1, peso=2],
        //              Tramo[origen=4, destino=5, peso=3], Tramo[origen=1, destino=3, peso=4],
        //              Tramo[origen=3, destino=4, peso=5]]
    }
}

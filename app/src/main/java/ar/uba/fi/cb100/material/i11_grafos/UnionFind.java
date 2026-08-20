package ar.uba.fi.cb100.material.i11_grafos;

/**
 * <b>Union-Find</b> (o "conjuntos disjuntos"): mantiene n elementos
 * repartidos en grupos, con dos operaciones:
 * <ul>
 *   <li>{@code encontrar(x)}: ¿a qué grupo pertenece x? (devuelve su representante)</li>
 *   <li>{@code unir(x, y)}: fusionar los grupos de x e y</li>
 * </ul>
 * Cada grupo es un arbolito: cada elemento apunta a su "padre" y el
 * representante es la raíz. Con las dos optimizaciones clásicas
 * (<b>compresión de caminos</b> y <b>unión por rango</b>) ambas operaciones
 * cuestan, en la práctica, O(1) amortizado.
 * <p>
 * Es la pieza que le falta a Kruskal para detectar ciclos al instante.
 */
public class UnionFind {

    private final int[] padre;
    private final int[] rango;         // cota de la altura del arbolito
    private int cantidadDeGrupos;

    public UnionFind(int cantidadDeElementos) {
        padre = new int[cantidadDeElementos];
        rango = new int[cantidadDeElementos];
        for (int elemento = 0; elemento < cantidadDeElementos; elemento++) {
            padre[elemento] = elemento;              // cada uno arranca solo en su grupo
        }
        cantidadDeGrupos = cantidadDeElementos;
    }

    /** El representante del grupo de x (con compresión de caminos). */
    public int encontrar(int elemento) {
        if (padre[elemento] != elemento) {
            padre[elemento] = encontrar(padre[elemento]);   // colgar directo de la raíz
        }
        return padre[elemento];
    }

    /**
     * Fusiona los grupos de x e y.
     *
     * @return {@code true} si estaban en grupos distintos (y se unieron);
     *         {@code false} si ya estaban en el mismo (unirlos formaría un ciclo)
     */
    public boolean unir(int unElemento, int otroElemento) {
        int unaRaiz = encontrar(unElemento);
        int otraRaiz = encontrar(otroElemento);
        if (unaRaiz == otraRaiz) {
            return false;                            // ya estaban juntos
        }
        if (rango[unaRaiz] < rango[otraRaiz]) {      // el más bajo se cuelga del más alto
            padre[unaRaiz] = otraRaiz;
        } else if (rango[unaRaiz] > rango[otraRaiz]) {
            padre[otraRaiz] = unaRaiz;
        } else {
            padre[otraRaiz] = unaRaiz;               // misma altura: uno cualquiera...
            rango[unaRaiz]++;                        // ...y su rango crece en 1
        }
        cantidadDeGrupos--;
        return true;
    }

    public boolean estanConectados(int unElemento, int otroElemento) {
        return encontrar(unElemento) == encontrar(otroElemento);
    }

    public int cantidadDeGrupos() {
        return cantidadDeGrupos;
    }

    public static void main(String[] args) {
        UnionFind grupos = new UnionFind(6);            // A=0 ... F=5
        System.out.println(grupos.cantidadDeGrupos());  // 6

        grupos.unir(1, 2);                              // B y C
        grupos.unir(0, 1);                              // A con {B, C}
        grupos.unir(4, 5);                              // E y F
        System.out.println(grupos.cantidadDeGrupos());       // 3: {A,B,C} {D} {E,F}
        System.out.println(grupos.estanConectados(0, 2));    // true  (A y C)
        System.out.println(grupos.estanConectados(0, 4));    // false (A y E)
        System.out.println(grupos.unir(0, 2));               // false: ¡formaría un ciclo!
    }
}

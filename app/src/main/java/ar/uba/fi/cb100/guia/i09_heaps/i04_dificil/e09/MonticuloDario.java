package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e09;

/**
 * Min-heap D-ARIO A MANO: cada nodo tiene hasta d hijos en vez de 2.
 *
 * Con el arbol por niveles en un arreglo, las formulas se generalizan:
 *  - hijo j (con j en 0..d-1) del nodo i: d*i + j + 1.
 *  - padre del nodo i: (i - 1) / d.
 * Con d = 2 vuelven a ser las de siempre (2*i + 1, 2*i + 2 y (i - 1) / 2).
 *
 * El arbol es mas "petiso": altura log_d(n) en vez de log_2(n). Por eso:
 *  - encolar (flotar) mejora: O(log_d n) comparaciones.
 *  - desencolar (hundir) paga un precio: en cada nivel hay que buscar el
 *    MENOR entre HASTA d hijos, o sea O(d * log_d n).
 * Un d entre 3 y 8 suele convenir cuando hay muchas mas inserciones que
 * extracciones (por ejemplo en Dijkstra con muchos decrease-key).
 */
public class MonticuloDario<T extends Comparable<T>> {

    private static final int CAPACIDAD_INICIAL = 8;

    /** Cantidad de hijos por nodo (aridad). */
    private final int d;

    /** Arreglo que guarda el arbol por niveles. */
    private Object[] datos;

    /** Cantidad de elementos guardados. */
    private int tamanio;

    /**
     * @param d aridad del heap, al menos 2.
     * @throws IllegalArgumentException si d es menor que 2.
     */
    public MonticuloDario(int d) {
        if (d < 2) {
            throw new IllegalArgumentException("La aridad debe ser al menos 2, vino " + d);
        }
        this.d = d;
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si el monticulo esta vacio. O(1). */
    public boolean estaVacio() {
        return tamanio == 0;
    }

    /**
     * Devuelve el minimo SIN sacarlo.
     *
     * @throws IllegalStateException si el monticulo esta vacio.
     */
    public T verMinimo() {
        if (estaVacio()) {
            throw new IllegalStateException("El monticulo esta vacio");
        }
        return elemento(0);
    }

    /** Agrega un elemento: entra como ultima hoja y flota con padre (i - 1) / d. */
    public void encolar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se admiten valores null");
        }
        asegurarCapacidad();
        datos[tamanio] = valor;
        flotar(tamanio);
        tamanio++;
    }

    /** Saca y devuelve el minimo: ultima hoja a la raiz + hundir d-ario. */
    public T desencolarMinimo() {
        T minimo = verMinimo();
        tamanio--;
        datos[0] = datos[tamanio];
        datos[tamanio] = null;
        if (tamanio > 0) {
            hundir(0);
        }
        return minimo;
    }

    private void asegurarCapacidad() {
        if (tamanio == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            System.arraycopy(datos, 0, nuevo, 0, tamanio);
            datos = nuevo;
        }
    }

    /** Sube el elemento de i mientras sea menor que su padre (i - 1) / d. */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / d;
            if (elemento(i).compareTo(elemento(padre)) >= 0) {
                break;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /**
     * Baja el elemento de i eligiendo el MENOR entre sus HASTA d hijos
     * (indices d*i + 1 .. d*i + d, recortados por el tamanio).
     */
    private void hundir(int i) {
        while (true) {
            int primerHijo = d * i + 1;
            if (primerHijo >= tamanio) {
                break; // Es hoja.
            }
            // Buscamos el menor entre los hijos que existen.
            int menor = primerHijo;
            int ultimoHijo = Math.min(primerHijo + d - 1, tamanio - 1);
            for (int hijo = primerHijo + 1; hijo <= ultimoHijo; hijo++) {
                if (elemento(hijo).compareTo(elemento(menor)) < 0) {
                    menor = hijo;
                }
            }
            if (elemento(menor).compareTo(elemento(i)) >= 0) {
                break; // Ya es menor o igual que todos sus hijos.
            }
            intercambiar(i, menor);
            i = menor;
        }
    }

    private void intercambiar(int i, int j) {
        Object aux = datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
    }

    @SuppressWarnings("unchecked")
    private T elemento(int i) {
        return (T) datos[i];
    }

    /** Demostracion: el mismo lote de valores con d = 3 sale igual de ordenado. */
    public static void main(String[] args) {
        MonticuloDario<Integer> monticulo = new MonticuloDario<>(3);
        for (int valor : new int[] {42, 17, 99, 3, 25, 60, 8}) {
            monticulo.encolar(valor);
        }
        StringBuilder salida = new StringBuilder("Con d=3 desencolo: ");
        while (!monticulo.estaVacio()) {
            salida.append(monticulo.desencolarMinimo()).append(' ');
        }
        System.out.println(salida); // 3 8 17 25 42 60 99
    }
}

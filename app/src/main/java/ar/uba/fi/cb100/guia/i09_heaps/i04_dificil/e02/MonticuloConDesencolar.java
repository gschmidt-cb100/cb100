package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e02;

/**
 * Min-heap A MANO con desencolar (replica y extiende el e01, que solo insertaba).
 *
 * desencolarMinimo, O(log n):
 *  1. El minimo a devolver es la raiz (posicion 0).
 *  2. Movemos la ULTIMA HOJA a la raiz para tapar el hueco sin romper la forma.
 *  3. La hundimos: en cada paso comparamos con el MENOR de los dos hijos
 *     (2*i + 1 y 2*i + 2) e intercambiamos si ese hijo es mas chico.
 *
 * OJO con el bug clasico: si al hundir comparamos SOLO con el hijo izquierdo,
 * el heap queda roto cuando el menor es el derecho. Ejemplo: raiz 9 con hijos
 * 8 y 2. Lo correcto es bajar el 9 por el lado del 2; la version con bug lo
 * intercambia con el 8 y deja el 2 debajo de un valor mayor. El test de esta
 * clase caza exactamente ese error.
 */
public class MonticuloConDesencolar<T extends Comparable<T>> {

    private static final int CAPACIDAD_INICIAL = 8;

    /** Arreglo que guarda el arbol por niveles. */
    private Object[] datos;

    /** Cantidad de elementos guardados. */
    private int tamanio;

    public MonticuloConDesencolar() {
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

    /** Agrega un elemento: entra como ultima hoja y flota. O(log n). */
    public void encolar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se admiten valores null");
        }
        asegurarCapacidad();
        datos[tamanio] = valor;
        flotar(tamanio);
        tamanio++;
    }

    /**
     * Saca y devuelve el minimo: ultima hoja a la raiz + hundir. O(log n).
     *
     * @throws IllegalStateException si el monticulo esta vacio.
     */
    public T desencolarMinimo() {
        T minimo = verMinimo(); // Ya valida que no este vacio.
        tamanio--;
        datos[0] = datos[tamanio];
        datos[tamanio] = null; // Ayudamos al recolector de basura.
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

    /** Sube el elemento de i mientras sea menor que su padre (i - 1) / 2. */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (elemento(i).compareTo(elemento(padre)) >= 0) {
                break;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /**
     * Baja el elemento de i comparando SIEMPRE con el MENOR de sus hijos.
     * Elegir el menor es lo que garantiza que el que sube a la posicion i
     * quede por debajo (o igual) de todo su subarbol.
     */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < tamanio && elemento(izquierdo).compareTo(elemento(menor)) < 0) {
                menor = izquierdo;
            }
            if (derecho < tamanio && elemento(derecho).compareTo(elemento(menor)) < 0) {
                menor = derecho; // Sin esta comparacion aparece el "bug del un solo hijo".
            }
            if (menor == i) {
                break; // Ya es menor o igual que ambos hijos.
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

    /** Demostracion: encolar desordenado y desencolar entrega los valores ordenados. */
    public static void main(String[] args) {
        MonticuloConDesencolar<Integer> monticulo = new MonticuloConDesencolar<>();
        for (int valor : new int[] {42, 17, 99, 3, 25}) {
            monticulo.encolar(valor);
        }
        StringBuilder salida = new StringBuilder("Desencolando: ");
        while (!monticulo.estaVacio()) {
            salida.append(monticulo.desencolarMinimo()).append(' ');
        }
        System.out.println(salida); // 3 17 25 42 99
    }
}
